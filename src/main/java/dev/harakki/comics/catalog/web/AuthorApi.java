package dev.harakki.comics.catalog.web;

import dev.harakki.comics.catalog.domain.Author;
import dev.harakki.comics.catalog.dto.AuthorCreateRequest;
import dev.harakki.comics.catalog.dto.AuthorResponse;
import dev.harakki.comics.catalog.dto.AuthorUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@Tag(name = "Authors", description = "Graphic literature authors/artists management")
public interface AuthorApi {

    @Operation(
            operationId = "getAuthor",
            summary = "Get author"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    AuthorResponse getAuthor(@Parameter(description = "Author UUID", required = true) @NotNull UUID id);

    @Operation(
            operationId = "searchAuthors",
            summary = "Search and filter authors"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of authors"),
            @ApiResponse(responseCode = "400", ref = "BadRequest")
    })
    @Parameters({
            @Parameter(name = "search", description = "Search by name or slug", example = "fujimoto"),
            @Parameter(name = "country", description = "Filter by Country ISO Code", example = "JP")
    })
    Page<AuthorResponse> getAuthors(@Parameter(hidden = true) Specification<Author> searchSpec,
                                    @Parameter(hidden = true) Specification<Author> filterSpec,
                                    @ParameterObject Pageable pageable);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "createAuthor",
            summary = "Create author"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully",
                    content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "400", ref = "BadRequest"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "409", ref = "Conflict")
    })
    AuthorResponse createAuthor(@Valid AuthorCreateRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "updateAuthor",
            summary = "Update author"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated"),
            @ApiResponse(responseCode = "400", ref = "BadRequest"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    AuthorResponse updateAuthor(@Parameter(description = "Author UUID", required = true) @NotNull UUID id,
                                @Valid AuthorUpdateRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "deleteAuthor",
            summary = "Delete author"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    void deleteAuthor(@Parameter(description = "Author UUID", required = true) @NotNull UUID id);

}
