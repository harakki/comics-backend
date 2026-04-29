package dev.harakki.comics.recommendations.repository;

import dev.harakki.comics.analytics.api.UserInteractionApi;
import dev.harakki.comics.recommendations.domain.UserTagProfile;
import dev.harakki.comics.recommendations.domain.UserTagProfileId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Repository
public interface SimilarTitlesRepository extends Repository<UserTagProfile, UserTagProfileId> {

    @Query(value = """
            SELECT   tt2.title_id                   AS titleId,
                     COUNT(DISTINCT tt1.tag_id)     AS score,
                     'similar'                      AS reason
            FROM     title_tags tt1
            JOIN     title_tags tt2 ON tt2.tag_id    = tt1.tag_id
                                   AND tt2.title_id != :titleId
            WHERE    tt1.title_id    = :titleId
            AND      tt2.title_id NOT IN (:excludedIds)
            GROUP BY tt2.title_id
            ORDER BY score DESC
            LIMIT    :limit
            """, nativeQuery = true)
    List<UserInteractionApi.ScoredTitleProjection> findSimilarTitles(UUID titleId, List<UUID> excludedIds, int limit);

}
