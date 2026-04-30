package dev.harakki.comics.analytics.api;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface UserInteractionApi {

    interface ScoredTitleProjection {

        UUID getTitleId();

        double getScore();

        String getReason();

    }

    interface TopViewedTitleProjection {

        UUID getTitleId();

        Long getViews();

    }

    interface UserVoteProjection {

        java.util.UUID getTargetId();

        String getVoteType();

        Instant getOccurredAt();

    }

    interface CoLikedProjection {

        UUID getTitleId();

        Long getCoLikeCount();

    }

    /**
     * Finds recent target IDs that the user has interacted with, ordered by most recent interaction.
     *
     * @param userId   User ID
     * @param type     Interaction type with type {@link InteractionType}
     * @param pageable Pagination information
     * @return List of title IDs that the user has interacted with, ordered by most recent interaction
     */
    List<UUID> findRecentTargetIds(UUID userId, InteractionType type, Pageable pageable);

    /**
     * Finds recommended title IDs based on shared tags with the seed title IDs, excluding specified title IDs and limited by the given number.
     *
     * @param seedTitleIds     List of seed title IDs to find recommendations based on shared tags
     * @param excludedTitleIds List of title IDs to exclude from recommendations
     * @param limit            Maximum number of recommended title IDs to return
     * @return List of recommended title IDs
     */
    List<UUID> findRecommendedTitleIdsBySharedTags(List<UUID> seedTitleIds, List<UUID> excludedTitleIds, int limit);

    /**
     * Finds top viewed titles in a time window.
     *
     * @param since inclusive lower bound for interaction timestamp
     * @param limit maximum amount of titles to return
     * @return sorted list by views desc and title id asc
     */
    List<TopViewedTitleProjection> findTopViewedTitlesSince(Instant since, int limit);

    /**
     * Finds recent vote interactions (LIKE / DISLIKE) by the user ordered by most recent.
     *
     * @param userId User ID
     * @param limit  Maximum number of interactions to return
     * @return List of recent vote interactions by the user, ordered by most recent
     */
    List<UserVoteProjection> findRecentVotesByUser(UUID userId, int limit);

    /**
     * Finds titles that are most often liked by users who also liked given seed titles.
     *
     * @param likedTitleIds    List of seed title IDs that users have liked
     * @param excludedTitleIds List of title IDs to exclude from the results (e.g. already interacted titles)
     * @param limit            Maximum number of co-liked titles to return
     * @return List of co-liked titles with their associated co-like counts, ordered by co-like count desc and title id asc
     */
    List<CoLikedProjection> findTopCoLikedTitles(List<UUID> likedTitleIds, List<UUID> excludedTitleIds, int limit);

    /**
     * Finds candidate titles for collaborative filtering recommendations based on the user's liked titles.
     *
     * @param userId        ID of the user for whom to find candidates
     * @param likedTitleIds List of title IDs that the user has liked
     * @param excludedIds   List if title IDs to exclude from the results (e.g. already interacted titles)
     * @param limit         Maximum number of candidate titles to return
     * @return List of candidate titles with their associated scores for collaborative filtering recommendations
     */
    List<ScoredTitleProjection> findCollabCandidates(UUID userId,
                                                     List<UUID> likedTitleIds,
                                                     List<UUID> excludedIds,
                                                     int limit);

    /**
     * Finds popular titles since a given timestamp, excluding specified title IDs and limited by the given number.
     *
     * @param since       Timestamp since when to consider interactions for determining popularity (inclusive lower bound)
     * @param excludedIds List if title IDs to exclude from the results (e.g. already interacted titles)
     * @param limit       Maximum number of candidate titles to return
     * @return List of popular titles with their associated scores for collaborative filtering recommendations
     */
    List<ScoredTitleProjection> findPopularSince(Instant since, List<UUID> excludedIds, int limit);

}
