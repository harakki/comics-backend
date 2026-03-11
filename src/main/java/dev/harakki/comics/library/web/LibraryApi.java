package dev.harakki.comics.library.web;

import dev.harakki.comics.library.domain.LibraryEntry;
import dev.harakki.comics.library.dto.LibraryEntryResponse;
import dev.harakki.comics.library.dto.LibraryEntryUpdateRequest;
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

@Tag(name = "Library", description = "User's personal library management")
@SecurityRequirement(name = "bearer-jwt")
public interface LibraryApi {

    @Operation(
            operationId = "addOrUpdateLibraryEntry",
            summary = "Add or update library entry"
    )
    @ApiResponse(responseCode = "200", description = "Library entry added or updated",
            content = @Content(schema = @Schema(implementation = LibraryEntryResponse.class)))
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    LibraryEntryResponse addOrUpdateLibraryEntry(
            @Parameter(description = "Title UUID", required = true) @NotNull UUID titleId,
            @Valid LibraryEntryUpdateRequest request
    );

    @Operation(
            operationId = "getLibraryEntry",
            summary = "Get library entry by title ID"
    )
    @ApiResponse(responseCode = "200", description = "Library entry found",
            content = @Content(schema = @Schema(implementation = LibraryEntryResponse.class)))
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    LibraryEntryResponse getLibraryEntry(@Parameter(description = "Title UUID", required = true) @NotNull UUID titleId);

    @Operation(
            operationId = "getMyLibrary",
            summary = "Get my library"
    )
    @ApiResponse(responseCode = "200", description = "Library entries retrieved")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    Page<LibraryEntryResponse> getMyLibrary(
            @Parameter(hidden = true) Specification<LibraryEntry> spec,
            @ParameterObject Pageable pageable
    );

    @Operation(
            operationId = "deleteLibraryEntry",
            summary = "Delete library entry"
    )
    @ApiResponse(responseCode = "204", description = "Title removed from library")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    void deleteLibraryEntry(@Parameter(description = "Library entry UUID", required = true) @NotNull UUID entryId);

}
