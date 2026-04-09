package dev.harakki.comics.analytics.application;

import dev.harakki.comics.analytics.api.InteractionType;
import dev.harakki.comics.analytics.api.UserInteractionApi;
import dev.harakki.comics.analytics.domain.UserInteraction;
import dev.harakki.comics.analytics.infrastructure.UserInteractionRepository;
import dev.harakki.comics.catalog.api.TitlePublicQueryApi;
import dev.harakki.comics.catalog.api.TitleShortInfo;
import dev.harakki.comics.catalog.api.TitleViewedEvent;
import dev.harakki.comics.content.api.ChapterReadEvent;
import dev.harakki.comics.library.api.LibraryAddTitleEvent;
import dev.harakki.comics.library.api.LibraryVoteTitleEvent;
import dev.harakki.comics.library.api.VoteType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private UserInteractionRepository userInteractionRepository;

    @Mock
    private TitlePublicQueryApi titlePublicQueryApi;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void getTitleAnalytics_whenDataExists_returnsAnalyticsResponse() {
        var titleId = UUID.randomUUID();

        when(userInteractionRepository.getAverageRating(titleId)).thenReturn(4.5);
        when(userInteractionRepository.countByTargetIdAndType(titleId, InteractionType.TITLE_VIEWED)).thenReturn(100L);

        var result = analyticsService.getTitleAnalytics(titleId);

        assertThat(result.titleId()).isEqualTo(titleId);
        assertThat(result.averageRating()).isEqualTo(4.5);
        assertThat(result.totalViews()).isEqualTo(100L);
        assertThat(result.lastUpdated()).isNotNull();
    }

    @Test
    void getTitleAnalytics_whenNoData_returnsZeroViews() {
        var titleId = UUID.randomUUID();

        when(userInteractionRepository.getAverageRating(titleId)).thenReturn(null);
        when(userInteractionRepository.countByTargetIdAndType(titleId, InteractionType.TITLE_VIEWED)).thenReturn(0L);

        var result = analyticsService.getTitleAnalytics(titleId);

        assertThat(result.titleId()).isEqualTo(titleId);
        assertThat(result.averageRating()).isNull();
        assertThat(result.totalViews()).isZero();
    }

    @Test
    void recordChapterRead_savesInteraction() {
        var userId = UUID.randomUUID();
        var chapterId = UUID.randomUUID();
        var event = new ChapterReadEvent(userId, chapterId, 5000L);

        when(userInteractionRepository.save(any(UserInteraction.class))).thenAnswer(inv -> inv.getArgument(0));

        analyticsService.recordChapterRead(event);

        var captor = ArgumentCaptor.forClass(UserInteraction.class);
        verify(userInteractionRepository).save(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo(userId);
        assertThat(captor.getValue().getType()).isEqualTo(InteractionType.CHAPTER_READ);
        assertThat(captor.getValue().getTargetId()).isEqualTo(chapterId);
        assertThat(captor.getValue().getMetadata()).containsEntry("readTimeMillis", 5000L);
    }

    @Test
    void recordTitleVote_savesInteraction() {
        var userId = UUID.randomUUID();
        var titleId = UUID.randomUUID();
        var event = new LibraryVoteTitleEvent(titleId, userId, VoteType.LIKE);

        when(userInteractionRepository.save(any(UserInteraction.class))).thenAnswer(inv -> inv.getArgument(0));

        analyticsService.recordTitleVote(event);

        var captor = ArgumentCaptor.forClass(UserInteraction.class);
        verify(userInteractionRepository).save(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo(userId);
        assertThat(captor.getValue().getType()).isEqualTo(InteractionType.TITLE_VOTED);
        assertThat(captor.getValue().getTargetId()).isEqualTo(titleId);
        assertThat(captor.getValue().getMetadata()).containsEntry("voteType", VoteType.LIKE);
    }

    @Test
    void recordTitleView_savesInteraction() {
        var userId = UUID.randomUUID();
        var titleId = UUID.randomUUID();
        var event = new TitleViewedEvent(titleId, userId);

        when(userInteractionRepository.save(any(UserInteraction.class))).thenAnswer(inv -> inv.getArgument(0));

        analyticsService.recordTitleView(event);

        var captor = ArgumentCaptor.forClass(UserInteraction.class);
        verify(userInteractionRepository).save(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo(userId);
        assertThat(captor.getValue().getType()).isEqualTo(InteractionType.TITLE_VIEWED);
        assertThat(captor.getValue().getTargetId()).isEqualTo(titleId);
    }

    @Test
    void recordTitleView_whenUserIdIsNull_savesAnonymousInteraction() {
        var titleId = UUID.randomUUID();
        var event = new TitleViewedEvent(titleId, null);

        when(userInteractionRepository.save(any(UserInteraction.class))).thenAnswer(inv -> inv.getArgument(0));

        analyticsService.recordTitleView(event);

        var captor = ArgumentCaptor.forClass(UserInteraction.class);
        verify(userInteractionRepository).save(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo(AnalyticsService.ANONYMOUS_USER_ID);
        assertThat(captor.getValue().getType()).isEqualTo(InteractionType.TITLE_VIEWED);
        assertThat(captor.getValue().getTargetId()).isEqualTo(titleId);
        assertThat(captor.getValue().getMetadata()).containsEntry("anonymous", true);
    }

    @Test
    void recordTitleAddToLibrary_savesInteraction() {
        var userId = UUID.randomUUID();
        var titleId = UUID.randomUUID();
        var event = new LibraryAddTitleEvent(titleId, userId);

        when(userInteractionRepository.save(any(UserInteraction.class))).thenAnswer(inv -> inv.getArgument(0));

        analyticsService.recordTitleAddToLibrary(event);

        var captor = ArgumentCaptor.forClass(UserInteraction.class);
        verify(userInteractionRepository).save(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo(userId);
        assertThat(captor.getValue().getType()).isEqualTo(InteractionType.TITLE_ADDED_TO_LIBRARY);
        assertThat(captor.getValue().getTargetId()).isEqualTo(titleId);
    }

    @Test
    void getTopWeeklyPopularTitles_whenDataExists_returnsEnrichedTopList() {
        var title1 = UUID.randomUUID();
        var title2 = UUID.randomUUID();

        when(userInteractionRepository.findTopViewedTitlesSince(any(), org.mockito.ArgumentMatchers.anyInt())).thenReturn(List.of(
                topViewed(title1, 20L),
                topViewed(title2, 10L)
        ));
        when(titlePublicQueryApi.getTitleShortInfoByIds(List.of(title1, title2))).thenReturn(List.of(
                new TitleShortInfo(title1, "Solo Leveling", null, "solo-leveling"),
                new TitleShortInfo(title2, "One Piece", null, "one-piece")
        ));

        var result = analyticsService.getTopWeeklyPopularTitles();

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().titleId()).isEqualTo(title1);
        assertThat(result.getFirst().weeklyViews()).isEqualTo(20L);
        assertThat(result.getFirst().rank()).isEqualTo(1);
        assertThat(result.get(1).titleId()).isEqualTo(title2);
        assertThat(result.get(1).rank()).isEqualTo(2);
    }

    @Test
    void getTopWeeklyPopularTitles_whenTitleMissing_skipsItemAndReranks() {
        var missingTitle = UUID.randomUUID();
        var existingTitle = UUID.randomUUID();

        when(userInteractionRepository.findTopViewedTitlesSince(any(), org.mockito.ArgumentMatchers.anyInt())).thenReturn(List.of(
                topViewed(missingTitle, 50L),
                topViewed(existingTitle, 40L)
        ));
        when(titlePublicQueryApi.getTitleShortInfoByIds(List.of(missingTitle, existingTitle))).thenReturn(List.of(
                new TitleShortInfo(existingTitle, "Bleach", null, "bleach")
        ));

        var result = analyticsService.getTopWeeklyPopularTitles();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().titleId()).isEqualTo(existingTitle);
        assertThat(result.getFirst().rank()).isEqualTo(1);
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
