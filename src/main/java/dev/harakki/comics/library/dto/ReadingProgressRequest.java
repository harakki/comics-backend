package dev.harakki.comics.library.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public record ReadingProgressRequest(
        @NotNull UUID chapterId
) implements Serializable {
}
