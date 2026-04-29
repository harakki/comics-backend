package dev.harakki.comics.recommendations.application;

import dev.harakki.comics.analytics.api.InteractionType;
import dev.harakki.comics.analytics.api.UserInteractionApi;
import dev.harakki.comics.catalog.api.TitlePublicQueryApi;
import dev.harakki.comics.catalog.api.TitleShortInfo;
import dev.harakki.comics.recommendations.dto.PersonalRecommendationResponse;
import dev.harakki.comics.recommendations.repository.SimilarTitlesRepository;
import dev.harakki.comics.recommendations.repository.UserTagProfileRepository;
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

    private static final int MIN_RECOMMENDATIONS_REQUIRED = 3;
    private static final int MAX_RECOMMENDATIONS = 20;

    private static final int MAX_FETCH_SIZE = 60;
    private static final int RECENT_VIEWED_TITLES_AMOUNT = 20;

    private static final int CANDIDATE_MULTIPLIER = 3;

    private static final double ALPHA_CONTENT = 0.75;
    private static final double BETA_COLLAB = 0.25;

    private static final List<Duration> FALLBACK_WINDOWS = List.of(
            Duration.ofDays(7),
            Duration.ofDays(30),
            Duration.ofDays(90)
    );

    private final SimilarTitlesRepository similarTitlesRepository;
    private final UserTagProfileRepository profileRepository;
    private final UserInteractionApi userInteractionApi;
    private final TitlePublicQueryApi titleApi;

    public List<PersonalRecommendationResponse> getPersonalRecommendations(UUID userId, int limit) {
        int normalizedLimit = Math.clamp(limit, 1, MAX_RECOMMENDATIONS);

        var excluded = buildExclusionSet(userId);
        var likedTitleIds = getLikedTitleIds(userId);

        var candidates = mergeCandidates(
                fetchContentCandidates(userId, excluded, normalizedLimit),
                fetchCollabCandidates(userId, likedTitleIds, excluded, normalizedLimit)
        );

        // Отражает, есть ли у пользователя оценки
        boolean hasPersonalized = !candidates.isEmpty();

        if (candidates.size() < normalizedLimit) {
            candidates.addAll(fetchPopularFallback(excluded, normalizedLimit - candidates.size()));
        }

        if (!hasPersonalized && candidates.size() < Math.min(normalizedLimit, MIN_RECOMMENDATIONS_REQUIRED)) {
            return Collections.emptyList();
        }

        return enrich(candidates, normalizedLimit);
    }

    private Set<UUID> buildExclusionSet(UUID userId) {
        var excluded = new HashSet<UUID>();
        excluded.addAll(getRecentViewedIds(userId));
        excluded.addAll(getDislikedIds(userId));
        return excluded;
    }

    private List<UUID> getRecentViewedIds(UUID userId) {
        return userInteractionApi
                .findRecentTargetIds(userId, InteractionType.TITLE_VIEWED, PageRequest.of(0, MAX_FETCH_SIZE))
                .stream()
                .distinct()
                .limit(RECENT_VIEWED_TITLES_AMOUNT)
                .toList();
    }

    private Set<UUID> getDislikedIds(UUID userId) {
        return userInteractionApi.findRecentVotesByUser(userId, MAX_FETCH_SIZE)
                .stream()
                .filter(v -> "DISLIKE".equalsIgnoreCase(v.getVoteType()))
                .map(UserInteractionApi.UserVoteProjection::getTargetId)
                .collect(Collectors.toSet());
    }

    private List<UUID> getLikedTitleIds(UUID userId) {
        return userInteractionApi.findRecentVotesByUser(userId, MAX_FETCH_SIZE)
                .stream()
                .filter(v -> "LIKE".equalsIgnoreCase(v.getVoteType()))
                .map(UserInteractionApi.UserVoteProjection::getTargetId)
                .toList();
    }

    private List<ScoredCandidate> fetchContentCandidates(UUID userId,
                                                         Set<UUID> excluded,
                                                         int limit) {
        // ScoredCandidate(titleId=11, contentScore=1.0, collabScore=0.0, reason="content")
        return profileRepository
                .findContentCandidates(userId, new ArrayList<>(excluded), limit * CANDIDATE_MULTIPLIER)
                .stream()
                .map(p -> new ScoredCandidate(p.getTitleId(), p.getScore(), 0.0, p.getReason()))
                .toList();
    }

    private List<ScoredCandidate> fetchCollabCandidates(UUID userId,
                                                        List<UUID> likedTitleIds,
                                                        Set<UUID> excluded,
                                                        int limit) {
        if (likedTitleIds.isEmpty()) {
            return Collections.emptyList();
        }

        var raw = userInteractionApi.findCollabCandidates(userId, likedTitleIds, new ArrayList<>(excluded), limit * CANDIDATE_MULTIPLIER);
        // max = 1.0, остальные делятся на него, чтобы нормализовать в диапазон [0, 1]
        double max = raw
                .stream()
                .mapToDouble(UserInteractionApi.ScoredTitleProjection::getScore)
                .max()
                .orElse(1.0);

        // ScoredCandidate(titleId=21, contentScore=0.0, collabScore=1.0, reason="collab")
        return raw
                .stream()
                .map(p -> new ScoredCandidate(p.getTitleId(), 0.0, p.getScore() / max, p.getReason()))
                .toList();
    }

    /**
     * Объединение content и collab кандидатов с учетом их скорингов и источников.
     * <p>
     * Если тайтл присутствует в обоих списках, то он помечается как "hybrid" и его скоринги комбинируются по формуле:
     * {@code final_score = α * content_score + β * collab_score}, где α и β - веса для каждого сигнала
     */
    private ArrayList<ScoredCandidate> mergeCandidates(List<ScoredCandidate> content,
                                                       List<ScoredCandidate> collab) {
        Map<UUID, ScoredCandidate> merged = new LinkedHashMap<>();

        // title=11 -> (contentScore=1.23, collabScore=0.0, reason="content")
        content.forEach(c -> merged.put(c.titleId(), c));

        // title=11 -> (contentScore=1.23, collabScore=0.9, reason="hybrid")
        collab.forEach(c -> merged.merge(c.titleId(), c, (existing, incoming) ->
                new ScoredCandidate(existing.titleId(), existing.contentScore(), incoming.collabScore(), "hybrid")));

        return merged.values()
                .stream()
                .map(c -> c.withFinalScore(ALPHA_CONTENT * c.contentScore() + BETA_COLLAB * c.collabScore()))
                .sorted(Comparator.comparingDouble(ScoredCandidate::finalScore).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<ScoredCandidate> fetchPopularFallback(Set<UUID> excluded, int limit) {
        for (var window : FALLBACK_WINDOWS) { // // 7d -> 30d -> 90d
            var result = userInteractionApi.findPopularSince(Instant.now().minus(window), new ArrayList<>(excluded), limit);
            if (!result.isEmpty()) {
                return result
                        .stream()
                        .map(p -> new ScoredCandidate(p.getTitleId(), 0.0, 0.0, p.getReason()))
                        .collect(Collectors.toCollection(ArrayList::new));
            }
        }
        return Collections.emptyList();
    }

    private List<PersonalRecommendationResponse> enrich(List<ScoredCandidate> candidates, int limit) {
        var ids = candidates
                .stream()
                .map(ScoredCandidate::titleId)
                .collect(Collectors.toSet());

        var infoById = titleApi.getTitleShortInfoByIds(ids)
                .stream()
                .collect(Collectors.toMap(TitleShortInfo::id, Function.identity()));

        return candidates.stream()
                .limit(limit)
                .map(c -> {
                    var info = infoById.get(c.titleId());
                    return (info == null) ? null : new PersonalRecommendationResponse(
                            info.id(), info.name(), info.mainCoverMediaId(),
                            info.slug(), c.finalScore(), c.reason()
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private record ScoredCandidate(
            UUID titleId,
            double contentScore,
            double collabScore,
            String reason,
            double finalScore
    ) {

        ScoredCandidate(UUID titleId, double contentScore, double collabScore, String reason) {
            this(titleId, contentScore, collabScore, reason, 0.0);
        }

        ScoredCandidate withFinalScore(double finalScore) {
            return new ScoredCandidate(titleId, contentScore, collabScore, reason, finalScore);
        }

    }

    public List<PersonalRecommendationResponse> getSimilarTitles(UUID titleId, int limit) {
        var candidates = similarTitlesRepository
                .findSimilarTitles(titleId, List.of(titleId), limit * CANDIDATE_MULTIPLIER)
                .stream()
                .map(p -> new ScoredCandidate(p.getTitleId(), p.getScore(), 0.0, p.getReason(), p.getScore()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (candidates.isEmpty()) {
            return Collections.emptyList();
        }

        return enrich(candidates, limit);
    }

}
