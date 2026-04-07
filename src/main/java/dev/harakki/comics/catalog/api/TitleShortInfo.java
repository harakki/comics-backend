package dev.harakki.comics.catalog.api;

import java.io.Serializable;
import java.util.UUID;

public record TitleShortInfo(
        UUID id,
        String name,
        UUID mainCoverMediaId,
        String slug
) implements Serializable {
}
