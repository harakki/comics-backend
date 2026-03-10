package dev.harakki.comics.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.harakki.comics.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ChapterE2ETest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    private String createTitleAndGetId() throws Exception {
        String uniqueName = "Chapter Title " + UUID.randomUUID().toString().substring(0, 8);
        var request = Map.of(
                "name", uniqueName,
                "type", "MANGA",
                "titleStatus", "ONGOING",
                "contentRating", "SIX_PLUS",
                "countryIsoCode", "JP"
        );

        var result = mockMvc.perform(post("/api/v1/titles")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        var body = objectMapper.readTree(result.getResponse().getContentAsString());
        return body.get("id").asText();
    }

    private String createChapterAndGetId(String titleId) throws Exception {
        var request = Map.of(
                "number", 1,
                "subNumber", 0,
                "pages", List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        );

        mockMvc.perform(post("/api/v1/titles/{titleId}/chapters", titleId)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        var chapters = mockMvc.perform(get("/api/v1/titles/{titleId}/chapters", titleId))
                .andExpect(status().isOk())
                .andReturn();

        var body = objectMapper.readTree(chapters.getResponse().getContentAsString());
        return body.get(0).get("id").asText();
    }

    @Test
    void createChapter_asAdmin_returnsCreated() throws Exception {
        String titleId = createTitleAndGetId();
        var request = Map.of(
                "number", 1,
                "subNumber", 0,
                "pages", List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString())
        );

        mockMvc.perform(post("/api/v1/titles/{titleId}/chapters", titleId)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void getChapters_byTitle_returnsOkList() throws Exception {
        String titleId = createTitleAndGetId();
        var request = Map.of(
                "number", 1,
                "subNumber", 0,
                "pages", List.of(UUID.randomUUID().toString())
        );

        mockMvc.perform(post("/api/v1/titles/{titleId}/chapters", titleId)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/titles/{titleId}/chapters", titleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void getChapterDetails_returnsOk() throws Exception {
        String titleId = createTitleAndGetId();
        String chapterId = createChapterAndGetId(titleId);

        mockMvc.perform(get("/api/v1/chapters/{chapterId}", chapterId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(chapterId))
                .andExpect(jsonPath("$.titleId").value(titleId));
    }

    @Test
    void updateChapter_asAdmin_returnsOk() throws Exception {
        String titleId = createTitleAndGetId();
        String chapterId = createChapterAndGetId(titleId);

        var updateRequest = Map.of("name", "Updated Chapter Name");

        mockMvc.perform(patch("/api/v1/chapters/{chapterId}", chapterId)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteChapter_asAdmin_returnsNoContent() throws Exception {
        String titleId = createTitleAndGetId();
        String chapterId = createChapterAndGetId(titleId);

        mockMvc.perform(delete("/api/v1/chapters/{chapterId}", chapterId)
                        .with(adminJwt()))
                .andExpect(status().isNoContent());
    }
}
