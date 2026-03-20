package dev.harakki.comics.catalog.application;

import dev.harakki.comics.catalog.api.TitleCreatedEvent;
import dev.harakki.comics.catalog.api.TitleDeletedEvent;
import dev.harakki.comics.catalog.api.TitleViewedEvent;
import dev.harakki.comics.catalog.domain.*;
import dev.harakki.comics.catalog.dto.TitleCreateRequest;
import dev.harakki.comics.catalog.dto.TitleResponse;
import dev.harakki.comics.catalog.dto.TitleUpdateRequest;
import dev.harakki.comics.catalog.infrastructure.*;
import dev.harakki.comics.shared.exception.ResourceAlreadyExistsException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TitleServiceTest {

    @Mock
    private TitleRepository titleRepository;

    @Mock
    private TitleMapper titleMapper;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private SlugGenerator slugGenerator;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TitleService titleService;

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

    private Title buildMinimalTitle(UUID id) {
        return Title.builder()
                .id(id)
                .name("One Piece")
                .slug("one-piece")
                .contentRating(ContentRating.SIX_PLUS)
                .type(TitleType.MANGA)
                .titleStatus(TitleStatus.ONGOING)
                .authors(new ArrayList<>())
                .tags(new LinkedHashSet<>())
                .build();
    }

    @Test
    void create_whenMinimalValid_returnsTitleResponse() {
        var request = TitleCreateRequest.builder()
                .name("One Piece")
                .type(TitleType.MANGA)
                .titleStatus(TitleStatus.ONGOING)
                .contentRating(ContentRating.SIX_PLUS)
                .countryIsoCode("JP")
                .build();
        var titleId = UUID.randomUUID();
        var title = buildMinimalTitle(titleId);
        var response = new TitleResponse(titleId, "One Piece", "one-piece", null, TitleType.MANGA,
                TitleStatus.ONGOING, null, ContentRating.SIX_PLUS, false, "JP", null, List.of(), null, Set.of());

        when(titleRepository.existsByName("One Piece")).thenReturn(false);
        when(titleMapper.toEntity(request)).thenReturn(title);
        when(slugGenerator.generate(eq("One Piece"), any())).thenReturn("one-piece");
        when(titleRepository.save(title)).thenReturn(title);
        when(titleMapper.toResponse(title)).thenReturn(response);

        var result = titleService.create(request);

        assertThat(result).isEqualTo(response);
        var captor = ArgumentCaptor.forClass(TitleCreatedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().titleId()).isEqualTo(titleId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void create_whenNameAlreadyExists_throwsException() {
        var request = TitleCreateRequest.builder()
                .name("One Piece")
                .type(TitleType.MANGA)
                .titleStatus(TitleStatus.ONGOING)
                .contentRating(ContentRating.SIX_PLUS)
                .countryIsoCode("JP")
                .build();

        when(titleRepository.existsByName("One Piece")).thenReturn(true);

        assertThatThrownBy(() -> titleService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("One Piece");

        verify(titleRepository, never()).save(any());
    }

    @Test
    void create_whenSlugProvided_andSlugExists_throwsException() {
        var request = TitleCreateRequest.builder()
                .name("One Piece")
                .slug("one-piece")
                .type(TitleType.MANGA)
                .titleStatus(TitleStatus.ONGOING)
                .contentRating(ContentRating.SIX_PLUS)
                .countryIsoCode("JP")
                .build();
        var title = buildMinimalTitle(null);

        when(titleRepository.existsByName("One Piece")).thenReturn(false);
        when(titleMapper.toEntity(request)).thenReturn(title);
        when(titleRepository.existsBySlug("one-piece")).thenReturn(true);

        assertThatThrownBy(() -> titleService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("one-piece");

        verify(titleRepository, never()).save(any());
    }

    @Test
    void create_whenAuthorNotFound_throwsException() {
        var authorId = UUID.randomUUID();
        var request = TitleCreateRequest.builder()
                .name("One Piece")
                .type(TitleType.MANGA)
                .titleStatus(TitleStatus.ONGOING)
                .contentRating(ContentRating.SIX_PLUS)
                .countryIsoCode("JP")
                .authorIds(Map.of(authorId, AuthorRole.STORY_AND_ART))
                .build();
        var title = buildMinimalTitle(null);

        when(titleRepository.existsByName("One Piece")).thenReturn(false);
        when(titleMapper.toEntity(request)).thenReturn(title);
        when(slugGenerator.generate(eq("One Piece"), any())).thenReturn("one-piece");
        // Return empty list - author not found
        when(authorRepository.findAllById(Set.of(authorId))).thenReturn(List.of());

        assertThatThrownBy(() -> titleService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("authors");
    }

    @Test
    void create_whenPublisherNotFound_throwsException() {
        var publisherId = UUID.randomUUID();
        var request = TitleCreateRequest.builder()
                .name("One Piece")
                .type(TitleType.MANGA)
                .titleStatus(TitleStatus.ONGOING)
                .contentRating(ContentRating.SIX_PLUS)
                .countryIsoCode("JP")
                .publisherId(publisherId)
                .build();
        var title = buildMinimalTitle(null);

        when(titleRepository.existsByName("One Piece")).thenReturn(false);
        when(titleMapper.toEntity(request)).thenReturn(title);
        when(slugGenerator.generate(eq("One Piece"), any())).thenReturn("one-piece");
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> titleService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(publisherId.toString());
    }

    @Test
    void create_whenTagNotFound_throwsException() {
        var tagId = UUID.randomUUID();
        var request = TitleCreateRequest.builder()
                .name("One Piece")
                .type(TitleType.MANGA)
                .titleStatus(TitleStatus.ONGOING)
                .contentRating(ContentRating.SIX_PLUS)
                .countryIsoCode("JP")
                .tagIds(Set.of(tagId))
                .build();
        var title = buildMinimalTitle(null);

        when(titleRepository.existsByName("One Piece")).thenReturn(false);
        when(titleMapper.toEntity(request)).thenReturn(title);
        when(slugGenerator.generate(eq("One Piece"), any())).thenReturn("one-piece");
        // Return empty list - tag not found
        when(tagRepository.findAllById(Set.of(tagId))).thenReturn(List.of());

        assertThatThrownBy(() -> titleService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("tags");
    }

    @Test
    void getById_whenFound_publishesTitleViewedEventAndReturnsResponse() {
        var id = UUID.randomUUID();
        var title = buildMinimalTitle(id);
        var response = new TitleResponse(id, "One Piece", "one-piece", null, TitleType.MANGA,
                TitleStatus.ONGOING, null, ContentRating.SIX_PLUS, false, "JP", null, List.of(), null, Set.of());

        when(titleRepository.findById(id)).thenReturn(Optional.of(title));
        when(titleMapper.toResponse(title)).thenReturn(response);

        var result = titleService.getById(id);

        assertThat(result).isEqualTo(response);
        var captor = ArgumentCaptor.forClass(TitleViewedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().titleId()).isEqualTo(id);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void getById_withoutAuthentication_publishesTitleViewedEventWithNullUserId() {
        var id = UUID.randomUUID();
        var title = buildMinimalTitle(id);
        var response = new TitleResponse(id, "One Piece", "one-piece", null, TitleType.MANGA,
                TitleStatus.ONGOING, null, ContentRating.SIX_PLUS, false, "JP", null, List.of(), null, Set.of());

        SecurityContextHolder.clearContext();

        when(titleRepository.findById(id)).thenReturn(Optional.of(title));
        when(titleMapper.toResponse(title)).thenReturn(response);

        var result = titleService.getById(id);

        assertThat(result).isEqualTo(response);
        var captor = ArgumentCaptor.forClass(TitleViewedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().titleId()).isEqualTo(id);
        assertThat(captor.getValue().userId()).isNull();
    }

    @Test
    void getById_whenNotFound_throwsException() {
        var id = UUID.randomUUID();

        when(titleRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> titleService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void update_whenValid_returnsUpdatedResponse() {
        var id = UUID.randomUUID();
        var request = TitleUpdateRequest.builder()
                .name("One Piece Updated")
                .slug("one-piece-updated")
                .build();
        var existing = buildMinimalTitle(id);
        var updated = Title.builder().id(id).name("One Piece Updated").slug("one-piece-updated")
                .contentRating(ContentRating.SIX_PLUS).type(TitleType.MANGA).titleStatus(TitleStatus.ONGOING)
                .authors(new ArrayList<>()).tags(new LinkedHashSet<>()).build();
        var response = new TitleResponse(id, "One Piece Updated", "one-piece-updated", null, TitleType.MANGA,
                TitleStatus.ONGOING, null, ContentRating.SIX_PLUS, false, "JP", null, List.of(), null, Set.of());

        when(titleRepository.findById(id)).thenReturn(Optional.of(existing));
        when(titleMapper.partialUpdate(request, existing)).thenReturn(updated);
        when(titleRepository.existsBySlugAndIdNot("one-piece-updated", id)).thenReturn(false);
        when(titleRepository.save(updated)).thenReturn(updated);
        when(titleMapper.toResponse(updated)).thenReturn(response);

        var result = titleService.update(id, request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void delete_whenFound_deletesAndPublishesEvent() {
        var id = UUID.randomUUID();
        var title = buildMinimalTitle(id);

        when(titleRepository.findById(id)).thenReturn(Optional.of(title));

        // Use reflection-based approach: just verify the delete and event
        // TitleService.delete is @Transactional; we test via calling delete
        titleService.delete(id);

        verify(titleRepository).delete(title);
        var captor = ArgumentCaptor.forClass(TitleDeletedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().titleId()).isEqualTo(id);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

}
