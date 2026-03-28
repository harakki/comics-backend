package dev.harakki.comics.analytics.infrastructure;

import dev.harakki.comics.analytics.api.UserInteractionApi;
import dev.harakki.comics.analytics.api.InteractionType;
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
                COUNT(*) AS weeklyViews
            FROM user_interactions
            WHERE type = 'TITLE_VIEWED'
              AND occurred_at >= :since
            GROUP BY target_id
            ORDER BY weeklyViews DESC, titleId ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<UserInteractionApi.TopViewedTitleProjection> findTopViewedTitlesSince(Instant since, int limit);

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

}
