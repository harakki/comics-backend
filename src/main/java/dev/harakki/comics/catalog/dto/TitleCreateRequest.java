package dev.harakki.comics.catalog.dto;

import dev.harakki.comics.catalog.domain.AuthorRole;
import dev.harakki.comics.catalog.domain.ContentRating;
import dev.harakki.comics.catalog.domain.TitleStatus;
import dev.harakki.comics.catalog.domain.TitleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Builder
public record TitleCreateRequest(
        @NotBlank String name,
        String slug,
        String description,
        @NotNull TitleType type,
        @NotNull TitleStatus titleStatus,
        Year releaseYear,
        @NotNull ContentRating contentRating,
        @NotBlank String countryIsoCode,
        UUID mainCoverMediaId,
        Map<UUID, AuthorRole> authorIds,
        List<UUID> publisherIds,
        Set<UUID> tagIds
) {
}
