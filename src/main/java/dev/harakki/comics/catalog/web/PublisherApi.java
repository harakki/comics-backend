package dev.harakki.comics.catalog.web;

import dev.harakki.comics.catalog.domain.Publisher;
import dev.harakki.comics.catalog.dto.PublisherCreateRequest;
import dev.harakki.comics.catalog.dto.PublisherResponse;
import dev.harakki.comics.catalog.dto.PublisherUpdateRequest;
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

@Tag(name = "Publishers", description = "Publishing houses management")
public interface PublisherApi {

    @Operation(
            operationId = "getPublisher",
            summary = "Get publisher"
    )
    @ApiResponse(responseCode = "200", description = "Publisher found",
            content = @Content(schema = @Schema(implementation = PublisherResponse.class)))
    @ApiResponse(responseCode = "404", ref = "NotFound")
    PublisherResponse getPublisher(
            @Parameter(description = "Publisher UUID", required = true) @NotNull UUID id
    );

    @Operation(
            operationId = "searchPublishers",
            summary = "Search and filter publishers"
    )
    @Parameter(name = "search", description = "Search by name or slug", example = "shueisha")
    @Parameter(name = "country", description = "Filter by Country ISO Code", example = "JP")
    @ApiResponse(responseCode = "200", description = "Page of publishers")
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    Page<PublisherResponse> getPublishers(
            @Parameter(hidden = true) Specification<Publisher> searchSpec,
            @Parameter(hidden = true) Specification<Publisher> filterSpec,
            @ParameterObject Pageable pageable
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "createPublisher",
            summary = "Create publisher"
    )
    @ApiResponse(responseCode = "201", description = "Publisher created",
            content = @Content(schema = @Schema(implementation = PublisherResponse.class)))
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "409", ref = "Conflict")
    PublisherResponse createPublisher(PublisherCreateRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "updatePublisher",
            summary = "Update publisher"
    )
    @ApiResponse(responseCode = "200", description = "Publisher updated",
            content = @Content(schema = @Schema(implementation = PublisherResponse.class)))
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    PublisherResponse updatePublisher(
            @Parameter(description = "Publisher UUID", required = true) @NotNull UUID id, @Valid PublisherUpdateRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "deletePublisher",
            summary = "Delete publisher"
    )
    @ApiResponse(responseCode = "204", description = "Publisher deleted")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    void deletePublisher(
            @Parameter(description = "Publisher UUID", required = true) @NotNull UUID id
    );

}
