package dev.harakki.comics.media.web;

import dev.harakki.comics.media.dto.MediaUploadUrlRequest;
import dev.harakki.comics.media.dto.MediaUploadUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Tag(name = "Media", description = "S3 objects management")
public interface MediaApi {

    @Operation(
            operationId = "getMediaUrl",
            summary = "Get Download Presigned URL"
    )
    @ApiResponse(responseCode = "200", description = "URL retrieved",
            content = @Content(schema = @Schema(type = "string", example = "https://s3.aws.com/bucket/uploads/uuid/cover.jpg?signature=...")))
    @ApiResponse(responseCode = "404", ref = "NotFound")
    String getMediaUrl(@Parameter(description = "Media UUID", required = true) @NotNull UUID id);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "generateUploadUrl",
            summary = "Get Upload Presigned URL"
    )
    @ApiResponse(responseCode = "200", description = "Presigned URL generated",
            content = @Content(schema = @Schema(implementation = MediaUploadUrlResponse.class)))
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    MediaUploadUrlResponse createMedia(@Valid MediaUploadUrlRequest request);

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "deleteMedia",
            summary = "Delete media"
    )
    @ApiResponse(responseCode = "204", description = "Media deleted")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    void deleteMedia(@Parameter(description = "Media UUID", required = true) @NotNull UUID id);

}
