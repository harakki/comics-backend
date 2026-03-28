package dev.harakki.comics.analytics.api;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface UserInteractionApi {

    interface TopViewedTitleProjection {

        UUID getTitleId();

        Long getWeeklyViews();

    }

    /**
     * Finds recent target IDs that the user has interacted with, ordered by most recent interaction.
     * @param userId User ID
     * @param type Interaction type with type {@link InteractionType}
     * @param pageable Pagination information
     * @return List of title IDs that the user has interacted with, ordered by most recent interaction
     */
    List<UUID> findRecentTargetIds(UUID userId, InteractionType type, Pageable pageable);

    /**
     * Finds recommended title IDs based on shared tags with the seed title IDs, excluding specified title IDs and limited by the given number.
     * @param seedTitleIds List of seed title IDs to find recommendations based on shared tags
     * @param excludedTitleIds List of title IDs to exclude from recommendations
     * @param limit Maximum number of recommended title IDs to return
     * @return List of recommended title IDs
     */
    List<UUID> findRecommendedTitleIdsBySharedTags(List<UUID> seedTitleIds, List<UUID> excludedTitleIds, int limit);

    /**
     * Finds top viewed titles in a time window.
     * @param since inclusive lower bound for interaction timestamp
     * @param limit maximum amount of titles to return
     * @return sorted list by views desc and title id asc
     */
    List<TopViewedTitleProjection> findTopViewedTitlesSince(Instant since, int limit);

}
