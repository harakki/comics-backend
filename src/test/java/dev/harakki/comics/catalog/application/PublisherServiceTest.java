package dev.harakki.comics.catalog.application;

import dev.harakki.comics.catalog.api.PublisherCreatedEvent;
import dev.harakki.comics.catalog.api.PublisherDeletedEvent;
import dev.harakki.comics.catalog.domain.Publisher;
import dev.harakki.comics.catalog.dto.PublisherCreateRequest;
import dev.harakki.comics.catalog.dto.PublisherResponse;
import dev.harakki.comics.catalog.dto.PublisherUpdateRequest;
import dev.harakki.comics.catalog.infrastructure.PublisherMapper;
import dev.harakki.comics.catalog.infrastructure.PublisherRepository;
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
class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private PublisherMapper publisherMapper;

    @Mock
    private SlugGenerator slugGenerator;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private PublisherService publisherService;

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
    void create_whenValid_returnsPublisherResponseAndPublishesEvent() {
        var request = new PublisherCreateRequest("Shueisha", "shueisha", "Japanese publisher", List.of(), "JP", null);
        var publisherId = UUID.randomUUID();
        var publisher = Publisher.builder().id(publisherId).name("Shueisha").slug("shueisha").build();
        var response = new PublisherResponse(publisherId, "Shueisha", "shueisha", "Japanese publisher", List.of(), "JP", null);

        when(publisherRepository.existsByName("Shueisha")).thenReturn(false);
        when(publisherMapper.toEntity(request)).thenReturn(publisher);
        when(publisherRepository.existsBySlug("shueisha")).thenReturn(false);
        when(publisherRepository.save(publisher)).thenReturn(publisher);
        when(publisherMapper.toResponse(publisher)).thenReturn(response);

        var result = publisherService.create(request);

        assertThat(result).isEqualTo(response);
        var captor = ArgumentCaptor.forClass(PublisherCreatedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().publisherId()).isEqualTo(publisherId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
        assertThat(captor.getValue().publisherName()).isEqualTo("Shueisha");
    }

    @Test
    void create_whenNameAlreadyExists_throwsException() {
        var request = new PublisherCreateRequest("Shueisha", null, null, null, null, null);

        when(publisherRepository.existsByName("Shueisha")).thenReturn(true);

        assertThatThrownBy(() -> publisherService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Shueisha");

        verify(publisherRepository, never()).save(any());
    }

    @Test
    void create_whenSlugExplicitlyProvided_andSlugExists_throwsException() {
        var request = new PublisherCreateRequest("Shueisha", "shueisha", null, null, null, null);
        var publisher = Publisher.builder().name("Shueisha").build();

        when(publisherRepository.existsByName("Shueisha")).thenReturn(false);
        when(publisherMapper.toEntity(request)).thenReturn(publisher);
        when(publisherRepository.existsBySlug("shueisha")).thenReturn(true);

        assertThatThrownBy(() -> publisherService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("shueisha");

        verify(publisherRepository, never()).save(any());
    }

    @Test
    void create_whenNoSlugProvided_generatesSlug() {
        var request = new PublisherCreateRequest("Shueisha", null, null, null, null, null);
        var publisherId = UUID.randomUUID();
        var publisher = Publisher.builder().id(publisherId).name("Shueisha").build();
        var response = new PublisherResponse(publisherId, "Shueisha", "shueisha", null, List.of(), null, null);

        when(publisherRepository.existsByName("Shueisha")).thenReturn(false);
        when(publisherMapper.toEntity(request)).thenReturn(publisher);
        when(slugGenerator.generate(eq("Shueisha"), any())).thenReturn("shueisha");
        when(publisherRepository.save(publisher)).thenReturn(publisher);
        when(publisherMapper.toResponse(publisher)).thenReturn(response);

        var result = publisherService.create(request);

        assertThat(result).isEqualTo(response);
        verify(slugGenerator).generate(eq("Shueisha"), any());
    }

    @Test
    void getById_whenExists_returnsResponse() {
        var id = UUID.randomUUID();
        var publisher = Publisher.builder().id(id).name("Shueisha").slug("shueisha").build();
        var response = new PublisherResponse(id, "Shueisha", "shueisha", null, List.of(), null, null);

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));
        when(publisherMapper.toResponse(publisher)).thenReturn(response);

        var result = publisherService.getById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getById_whenNotFound_throwsException() {
        var id = UUID.randomUUID();

        when(publisherRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> publisherService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void update_whenValid_returnsUpdatedResponse() {
        var id = UUID.randomUUID();
        var request = new PublisherUpdateRequest("New Publisher", "new-publisher", null, null, null, null);
        var existing = Publisher.builder().id(id).name("Shueisha").slug("shueisha").build();
        var updated = Publisher.builder().id(id).name("New Publisher").slug("new-publisher").build();
        var response = new PublisherResponse(id, "New Publisher", "new-publisher", null, List.of(), null, null);

        when(publisherRepository.findById(id)).thenReturn(Optional.of(existing));
        when(publisherRepository.existsByNameAndIdNot("New Publisher", id)).thenReturn(false);
        when(publisherMapper.partialUpdate(request, existing)).thenReturn(updated);
        when(publisherRepository.existsBySlugAndIdNot("new-publisher", id)).thenReturn(false);
        when(publisherRepository.save(updated)).thenReturn(updated);
        when(publisherMapper.toResponse(updated)).thenReturn(response);

        var result = publisherService.update(id, request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void update_whenNameConflict_throwsException() {
        var id = UUID.randomUUID();
        var request = new PublisherUpdateRequest("Taken Name", null, null, null, null, null);
        var existing = Publisher.builder().id(id).name("Shueisha").slug("shueisha").build();

        when(publisherRepository.findById(id)).thenReturn(Optional.of(existing));
        when(publisherRepository.existsByNameAndIdNot("Taken Name", id)).thenReturn(true);

        assertThatThrownBy(() -> publisherService.update(id, request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Taken Name");

        verify(publisherRepository, never()).save(any());
    }

    @Test
    void delete_whenExists_deletesAndPublishesEvent() {
        var id = UUID.randomUUID();
        var publisher = Publisher.builder().id(id).name("Shueisha").slug("shueisha").build();

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));

        publisherService.delete(id);

        verify(publisherRepository).delete(publisher);
        verify(publisherRepository).flush();
        var captor = ArgumentCaptor.forClass(PublisherDeletedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().publisherId()).isEqualTo(id);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void delete_whenInUse_throwsResourceInUseException() {
        var id = UUID.randomUUID();
        var publisher = Publisher.builder().id(id).name("Shueisha").slug("shueisha").build();

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));
        doThrow(DataIntegrityViolationException.class).when(publisherRepository).flush();

        assertThatThrownBy(() -> publisherService.delete(id))
                .isInstanceOf(ResourceInUseException.class)
                .hasMessageContaining(id.toString());
    }

}
