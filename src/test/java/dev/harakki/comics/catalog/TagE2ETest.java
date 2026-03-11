package dev.harakki.comics.catalog;

import dev.harakki.comics.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TagE2ETest extends BaseIntegrationTest {

    @Autowired
    private JsonMapper jsonMapper;

    private String createTagAndGetId(String name, String type) throws Exception {
        var request = Map.of("name", name, "type", type);

        var result = mockMvc.perform(post("/api/v1/tags")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        var body = jsonMapper.readTree(result.getResponse().getContentAsString());
        return body.get("id").asText();
    }

    @Test
    void createTag_asAdmin_returnsCreated() throws Exception {
        String uniqueName = "Genre Tag " + UUID.randomUUID().toString().substring(0, 8);
        var request = Map.of("name", uniqueName, "type", "GENRE");

        mockMvc.perform(post("/api/v1/tags")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(uniqueName))
                .andExpect(jsonPath("$.type").value("GENRE"));
    }

    @Test
    void createTag_withoutAuth_isForbidden() throws Exception {
        var request = Map.of("name", "Unauthorized Tag", "type", "GENRE");

        mockMvc.perform(post("/api/v1/tags")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getTag_returnsOk() throws Exception {
        String name = "Theme Tag " + UUID.randomUUID().toString().substring(0, 8);
        String id = createTagAndGetId(name, "THEME");

        mockMvc.perform(get("/api/v1/tags/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.type").value("THEME"));
    }

    @Test
    void getTags_returnsPaginatedList() throws Exception {
        mockMvc.perform(get("/api/v1/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void updateTag_asAdmin_returnsUpdated() throws Exception {
        String name = "Update Tag " + UUID.randomUUID().toString().substring(0, 8);
        String id = createTagAndGetId(name, "GENRE");

        String updatedName = "Updated " + name;
        var updateRequest = Map.of("name", updatedName);

        mockMvc.perform(patch("/api/v1/tags/{id}", id)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(updatedName));
    }

    @Test
    void deleteTag_asAdmin_returnsNoContent() throws Exception {
        String name = "Delete Tag " + UUID.randomUUID().toString().substring(0, 8);
        String id = createTagAndGetId(name, "CONTENT_WARNING");

        mockMvc.perform(delete("/api/v1/tags/{id}", id)
                        .with(adminJwt()))
                .andExpect(status().isNoContent());
    }
}
