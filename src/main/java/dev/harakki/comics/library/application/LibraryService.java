package dev.harakki.comics.library.application;

import dev.harakki.comics.library.api.LibraryAddTitleEvent;
import dev.harakki.comics.library.api.LibraryRemoveTitleEvent;
import dev.harakki.comics.library.api.LibraryVoteTitleEvent;
import dev.harakki.comics.library.domain.LibraryEntry;
import dev.harakki.comics.library.domain.ReadingStatus;
import dev.harakki.comics.library.dto.LibraryEntryResponse;
import dev.harakki.comics.library.dto.LibraryEntryUpdateRequest;
import dev.harakki.comics.library.infrastructure.LibraryEntryMapper;
import dev.harakki.comics.library.infrastructure.LibraryEntryRepository;
import dev.harakki.comics.shared.exception.ResourceAlreadyExistsException;
import dev.harakki.comics.shared.exception.ResourceNotFoundException;
import dev.harakki.comics.shared.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryService {

    private final LibraryEntryRepository libraryEntryRepository;
    private final LibraryEntryMapper libraryEntryMapper;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public LibraryEntryResponse addOrUpdateLibraryEntry(UUID titleId, @Valid LibraryEntryUpdateRequest request) {
        UUID currentUserId = getCurrentUserId();

        var existingEntry = libraryEntryRepository.findByUserIdAndTitleId(currentUserId, titleId);

        if (existingEntry.isPresent()) {
            var entry = existingEntry.get();
            var oldVote = entry.getVote();

            entry = libraryEntryMapper.partialUpdate(request, entry);
            entry = libraryEntryRepository.save(entry);

            log.debug("Updated library entry via addOrUpdate: titleId={}", titleId);

            var newVote = entry.getVote();
            if (newVote != null && !newVote.equals(oldVote)) {
                eventPublisher.publishEvent(new LibraryVoteTitleEvent(entry.getTitleId(), currentUserId, newVote));
                log.debug("Published TitleVoteEvent for updated library entry: titleId={}, userId={}, rating={}",
                        entry.getTitleId(), currentUserId, newVote);
            }

            return libraryEntryMapper.toResponse(entry);
        } else {
            var entry = new LibraryEntry();
            entry.setUserId(currentUserId);
            entry.setTitleId(titleId);

            // Применить данные из request
            entry = libraryEntryMapper.partialUpdate(request, entry);

            try {
                entry = libraryEntryRepository.save(entry);
                libraryEntryRepository.flush();

                eventPublisher.publishEvent(new LibraryAddTitleEvent(titleId, currentUserId));
                log.info("Added title {} to library for user {} via addOrUpdate", titleId, currentUserId);
            } catch (DataIntegrityViolationException e) {
                throw new ResourceAlreadyExistsException("Title already exists in library");
            }

            if (entry.getVote() != null) {
                eventPublisher.publishEvent(new LibraryVoteTitleEvent(titleId, currentUserId, entry.getVote()));
                log.debug("Published TitleVoteEvent for new library entry: titleId={}, userId={}, rating={}",
                        titleId, currentUserId, entry.getVote());
            }

            return libraryEntryMapper.toResponse(entry);
        }
    }

    @Transactional
    public void removeFromLibrary(UUID entryId) {
        UUID currentUserId = getCurrentUserId();

        var entry = libraryEntryRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Library entry not found"));

        if (!entry.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You don't have permission to delete this entry");
        }

        libraryEntryRepository.delete(entry);

        eventPublisher.publishEvent(new LibraryRemoveTitleEvent(entry.getTitleId(), currentUserId));
        log.info("Removed library entry: id={} for user {}", entryId, currentUserId);
    }

    // TODO entry и title IDs создают путаницу, необходимо избавиться от чего-то одного
    public LibraryEntryResponse getById(UUID entryId) {
        UUID currentUserId = getCurrentUserId();

        var entry = libraryEntryRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Library entry not found"));

        if (!entry.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You don't have permission to view this entry");
        }

        return libraryEntryMapper.toResponse(entry);
    }

    public LibraryEntryResponse getByTitleId(UUID titleId) {
        UUID currentUserId = getCurrentUserId();

        return libraryEntryRepository.findByUserIdAndTitleId(currentUserId, titleId)
                .map(libraryEntryMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Library entry not found"));
    }

    public Page<LibraryEntryResponse> getMyLibrary(Pageable pageable) {
        UUID currentUserId = getCurrentUserId();
        return libraryEntryRepository.findByUserId(currentUserId, pageable)
                .map(libraryEntryMapper::toResponse);
    }

    public Page<LibraryEntryResponse> getMyLibraryByStatus(ReadingStatus status, Pageable pageable) {
        UUID currentUserId = getCurrentUserId();
        return libraryEntryRepository.findByUserIdAndStatus(currentUserId, status, pageable)
                .map(libraryEntryMapper::toResponse);
    }

    public Page<LibraryEntryResponse> getUserLibrary(UUID userId, Pageable pageable) {
        UUID currentUserId = getCurrentUserId();
        if (!userId.equals(currentUserId)) {
            throw new AccessDeniedException("You don't have permission to view this user's library");
        }

        return libraryEntryRepository.findByUserId(userId, pageable)
                .map(libraryEntryMapper::toResponse);
    }

    public Page<LibraryEntryResponse> searchLibrary(Specification<LibraryEntry> spec, Pageable pageable) {
        UUID currentUserId = getCurrentUserId();

        // Add filter for current user
        Specification<LibraryEntry> userSpec = (root, query, cb) ->
                cb.equal(root.get("userId"), currentUserId);

        Specification<LibraryEntry> finalSpec = Specification.where(userSpec).and(spec);

        return libraryEntryRepository.findAll(finalSpec, pageable)
                .map(libraryEntryMapper::toResponse);
    }

    private UUID getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }

}
