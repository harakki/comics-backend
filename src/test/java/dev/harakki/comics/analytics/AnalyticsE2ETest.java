package dev.harakki.comics.analytics;

import dev.harakki.comics.BaseIntegrationTest;
import dev.harakki.comics.analytics.api.InteractionType;
import dev.harakki.comics.analytics.domain.UserInteraction;
import dev.harakki.comics.analytics.infrastructure.UserInteractionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnalyticsE2ETest extends BaseIntegrationTest {

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    private UserInteractionRepository userInteractionRepository;

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

        return jsonMapper.readTree(result.getResponse().getContentAsString()).get("id").asString();
    }

    @Test
    void getTopWeeklyPopularTitles_returnsTop10SortedByViews() throws Exception {
        var idsByPopularity = new java.util.ArrayList<UUID>();

        for (int i = 1; i <= 11; i++) {
            var titleId = UUID.fromString(createTitleAndGetId("Weekly top " + i + " " + UUID.randomUUID().toString().substring(0, 8)));
            idsByPopularity.add(titleId);

            for (int views = 0; views < i; views++) {
                userInteractionRepository.save(UserInteraction.builder()
                        .userId(UUID.randomUUID())
                        .type(InteractionType.TITLE_VIEWED)
                        .targetId(titleId)
                        .build());
            }
        }

        var top1 = idsByPopularity.getLast();
        var top10 = idsByPopularity.get(1);
        var outOfTop10 = idsByPopularity.getFirst();

        mockMvc.perform(get("/api/v1/analytics/titles/top-weekly"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].titleId").value(top1.toString()))
                .andExpect(jsonPath("$[0].weeklyViews").value(11))
                .andExpect(jsonPath("$[0].rank").value(1))
                .andExpect(jsonPath("$[9].titleId").value(top10.toString()))
                .andExpect(jsonPath("$[9].weeklyViews").value(2))
                .andExpect(jsonPath("$[9].rank").value(10))
                .andExpect(jsonPath("$[*].titleId", not(hasItem(outOfTop10.toString()))));
    }

}
