package dev.harakki.comics.content.web;

import dev.harakki.comics.content.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chapters retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChapterSummaryResponse.class)))),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    List<ChapterSummaryResponse> getChaptersInfoByTitle(
            @Parameter(description = "Title UUID", required = true) @NotNull UUID titleId
    );

    @Operation(
            operationId = "getFullChapter",
            summary = "Get full chapter (including pages URLs)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chapter details",
                    content = @Content(schema = @Schema(implementation = ChapterDetailsResponse.class))),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    ChapterDetailsResponse getFullChapter(
            @Parameter(description = "Chapter UUID", required = true) @NotNull UUID chapterId
    );

    @Operation(
            operationId = "createChapter",
            summary = "Create chapter"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Chapter created"),
            @ApiResponse(responseCode = "400", ref = "BadRequest"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    void createChapter(
            @Parameter(description = "Title UUID", required = true) @NotNull UUID titleId,
            @Valid ChapterCreateRequest request
    );

    @Operation(
            operationId = "updateChapter",
            summary = "Update chapter"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chapter updated"),
            @ApiResponse(responseCode = "400", ref = "BadRequest"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    void updateChapter(
            @Parameter(description = "Chapter UUID", required = true) @NotNull UUID chapterId,
            @Valid ChapterUpdateRequest request
    );

    @Operation(
            operationId = "deleteChapter",
            summary = "Delete chapter"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Chapter deleted"),
            @ApiResponse(responseCode = "401", ref = "Unauthorized"),
            @ApiResponse(responseCode = "403", ref = "Forbidden"),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    void deleteChapter(
            @Parameter(description = "Chapter UUID", required = true) @NotNull UUID chapterId
    );

}
