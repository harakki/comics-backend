package dev.harakki.comics.analytics.infrastructure;

import dev.harakki.comics.analytics.api.InteractionType;
import dev.harakki.comics.analytics.api.UserInteractionApi;
import dev.harakki.comics.analytics.domain.UserInteraction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserInteractionRepository extends UserInteractionApi, JpaRepository<UserInteraction, Long> {

    long countByTargetIdAndType(UUID targetId, InteractionType type);

    @Query(value = """
                SELECT COALESCE(
                    ROUND(
                        AVG(
                            CASE
                                WHEN metadata ->> 'voteType' = 'LIKE' THEN 1
                                WHEN metadata ->> 'voteType' = 'DISLIKE' THEN 0
                            END
                        ) * 100,
                        1
                    ),
                    0
                )
                FROM (
                    SELECT DISTINCT ON (user_id)
                        user_id,
                        metadata
                    FROM user_interactions
                    WHERE target_id = :titleId
                      AND type = 'TITLE_VOTED'
                    ORDER BY user_id, occurred_at DESC
                ) t
            """, nativeQuery = true)
    Double getAverageRating(UUID titleId);

    @Query(value = """
            SELECT
                target_id AS titleId,
                COUNT(*) AS views
            FROM user_interactions
            WHERE type = 'TITLE_VIEWED'
              AND occurred_at >= :since
            GROUP BY target_id
            ORDER BY views DESC, titleId ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<UserInteractionApi.TopViewedTitleProjection> findTopViewedTitlesSince(Instant since, int limit);

    @Query(value = """
            SELECT
                target_id AS titleId,
                COUNT(*) AS views
            FROM user_interactions
            WHERE type = 'TITLE_VIEWED'
            GROUP BY target_id
            ORDER BY views DESC, titleId ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<UserInteractionApi.TopViewedTitleProjection> findTopViewedTitles(int limit);

    @Query(value = """
            SELECT ui.target_id AS targetId,
                   ui.metadata ->> 'voteType' AS voteType,
                   ui.occurred_at AS occurredAt
            FROM user_interactions ui
            WHERE ui.user_id = :userId
              AND ui.type = 'TITLE_VOTED'
            ORDER BY ui.occurred_at DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<UserInteractionApi.UserVoteProjection> findRecentVotesByUser(UUID userId, int limit);

    @Query(value = """
            SELECT ui2.target_id AS titleId,
                   COUNT(DISTINCT ui2.user_id) AS coLikeCount
            FROM user_interactions ui1
                     JOIN user_interactions ui2 ON ui1.user_id = ui2.user_id
            WHERE ui1.type = 'TITLE_VOTED'
              AND ui1.metadata ->> 'voteType' = 'LIKE'
              AND ui1.target_id IN (:likedTitleIds)
              AND ui2.type = 'TITLE_VOTED'
              AND ui2.metadata ->> 'voteType' = 'LIKE'
              AND ui2.target_id NOT IN (:excludedTitleIds)
            GROUP BY ui2.target_id
            ORDER BY coLikeCount DESC, ui2.target_id ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<UserInteractionApi.CoLikedProjection> findTopCoLikedTitles(List<UUID> likedTitleIds, List<UUID> excludedTitleIds, int limit);

    boolean existsByUserIdAndTargetIdAndType(UUID userId, UUID targetId, InteractionType type);

    @Query("""
            SELECT ui.targetId
            FROM UserInteraction ui
            WHERE ui.userId = :userId
              AND ui.targetId IN :chapterIds
              AND ui.type = :type
            """)
    List<UUID> findReadChapterIds(UUID userId, List<UUID> chapterIds, InteractionType type);

    @Query("""
            SELECT ui.targetId
            FROM UserInteraction ui
            WHERE ui.userId = :userId
              AND ui.type = :type
            ORDER BY ui.occurredAt DESC
            """)
    List<UUID> findRecentTargetIds(UUID userId, InteractionType type, Pageable pageable);

    @Query(value = """
            SELECT tt2.title_id
            FROM title_tags tt1
                     JOIN title_tags tt2 ON tt1.tag_id = tt2.tag_id
            WHERE tt1.title_id IN :seedTitleIds
              AND tt2.title_id NOT IN :excludedTitleIds
            GROUP BY tt2.title_id
            ORDER BY COUNT(*) DESC, tt2.title_id ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<UUID> findRecommendedTitleIdsBySharedTags(List<UUID> seedTitleIds, List<UUID> excludedTitleIds, int limit);

    @Query(value = """
            SELECT ui2.target_id AS titleId,
                   COUNT(*)      AS score,
                   'collab'      AS reason
            FROM user_interactions ui1
                     JOIN user_interactions ui2
                          ON ui2.user_id = ui1.user_id
                              AND ui2.type = 'TITLE_VOTED'
                              AND ui2.metadata ->> 'voteType' = 'LIKE'
            WHERE ui1.target_id IN (:likedTitleIds)
              AND ui1.type = 'TITLE_VOTED'
              AND ui1.metadata ->> 'voteType' = 'LIKE'
              AND ui1.user_id != :userId
              AND ui2.target_id NOT IN (:excludedIds)
            GROUP BY ui2.target_id
            ORDER BY score DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<UserInteractionApi.ScoredTitleProjection> findCollabCandidates(UUID userId,
                                                                        List<UUID> likedTitleIds,
                                                                        List<UUID> excludedIds,
                                                                        int limit);

    @Query(value = """
            SELECT target_id AS titleId,
                   COUNT(*)  AS score,
                   'popular' AS reason
            FROM user_interactions
            WHERE type = 'TITLE_VIEWED'
              AND occurred_at >= :since
              AND target_id NOT IN (:excludedIds)
            GROUP BY target_id
            ORDER BY score DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<UserInteractionApi.ScoredTitleProjection> findPopularSince(Instant since, List<UUID> excludedIds, int limit);

}
