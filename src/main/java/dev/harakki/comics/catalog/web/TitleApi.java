package dev.harakki.comics.catalog.web;

import dev.harakki.comics.catalog.domain.Title;
import dev.harakki.comics.catalog.dto.TitleCreateRequest;
import dev.harakki.comics.catalog.dto.TitleResponse;
import dev.harakki.comics.catalog.dto.TitleUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@Tag(name = "Titles", description = "Graphic literature management")
public interface TitleApi {

    @Operation(
            operationId = "getTitle",
            summary = "Get title"
    )
    @ApiResponse(responseCode = "200", description = "Title found",
            content = @Content(schema = @Schema(implementation = TitleResponse.class)))
    @ApiResponse(responseCode = "404", ref = "NotFound")
    TitleResponse getTitle(@Parameter(description = "Title UUID", required = true) @NotNull UUID id);

    @Operation(
            operationId = "searchTitles",
            summary = "Search and filter titles"
    )
    @ApiResponse(responseCode = "200", description = "Page of titles")
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @Parameter(name = "search", description = "Search text", example = "chainsaw man")
    @Parameter(name = "type", description = "Filter by type", example = "MANGA")
    @Parameter(name = "titleStatus", description = "Filter by status", example = "COMPLETED")
    @Parameter(name = "country", description = "Filter by Country ISO Code", example = "JP")
    @Parameter(name = "tags", description = "Filter by tag slugs", example = "action,shonen")
    @Parameter(name = "releaseYear", description = "Release year", example = "2018")
    @Parameter(name = "yearFrom", description = "Min release year", example = "2000")
    @Parameter(name = "yearTo", description = "Max release year", example = "2020")
    @Parameter(name = "contentRating", description = "Max content rating", example = "EIGHTEEN_PLUS")
    Page<TitleResponse> getTitles(
            @Parameter(hidden = true) Specification<Title> searchSpec,
            @Parameter(hidden = true) Specification<Title> filterSpec,
            @ParameterObject Pageable pageable
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "createTitle",
            summary = "Create title"
    )
    @ApiResponse(responseCode = "201", description = "Title created successfully",
            content = @Content(schema = @Schema(implementation = TitleResponse.class)))
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "409", ref = "Conflict")
    TitleResponse createTitle(@Valid TitleCreateRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "updateTitle",
            summary = "Update title"
    )
    @ApiResponse(responseCode = "200", description = "Title updated",
            content = @Content(schema = @Schema(implementation = TitleResponse.class)))
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    TitleResponse updateTitle(
            @Parameter(description = "Title UUID", required = true) @NotNull UUID id,
            @Valid TitleUpdateRequest request
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "deleteTitle",
            summary = "Delete title"
    )
    @ApiResponse(responseCode = "204", description = "Title deleted")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    void deleteTitle(@Parameter(description = "Title UUID", required = true) @NotNull UUID id);

}
