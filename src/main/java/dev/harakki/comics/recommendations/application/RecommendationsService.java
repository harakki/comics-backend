package dev.harakki.comics.recommendations.application;

import dev.harakki.comics.analytics.api.InteractionType;
import dev.harakki.comics.analytics.api.UserInteractionApi;
import dev.harakki.comics.catalog.api.TitlePublicQueryApi;
import dev.harakki.comics.catalog.api.TitleShortInfo;
import dev.harakki.comics.recommendations.dto.PersonalRecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationsService {

    private static final int MAX_RECOMMENDATIONS_LIMIT = 20;
    private static final int RECENT_HISTORY_LIMIT = 20;
    private static final int MIN_RECOMMENDATIONS_REQUIRED = 3;
    private static final double RECENT_ACTIVITY_BONUS = 0.5;
    private static final List<FallbackWindow> POPULARITY_WINDOWS = List.of(
            new FallbackWindow(Duration.ofDays(7), "popular_7d"),
            new FallbackWindow(Duration.ofDays(30), "popular_30d"),
            new FallbackWindow(Duration.ofDays(90), "popular_90d")
    );

    private final TitlePublicQueryApi titlePublicQueryApi;
    private final UserInteractionApi userInteractionRepository;

    public List<PersonalRecommendationResponse> getPersonalRecommendations(UUID userId, int limit) {
        var normalizedLimit = Math.clamp(limit, 1, MAX_RECOMMENDATIONS_LIMIT);
        var viewedTitleIds = getRecentViewedTitleIds(userId);
        var excludedTitleIds = new LinkedHashSet<>(viewedTitleIds);
        var personalizedRecommendations = getPersonalizedRecommendations(viewedTitleIds, normalizedLimit);
        var result = new ArrayList<>(personalizedRecommendations);

        excludedTitleIds.addAll(result.stream()
                .map(PersonalRecommendationResponse::titleId)
                .toList());

        if (result.size() < normalizedLimit) {
            result.addAll(getPopularFallbackRecommendations(
                    excludedTitleIds,
                    normalizedLimit - result.size()
            ));
        }

        // Keep recommendations hidden on cold-start when we cannot build a minimally useful list.
        if (personalizedRecommendations.isEmpty()
                && result.size() < Math.min(normalizedLimit, MIN_RECOMMENDATIONS_REQUIRED)) {
            return Collections.emptyList();
        }

        return result;
    }

    private List<PersonalRecommendationResponse> getPersonalizedRecommendations(List<UUID> viewedTitleIds, int limit) {
        if (viewedTitleIds.isEmpty()) {
            return Collections.emptyList();
        }

        var tagsByViewedTitleId = titlePublicQueryApi.getTitleTagIdsByIds(viewedTitleIds);
        var seedTitleIds = viewedTitleIds.stream()
                .filter(titleId -> !tagsByViewedTitleId.getOrDefault(titleId, Set.of()).isEmpty())
                .toList();

        if (seedTitleIds.isEmpty()) {
            return Collections.emptyList();
        }

        var excludedTitleIds = new LinkedHashSet<>(viewedTitleIds);
        var candidateIds = userInteractionRepository.findRecommendedTitleIdsBySharedTags(
                seedTitleIds,
                new ArrayList<>(excludedTitleIds),
                limit * 3
        );

        if (candidateIds.isEmpty()) {
            return Collections.emptyList();
        }

        var seedTagIds = seedTitleIds.stream()
                .flatMap(titleId -> tagsByViewedTitleId.getOrDefault(titleId, Set.of()).stream())
                .collect(Collectors.toSet());
        var mostRecentTagIds = tagsByViewedTitleId.getOrDefault(seedTitleIds.getFirst(), Set.of());
        var candidateTagIds = titlePublicQueryApi.getTitleTagIdsByIds(candidateIds);

        var scoredCandidates = candidateIds.stream()
                .filter(candidateId -> !excludedTitleIds.contains(candidateId))
                .map(candidateId -> {
                    var tags = candidateTagIds.getOrDefault(candidateId, Set.of());
                    var overlap = countOverlap(tags, seedTagIds);

                    if (overlap == 0) {
                        return null;
                    }

                    var hasRecentOverlap = hasOverlap(tags, mostRecentTagIds);
                    var score = overlap + (hasRecentOverlap ? RECENT_ACTIVITY_BONUS : 0.0);
                    return new ScoredRecommendation(candidateId, score, "tag_overlap");
                })
                .filter(Objects::nonNull)
                .sorted((left, right) -> {
                    var scoreComparison = Double.compare(right.score(), left.score());
                    if (scoreComparison != 0) {
                        return scoreComparison;
                    }
                    return left.titleId().compareTo(right.titleId());
                })
                .limit(limit)
                .toList();

        if (scoredCandidates.isEmpty()) {
            return Collections.emptyList();
        }

        var titleInfoById = titlePublicQueryApi.getTitleShortInfoByIds(scoredCandidates.stream()
                        .map(ScoredRecommendation::titleId)
                        .toList())
                .stream()
                .collect(Collectors.toMap(TitleShortInfo::id, titleInfo -> titleInfo));

        return scoredCandidates.stream()
                .map(scored -> {
                    var titleInfo = titleInfoById.get(scored.titleId());
                    if (titleInfo == null) {
                        return null;
                    }
                    return new PersonalRecommendationResponse(
                            titleInfo.id(),
                            titleInfo.name(),
                            titleInfo.slug(),
                            scored.score(),
                            scored.reason()
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private List<PersonalRecommendationResponse> getPopularFallbackRecommendations(Set<UUID> excludedTitleIds, int limit) {
        if (limit <= 0) {
            return List.of();
        }

        var fallbackByTitle = new LinkedHashMap<UUID, ScoredRecommendation>();

        for (var window : POPULARITY_WINDOWS) {
            var remaining = limit - fallbackByTitle.size();
            if (remaining <= 0) {
                break;
            }

            var topViews = userInteractionRepository.findTopViewedTitlesSince(
                    Instant.now().minus(window.duration()),
                    remaining * 3
            );

            for (var topView : topViews) {
                var titleId = topView.getTitleId();
                if (excludedTitleIds.contains(titleId) || fallbackByTitle.containsKey(titleId)) {
                    continue;
                }

                fallbackByTitle.put(
                        titleId,
                        new ScoredRecommendation(titleId, topView.getWeeklyViews().doubleValue(), window.reason())
                );

                if (fallbackByTitle.size() >= limit) {
                    break;
                }
            }
        }

        if (fallbackByTitle.isEmpty()) {
            return List.of();
        }

        var titleInfoById = titlePublicQueryApi.getTitleShortInfoByIds(fallbackByTitle.keySet()).stream()
                .collect(Collectors.toMap(TitleShortInfo::id, titleInfo -> titleInfo));

        return fallbackByTitle.values().stream()
                .map(scored -> {
                    var titleInfo = titleInfoById.get(scored.titleId());
                    if (titleInfo == null) {
                        return null;
                    }

                    return new PersonalRecommendationResponse(
                            titleInfo.id(),
                            titleInfo.name(),
                            titleInfo.slug(),
                            scored.score(),
                            scored.reason()
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private List<UUID> getRecentViewedTitleIds(UUID userId) {
        var recentTargets = userInteractionRepository.findRecentTargetIds(
                userId,
                InteractionType.TITLE_VIEWED,
                PageRequest.of(0, RECENT_HISTORY_LIMIT * 3)
        );

        return new ArrayList<>(new LinkedHashSet<>(recentTargets))
                .stream()
                .limit(RECENT_HISTORY_LIMIT)
                .toList();
    }

    private static int countOverlap(Set<UUID> left, Set<UUID> right) {
        var overlap = 0;
        for (var tagId : left) {
            if (right.contains(tagId)) {
                overlap++;
            }
        }
        return overlap;
    }

    private static boolean hasOverlap(Set<UUID> left, Set<UUID> right) {
        for (var tagId : left) {
            if (right.contains(tagId)) {
                return true;
            }
        }
        return false;
    }

    private record ScoredRecommendation(UUID titleId, double score, String reason) {
    }

    private record FallbackWindow(Duration duration, String reason) {
    }

}
