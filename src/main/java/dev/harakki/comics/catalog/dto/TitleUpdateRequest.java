package dev.harakki.comics.catalog.dto;

import dev.harakki.comics.catalog.domain.ContentRating;
import dev.harakki.comics.catalog.domain.TitleStatus;
import dev.harakki.comics.catalog.domain.TitleType;
import lombok.Builder;

import java.time.Year;
import java.util.UUID;

@Builder
public record TitleUpdateRequest(
        String name,
        String description,
        TitleType type,
        TitleStatus titleStatus,
        Year releaseYear,
        ContentRating contentRating,
        String countryIsoCode,
        UUID mainCoverMediaId,
        UUID publisherId
) {
}
