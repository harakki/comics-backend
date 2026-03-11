package dev.harakki.comics.analytics.application;

import dev.harakki.comics.analytics.domain.InteractionType;
import dev.harakki.comics.analytics.infrastructure.UserInteractionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChapterReadHistoryServiceTest {

    @Mock
    private UserInteractionRepository interactionRepository;

    @InjectMocks
    private ChapterReadHistoryService chapterReadHistoryService;

    @Test
    void isChapterRead_whenInteractionExists_returnsTrue() {
        var userId = UUID.randomUUID();
        var chapterId = UUID.randomUUID();

        when(interactionRepository.existsByUserIdAndTargetIdAndType(userId, chapterId, InteractionType.CHAPTER_READ))
                .thenReturn(true);

        var result = chapterReadHistoryService.isChapterRead(userId, chapterId);

        assertThat(result).isTrue();
    }

    @Test
    void isChapterRead_whenInteractionNotExists_returnsFalse() {
        var userId = UUID.randomUUID();
        var chapterId = UUID.randomUUID();

        when(interactionRepository.existsByUserIdAndTargetIdAndType(userId, chapterId, InteractionType.CHAPTER_READ))
                .thenReturn(false);

        var result = chapterReadHistoryService.isChapterRead(userId, chapterId);

        assertThat(result).isFalse();
    }

    @Test
    void getReadChapterIds_whenEmpty_returnsEmptySet() {
        var userId = UUID.randomUUID();

        var result = chapterReadHistoryService.getReadChapterIds(userId, List.of());

        assertThat(result).isEmpty();
        // Should short-circuit and never call repository
        verifyNoInteractions(interactionRepository);
    }

    @Test
    void getReadChapterIds_whenSomeRead_returnsReadIds() {
        var userId = UUID.randomUUID();
        var ch1 = UUID.randomUUID();
        var ch2 = UUID.randomUUID();
        var ch3 = UUID.randomUUID();
        var allChapterIds = List.of(ch1, ch2, ch3);

        // Only ch1 and ch3 are read
        when(interactionRepository.findReadChapterIds(userId, allChapterIds, InteractionType.CHAPTER_READ))
                .thenReturn(List.of(ch1, ch3));

        var result = chapterReadHistoryService.getReadChapterIds(userId, allChapterIds);

        assertThat(result)
                .containsExactlyInAnyOrder(ch1, ch3)
                .doesNotContain(ch2);
    }
}
