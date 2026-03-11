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

class TitleE2ETest extends BaseIntegrationTest {

    @Autowired
    private JsonMapper jsonMapper;

    private String createTitleAndGetId(String name) throws Exception {
        var request = Map.of(
                "name", name,
                "type", "MANGA",
                "titleStatus", "ONGOING",
                "contentRating", "SIX_PLUS",
                "countryIsoCode", "JP"
        );

        var result = mockMvc.perform(post("/api/v1/titles")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        var body = jsonMapper.readTree(result.getResponse().getContentAsString());
        return body.get("id").asText();
    }

    @Test
    void createTitle_asAdmin_returnsCreated() throws Exception {
        String uniqueName = "Naruto " + UUID.randomUUID().toString().substring(0, 8);
        var request = Map.of(
                "name", uniqueName,
                "type", "MANGA",
                "titleStatus", "ONGOING",
                "contentRating", "SIX_PLUS",
                "countryIsoCode", "JP"
        );

        mockMvc.perform(post("/api/v1/titles")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(uniqueName))
                .andExpect(jsonPath("$.type").value("MANGA"))
                .andExpect(jsonPath("$.titleStatus").value("ONGOING"))
                .andExpect(jsonPath("$.contentRating").value("SIX_PLUS"));
    }

    @Test
    void createTitle_withoutAuth_isForbidden() throws Exception {
        var request = Map.of(
                "name", "Unauthorized Title",
                "type", "MANGA",
                "titleStatus", "ONGOING",
                "contentRating", "SIX_PLUS",
                "countryIsoCode", "JP"
        );

        mockMvc.perform(post("/api/v1/titles")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getTitle_returnsOk() throws Exception {
        String name = "Get Title " + UUID.randomUUID().toString().substring(0, 8);
        String id = createTitleAndGetId(name);

        mockMvc.perform(get("/api/v1/titles/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getTitles_returnsPaginatedList() throws Exception {
        mockMvc.perform(get("/api/v1/titles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void updateTitle_asAdmin_returnsUpdated() throws Exception {
        String name = "Update Title " + UUID.randomUUID().toString().substring(0, 8);
        String id = createTitleAndGetId(name);

        var updateRequest = Map.of("titleStatus", "COMPLETED");

        mockMvc.perform(patch("/api/v1/titles/{id}", id)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.titleStatus").value("COMPLETED"));
    }

    @Test
    void deleteTitle_asAdmin_returnsNoContent() throws Exception {
        String name = "Delete Title " + UUID.randomUUID().toString().substring(0, 8);
        String id = createTitleAndGetId(name);

        mockMvc.perform(delete("/api/v1/titles/{id}", id)
                        .with(adminJwt()))
                .andExpect(status().isNoContent());
    }
}
