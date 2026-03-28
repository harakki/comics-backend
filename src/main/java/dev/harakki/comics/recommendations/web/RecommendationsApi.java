package dev.harakki.comics.recommendations.web;

import dev.harakki.comics.recommendations.dto.PersonalRecommendationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Recommendations", description = "Personalized recommendations")
public interface RecommendationsApi {

    @SecurityRequirement(name = "bearer-jwt")
    @Operation(
            operationId = "getMyRecommendations",
            summary = "Get personal recommendations for current user"
    )
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonalRecommendationResponse.class))))
    @ApiResponse(responseCode = "401", ref = "Unauthorized")
    @ApiResponse(responseCode = "403", ref = "Forbidden")
    List<PersonalRecommendationResponse> getMyRecommendations(
            @Parameter(description = "Max number of recommendations", example = "10") Integer limit
    );

}
