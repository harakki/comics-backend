package dev.harakki.comics.catalog.application;

import dev.harakki.comics.catalog.domain.Tag;
import dev.harakki.comics.catalog.domain.TagType;
import dev.harakki.comics.catalog.dto.TagCreateRequest;
import dev.harakki.comics.catalog.dto.TagResponse;
import dev.harakki.comics.catalog.dto.TagUpdateRequest;
import dev.harakki.comics.catalog.infrastructure.TagMapper;
import dev.harakki.comics.catalog.infrastructure.TagRepository;
import dev.harakki.comics.shared.exception.ResourceAlreadyExistsException;
import dev.harakki.comics.shared.exception.ResourceInUseException;
import dev.harakki.comics.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private SlugGenerator slugGenerator;

    @InjectMocks
    private TagService tagService;

    @Test
    void create_whenValid_returnsTagResponse() {
        var request = new TagCreateRequest("Action", "action", TagType.GENRE, "Action genre");
        var tagId = UUID.randomUUID();
        var tag = Tag.builder().id(tagId).name("Action").slug("action").type(TagType.GENRE).build();
        var response = new TagResponse(tagId, "Action", "action", TagType.GENRE, "Action genre");

        when(tagRepository.existsByName("Action")).thenReturn(false);
        when(tagMapper.toEntity(request)).thenReturn(tag);
        when(tagRepository.existsBySlug("action")).thenReturn(false);
        when(tagRepository.save(tag)).thenReturn(tag);
        when(tagMapper.toResponse(tag)).thenReturn(response);

        var result = tagService.create(request);

        assertThat(result).isEqualTo(response);
        verify(tagMapper).toEntity(request);
        verify(tagRepository).save(tag);
        verify(tagMapper).toResponse(tag);
    }

    @Test
    void create_whenNameAlreadyExists_throwsResourceAlreadyExistsException() {
        var request = new TagCreateRequest("Action", null, TagType.GENRE, null);

        when(tagRepository.existsByName("Action")).thenReturn(true);

        assertThatThrownBy(() -> tagService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Action");

        verify(tagRepository, never()).save(any());
    }

    @Test
    void create_whenSlugExplicitlyProvided_andSlugAlreadyExists_throwsException() {
        var request = new TagCreateRequest("Action", "action", TagType.GENRE, null);
        var tag = Tag.builder().name("Action").type(TagType.GENRE).build();

        when(tagRepository.existsByName("Action")).thenReturn(false);
        when(tagMapper.toEntity(request)).thenReturn(tag);
        when(tagRepository.existsBySlug("action")).thenReturn(true);

        assertThatThrownBy(() -> tagService.create(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("action");

        verify(tagRepository, never()).save(any());
    }

    @Test
    void create_whenNoSlugProvided_generatesSlugAutomatically() {
        var request = new TagCreateRequest("Action", null, TagType.GENRE, null);
        var tagId = UUID.randomUUID();
        var tag = Tag.builder().id(tagId).name("Action").type(TagType.GENRE).build();
        var response = new TagResponse(tagId, "Action", "action", TagType.GENRE, null);

        when(tagRepository.existsByName("Action")).thenReturn(false);
        when(tagMapper.toEntity(request)).thenReturn(tag);
        when(slugGenerator.generate(eq("Action"), any())).thenReturn("action");
        when(tagRepository.save(tag)).thenReturn(tag);
        when(tagMapper.toResponse(tag)).thenReturn(response);

        var result = tagService.create(request);

        assertThat(result).isEqualTo(response);
        verify(slugGenerator).generate(eq("Action"), any());
    }

    @Test
    void getById_whenTagExists_returnsTagResponse() {
        var id = UUID.randomUUID();
        var tag = Tag.builder().id(id).name("Action").slug("action").type(TagType.GENRE).build();
        var response = new TagResponse(id, "Action", "action", TagType.GENRE, null);

        when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
        when(tagMapper.toResponse(tag)).thenReturn(response);

        var result = tagService.getById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void getById_whenTagNotFound_throwsResourceNotFoundException() {
        var id = UUID.randomUUID();

        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void update_whenValid_returnsUpdatedTagResponse() {
        var id = UUID.randomUUID();
        var request = new TagUpdateRequest("Horror", "horror", TagType.GENRE, "Horror genre");
        var existingTag = Tag.builder().id(id).name("Action").slug("action").type(TagType.GENRE).build();
        var updatedTag = Tag.builder().id(id).name("Horror").slug("horror").type(TagType.GENRE).build();
        var response = new TagResponse(id, "Horror", "horror", TagType.GENRE, "Horror genre");

        when(tagRepository.findById(id)).thenReturn(Optional.of(existingTag));
        when(tagRepository.existsBySlugAndIdNot("horror", id)).thenReturn(false);
        when(tagMapper.partialUpdate(request, existingTag)).thenReturn(updatedTag);
        when(tagRepository.save(updatedTag)).thenReturn(updatedTag);
        when(tagMapper.toResponse(updatedTag)).thenReturn(response);

        var result = tagService.update(id, request);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void update_whenSlugConflict_throwsResourceAlreadyExistsException() {
        var id = UUID.randomUUID();
        var request = new TagUpdateRequest("Horror", "horror", TagType.GENRE, null);
        var existingTag = Tag.builder().id(id).name("Action").slug("action").type(TagType.GENRE).build();

        when(tagRepository.findById(id)).thenReturn(Optional.of(existingTag));
        when(tagRepository.existsBySlugAndIdNot("horror", id)).thenReturn(true);

        assertThatThrownBy(() -> tagService.update(id, request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("horror");

        verify(tagRepository, never()).save(any());
    }

    @Test
    void delete_whenExists_deletesSuccessfully() {
        var id = UUID.randomUUID();
        var tag = Tag.builder().id(id).name("Action").slug("action").type(TagType.GENRE).build();

        when(tagRepository.findById(id)).thenReturn(Optional.of(tag));

        tagService.delete(id);

        verify(tagRepository).delete(tag);
        verify(tagRepository).flush();
    }

    @Test
    void delete_whenTagInUse_throwsResourceInUseException() {
        var id = UUID.randomUUID();
        var tag = Tag.builder().id(id).name("Action").slug("action").type(TagType.GENRE).build();

        when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
        doThrow(DataIntegrityViolationException.class).when(tagRepository).flush();

        assertThatThrownBy(() -> tagService.delete(id))
                .isInstanceOf(ResourceInUseException.class)
                .hasMessageContaining(id.toString());
    }

}
