package dev.harakki.comics.recommendations.api;

import java.util.UUID;

public class RecommendationsApi {

    public interface ScoredTitleProjection {

        UUID getTitleId();

        double getScore();

        String getReason();

    }

}
