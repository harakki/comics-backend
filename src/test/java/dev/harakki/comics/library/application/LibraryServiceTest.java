package dev.harakki.comics.library.application;

import dev.harakki.comics.library.api.LibraryAddTitleEvent;
import dev.harakki.comics.library.api.LibraryRemoveTitleEvent;
import dev.harakki.comics.library.api.LibraryVoteTitleEvent;
import dev.harakki.comics.library.api.VoteType;
import dev.harakki.comics.library.domain.LibraryEntry;
import dev.harakki.comics.library.domain.ReadingStatus;
import dev.harakki.comics.library.dto.LibraryEntryResponse;
import dev.harakki.comics.library.dto.LibraryEntryUpdateRequest;
import dev.harakki.comics.library.infrastructure.LibraryEntryMapper;
import dev.harakki.comics.library.infrastructure.LibraryEntryRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private LibraryEntryRepository libraryEntryRepository;

    @Mock
    private LibraryEntryMapper libraryEntryMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private LibraryService libraryService;

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
    void addOrUpdateLibraryEntry_whenNew_createsEntryAndPublishesEvent() {
        var titleId = UUID.randomUUID();
        var entryId = UUID.randomUUID();
        var request = new LibraryEntryUpdateRequest(ReadingStatus.READING, null, null);

        var savedEntry = LibraryEntry.builder()
                .id(entryId).userId(userId).titleId(titleId).status(ReadingStatus.READING).build();
        var response = new LibraryEntryResponse(entryId, userId, titleId, ReadingStatus.READING, null, null,
                Instant.now(), Instant.now());

        when(libraryEntryRepository.findByUserIdAndTitleId(userId, titleId)).thenReturn(Optional.empty());
        when(libraryEntryMapper.partialUpdate(eq(request), any(LibraryEntry.class))).thenReturn(savedEntry);
        when(libraryEntryRepository.save(savedEntry)).thenReturn(savedEntry);
        when(libraryEntryMapper.toResponse(savedEntry)).thenReturn(response);

        var result = libraryService.addOrUpdateLibraryEntry(titleId, request);

        assertThat(result).isEqualTo(response);
        var captor = ArgumentCaptor.forClass(LibraryAddTitleEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().titleId()).isEqualTo(titleId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void addOrUpdateLibraryEntry_whenExisting_updatesEntry() {
        var titleId = UUID.randomUUID();
        var entryId = UUID.randomUUID();
        var request = new LibraryEntryUpdateRequest(ReadingStatus.COMPLETED, null, null);

        var existingEntry = LibraryEntry.builder()
                .id(entryId).userId(userId).titleId(titleId).status(ReadingStatus.READING).vote(null).build();
        var updatedEntry = LibraryEntry.builder()
                .id(entryId).userId(userId).titleId(titleId).status(ReadingStatus.COMPLETED).vote(null).build();
        var response = new LibraryEntryResponse(entryId, userId, titleId, ReadingStatus.COMPLETED, null, null,
                Instant.now(), Instant.now());

        when(libraryEntryRepository.findByUserIdAndTitleId(userId, titleId)).thenReturn(Optional.of(existingEntry));
        when(libraryEntryMapper.partialUpdate(request, existingEntry)).thenReturn(updatedEntry);
        when(libraryEntryRepository.save(updatedEntry)).thenReturn(updatedEntry);
        when(libraryEntryMapper.toResponse(updatedEntry)).thenReturn(response);

        var result = libraryService.addOrUpdateLibraryEntry(titleId, request);

        assertThat(result).isEqualTo(response);
        // No LibraryAddTitleEvent for update
        verify(eventPublisher, never()).publishEvent(any(LibraryAddTitleEvent.class));
    }

    @Test
    void addOrUpdateLibraryEntry_whenExistingWithNewVote_publishesVoteEvent() {
        var titleId = UUID.randomUUID();
        var entryId = UUID.randomUUID();
        var request = new LibraryEntryUpdateRequest(ReadingStatus.COMPLETED, VoteType.LIKE, null);

        var existingEntry = LibraryEntry.builder()
                .id(entryId).userId(userId).titleId(titleId).status(ReadingStatus.READING).vote(null).build();
        var updatedEntry = LibraryEntry.builder()
                .id(entryId).userId(userId).titleId(titleId).status(ReadingStatus.COMPLETED).vote(VoteType.LIKE).build();
        var response = new LibraryEntryResponse(entryId, userId, titleId, ReadingStatus.COMPLETED, VoteType.LIKE, null,
                Instant.now(), Instant.now());

        when(libraryEntryRepository.findByUserIdAndTitleId(userId, titleId)).thenReturn(Optional.of(existingEntry));
        when(libraryEntryMapper.partialUpdate(request, existingEntry)).thenReturn(updatedEntry);
        when(libraryEntryRepository.save(updatedEntry)).thenReturn(updatedEntry);
        when(libraryEntryMapper.toResponse(updatedEntry)).thenReturn(response);

        libraryService.addOrUpdateLibraryEntry(titleId, request);

        var captor = ArgumentCaptor.forClass(LibraryVoteTitleEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().titleId()).isEqualTo(titleId);
        assertThat(captor.getValue().vote()).isEqualTo(VoteType.LIKE);
    }

    @Test
    void removeFromLibrary_whenOwner_removesEntryAndPublishesEvent() {
        var entryId = UUID.randomUUID();
        var titleId = UUID.randomUUID();
        var entry = LibraryEntry.builder()
                .id(entryId).userId(userId).titleId(titleId).status(ReadingStatus.READING).build();

        when(libraryEntryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        libraryService.removeFromLibrary(entryId);

        verify(libraryEntryRepository).delete(entry);
        var captor = ArgumentCaptor.forClass(LibraryRemoveTitleEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().titleId()).isEqualTo(titleId);
        assertThat(captor.getValue().userId()).isEqualTo(userId);
    }

    @Test
    void removeFromLibrary_whenNotOwner_throwsAccessDeniedException() {
        var entryId = UUID.randomUUID();
        var otherUserId = UUID.randomUUID();
        var entry = LibraryEntry.builder()
                .id(entryId).userId(otherUserId).titleId(UUID.randomUUID()).status(ReadingStatus.READING).build();

        when(libraryEntryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        assertThatThrownBy(() -> libraryService.removeFromLibrary(entryId))
                .isInstanceOf(AccessDeniedException.class);

        verify(libraryEntryRepository, never()).delete(any(LibraryEntry.class));
    }

    @Test
    void getById_whenOwner_returnsResponse() {
        var entryId = UUID.randomUUID();
        var titleId = UUID.randomUUID();
        var entry = LibraryEntry.builder()
                .id(entryId).userId(userId).titleId(titleId).status(ReadingStatus.READING).build();
        var response = new LibraryEntryResponse(entryId, userId, titleId, ReadingStatus.READING, null, null,
                Instant.now(), Instant.now());

        when(libraryEntryRepository.findById(entryId)).thenReturn(Optional.of(entry));
        when(libraryEntryMapper.toResponse(entry)).thenReturn(response);

        var result = libraryService.getById(entryId);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getById_whenNotOwner_throwsAccessDeniedException() {
        var entryId = UUID.randomUUID();
        var otherUserId = UUID.randomUUID();
        var entry = LibraryEntry.builder()
                .id(entryId).userId(otherUserId).titleId(UUID.randomUUID()).status(ReadingStatus.READING).build();

        when(libraryEntryRepository.findById(entryId)).thenReturn(Optional.of(entry));

        assertThatThrownBy(() -> libraryService.getById(entryId))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void getById_whenNotFound_throwsException() {
        var entryId = UUID.randomUUID();

        when(libraryEntryRepository.findById(entryId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> libraryService.getById(entryId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

}
