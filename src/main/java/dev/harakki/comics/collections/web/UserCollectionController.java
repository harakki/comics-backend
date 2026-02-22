package dev.harakki.comics.collections.web;

import dev.harakki.comics.collections.application.CollectionService;
import dev.harakki.comics.collections.dto.CollectionCreateRequest;
import dev.harakki.comics.collections.dto.CollectionUpdateRequest;
import dev.harakki.comics.collections.dto.UserCollectionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/collections", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserCollectionController implements UserCollectionApi {

    private final CollectionService collectionService;

    @GetMapping("/{id}")
    public UserCollectionResponse getCollection(@PathVariable UUID id) {
        return collectionService.getById(id);
    }

    @GetMapping
    public Page<UserCollectionResponse> getCollections(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return collectionService.search(search, pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public Page<UserCollectionResponse> getMyCollections(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return collectionService.getMyCollections(search, pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCollectionResponse createCollection(@RequestBody CollectionCreateRequest request) {
        return collectionService.create(request);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}")
    public UserCollectionResponse updateCollection(
            @PathVariable UUID id,
            @RequestBody CollectionUpdateRequest request
    ) {
        return collectionService.update(id, request);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCollection(@PathVariable UUID id) {
        collectionService.delete(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/titles/{titleId}")
    public UserCollectionResponse addTitle(
            @PathVariable UUID id,
            @PathVariable UUID titleId
    ) {
        return collectionService.addTitle(id, titleId);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}/titles/{titleId}")
    public UserCollectionResponse removeTitle(
            @PathVariable UUID id,
            @PathVariable UUID titleId
    ) {
        return collectionService.removeTitle(id, titleId);
    }

}
