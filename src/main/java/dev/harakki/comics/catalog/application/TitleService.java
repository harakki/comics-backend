package dev.harakki.comics.catalog.application;

import dev.harakki.comics.catalog.api.TitleCreatedEvent;
import dev.harakki.comics.catalog.api.TitleDeletedEvent;
import dev.harakki.comics.catalog.api.TitleUpdatedEvent;
import dev.harakki.comics.catalog.api.TitleViewedEvent;
import dev.harakki.comics.catalog.domain.*;
import dev.harakki.comics.catalog.dto.TitleCreateRequest;
import dev.harakki.comics.catalog.dto.TitleResponse;
import dev.harakki.comics.catalog.dto.TitleUpdateRequest;
import dev.harakki.comics.catalog.infrastructure.*;
import dev.harakki.comics.media.api.MediaDeleteRequestedEvent;
import dev.harakki.comics.media.api.MediaFixateRequestedEvent;
import dev.harakki.comics.shared.exception.ResourceAlreadyExistsException;
import dev.harakki.comics.shared.exception.ResourceInUseException;
import dev.harakki.comics.shared.exception.ResourceNotFoundException;
import dev.harakki.comics.shared.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TitleService {

    private final TitleRepository titleRepository;
    private final TitleMapper titleMapper;

    private final PublisherRepository publisherRepository;
    private final TagRepository tagRepository;
    private final AuthorRepository authorRepository;

    private final SlugGenerator slugGenerator;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public TitleResponse create(TitleCreateRequest request) {
        // TODO Cognitive Complexity of methods should not be too high
        if (titleRepository.existsByName(request.name())) {
            throw new ResourceAlreadyExistsException("Title with name '" + request.name() + "' already exists");
        }

        var title = titleMapper.toEntity(request);

        // Process slug
        var slug = request.slug();
        if (slug != null && !slug.isBlank()) {
            if (titleRepository.existsBySlug(slug)) {
                throw new ResourceAlreadyExistsException("Title with slug '" + slug + "' already exists");
            }
        } else {
            slug = slugGenerator.generate(request.name(), titleRepository::existsBySlug);
        }
        title.setSlug(slug);

        // Process main cover media
        if (request.mainCoverMediaId() != null) {
            eventPublisher.publishEvent(new MediaFixateRequestedEvent(request.mainCoverMediaId()));
        }

        // Process connection with authors
        if (request.authorIds() != null && !request.authorIds().isEmpty()) {
            Set<UUID> authorIds = request.authorIds().keySet();

            List<Author> authors = authorRepository.findAllById(authorIds);
            if (authors.size() != authorIds.size()) {
                throw new ResourceNotFoundException("One or more authors not found");
            }

            int sortOrder = 0;

            for (var author : authors) {
                var role = request.authorIds().get(author.getId());

                var titleAuthor = TitleAuthor.builder()
                        .title(title) // Bind to title
                        .author(author) // Bind to author
                        .role(role)
                        .sortOrder(sortOrder++) // 0, 1, 2, ...
                        .build();

                // Add to title's collection
                title.getAuthors().add(titleAuthor);
            }
        }

        // Process connection with publisher
        if (request.publisherIds() != null && !request.publisherIds().isEmpty()) {
            Set<UUID> publisherIds = new HashSet<>(request.publisherIds());

            List<Publisher> publishers = publisherRepository.findAllById(publisherIds);
            if (publishers.size() != publisherIds.size()) {
                throw new ResourceNotFoundException("One or more publishers not found");
            }

            int sortOrder = 0;

            for (var publisher : publishers) {
                var titlePublisher = TitlePublisher.builder()
                        .title(title) // Bind to title
                        .publisher(publisher) // Bind to publisher
                        .sortOrder(sortOrder++) // 0, 1, 2, ...
                        .build();

                // Add to title's collection
                title.getPublishers().add(titlePublisher);
            }
        }

        // Process connection with tags
        if (request.tagIds() != null && !request.tagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.tagIds()));
            if (tags.size() != request.tagIds().size()) {
                throw new ResourceNotFoundException("Some tags were not found");
            }
            title.setTags(tags);
        }

        try {
            title = titleRepository.save(title);
            log.info("Created title: id={}, name={}", title.getId(), title.getName());

            var userId = SecurityUtils.getOptionalCurrentUserId().orElse(null);
            eventPublisher.publishEvent(new TitleCreatedEvent(title.getId(), userId, title.getName()));

        } catch (DataIntegrityViolationException _) {
            throw new ResourceAlreadyExistsException("Title with this slug already exists");
        }

        return titleMapper.toResponse(title);
    }

    public TitleResponse getById(UUID id) {
        var response = titleRepository.findById(id)
                .map(titleMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Title with id " + id + " not found"));

        var userId = SecurityUtils.getOptionalCurrentUserId().orElse(null);
        eventPublisher.publishEvent(new TitleViewedEvent(response.id(), userId));

        return response;
    }

    public Page<TitleResponse> getAll(Specification<Title> spec, Pageable pageable) {
        return titleRepository.findAll(spec, pageable)
                .map(titleMapper::toResponse);
    }

    @Transactional
    public TitleResponse update(UUID id, TitleUpdateRequest request) {
        // TODO Cognitive Complexity of methods should not be too high
        var title = titleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Title with id " + id + " not found"));

        var oldMediaId = title.getMainCoverMediaId();
        var newMediaId = request.mainCoverMediaId();

        title = titleMapper.partialUpdate(request, title);

        // Process main cover media change
        if (!Objects.equals(oldMediaId, newMediaId)) {
            if (newMediaId != null) {
                eventPublisher.publishEvent(new MediaFixateRequestedEvent(newMediaId));
            }
            if (oldMediaId != null) {
                eventPublisher.publishEvent(new MediaDeleteRequestedEvent(oldMediaId));
            }
        }

        // Process slug change
        if (request.slug() != null && titleRepository.existsBySlugAndIdNot(request.slug(), id)) {
            throw new ResourceAlreadyExistsException("Title with slug '" + request.slug() + "' already exists");
        }

        // Process connection with authors
        if (request.authorIds() != null) {
            Set<UUID> authorIds = request.authorIds().keySet();
            title.getAuthors().clear();

            List<Author> authors = authorRepository.findAllById(authorIds);
            if (authors.size() != authorIds.size()) {
                throw new ResourceNotFoundException("One or more authors not found");
            }

            int sortOrder = 0;

            for (var author : authors) {
                var role = request.authorIds().get(author.getId());

                var titleAuthor = TitleAuthor.builder()
                        .title(title) // Bind to title
                        .author(author) // Bind to author
                        .role(role)
                        .sortOrder(sortOrder++) // 0, 1, 2, ...
                        .build();

                // Add to title's collection
                title.getAuthors().add(titleAuthor);
            }
        }

        // Process connection with publisher
        if (request.publisherIds() != null) {
            Set<UUID> publisherId = new HashSet<>(request.publisherIds());
            title.getPublishers().clear();

            List<Publisher> publishers = publisherRepository.findAllById(publisherId);
            if (publishers.size() != publisherId.size()) {
                throw new ResourceNotFoundException("One or more authors not found");
            }

            int sortOrder = 0;

            for (var publisher : publishers) {
                var titlePublisher = TitlePublisher.builder()
                        .title(title) // Bind to title
                        .publisher(publisher) // Bind to publisher
                        .sortOrder(sortOrder++) // 0, 1, 2, ...
                        .build();

                // Add to title's collection
                title.getPublishers().add(titlePublisher);
            }
        }

        // Process connection with tags
        if (request.tagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.tagIds()));
            if (tags.size() != request.tagIds().size()) {
                throw new ResourceNotFoundException("Some tags were not found");
            }
            title.setTags(tags);
        }

        title = titleRepository.save(title);
        log.debug("Updated title: id={}", id);

        var userId = SecurityUtils.getOptionalCurrentUserId().orElse(null);
        eventPublisher.publishEvent(new TitleUpdatedEvent(title.getId(), userId));

        return titleMapper.toResponse(title);
    }

    @Transactional
    public void delete(UUID id) {
        var title = titleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Title with id " + id + " not found"));
        try {
            titleRepository.delete(title);
            titleRepository.flush();

            if (title.getMainCoverMediaId() != null) {
                eventPublisher.publishEvent(new MediaDeleteRequestedEvent(title.getMainCoverMediaId()));
            }

            var userId = SecurityUtils.getOptionalCurrentUserId().orElse(null);
            eventPublisher.publishEvent(new TitleDeletedEvent(title.getId(), userId));

            log.info("Deleted title: id={}", id);
        } catch (DataIntegrityViolationException _) {
            throw new ResourceInUseException("Cannot delete title with id " + id + " because it is referenced by other resources");
        }
    }

}
