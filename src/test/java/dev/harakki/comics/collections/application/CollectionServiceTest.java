package dev.harakki.comics.collections.application;

import dev.harakki.comics.collections.api.CollectionCreatedEvent;
import dev.harakki.comics.collections.api.CollectionDeletedEvent;
import dev.harakki.comics.collections.api.CollectionUpdatedEvent;
import dev.harakki.comics.collections.domain.Collection;
import dev.harakki.comics.collections.dto.CollectionCreateRequest;
import dev.harakki.comics.collections.dto.CollectionUpdateRequest;
import dev.harakki.comics.collections.dto.UserCollectionResponse;
import dev.harakki.comics.collections.infrastructure.CollectionMapper;
import dev.harakki.comics.collections.infrastructure.CollectionRepository;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private CollectionMapper collectionMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CollectionService collectionService;

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

    private UserCollectionResponse buildResponse(UUID id, UUID authorId, String name) {
        return new UserCollectionResponse(id, authorId, name, null, true, List.of(), Instant.now(), Instant.now());
    }

    @Test
    void create_whenValid_createsCollectionAndPublishesEvent() {
        var collectionId = UUID.randomUUID();
        var request = new CollectionCreateRequest("My Collection", "A description", true, List.of());
        var entity = Collection.builder().id(collectionId).authorId(userId).name("My Collection").isPublic(true).titleIds(new ArrayList<>()).build();
        var response = buildResponse(collectionId, userId, "My Collection");

        when(collectionRepository.existsByAuthorIdAndName(userId, "My Collection")).thenReturn(false);
        when(collectionMapper.toEntity(request)).thenReturn(entity);
        when(collectionRepository.save(entity)).thenReturn(entity);
        when(collectionMapper.toResponse(entity)).thenReturn(response);

        var result = collectionService.create(request);

        assertThat(result).isEqualTo(response);
        var captor = ArgumentCaptor.forClass(CollectionCreatedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().collectionId()).isEqualTo(collectionId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
        assertThat(captor.getValue().collectionName()).isEqualTo("My Collection");
    }

    @Test
    void create_whenNameAlreadyExists_throwsException() {
        var request = new CollectionCreateRequest("My Collection", null, true, null);

        when(collectionRepository.existsByAuthorIdAndName(userId, "My Collection")).thenReturn(true);

        assertThatThrownBy(() -> collectionService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("My Collection");

        verify(collectionRepository, never()).save(any());
    }

    @Test
    void getById_whenPublic_returnsResponse() {
        var id = UUID.randomUUID();
        var otherUserId = UUID.randomUUID();
        var entity = Collection.builder().id(id).authorId(otherUserId).name("Public Collection").isPublic(true).titleIds(new ArrayList<>()).build();
        var response = buildResponse(id, otherUserId, "Public Collection");

        when(collectionRepository.findById(id)).thenReturn(Optional.of(entity));
        when(collectionMapper.toResponse(entity)).thenReturn(response);

        var result = collectionService.getById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getById_whenPrivateAndOwner_returnsResponse() {
        var id = UUID.randomUUID();
        var entity = Collection.builder().id(id).authorId(userId).name("Private Collection").isPublic(false).titleIds(new ArrayList<>()).build();
        var response = new UserCollectionResponse(id, userId, "Private Collection", null, false, List.of(), Instant.now(), Instant.now());

        when(collectionRepository.findById(id)).thenReturn(Optional.of(entity));
        when(collectionMapper.toResponse(entity)).thenReturn(response);

        var result = collectionService.getById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getById_whenPrivateAndNotOwner_throwsAccessDeniedException() {
        var id = UUID.randomUUID();
        var otherUserId = UUID.randomUUID();
        var entity = Collection.builder().id(id).authorId(otherUserId).name("Private").isPublic(false).titleIds(new ArrayList<>()).build();

        when(collectionRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> collectionService.getById(id))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void update_whenOwner_updatesAndPublishesEvent() {
        var id = UUID.randomUUID();
        var request = new CollectionUpdateRequest("Updated Name", null, null, null);
        var entity = Collection.builder().id(id).authorId(userId).name("My Collection").isPublic(true).titleIds(new ArrayList<>()).build();
        var updatedEntity = Collection.builder().id(id).authorId(userId).name("Updated Name").isPublic(true).titleIds(new ArrayList<>()).build();
        var response = buildResponse(id, userId, "Updated Name");

        when(collectionRepository.findById(id)).thenReturn(Optional.of(entity));
        when(collectionRepository.existsByAuthorIdAndNameAndIdNot(userId, "Updated Name", id)).thenReturn(false);
        when(collectionMapper.partialUpdate(request, entity)).thenReturn(updatedEntity);
        when(collectionRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(collectionMapper.toResponse(updatedEntity)).thenReturn(response);

        var result = collectionService.update(id, request);

        assertThat(result).isEqualTo(response);
        var captor = ArgumentCaptor.forClass(CollectionUpdatedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().collectionId()).isEqualTo(id);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void update_whenNotOwner_throwsAccessDeniedException() {
        var id = UUID.randomUUID();
        var otherUserId = UUID.randomUUID();
        var entity = Collection.builder().id(id).authorId(otherUserId).name("My Collection").isPublic(true).titleIds(new ArrayList<>()).build();
        var request = new CollectionUpdateRequest("New Name", null, null, null);

        when(collectionRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> collectionService.update(id, request))
                .isInstanceOf(AccessDeniedException.class);

        verify(collectionRepository, never()).save(any());
    }

    @Test
    void delete_whenOwner_deletesAndPublishesEvent() {
        var id = UUID.randomUUID();
        var entity = Collection.builder().id(id).authorId(userId).name("My Collection").isPublic(true).titleIds(new ArrayList<>()).build();

        when(collectionRepository.findById(id)).thenReturn(Optional.of(entity));

        collectionService.delete(id);

        verify(collectionRepository).delete(entity);
        var captor = ArgumentCaptor.forClass(CollectionDeletedEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().collectionId()).isEqualTo(id);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void delete_whenNotOwner_throwsAccessDeniedException() {
        var id = UUID.randomUUID();
        var otherUserId = UUID.randomUUID();
        var entity = Collection.builder().id(id).authorId(otherUserId).name("My Collection").isPublic(true).titleIds(new ArrayList<>()).build();

        when(collectionRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> collectionService.delete(id))
                .isInstanceOf(AccessDeniedException.class);

        verify(collectionRepository, never()).delete(any());
    }
}
