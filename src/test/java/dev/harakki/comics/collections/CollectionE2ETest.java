package dev.harakki.comics.collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.harakki.comics.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CollectionE2ETest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    private String createCollectionAndGetId(String name) throws Exception {
        var request = Map.of("name", name, "isPublic", true);

        var result = mockMvc.perform(post("/api/v1/collections")
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        var body = objectMapper.readTree(result.getResponse().getContentAsString());
        return body.get("id").asText();
    }

    @Test
    void createCollection_asUser_returnsCreated() throws Exception {
        String uniqueName = "My Collection " + UUID.randomUUID().toString().substring(0, 8);
        var request = Map.of("name", uniqueName, "isPublic", true);

        mockMvc.perform(post("/api/v1/collections")
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(uniqueName))
                .andExpect(jsonPath("$.isPublic").value(true));
    }

    @Test
    void getCollection_publicCollection_returnsOk() throws Exception {
        String name = "Public Collection " + UUID.randomUUID().toString().substring(0, 8);
        String id = createCollectionAndGetId(name);

        mockMvc.perform(get("/api/v1/collections/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getCollections_returnsPublicCollections() throws Exception {
        mockMvc.perform(get("/api/v1/collections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void updateCollection_asOwner_returnsUpdated() throws Exception {
        String name = "Update Collection " + UUID.randomUUID().toString().substring(0, 8);
        String id = createCollectionAndGetId(name);

        String updatedName = "Updated " + name;
        var updateRequest = Map.of("name", updatedName);

        mockMvc.perform(patch("/api/v1/collections/{id}", id)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(updatedName));
    }

    @Test
    void deleteCollection_asOwner_returnsNoContent() throws Exception {
        String name = "Delete Collection " + UUID.randomUUID().toString().substring(0, 8);
        String id = createCollectionAndGetId(name);

        mockMvc.perform(delete("/api/v1/collections/{id}", id)
                        .with(userJwt()))
                .andExpect(status().isNoContent());
    }

    @Test
    void createCollection_withoutAuth_isForbidden() throws Exception {
        var request = Map.of("name", "Unauthorized Collection", "isPublic", true);

        mockMvc.perform(post("/api/v1/collections")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }
}
