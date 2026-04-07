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
import java.util.function.Function;
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

    private static int countOverlap(Set<UUID> left, Set<UUID> right) {
        Set<UUID> smaller = left.size() < right.size() ? left : right;
        Set<UUID> larger = left.size() < right.size() ? right : left;

        int overlap = 0;
        for (var tagId : smaller) {
            if (larger.contains(tagId)) {
                overlap++;
            }
        }
        return overlap;
    }

    public List<PersonalRecommendationResponse> getPersonalRecommendations(UUID userId, int limit) {
        var normalizedLimit = Math.clamp(limit, 1, MAX_RECOMMENDATIONS_LIMIT);

        var viewedTitleIds = getRecentViewedTitleIds(userId);
        var excludedTitleIds = new HashSet<>(viewedTitleIds);

        // Получить персонализированные рекомендации
        List<ScoredRecommendation> recommendations = new ArrayList<>(
                getPersonalizedRecommendations(viewedTitleIds, excludedTitleIds, normalizedLimit)
        );

        boolean hasPersonalized = !recommendations.isEmpty();

        // Если не хватает рекомендаций, то добить популярными
        if (recommendations.size() < normalizedLimit) {
            // Добавить персонализированные в исключения, чтобы не дублировать в фоллбеках
            recommendations.forEach(r -> excludedTitleIds.add(r.titleId()));

            recommendations.addAll(getPopularFallbackRecommendations(
                    excludedTitleIds,
                    normalizedLimit - recommendations.size()
            ));
        }

        // Проверка на холодный старт
        if (!hasPersonalized && recommendations.size() < Math.min(normalizedLimit, MIN_RECOMMENDATIONS_REQUIRED)) {
            return Collections.emptyList();
        }

        // Наполнить данными о тайтлах
        return enrichWithTitleInfo(recommendations);
    }

    private List<ScoredRecommendation> getPersonalizedRecommendations(List<UUID> viewedTitleIds,
                                                                      Set<UUID> excludedTitleIds,
                                                                      int limit) {
        if (viewedTitleIds.isEmpty()) {
            return Collections.emptyList();
        }

        var tagIdsOfViewedTitles = titlePublicQueryApi.getTitleTagIdsByIds(viewedTitleIds);
        var seedTitleIds = viewedTitleIds.stream()
                .filter(titleId -> !tagIdsOfViewedTitles.getOrDefault(titleId, Collections.emptySet()).isEmpty())
                .toList();

        if (seedTitleIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Найди тайтлы с похожими тегами, но НЕ из черного списка
        var candidateIds = userInteractionRepository.findRecommendedTitleIdsBySharedTags(
                seedTitleIds,
                new ArrayList<>(excludedTitleIds),
                limit * 3
        );

        if (candidateIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Все теги, которые нравятся пользователю
        var seedTagIds = seedTitleIds.stream()
                .flatMap(titleId -> tagIdsOfViewedTitles.getOrDefault(titleId, Collections.emptySet()).stream())
                .collect(Collectors.toSet());

        var mostRecentTagIds = tagIdsOfViewedTitles.getOrDefault(seedTitleIds.getFirst(), Collections.emptySet());
        var candidateTagIds = titlePublicQueryApi.getTitleTagIdsByIds(candidateIds);

        return candidateIds.stream()
                .filter(candidateId -> !excludedTitleIds.contains(candidateId))
                .map(candidateId -> scoreCandidate(
                                candidateId,
                                candidateTagIds.getOrDefault(candidateId, Collections.emptySet()),
                                seedTagIds,
                                mostRecentTagIds
                        )
                )
                .filter(Objects::nonNull)
                // Сортировка по максимальному баллу и ID
                .sorted(Comparator.comparingDouble(ScoredRecommendation::score).reversed()
                        .thenComparing(ScoredRecommendation::titleId))
                .limit(limit)
                .toList();
    }

    private ScoredRecommendation scoreCandidate(UUID candidateId,
                                                Set<UUID> candidateTags,
                                                Set<UUID> seedTags,
                                                Set<UUID> recentTags) {
        // Количество общих тегов у кандидата и всей истории пользователя
        int overlap = countOverlap(candidateTags, seedTags);
        if (overlap == 0) {
            return null;
        }

        boolean hasRecentOverlap = !Collections.disjoint(candidateTags, recentTags);
        // 1 общий тег = 1 балл. Плюс бонус 0.5, если похоже на последнее прочитанное
        double score = overlap + (hasRecentOverlap ? RECENT_ACTIVITY_BONUS : 0.0);

        return new ScoredRecommendation(candidateId, score, "tag_overlap");
    }

    private List<ScoredRecommendation> getPopularFallbackRecommendations(Set<UUID> excludedTitleIds, int limit) {
        if (limit <= 0) {
            return Collections.emptyList();
        }

        List<ScoredRecommendation> fallbacks = new ArrayList<>();
        Instant now = Instant.now();

        for (var window : POPULARITY_WINDOWS) {
            int remaining = limit - fallbacks.size();
            if (remaining <= 0) {
                break;
            }

            var topViews = userInteractionRepository.findTopViewedTitlesSince(
                    now.minus(window.duration()),
                    remaining * 3
            );

            for (var topView : topViews) {
                UUID titleId = topView.getTitleId();
                // Мутировать excludedTitleIds напрямую, чтобы избежать дублей между окнами
                if (excludedTitleIds.add(titleId)) {
                    fallbacks.add(new ScoredRecommendation(titleId, topView.getWeeklyViews().doubleValue(), window.reason()));
                    if (fallbacks.size() >= limit) {
                        break;
                    }
                }
            }
        }

        return fallbacks;
    }

    private List<PersonalRecommendationResponse> enrichWithTitleInfo(List<ScoredRecommendation> recommendations) {
        if (recommendations.isEmpty()) {
            return Collections.emptyList();
        }

        Set<UUID> titleIdsToEnrich = recommendations.stream()
                .map(ScoredRecommendation::titleId)
                .collect(Collectors.toSet());

        Map<UUID, TitleShortInfo> titleInfoById = titlePublicQueryApi.getTitleShortInfoByIds(titleIdsToEnrich).stream()
                .collect(Collectors.toMap(TitleShortInfo::id, Function.identity()));

        return recommendations.stream()
                .map(scored -> {
                    var info = titleInfoById.get(scored.titleId());
                    return info != null ? new PersonalRecommendationResponse(
                            info.id(), info.name(), info.mainCoverMediaId(), info.slug(), scored.score(), scored.reason()
                    ) : null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private List<UUID> getRecentViewedTitleIds(UUID userId) {
        return userInteractionRepository.findRecentTargetIds(
                        userId,
                        InteractionType.TITLE_VIEWED,
                        PageRequest.of(0, RECENT_HISTORY_LIMIT * 3)
                ).stream()
                .distinct()
                .limit(RECENT_HISTORY_LIMIT)
                .toList();
    }

    private record ScoredRecommendation(UUID titleId, double score, String reason) {
    }

    private record FallbackWindow(Duration duration, String reason) {
    }

}
