package dev.harakki.comics.recommendations.web;

import dev.harakki.comics.recommendations.application.RecommendationsService;
import dev.harakki.comics.recommendations.dto.PersonalRecommendationResponse;
import dev.harakki.comics.shared.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecommendationsController implements RecommendationsApi {

    private final RecommendationsService recommendationsService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/recommendations/me")
    public List<PersonalRecommendationResponse> getMyRecommendations(@RequestParam(required = false) Integer limit) {
        return recommendationsService.getPersonalRecommendations(SecurityUtils.getCurrentUserId(), (limit == null) ? 10 : limit);
    }

}
