package dev.harakki.comics.content.application;

import dev.harakki.comics.content.api.ChapterCreatedEvent;
import dev.harakki.comics.content.api.ChapterDeletedEvent;
import dev.harakki.comics.content.api.ChapterReadEvent;
import dev.harakki.comics.content.api.ChapterUpdatedEvent;
import dev.harakki.comics.content.domain.Chapter;
import dev.harakki.comics.content.domain.Page;
import dev.harakki.comics.content.dto.*;
import dev.harakki.comics.content.infrastructure.ChapterMapper;
import dev.harakki.comics.content.infrastructure.ChapterRepository;
import dev.harakki.comics.media.api.MediaUrlProvider;
import dev.harakki.comics.shared.api.ChapterReadHistoryProvider;
import dev.harakki.comics.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChapterServiceTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @Mock
    private MediaUrlProvider mediaUrlProvider;

    @Mock
    private ChapterReadHistoryProvider chapterReadHistoryProvider;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ChapterService chapterService;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        mockAuthentication(userId);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void mockAuthentication(UUID userId) {
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .subject(userId.toString())
                .claim("realm_access", java.util.Map.of("roles", java.util.List.of("USER", "ADMIN")))
                .build();
        var authentication = new JwtAuthenticationToken(jwt,
                List.of(new SimpleGrantedAuthority("ROLE_USER"),
                        new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void create_whenValid_savesChapterAndPublishesEvents() {
        var titleId = UUID.randomUUID();
        var mediaId1 = UUID.randomUUID();
        var mediaId2 = UUID.randomUUID();
        var request = new ChapterCreateRequest(1, 0, "Chapter 1", 1, List.of(mediaId1, mediaId2));

        when(chapterRepository.save(any(Chapter.class))).thenAnswer(inv -> {
            Chapter ch = inv.getArgument(0);
            ch.setId(UUID.randomUUID());
            return ch;
        });

        chapterService.create(titleId, request);

        verify(chapterRepository).save(any(Chapter.class));
        var captor = ArgumentCaptor.forClass(ChapterCreatedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().titleId()).isEqualTo(titleId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
        assertThat(captor.getValue().chapterNumber()).isEqualTo("1");
    }

    @Test
    void create_whenTooManyPages_throwsIllegalArgumentException() {
        var titleId = UUID.randomUUID();
        var pages = new ArrayList<UUID>();
        for (int i = 0; i < 501; i++) {
            pages.add(UUID.randomUUID());
        }
        var request = new ChapterCreateRequest(1, 0, "Chapter 1", null, pages);

        assertThatThrownBy(() -> chapterService.create(titleId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("500");
    }

    @Test
    void create_whenDuplicatePageIds_throwsIllegalArgumentException() {
        var titleId = UUID.randomUUID();
        var mediaId = UUID.randomUUID();
        var request = new ChapterCreateRequest(1, 0, "Chapter 1", null, List.of(mediaId, mediaId));

        assertThatThrownBy(() -> chapterService.create(titleId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Duplicate");
    }

    @Test
    void getChapterDetails_whenFound_returnsResponse() {
        var chapterId = UUID.randomUUID();
        var titleId = UUID.randomUUID();
        var mediaId = UUID.randomUUID();

        var page = Page.builder()
                .id(UUID.randomUUID())
                .mediaId(mediaId)
                .pageOrder(0)
                .build();

        var chapter = Chapter.builder()
                .id(chapterId)
                .titleId(titleId)
                .number(1)
                .subNumber(0)
                .name("Chapter 1")
                .pages(new ArrayList<>(List.of(page)))
                .build();

        when(chapterRepository.findByIdWithPages(chapterId)).thenReturn(Optional.of(chapter));
        when(mediaUrlProvider.getPublicUrl(mediaId)).thenReturn("http://example.com/img.jpg");
        when(chapterRepository.findPrevChapter(titleId, 1, 0)).thenReturn(Optional.empty());
        when(chapterRepository.findNextChapter(titleId, 1, 0)).thenReturn(Optional.empty());

        var result = chapterService.getChapterDetails(chapterId);

        assertThat(result.id()).isEqualTo(chapterId);
        assertThat(result.titleId()).isEqualTo(titleId);
        assertThat(result.displayNumber()).isEqualTo("1");
        assertThat(result.pages()).hasSize(1);
        assertThat(result.pages().getFirst().url()).isEqualTo("http://example.com/img.jpg");
    }

    @Test
    void getChapterDetails_whenNotFound_throwsException() {
        var chapterId = UUID.randomUUID();

        when(chapterRepository.findByIdWithPages(chapterId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chapterService.getChapterDetails(chapterId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updatePages_whenValid_updatesAndPublishesEvents() {
        var chapterId = UUID.randomUUID();
        var titleId = UUID.randomUUID();
        var mediaId = UUID.randomUUID();

        var existingPage = Page.builder()
                .id(UUID.randomUUID())
                .mediaId(UUID.randomUUID())
                .pageOrder(0)
                .build();

        var chapter = Chapter.builder()
                .id(chapterId)
                .titleId(titleId)
                .number(1)
                .subNumber(0)
                .pages(new ArrayList<>(List.of(existingPage)))
                .build();

        when(chapterRepository.findByIdWithPages(chapterId)).thenReturn(Optional.of(chapter));
        when(chapterRepository.save(chapter)).thenReturn(chapter);

        chapterService.updatePages(chapterId, List.of(mediaId));

        verify(chapterRepository).save(chapter);
        var captor = ArgumentCaptor.forClass(ChapterUpdatedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().chapterId()).isEqualTo(chapterId);
        assertThat(captor.getValue().titleId()).isEqualTo(titleId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void delete_whenFound_deletesAndPublishesEvents() {
        var chapterId = UUID.randomUUID();
        var titleId = UUID.randomUUID();

        var chapter = Chapter.builder()
                .id(chapterId)
                .titleId(titleId)
                .number(1)
                .subNumber(0)
                .pages(new ArrayList<>())
                .build();

        when(chapterRepository.findByIdWithPages(chapterId)).thenReturn(Optional.of(chapter));

        chapterService.delete(chapterId);

        verify(chapterRepository).delete(chapter);
        var captor = ArgumentCaptor.forClass(ChapterDeletedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().chapterId()).isEqualTo(chapterId);
        assertThat(captor.getValue().titleId()).isEqualTo(titleId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void getChaptersByTitle_returnsListOfSummaries() {
        var titleId = UUID.randomUUID();
        var ch1 = Chapter.builder().id(UUID.randomUUID()).titleId(titleId).number(1).subNumber(0).name("ch1").pages(new ArrayList<>()).build();
        var ch2 = Chapter.builder().id(UUID.randomUUID()).titleId(titleId).number(2).subNumber(0).name("ch2").pages(new ArrayList<>()).build();

        when(chapterRepository.findAllByTitleId(titleId)).thenReturn(List.of(ch1, ch2));

        var result = chapterService.getChaptersByTitle(titleId);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).displayNumber()).isEqualTo("1");
        assertThat(result.get(1).displayNumber()).isEqualTo("2");
    }

    @Test
    void recordChapterRead_publishesEvent() {
        var chapterId = UUID.randomUUID();
        var titleId = UUID.randomUUID();
        var request = new ChapterReadRequest(5000L);

        var chapter = Chapter.builder()
                .id(chapterId)
                .titleId(titleId)
                .number(1)
                .subNumber(0)
                .pages(new ArrayList<>())
                .build();

        when(chapterRepository.findById(chapterId)).thenReturn(Optional.of(chapter));
        when(chapterRepository.findAllByTitleId(titleId)).thenReturn(List.of(chapter));
        when(chapterReadHistoryProvider.getReadChapterIds(eq(userId), anyList())).thenReturn(Set.of());

        chapterService.recordChapterRead(chapterId, request);

        var captor = ArgumentCaptor.forClass(ChapterReadEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().userId()).isEqualTo(userId);
        assertThat(captor.getValue().chapterId()).isEqualTo(chapterId);
        assertThat(captor.getValue().readTimeMillis()).isEqualTo(5000L);
    }
}
