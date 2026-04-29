package dev.harakki.comics.recommendations.application;

import dev.harakki.comics.analytics.api.InteractionType;
import dev.harakki.comics.analytics.api.UserInteractionApi;
import dev.harakki.comics.catalog.api.TitlePublicQueryApi;
import dev.harakki.comics.catalog.api.TitleShortInfo;
import dev.harakki.comics.recommendations.dto.PersonalRecommendationResponse;
import dev.harakki.comics.recommendations.repository.SimilarTitlesRepository;
import dev.harakki.comics.recommendations.repository.UserTagProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationsServiceTest {

    @Mock
    private SimilarTitlesRepository similarTitlesRepository;

    @Mock
    private UserTagProfileRepository profileRepository;

    @Mock
    private UserInteractionApi userInteractionApi;

    @Mock
    private TitlePublicQueryApi titlePublicQueryApi;

    @InjectMocks
    private RecommendationsService recommendationsService;

    @Test
    void getPersonalRecommendations_whenNoSignals_returnsPopularFallback() {
        var userId = UUID.randomUUID();
        var top7dA = UUID.randomUUID();
        var top7dB = UUID.randomUUID();
        var top30d = UUID.randomUUID();

        when(userInteractionApi.findRecentTargetIds(eq(userId), eq(InteractionType.TITLE_VIEWED), any()))
                .thenReturn(List.of());
        when(userInteractionApi.findRecentVotesByUser(eq(userId), anyInt()))
                .thenReturn(List.of());
        when(profileRepository.findContentCandidates(eq(userId), anyList(), anyInt()))
                .thenReturn(List.of());
        when(userInteractionApi.findPopularSince(any(), anyList(), anyInt()))
                .thenReturn(List.of(
                        scored(top7dA, 42.0, "popular_7d"),
                        scored(top7dB, 40.0, "popular_7d"),
                        scored(top30d, 35.0, "popular_30d")
                ));
        when(titlePublicQueryApi.getTitleShortInfoByIds(any()))
                .thenAnswer(invocation -> {
                    Collection<UUID> ids = invocation.getArgument(0);
                    return ids.stream().map(id -> new TitleShortInfo(id, "Title " + id, null, id.toString())).toList();
                });

        var result = recommendationsService.getPersonalRecommendations(userId, 3);

        assertThat(result).hasSize(3);
        assertThat(result).extracting(PersonalRecommendationResponse::titleId)
                .containsExactly(top7dA, top7dB, top30d);
        assertThat(result).extracting(PersonalRecommendationResponse::reason)
                .containsExactly("popular_7d", "popular_7d", "popular_30d");
    }

    @Test
    void getPersonalRecommendations_whenFallbackIsTooSmall_returnsEmptyList() {
        var userId = UUID.randomUUID();

        when(userInteractionApi.findRecentTargetIds(eq(userId), eq(InteractionType.TITLE_VIEWED), any()))
                .thenReturn(List.of());
        when(userInteractionApi.findRecentVotesByUser(eq(userId), anyInt()))
                .thenReturn(List.of());
        when(profileRepository.findContentCandidates(eq(userId), anyList(), anyInt()))
                .thenReturn(List.of());
        when(userInteractionApi.findPopularSince(any(), anyList(), anyInt()))
                .thenReturn(List.of(scored(UUID.randomUUID(), 10.0, "popular_7d")));

        var result = recommendationsService.getPersonalRecommendations(userId, 10);

        assertThat(result).isEmpty();
    }

    @Test
    void getPersonalRecommendations_whenContentAndCollabOverlap_mergesToHybrid() {
        var userId = UUID.randomUUID();
        var liked = UUID.randomUUID();
        var hybrid = UUID.randomUUID();
        var contentOnly = UUID.randomUUID();
        var collabOnly = UUID.randomUUID();

        when(userInteractionApi.findRecentTargetIds(eq(userId), eq(InteractionType.TITLE_VIEWED), any()))
                .thenReturn(List.of());
        when(userInteractionApi.findRecentVotesByUser(eq(userId), anyInt()))
                .thenReturn(List.of(vote(liked, "LIKE", java.time.Instant.now().minusSeconds(3600))));

        when(profileRepository.findContentCandidates(eq(userId), anyList(), anyInt()))
                .thenReturn(List.of(
                        scored(hybrid, 3.0, "content"),
                        scored(contentOnly, 2.0, "content")
                ));
        when(userInteractionApi.findCollabCandidates(eq(userId), eq(List.of(liked)), anyList(), anyInt()))
                .thenReturn(List.of(
                        scored(hybrid, 10.0, "collab"),
                        scored(collabOnly, 4.0, "collab")
                ));
        when(titlePublicQueryApi.getTitleShortInfoByIds(any())).thenAnswer(invocation -> {
            Collection<UUID> ids = invocation.getArgument(0);
            return ids.stream().map(id -> new TitleShortInfo(id, id.toString(), null, id.toString())).toList();
        });

        var result = recommendationsService.getPersonalRecommendations(userId, 3);

        assertThat(result).hasSize(3);
        assertThat(result).extracting(PersonalRecommendationResponse::titleId)
                .containsExactly(hybrid, contentOnly, collabOnly);
        assertThat(result.getFirst().reason()).isEqualTo("hybrid");
        assertThat(result).extracting(PersonalRecommendationResponse::reason)
                .containsExactly("hybrid", "content", "collab");
        verify(userInteractionApi, never()).findPopularSince(any(), anyList(), anyInt());
    }

    @Test
    void getPersonalRecommendations_whenViewedAndDislikedExist_passesThemAsExclusions() {
        var userId = UUID.randomUUID();
        var viewed1 = UUID.randomUUID();
        var viewed2 = UUID.randomUUID();
        var disliked = UUID.randomUUID();
        var candidate = UUID.randomUUID();

        when(userInteractionApi.findRecentTargetIds(eq(userId), eq(InteractionType.TITLE_VIEWED), any()))
                .thenReturn(List.of(viewed1, viewed2, viewed1));
        when(userInteractionApi.findRecentVotesByUser(eq(userId), anyInt()))
                .thenReturn(List.of(vote(disliked, "DISLIKE", java.time.Instant.now().minusSeconds(3600))));

        when(profileRepository.findContentCandidates(eq(userId), anyList(), anyInt()))
                .thenAnswer(invocation -> {
                    List<UUID> excluded = invocation.getArgument(1);
                    assertThat(excluded).contains(viewed1, viewed2, disliked);
                    return List.of(scored(candidate, 5.0, "content"));
                });
        when(titlePublicQueryApi.getTitleShortInfoByIds(any())).thenAnswer(invocation -> {
            Collection<UUID> ids = invocation.getArgument(0);
            return ids.stream().map(id -> new TitleShortInfo(id, id.toString(), null, id.toString())).toList();
        });

        var result = recommendationsService.getPersonalRecommendations(userId, 1);

        assertThat(result).extracting(PersonalRecommendationResponse::titleId)
                .containsExactly(candidate);
    }

    @Test
    void getSimilarTitles_returnsEnrichedAndSortedTitles() {
        var titleId = UUID.randomUUID();
        var top = UUID.randomUUID();
        var second = UUID.randomUUID();

        when(similarTitlesRepository.findSimilarTitles(eq(titleId), eq(List.of(titleId)), anyInt()))
                .thenReturn(List.of(
                        scored(top, 12.0, "similar"),
                        scored(second, 7.0, "similar")
                ));
        when(titlePublicQueryApi.getTitleShortInfoByIds(any())).thenAnswer(invocation -> {
            Collection<UUID> ids = invocation.getArgument(0);
            return ids.stream().map(id -> new TitleShortInfo(id, "Title " + id, null, id.toString())).toList();
        });

        var result = recommendationsService.getSimilarTitles(titleId, 2);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(PersonalRecommendationResponse::titleId)
                .containsExactly(top, second);
        assertThat(result).extracting(PersonalRecommendationResponse::reason)
                .containsExactly("similar", "similar");
    }

    private static UserInteractionApi.UserVoteProjection vote(UUID targetId, String voteType, java.time.Instant at) {
        return new UserInteractionApi.UserVoteProjection() {
            @Override
            public java.util.UUID getTargetId() {
                return targetId;
            }

            @Override
            public String getVoteType() {
                return voteType;
            }

            @Override
            public java.time.Instant getOccurredAt() {
                return at;
            }
        };
    }

    private static UserInteractionApi.ScoredTitleProjection scored(UUID titleId, double score, String reason) {
        return new UserInteractionApi.ScoredTitleProjection() {
            @Override
            public UUID getTitleId() {
                return titleId;
            }

            @Override
            public double getScore() {
                return score;
            }

            @Override
            public String getReason() {
                return reason;
            }
        };
    }

}
