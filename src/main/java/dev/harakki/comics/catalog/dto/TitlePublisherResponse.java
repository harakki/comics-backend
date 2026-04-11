package dev.harakki.comics.catalog.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link dev.harakki.comics.catalog.domain.TitlePublisher}
 */
public record TitlePublisherResponse(
        UUID id,
        PublisherResponse publisher,
        Integer sortOrder
) implements Serializable {
}
