package dev.harakki.comics.analytics.web;

import dev.harakki.comics.analytics.dto.TitleAnalyticsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Tag(name = "Analytics", description = "User interactions statistics")
public interface AnalyticsApi {

    @Operation(
            operationId = "getTitleAnalytics",
            summary = "Get analytics for title"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = TitleAnalyticsResponse.class))),
            @ApiResponse(responseCode = "404", ref = "NotFound")
    })
    TitleAnalyticsResponse getTitleAnalytics(@Parameter(description = "Title UUID", required = true) @NotNull UUID titleId);

}
