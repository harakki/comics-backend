package dev.harakki.comics.catalog.application;

import dev.harakki.comics.catalog.api.AuthorCreatedEvent;
import dev.harakki.comics.catalog.api.AuthorDeletedEvent;
import dev.harakki.comics.catalog.domain.Author;
import dev.harakki.comics.catalog.dto.AuthorCreateRequest;
import dev.harakki.comics.catalog.dto.AuthorResponse;
import dev.harakki.comics.catalog.dto.AuthorUpdateRequest;
import dev.harakki.comics.catalog.infrastructure.AuthorMapper;
import dev.harakki.comics.catalog.infrastructure.AuthorRepository;
import dev.harakki.comics.shared.exception.ResourceAlreadyExistsException;
import dev.harakki.comics.shared.exception.ResourceInUseException;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private SlugGenerator slugGenerator;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AuthorService authorService;

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
    void create_whenValid_returnsAuthorResponseAndPublishesEvent() {
        var request = new AuthorCreateRequest("Oda Eiichiro", "oda-eiichiro", "Manga author", List.of(), "JP", null);
        var authorId = UUID.randomUUID();
        var author = Author.builder().id(authorId).name("Oda Eiichiro").slug("oda-eiichiro").build();
        var response = new AuthorResponse(authorId, "Oda Eiichiro", "oda-eiichiro", "Manga author", List.of(), "JP", null);

        when(authorRepository.existsByName("Oda Eiichiro")).thenReturn(false);
        when(authorMapper.toEntity(request)).thenReturn(author);
        when(authorRepository.existsBySlug("oda-eiichiro")).thenReturn(false);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toResponse(author)).thenReturn(response);

        var result = authorService.create(request);

        assertThat(result).isEqualTo(response);
        var captor = ArgumentCaptor.forClass(AuthorCreatedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().authorId()).isEqualTo(authorId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void create_whenNameAlreadyExists_throwsResourceAlreadyExistsException() {
        var request = new AuthorCreateRequest("Oda Eiichiro", null, null, null, null, null);

        when(authorRepository.existsByName("Oda Eiichiro")).thenReturn(true);

        assertThatThrownBy(() -> authorService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Oda Eiichiro");

        verify(authorRepository, never()).save(any());
    }

    @Test
    void create_whenSlugExplicitlyProvided_andSlugExists_throwsException() {
        var request = new AuthorCreateRequest("Oda Eiichiro", "oda-eiichiro", null, null, null, null);
        var author = Author.builder().name("Oda Eiichiro").build();

        when(authorRepository.existsByName("Oda Eiichiro")).thenReturn(false);
        when(authorMapper.toEntity(request)).thenReturn(author);
        when(authorRepository.existsBySlug("oda-eiichiro")).thenReturn(true);

        assertThatThrownBy(() -> authorService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("oda-eiichiro");

        verify(authorRepository, never()).save(any());
    }

    @Test
    void create_whenNoSlugProvided_generatesSlug() {
        var request = new AuthorCreateRequest("Oda Eiichiro", null, null, null, null, null);
        var authorId = UUID.randomUUID();
        var author = Author.builder().id(authorId).name("Oda Eiichiro").build();
        var response = new AuthorResponse(authorId, "Oda Eiichiro", "oda-eiichiro", null, List.of(), null, null);

        when(authorRepository.existsByName("Oda Eiichiro")).thenReturn(false);
        when(authorMapper.toEntity(request)).thenReturn(author);
        when(slugGenerator.generate(eq("Oda Eiichiro"), any())).thenReturn("oda-eiichiro");
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toResponse(author)).thenReturn(response);

        var result = authorService.create(request);

        assertThat(result).isEqualTo(response);
        verify(slugGenerator).generate(eq("Oda Eiichiro"), any());
    }

    @Test
    void getById_whenExists_returnsResponse() {
        var id = UUID.randomUUID();
        var author = Author.builder().id(id).name("Oda Eiichiro").slug("oda-eiichiro").build();
        var response = new AuthorResponse(id, "Oda Eiichiro", "oda-eiichiro", null, List.of(), null, null);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorMapper.toResponse(author)).thenReturn(response);

        var result = authorService.getById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getById_whenNotFound_throwsException() {
        var id = UUID.randomUUID();

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void update_whenValid_returnsUpdatedResponse() {
        var id = UUID.randomUUID();
        var request = new AuthorUpdateRequest("New Name", "new-name", null, null, null, null);
        var existing = Author.builder().id(id).name("Oda Eiichiro").slug("oda-eiichiro").build();
        var updated = Author.builder().id(id).name("New Name").slug("new-name").build();
        var response = new AuthorResponse(id, "New Name", "new-name", null, List.of(), null, null);

        when(authorRepository.findById(id)).thenReturn(Optional.of(existing));
        when(authorRepository.existsBySlugAndIdNot("new-name", id)).thenReturn(false);
        when(authorMapper.partialUpdate(request, existing)).thenReturn(updated);
        when(authorRepository.save(updated)).thenReturn(updated);
        when(authorMapper.toResponse(updated)).thenReturn(response);

        var result = authorService.update(id, request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void update_whenSlugConflict_throwsException() {
        var id = UUID.randomUUID();
        var request = new AuthorUpdateRequest(null, "taken-slug", null, null, null, null);
        var existing = Author.builder().id(id).name("Oda Eiichiro").slug("oda-eiichiro").build();
        var updated = Author.builder().id(id).name("Oda Eiichiro").slug("taken-slug").build();

        when(authorRepository.findById(id)).thenReturn(Optional.of(existing));
        when(authorMapper.partialUpdate(request, existing)).thenReturn(updated);
        when(authorRepository.existsBySlugAndIdNot("taken-slug", id)).thenReturn(true);

        assertThatThrownBy(() -> authorService.update(id, request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("taken-slug");

        verify(authorRepository, never()).save(any());
    }

    @Test
    void delete_whenExists_deletesAndPublishesEvent() {
        var id = UUID.randomUUID();
        var author = Author.builder().id(id).name("Oda Eiichiro").slug("oda-eiichiro").build();

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        authorService.delete(id);

        verify(authorRepository).delete(author);
        verify(authorRepository).flush();
        var captor = ArgumentCaptor.forClass(AuthorDeletedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().authorId()).isEqualTo(id);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void delete_whenInUse_throwsResourceInUseException() {
        var id = UUID.randomUUID();
        var author = Author.builder().id(id).name("Oda Eiichiro").slug("oda-eiichiro").build();

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        doThrow(DataIntegrityViolationException.class).when(authorRepository).flush();

        assertThatThrownBy(() -> authorService.delete(id))
                .isInstanceOf(ResourceInUseException.class)
                .hasMessageContaining(id.toString());
    }
}
