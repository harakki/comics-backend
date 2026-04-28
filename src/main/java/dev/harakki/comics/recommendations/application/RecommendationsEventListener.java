package dev.harakki.comics.recommendations.application;

import dev.harakki.comics.library.api.LibraryVoteTitleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationsEventListener {

    private final UserTagProfileService userTagProfileService;

    @Async
    @ApplicationModuleListener
    public void on(LibraryVoteTitleEvent event) {
        log.debug("Updating tag profile: userId={}, titleId={}, vote={}",
                event.userId(), event.titleId(), event.vote());
        try {
            userTagProfileService.onTitleVoted(event.userId(), event.titleId(), event.vote());
        } catch (Exception e) {
            log.error("Failed to update tag profile: userId={}, titleId={}",
                    event.userId(), event.titleId(), e);
        }
    }

}
