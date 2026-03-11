package dev.harakki.comics.catalog.application;

import com.github.slugify.Slugify;
import dev.harakki.comics.catalog.domain.SlugSequence;
import dev.harakki.comics.catalog.infrastructure.SlugSequenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlugGeneratorTest {

    @Mock
    private Slugify slugify;

    @Mock
    private SlugSequenceRepository slugSequenceRepository;

    @InjectMocks
    private SlugGenerator slugGenerator;

    @Test
    void generate_whenSlugIsUnique_returnsBaseSlug() {
        Predicate<String> existenceCheck = slug -> false;

        when(slugify.slugify("Test Name")).thenReturn("test-name");

        var result = slugGenerator.generate("Test Name", existenceCheck);

        assertThat(result).isEqualTo("test-name");
        verify(slugSequenceRepository, never()).save(any());
    }

    @Test
    void generate_whenBaseSlugExists_returnsSuffixedSlug() {
        // First call (base slug) returns true (exists), second call (suffixed) returns false
        Predicate<String> existenceCheck = slug -> slug.equals("test-name");

        when(slugify.slugify("Test Name")).thenReturn("test-name");
        when(slugSequenceRepository.findBySlugPrefixWithLock("test-name")).thenReturn(Optional.empty());
        when(slugSequenceRepository.save(any(SlugSequence.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = slugGenerator.generate("Test Name", existenceCheck);

        assertThat(result).isEqualTo("test-name-2");
        verify(slugSequenceRepository).save(argThat(seq ->
                seq.getSlugPrefix().equals("test-name") && seq.getCounter() == 2L));
    }

    @Test
    void generate_whenSequenceExists_incrementsCounter() {
        // Base slug exists, first suffixed slug also exists, second doesn't
        var callCount = new int[]{0};
        Predicate<String> existenceCheck = slug -> {
            callCount[0]++;
            // base slug and "test-name-5" exist; "test-name-6" is free
            return slug.equals("test-name") || slug.equals("test-name-5");
        };

        var existingSequence = new SlugSequence("test-name", 4L);

        when(slugify.slugify("Test Name")).thenReturn("test-name");
        when(slugSequenceRepository.findBySlugPrefixWithLock("test-name"))
                .thenReturn(Optional.of(existingSequence))
                .thenReturn(Optional.of(new SlugSequence("test-name", 5L)));
        when(slugSequenceRepository.save(any(SlugSequence.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = slugGenerator.generate("Test Name", existenceCheck);

        assertThat(result).isEqualTo("test-name-6");
        verify(slugSequenceRepository, atLeast(2)).findBySlugPrefixWithLock("test-name");
    }

}
