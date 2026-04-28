package dev.harakki.comics.recommendations.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record UserTagProfileId(
        @Column(nullable = false)
        UUID userId,

        @Column(nullable = false)
        UUID tagId
) implements Serializable {
}
