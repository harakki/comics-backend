package dev.harakki.comics.content.dto;

import jakarta.validation.constraints.Positive;

public record ChapterReadRequest(
        @Positive(message = "Read time must be positive")
        long readTimeMillis
) {
}
