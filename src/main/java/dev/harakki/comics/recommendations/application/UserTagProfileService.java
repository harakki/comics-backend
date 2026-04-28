package dev.harakki.comics.recommendations.application;

import dev.harakki.comics.catalog.api.TitlePublicQueryApi;
import dev.harakki.comics.library.api.VoteType;
import dev.harakki.comics.recommendations.repository.UserTagProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserTagProfileService {

    private static final double LIKE_CONTRIBUTION = 1.0;
    private static final double DISLIKE_CONTRIBUTION = -1.0;

    private final UserTagProfileRepository profileRepository;
    private final TitlePublicQueryApi titlePublicQueryApi;

    @Transactional
    public void onTitleVoted(UUID userId, UUID titleId, VoteType vote) {
        double contribution = switch (vote) {
            case VoteType.LIKE -> LIKE_CONTRIBUTION;
            case VoteType.DISLIKE -> DISLIKE_CONTRIBUTION;
        };

        var tagIds = titlePublicQueryApi
                .getTitleTagIdsByIds(List.of(titleId))
                .getOrDefault(titleId, Collections.emptySet());

        if (tagIds.isEmpty()) {
            log.debug("No tags found for titleId={}, skipping profile update", titleId);
            return;
        }

        tagIds.forEach(tagId ->
                profileRepository.upsertTagContribution(userId, tagId, contribution)
        );

        log.debug("Updated tag profile: userId={}, titleId={}, tags={}, contribution={}",
                userId, titleId, tagIds.size(), contribution);
    }

}
