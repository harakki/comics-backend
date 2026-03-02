package dev.harakki.comics.content.web;

import dev.harakki.comics.content.application.ChapterService;
import dev.harakki.comics.content.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChapterController implements ChapterApi {

    private final ChapterService chapterService;

    @GetMapping("/titles/{titleId}/chapters")
    public List<ChapterSummaryResponse> getChaptersInfoByTitle(@PathVariable UUID titleId) {
        return chapterService.getChaptersByTitle(titleId);
    }

    @GetMapping("/chapters/{chapterId}")
    public ChapterDetailsResponse getFullChapter(@PathVariable UUID chapterId) {
        return chapterService.getChapterDetails(chapterId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/titles/{titleId}/chapters")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChapter(
            @PathVariable UUID titleId,
            @RequestBody ChapterCreateRequest request
    ) {
        chapterService.create(titleId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/chapters/{chapterId}")
    public void updateChapter(
            @PathVariable UUID chapterId,
            @RequestBody ChapterUpdateRequest request
    ) {
        chapterService.updateMetadata(chapterId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/chapters/{chapterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChapter(@PathVariable UUID chapterId) {
        chapterService.delete(chapterId);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/chapters/{chapterId}/read")
    public NextChapterResponse recordChapterRead(@PathVariable UUID chapterId, @RequestBody ChapterReadRequest request) {
        return chapterService.recordChapterRead(chapterId, request);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/chapters/{chapterId}/read-status")
    public ChapterReadStatusResponse isChapterRead(@PathVariable UUID chapterId) {
        return chapterService.isChapterRead(chapterId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/titles/{titleId}/next-chapter")
    public NextChapterResponse getNextUnreadChapter(@PathVariable UUID titleId) {
        return chapterService.getNextUnreadChapter(titleId);
    }

}
