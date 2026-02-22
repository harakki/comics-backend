package dev.harakki.comics.analytics.web;

import dev.harakki.comics.analytics.application.AnalyticsService;
import dev.harakki.comics.analytics.dto.TitleAnalyticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnalyticsController implements AnalyticsApi {

    private final AnalyticsService analyticsService;

    @GetMapping("/titles/{titleId}/analytics")
    public TitleAnalyticsResponse getTitleAnalytics(@PathVariable UUID titleId) {
        return analyticsService.getTitleAnalytics(titleId);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/titles/{titleId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void likeTitle(@PathVariable UUID titleId) {
        analyticsService.likeTitle(titleId);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/titles/{titleId}/dislike")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dislikeTitle(@PathVariable UUID titleId) {
        analyticsService.dislikeTitle(titleId);
    }

}
