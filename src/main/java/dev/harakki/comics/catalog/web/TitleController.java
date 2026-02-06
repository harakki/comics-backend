package dev.harakki.comics.catalog.web;

import dev.harakki.comics.catalog.application.TitleService;
import dev.harakki.comics.catalog.domain.Title;
import dev.harakki.comics.catalog.domain.Title_;
import dev.harakki.comics.catalog.dto.TitleCreateRequest;
import dev.harakki.comics.catalog.dto.TitleResponse;
import dev.harakki.comics.catalog.dto.TitleUpdateRequest;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                    @Spec(path = Title_.NAME, params = "search", spec = LikeIgnoreCase.class),
                    @Spec(path = Title_.SLUG, params = "search", spec = LikeIgnoreCase.class)
            }) Specification<Title> searchSpec,
            @Join(path = Title_.TAGS, alias = "t", type = JoinType.LEFT)
            @And({
                    @Spec(path = Title_.TYPE, spec = In.class),
                    @Spec(path = Title_.TITLE_STATUS, spec = In.class),
                    @Spec(path = Title_.COUNTRY_ISO_CODE, params = "country", spec = Equal.class),
                    @Spec(path = Title_.RELEASE_YEAR, params = "releaseYear", spec = Equal.class),
                    @Spec(path = Title_.RELEASE_YEAR, params = "yearFrom", spec = GreaterThanOrEqual.class),
                    @Spec(path = Title_.RELEASE_YEAR, params = "yearTo", spec = LessThanOrEqual.class),
                    @Spec(path = Title_.CONTENT_RATING, params = "contentRating", spec = LessThanOrEqual.class),
                    @Spec(path = "t.slug", params = "tags", spec = In.class)
            }) Specification<Title> filterSpec,
            @PageableDefault(sort = Title_.NAME) Pageable pageable
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
