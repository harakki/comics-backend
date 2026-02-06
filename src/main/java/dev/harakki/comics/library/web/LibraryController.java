package dev.harakki.comics.library.web;

import dev.harakki.comics.library.application.LibraryService;
import dev.harakki.comics.library.domain.LibraryEntry;
import dev.harakki.comics.library.dto.LibraryEntryResponse;
import dev.harakki.comics.library.dto.LibraryEntryUpdateRequest;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/library", produces = MediaType.APPLICATION_JSON_VALUE)
class LibraryController implements LibraryApi {

    private final LibraryService libraryService;

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/titles/{titleId}")
    public LibraryEntryResponse addOrUpdateLibraryEntry(
            @PathVariable UUID titleId,
            @RequestBody LibraryEntryUpdateRequest request
    ) {
        return libraryService.addOrUpdateLibraryEntry(titleId, request);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{titleId}")
    public LibraryEntryResponse getLibraryEntry(@PathVariable UUID titleId) {
        return libraryService.getById(titleId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<LibraryEntryResponse> getMyLibrary(
            @And({
                    @Spec(path = "status", params = "status", spec = Equal.class),
                    @Spec(path = "title.id", params = "titleId", spec = Equal.class)
            }) Specification<LibraryEntry> spec,
            @PageableDefault(sort = "updatedAt") Pageable pageable
    ) {
        return libraryService.searchLibrary(spec, pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLibraryEntry(@PathVariable UUID entryId) {
        libraryService.removeFromLibrary(entryId);
    }

    // TODO эндпоинт Reading Progress POST /api/v1/library/{titleId}/progress (или PATCH /library/entry)
    // Бэкенд сам должен
    // Найти или создать запись в библиотеке для этого юзера и тайтла.
    // Обновить lastReadChapterId.
    // Автоматически переключить статус на READING (если он был TO_READ).
    // (Опционально) Вернуть ID следующей главы в ответе.

}
