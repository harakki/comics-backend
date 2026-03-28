package dev.harakki.comics.recommendations;

import dev.harakki.comics.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecommendationsE2ETest extends BaseIntegrationTest {

    @Autowired
    private JsonMapper jsonMapper;

    private String createTagAndGetId(String name) throws Exception {
        var request = Map.of(
                "name", name,
                "type", "GENRE"
        );

        var result = mockMvc.perform(post("/api/v1/tags")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        return jsonMapper.readTree(result.getResponse().getContentAsString()).get("id").asString();
    }

    private String createTitleAndGetId(String name, Set<String> tagIds) throws Exception {
        var request = Map.of(
                "name", name,
                "type", "MANGA",
                "titleStatus", "ONGOING",
                "contentRating", "SIX_PLUS",
                "countryIsoCode", "JP",
                "tagIds", tagIds
        );

        var result = mockMvc.perform(post("/api/v1/titles")
                        .with(adminJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        return jsonMapper.readTree(result.getResponse().getContentAsString()).get("id").asString();
    }

    @Test
    void getMyRecommendations_returnsUnreadTitlesOnlyForUser() throws Exception {
        var tagId = createTagAndGetId("Action " + UUID.randomUUID().toString().substring(0, 8));

        var viewed1 = UUID.fromString(createTitleAndGetId("Viewed 1 " + UUID.randomUUID().toString().substring(0, 8), Set.of(tagId)));
        var viewed2 = UUID.fromString(createTitleAndGetId("Viewed 2 " + UUID.randomUUID().toString().substring(0, 8), Set.of(tagId)));
        var candidate = UUID.fromString(createTitleAndGetId("Candidate " + UUID.randomUUID().toString().substring(0, 8), Set.of(tagId)));

        mockMvc.perform(get("/api/v1/titles/{id}", viewed1).with(userJwt()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/titles/{id}", viewed2).with(userJwt()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/recommendations/me").with(userJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].titleId", hasItem(candidate.toString())))
                .andExpect(jsonPath("$[*].titleId", not(hasItem(viewed1.toString()))))
                .andExpect(jsonPath("$[*].titleId", not(hasItem(viewed2.toString()))))
                .andExpect(jsonPath("$[0].reason").value("tag_overlap"));
    }

}
