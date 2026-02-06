package dev.harakki.comics.catalog.web;

import dev.harakki.comics.catalog.dto.TagCreateRequest;
import dev.harakki.comics.catalog.dto.TagResponse;
import dev.harakki.comics.catalog.dto.TagUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tag found",
                    content = @Content(schema = @Schema(implementation = TagResponse.class))),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    public TagResponse getTag(@Parameter(description = "Tag UUID", required = true) @NotNull UUID id);

    @Operation(
            operationId = "getTags",
            summary = "Get all tags"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of tags",
                    content = @Content(schema = @Schema(implementation = TagResponse.class))),
            @ApiResponse(responseCode = "400", ref = "BadRequest")
    })
    public Page<TagResponse> getTags(@ParameterObject Pageable pageable);

    @Operation(
            operationId = "createTag",
            summary = "Create tag"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tag created",
                    content = @Content(schema = @Schema(implementation = TagResponse.class))),
            @ApiResponse(responseCode = "400", ref = "BadRequest"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "409", ref = "Conflict")
    })
    public TagResponse createTag(@Valid TagCreateRequest request);

    @Operation(
            operationId = "updateTag",
            summary = "Update tag"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tag updated",
                    content = @Content(schema = @Schema(implementation = TagResponse.class))),
            @ApiResponse(responseCode = "400", ref = "BadRequest"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    public TagResponse updateTag(@Parameter(description = "Tag UUID", required = true) @NotNull UUID id,
                                 @Valid TagUpdateRequest request);

    @Operation(
            operationId = "deleteTag",
            summary = "Delete tag"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tag deleted"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    public void deleteTag(@Parameter(description = "Tag UUID", required = true) @NotNull UUID id);

}
