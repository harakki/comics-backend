package dev.harakki.comics.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.harakki.comics.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthorE2ETest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    private String createAuthorAndGetId(String name) throws Exception {
        var request = Map.of("name", name);

        var result = mockMvc.perform(post("/api/v1/authors")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        var body = objectMapper.readTree(result.getResponse().getContentAsString());
        return body.get("id").asText();
    }

    @Test
    void createAuthor_asAdmin_returnsCreated() throws Exception {
        String uniqueName = "Author " + UUID.randomUUID().toString().substring(0, 8);
        var request = Map.of("name", uniqueName);

        mockMvc.perform(post("/api/v1/authors")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(uniqueName))
                .andExpect(jsonPath("$.slug").exists());
    }

    @Test
    void createAuthor_withoutAuth_isForbidden() throws Exception {
        var request = Map.of("name", "Unauthorized Author");

        mockMvc.perform(post("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAuthor_returnsOk() throws Exception {
        String name = "Get Author " + UUID.randomUUID().toString().substring(0, 8);
        String id = createAuthorAndGetId(name);

        mockMvc.perform(get("/api/v1/authors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getAuthors_returnsPaginatedList() throws Exception {
        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void updateAuthor_asAdmin_returnsUpdated() throws Exception {
        String name = "Update Author " + UUID.randomUUID().toString().substring(0, 8);
        String id = createAuthorAndGetId(name);

        String updatedName = "Updated " + name;
        var updateRequest = Map.of("name", updatedName);

        mockMvc.perform(patch("/api/v1/authors/{id}", id)
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(updatedName));
    }

    @Test
    void deleteAuthor_asAdmin_returnsNoContent() throws Exception {
        String name = "Delete Author " + UUID.randomUUID().toString().substring(0, 8);
        String id = createAuthorAndGetId(name);

        mockMvc.perform(delete("/api/v1/authors/{id}", id)
                        .with(adminJwt()))
                .andExpect(status().isNoContent());
    }
}
