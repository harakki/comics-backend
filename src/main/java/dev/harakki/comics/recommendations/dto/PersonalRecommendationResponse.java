package dev.harakki.comics.recommendations.dto;

import java.io.Serializable;
import java.util.UUID;

public record PersonalRecommendationResponse(
        UUID titleId,
        String name,
        String slug,
        double score,
        String reason
) implements Serializable {
}
