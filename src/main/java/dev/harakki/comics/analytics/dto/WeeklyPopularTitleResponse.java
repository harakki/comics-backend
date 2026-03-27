package dev.harakki.comics.analytics.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.UUID;

@Schema(description = "Weekly top title item")
public record WeeklyPopularTitleResponse(

        @Schema(description = "Title unique identifier", example = "019b9d1e-bc3a-70f3-8520-36e8d82dc9e0")
        UUID titleId,

        @Schema(description = "Title name", example = "Chainsaw Man")
        String name,

        @Schema(description = "Title slug", example = "chainsaw-man")
        String slug,

        @Schema(description = "Views for the last 7 days", example = "1242")
        Long weeklyViews,

        @Schema(description = "Rank in top list", example = "1")
        Integer rank

) implements Serializable {
}

