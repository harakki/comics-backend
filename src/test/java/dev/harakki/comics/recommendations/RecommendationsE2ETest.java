package dev.harakki.comics.recommendations;

import dev.harakki.comics.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.json.JsonMapper;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.LockSupport;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecommendationsE2ETest extends BaseIntegrationTest {

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

    private void likeTitle(String titleId) throws Exception {
        var request = Map.of(
                "status", "TO_READ",
                "vote", "LIKE"
        );

        mockMvc.perform(put("/api/v1/library/titles/{titleId}", titleId)
                        .with(userJwt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    private void waitForContentRecommendation(String candidateTitleId) throws Exception {
        long deadline = System.nanoTime() + Duration.ofSeconds(10).toNanos();
        AssertionError lastError = null;

        while (System.nanoTime() < deadline) {
            try {
                var result = mockMvc.perform(get("/api/v1/recommendations/me").with(userJwt()))
                        .andExpect(status().isOk())
                        .andReturn();

                var root = jsonMapper.readTree(result.getResponse().getContentAsString());
                if (root.isArray() && !root.isEmpty()) {
                    boolean hasCandidate = false;
                    boolean hasContentReason = false;
                    for (var node : root) {
                        if (candidateTitleId.equals(text(node, "titleId"))) {
                            hasCandidate = true;
                        }
                        if ("content".equals(text(node, "reason"))) {
                            hasContentReason = true;
                        }
                    }
                    if (hasCandidate && hasContentReason) {
                        return;
                    }
                }
            } catch (AssertionError e) {
                lastError = e;
            }

            LockSupport.parkNanos(Duration.ofMillis(250).toNanos());
        }

        if (lastError != null) {
            throw lastError;
        }

        throw new AssertionError("Timed out waiting for content recommendation for titleId=" + candidateTitleId);
    }

    private static String text(Object node, String field) {
        return node instanceof tools.jackson.databind.JsonNode jsonNode
                ? jsonNode.path(field).toString().replace("\"", "")
                : "";
    }

    @Test
    void getMyRecommendations_returnsContentBasedRecommendationsForLikedTitles() throws Exception {
        var tagId = createTagAndGetId("Action " + UUID.randomUUID().toString().substring(0, 8));

        var likedTitle = createTitleAndGetId("Liked " + UUID.randomUUID().toString().substring(0, 8), Set.of(tagId));
        var candidateTitle = UUID.fromString(createTitleAndGetId("Candidate " + UUID.randomUUID().toString().substring(0, 8), Set.of(tagId)));
        var viewedTitle = UUID.fromString(createTitleAndGetId("Viewed " + UUID.randomUUID().toString().substring(0, 8), Set.of(tagId)));

        mockMvc.perform(get("/api/v1/titles/{id}", viewedTitle).with(userJwt()))
                .andExpect(status().isOk());

        likeTitle(likedTitle);

        waitForContentRecommendation(candidateTitle.toString());

        mockMvc.perform(get("/api/v1/recommendations/me").with(userJwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].titleId", hasItem(candidateTitle.toString())))
                .andExpect(jsonPath("$[*].titleId", not(hasItem(viewedTitle.toString()))))
                .andExpect(jsonPath("$[*].reason", hasItem("content")))
                .andExpect(jsonPath("$[*].reason", not(hasItem("tag_overlap"))));
    }

}
