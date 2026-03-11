package dev.harakki.comics.catalog.web;

import dev.harakki.comics.catalog.dto.TagCreateRequest;
import dev.harakki.comics.catalog.dto.TagResponse;
import dev.harakki.comics.catalog.dto.TagUpdateRequest;
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

import java.util.UUID;

@Tag(name = "Tags", description = "Tags management")
public interface TagApi {

    @Operation(
            operationId = "getTag",
            summary = "Get tag"
    )
    @ApiResponse(responseCode = "200", description = "Tag found",
            content = @Content(schema = @Schema(implementation = TagResponse.class)))
    @ApiResponse(responseCode = "404", ref = "NotFound")
    TagResponse getTag(@Parameter(description = "Tag UUID", required = true) @NotNull UUID id);

    @Operation(
            operationId = "getTags",
            summary = "Get all tags"
    )
    @ApiResponse(responseCode = "200", description = "Page of tags")
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    Page<TagResponse> getTags(@ParameterObject Pageable pageable);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "createTag",
            summary = "Create tag"
    )
    @ApiResponse(responseCode = "201", description = "Tag created",
            content = @Content(schema = @Schema(implementation = TagResponse.class)))
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "409", ref = "Conflict")
    TagResponse createTag(@Valid TagCreateRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "updateTag",
            summary = "Update tag"
    )
    @ApiResponse(responseCode = "200", description = "Tag updated",
            content = @Content(schema = @Schema(implementation = TagResponse.class)))
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    TagResponse updateTag(@Parameter(description = "Tag UUID", required = true) @NotNull UUID id,
                          @Valid TagUpdateRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "deleteTag",
            summary = "Delete tag"
    )
    @ApiResponse(responseCode = "204", description = "Tag deleted")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    void deleteTag(@Parameter(description = "Tag UUID", required = true) @NotNull UUID id);

}
