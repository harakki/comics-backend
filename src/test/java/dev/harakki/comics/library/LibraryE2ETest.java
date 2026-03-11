package dev.harakki.comics.library;

import dev.harakki.comics.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LibraryE2ETest extends BaseIntegrationTest {

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void addToLibrary_asUser_returnsOk() throws Exception {
        UUID titleId = UUID.randomUUID();
        var request = Map.of("status", "READING");

        mockMvc.perform(put("/api/v1/library/titles/{titleId}", titleId)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titleId").value(titleId.toString()))
                .andExpect(jsonPath("$.status").value("READING"));
    }

    @Test
    void getMyLibrary_asUser_returnsPaginatedList() throws Exception {
        UUID titleId = UUID.randomUUID();
        var request = Map.of("status", "TO_READ");

        mockMvc.perform(put("/api/v1/library/titles/{titleId}", titleId)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/library")
                        .with(userJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void getLibraryByTitle_asUser_returnsOk() throws Exception {
        UUID titleId = UUID.randomUUID();
        var request = Map.of("status", "ON_HOLD");

        mockMvc.perform(put("/api/v1/library/titles/{titleId}", titleId)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/library/{titleId}", titleId)
                        .with(userJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titleId").value(titleId.toString()));
    }

    @Test
    void deleteFromLibrary_asUser_returnsNoContent() throws Exception {
        UUID titleId = UUID.randomUUID();
        var request = Map.of("status", "DROPPED");

        var result = mockMvc.perform(put("/api/v1/library/titles/{titleId}", titleId)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        var body = jsonMapper.readTree(result.getResponse().getContentAsString());
        String entryId = body.get("id").asString();

        mockMvc.perform(delete("/api/v1/library/{entryId}", entryId)
                        .with(userJwt()))
                .andExpect(status().isNoContent());
    }

    @Test
    void addToLibrary_withoutAuth_isForbidden() throws Exception {
        UUID titleId = UUID.randomUUID();
        var request = Map.of("status", "READING");

        mockMvc.perform(put("/api/v1/library/titles/{titleId}", titleId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }
}
