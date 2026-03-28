package dev.harakki.comics.recommendations.application;

import dev.harakki.comics.analytics.api.InteractionType;
import dev.harakki.comics.analytics.api.UserInteractionApi;
import dev.harakki.comics.catalog.api.TitlePublicQueryApi;
import dev.harakki.comics.catalog.api.TitleShortInfo;
import dev.harakki.comics.recommendations.dto.PersonalRecommendationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationsServiceTest {

    @Mock
    private UserInteractionApi userInteractionApi;

    @Mock
    private TitlePublicQueryApi titlePublicQueryApi;

    @InjectMocks
    private RecommendationsService recommendationsService;

    @Test
    void getPersonalRecommendations_whenNoHistory_returnsCascadedPopularFallback() {
        var userId = UUID.randomUUID();
        var top7dA = UUID.randomUUID();
        var top7dB = UUID.randomUUID();
        var top30d = UUID.randomUUID();
        var allTitles = Map.of(
                top7dA, new TitleShortInfo(top7dA, "Top 7d A", "top-7d-a"),
                top7dB, new TitleShortInfo(top7dB, "Top 7d B", "top-7d-b"),
                top30d, new TitleShortInfo(top30d, "Top 30d", "top-30d")
        );

        when(userInteractionApi.findRecentTargetIds(org.mockito.ArgumentMatchers.eq(userId), org.mockito.ArgumentMatchers.eq(InteractionType.TITLE_VIEWED), any()))
                .thenReturn(List.of());
        when(userInteractionApi.findTopViewedTitlesSince(any(), anyInt()))
                .thenReturn(List.of(topViewed(top7dA, 42L), topViewed(top7dB, 40L)))
                .thenReturn(List.of(topViewed(top7dA, 41L), topViewed(top30d, 35L)))
                .thenReturn(List.of());
        when(titlePublicQueryApi.getTitleShortInfoByIds(any()))
                .thenAnswer(invocation -> {
                    Collection<UUID> ids = invocation.getArgument(0);
                    return ids.stream().map(allTitles::get).toList();
                });

        var result = recommendationsService.getPersonalRecommendations(userId, 3);

        assertThat(result).hasSize(3);
        assertThat(result).extracting(PersonalRecommendationResponse::titleId)
                .containsExactly(top7dA, top7dB, top30d);
        assertThat(result).extracting(PersonalRecommendationResponse::reason)
                .containsExactly("popular_7d", "popular_7d", "popular_30d");
    }

    @Test
    void getPersonalRecommendations_whenNotEnoughData_returnsEmptyList() {
        var userId = UUID.randomUUID();
        var onlyTitle = UUID.randomUUID();

        when(userInteractionApi.findRecentTargetIds(org.mockito.ArgumentMatchers.eq(userId), org.mockito.ArgumentMatchers.eq(InteractionType.TITLE_VIEWED), any()))
                .thenReturn(List.of());
        when(userInteractionApi.findTopViewedTitlesSince(any(), anyInt()))
                .thenReturn(List.of(topViewed(onlyTitle, 10L)))
                .thenReturn(List.of())
                .thenReturn(List.of());
        when(titlePublicQueryApi.getTitleShortInfoByIds(any()))
                .thenAnswer(invocation -> {
                    Collection<UUID> ids = invocation.getArgument(0);
                    if (ids.contains(onlyTitle)) {
                        return List.of(new TitleShortInfo(onlyTitle, "Only", "only"));
                    }
                    return List.of();
                });

        var result = recommendationsService.getPersonalRecommendations(userId, 10);

        assertThat(result).isEmpty();
    }

    @Test
    void getPersonalRecommendations_whenHistoryExists_isDeterministicAndSkipsViewed() {
        var userId = UUID.randomUUID();
        var viewedOld = UUID.fromString("00000000-0000-0000-0000-000000000100");
        var viewedRecent = UUID.fromString("00000000-0000-0000-0000-000000000101");
        var candidateHighScore = UUID.fromString("00000000-0000-0000-0000-000000000200");
        var candidateTieA = UUID.fromString("00000000-0000-0000-0000-000000000201");
        var candidateTieB = UUID.fromString("00000000-0000-0000-0000-000000000202");
        var tagA = UUID.fromString("00000000-0000-0000-0000-000000000301");
        var tagB = UUID.fromString("00000000-0000-0000-0000-000000000302");

        when(userInteractionApi.findRecentTargetIds(org.mockito.ArgumentMatchers.eq(userId), org.mockito.ArgumentMatchers.eq(InteractionType.TITLE_VIEWED), any()))
                .thenReturn(List.of(viewedRecent, viewedOld, viewedRecent));
        when(titlePublicQueryApi.getTitleTagIdsByIds(List.of(viewedRecent, viewedOld))).thenReturn(Map.of(
                viewedRecent, java.util.Set.of(tagA),
                viewedOld, java.util.Set.of(tagB)
        ));
        when(userInteractionApi.findRecommendedTitleIdsBySharedTags(
                List.of(viewedRecent, viewedOld),
                List.of(viewedRecent, viewedOld),
                9
        )).thenReturn(List.of(candidateTieB, viewedRecent, candidateHighScore, candidateTieA));
        when(titlePublicQueryApi.getTitleTagIdsByIds(List.of(candidateTieB, viewedRecent, candidateHighScore, candidateTieA))).thenReturn(Map.of(
                candidateHighScore, java.util.Set.of(tagA, tagB),
                candidateTieA, java.util.Set.of(tagB),
                candidateTieB, java.util.Set.of(tagB)
        ));
        when(titlePublicQueryApi.getTitleShortInfoByIds(List.of(candidateHighScore, candidateTieA, candidateTieB))).thenReturn(List.of(
                new TitleShortInfo(candidateHighScore, "High", "high"),
                new TitleShortInfo(candidateTieA, "Tie A", "tie-a"),
                new TitleShortInfo(candidateTieB, "Tie B", "tie-b")
        ));

        var result = recommendationsService.getPersonalRecommendations(userId, 3);

        assertThat(result).hasSize(3);
        assertThat(result).extracting(PersonalRecommendationResponse::titleId)
                .containsExactly(candidateHighScore, candidateTieA, candidateTieB);
        assertThat(result).extracting(PersonalRecommendationResponse::reason)
                .containsOnly("tag_overlap");
        assertThat(result).extracting(PersonalRecommendationResponse::titleId)
                .doesNotContain(viewedRecent, viewedOld);
        verify(userInteractionApi, never()).findTopViewedTitlesSince(any(), anyInt());
    }

    private static UserInteractionApi.TopViewedTitleProjection topViewed(UUID titleId, long views) {
        return new UserInteractionApi.TopViewedTitleProjection() {
            @Override
            public UUID getTitleId() {
                return titleId;
            }

            @Override
            public Long getWeeklyViews() {
                return views;
            }
        };
    }

}
