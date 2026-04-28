package dev.harakki.comics.catalog.web;

import dev.harakki.comics.catalog.application.TitleService;
import dev.harakki.comics.catalog.domain.Title;
import dev.harakki.comics.catalog.dto.TitleCreateRequest;
import dev.harakki.comics.catalog.dto.TitleResponse;
import dev.harakki.comics.catalog.dto.TitleUpdateRequest;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/titles", produces = MediaType.APPLICATION_JSON_VALUE)
class TitleController implements TitleApi {

    private final TitleService titleService;

    @GetMapping("/{id}")
    public TitleResponse getTitle(@PathVariable UUID id) {
        return titleService.getById(id);
    }

    @GetMapping
    public Page<TitleResponse> getTitles(
            @Or({
                    @Spec(path = "name", params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = "slug", params = "search", spec = LikeIgnoreCase.class)
            }) Specification<Title> searchSpec,
            @Join(path = "authors", alias = "a")
            @Join(path = "publishers", alias = "p")
            @Join(path = "tags", alias = "t")
            @And({
                    @Spec(path = "type", spec = In.class),
                    @Spec(path = "titleStatus", spec = In.class),
                    @Spec(path = "countryIsoCode", params = "country", spec = Equal.class),
                    @Spec(path = "releaseYear", params = "releaseYear", spec = Equal.class),
                    @Spec(path = "releaseYear", params = "yearFrom", spec = GreaterThanOrEqual.class),
                    @Spec(path = "releaseYear", params = "yearTo", spec = LessThanOrEqual.class),
                    @Spec(path = "contentRating", params = "contentRating", spec = LessThanOrEqual.class),
                    @Spec(path = "a.author.id", params = "authorId", spec = Equal.class),
                    @Spec(path = "p.publisher.id", params = "publisherId", spec = Equal.class),
                    @Spec(path = "t.id", params = "tags", spec = In.class)
            }) Specification<Title> filterSpec,
            @PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Specification<Title> spec = Specification.where(searchSpec).and(filterSpec);
        return titleService.getAll(spec, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TitleResponse createTitle(@RequestBody TitleCreateRequest request) {
        return titleService.create(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public TitleResponse updateTitle(
            @PathVariable UUID id,
            @RequestBody TitleUpdateRequest request
    ) {
        return titleService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTitle(@PathVariable UUID id) {
        titleService.delete(id);
    }

}
