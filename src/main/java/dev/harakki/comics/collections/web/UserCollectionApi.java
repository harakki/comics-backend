package dev.harakki.comics.collections.web;

import dev.harakki.comics.collections.dto.CollectionCreateRequest;
import dev.harakki.comics.collections.dto.CollectionUpdateRequest;
import dev.harakki.comics.collections.dto.UserCollectionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.UUID;

@Tag(name = "Collections", description = "User collections management")
public interface UserCollectionApi {

    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-jwt"),
            @SecurityRequirement(name = "")
    })
    @Operation(
            operationId = "getCollection",
            summary = "Get collection"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Collection retrieved",
                    content = @Content(schema = @Schema(implementation = UserCollectionResponse.class))),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    UserCollectionResponse getCollection(
            @Parameter(description = "Collection UUID", required = true) @NotNull UUID id
    );

    @Operation(
            operationId = "searchCollections",
            summary = "Search public collections"
    )
    Page<UserCollectionResponse> getCollections(
            @Parameter(description = "Search query") String search,
            @ParameterObject Pageable pageable
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "getMyCollections",
            summary = "Get my collections"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Collections retrieved",
                    content = @Content(schema = @Schema(implementation = UserCollectionResponse.class))),
            @ApiResponse(responseCode = "401", ref = "Unauthorized")
    })
    Page<UserCollectionResponse> getMyCollections(
            @Parameter(description = "Search query") String search,
            @ParameterObject Pageable pageable
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "createCollection",
            summary = "Create collection"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Collection created",
                    content = @Content(schema = @Schema(implementation = UserCollectionResponse.class))),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "400", ref = "BadRequest")
    })
    UserCollectionResponse createCollection(@Valid CollectionCreateRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "updateCollection",
            summary = "Update collection"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Collection updated",
                    content = @Content(schema = @Schema(implementation = UserCollectionResponse.class))),
            @ApiResponse(responseCode = "400", ref = "BadRequest"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    UserCollectionResponse updateCollection(
            @Parameter(description = "Collection UUID", required = true) @NotNull UUID id,
            @Valid CollectionUpdateRequest request
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "deleteCollection",
            summary = "Delete collection"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Collection deleted"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    void deleteCollection(
            @Parameter(description = "Collection UUID", required = true) @NotNull UUID id
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "addTitleToCollection",
            summary = "Add a title to collection"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Title added to collection",
                    content = @Content(schema = @Schema(implementation = UserCollectionResponse.class))),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    UserCollectionResponse addTitle(
            @Parameter(description = "Collection UUID", required = true) @NotNull UUID id,
            @Parameter(description = "Title UUID", required = true) @NotNull UUID titleId
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "removeTitleFromCollection",
            summary = "Remove a title from collection"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Title removed from collection",
                    content = @Content(schema = @Schema(implementation = UserCollectionResponse.class))),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    UserCollectionResponse removeTitle(
            @Parameter(description = "Collection UUID", required = true) @NotNull UUID id,
            @Parameter(description = "Title UUID", required = true) @NotNull UUID titleId
    );

}
