package dev.harakki.comics.content.web;

import dev.harakki.comics.content.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Tag(name = "Chapters", description = "Title chapters management")
public interface ChapterApi {

    @Operation(
            operationId = "getChaptersInfoByTitle",
            summary = "Get chapters info for title"
    )
    @ApiResponse(responseCode = "200", description = "Chapters retrieved",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChapterSummaryResponse.class))))
    @ApiResponse(responseCode = "404", ref = "NotFound")
    List<ChapterSummaryResponse> getChaptersInfoByTitle(
            @Parameter(description = "Title UUID", required = true) @NotNull UUID titleId
    );

    @Operation(
            operationId = "getFullChapter",
            summary = "Get full chapter (including pages URLs)"
    )
    @ApiResponse(responseCode = "200", description = "Chapter details",
            content = @Content(schema = @Schema(implementation = ChapterDetailsResponse.class)))
    @ApiResponse(responseCode = "404", ref = "NotFound")
    ChapterDetailsResponse getFullChapter(
            @Parameter(description = "Chapter UUID", required = true) @NotNull UUID chapterId
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "createChapter",
            summary = "Create chapter"
    )
    @ApiResponse(responseCode = "201", description = "Chapter created")
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    void createChapter(
            @Parameter(description = "Title UUID", required = true) @NotNull UUID titleId,
            @Valid ChapterCreateRequest request
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "updateChapter",
            summary = "Update chapter"
    )
    @ApiResponse(responseCode = "200", description = "Chapter updated")
    @ApiResponse(responseCode = "400", ref = "BadRequest")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    void updateChapter(
            @Parameter(description = "Chapter UUID", required = true) @NotNull UUID chapterId,
            @Valid ChapterUpdateRequest request
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "deleteChapter",
            summary = "Delete chapter"
    )
    @ApiResponse(responseCode = "204", description = "Chapter deleted")
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    void deleteChapter(
            @Parameter(description = "Chapter UUID", required = true) @NotNull UUID chapterId
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "recordChapterRead",
            summary = "Record chapter as read and get next unread chapter"
    )
    @ApiResponse(responseCode = "200", description = "Chapter marked as read, next unread chapter returned",
            content = @Content(schema = @Schema(implementation = NextChapterResponse.class)))
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    @SecurityRequirement(name = "bearer-jwt")
    NextChapterResponse recordChapterRead(
            @Parameter(description = "Chapter UUID", required = true) @NotNull UUID chapterId,
            @Valid ChapterReadRequest request
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "isChapterRead",
            summary = "Check if chapter has been read by current user"
    )
    @ApiResponse(responseCode = "200", description = "Read status",
            content = @Content(schema = @Schema(implementation = ChapterReadStatusResponse.class)))
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    @SecurityRequirement(name = "bearer-jwt")
    ChapterReadStatusResponse isChapterRead(
            @Parameter(description = "Chapter UUID", required = true) @NotNull UUID chapterId
    );

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "getNextUnreadChapter",
            summary = "Get next unread chapter for a title"
    )
    @ApiResponse(responseCode = "200", description = "Next unread chapter",
            content = @Content(schema = @Schema(implementation = NextChapterResponse.class)))
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "404", ref = "NotFound")
    @SecurityRequirement(name = "bearer-jwt")
    NextChapterResponse getNextUnreadChapter(
            @Parameter(description = "Title UUID", required = true) @NotNull UUID titleId
    );

}
