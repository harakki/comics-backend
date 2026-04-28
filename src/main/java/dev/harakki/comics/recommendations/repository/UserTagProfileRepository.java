package dev.harakki.comics.recommendations.repository;

import dev.harakki.comics.recommendations.api.RecommendationsApi;
import dev.harakki.comics.recommendations.domain.UserTagProfile;
import dev.harakki.comics.recommendations.domain.UserTagProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserTagProfileRepository extends JpaRepository<UserTagProfile, UserTagProfileId> {

    List<UserTagProfile> findByIdUserId(UUID userId);

    @Modifying
    @Query(value = """
            INSERT INTO user_tag_profiles (user_id, tag_id, score, count, created_at, updated_at)
            VALUES (:userId, :tagId, :contribution, 1, NOW(), NOW())
            ON CONFLICT (user_id, tag_id) DO UPDATE SET count      = user_tag_profiles.count + 1,
                                                        score      = (user_tag_profiles.score * user_tag_profiles.count + :contribution) / (user_tag_profiles.count + 1), -- скользящее среднее
                                                        updated_at = NOW()
            """, nativeQuery = true)
    void upsertTagContribution(UUID userId, UUID tagId, double contribution);

    @Query(nativeQuery = true, value = """
            SELECT tt.title_id    AS titleId,
                   SUM(utp.score) AS score,
                   'content'      AS reason
            FROM title_tags tt
                     JOIN user_tag_profiles utp ON utp.tag_id = tt.tag_id
                AND utp.user_id = :userId
                AND utp.score > 0
            WHERE tt.title_id NOT IN (:excludedIds)
            GROUP BY tt.title_id
            HAVING SUM(utp.score) > 0
            ORDER BY score DESC
            LIMIT :limit
            """)
    List<RecommendationsApi.ScoredTitleProjection> findContentCandidates(UUID userId, List<UUID> excludedIds, int limit);

}
