package com.daily.plan.DailyPlan.Controller;

import com.daily.plan.DailyActivityTracker.DailyPlan.Controller.DailyPlanController;
import com.daily.plan.DailyActivityTracker.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyActivityTracker.DailyPlan.DTO.ToggleFlagDTO;
import com.daily.plan.DailyActivityTracker.DailyPlan.Service.DailyPlanService;
import com.daily.plan.DailyActivityTracker.Timer.Service.TimerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DailyPlanController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestDailyPlanController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DailyPlanService dailyPlanService;

    @MockBean
    private TimerService timerService;

    @Nested
    @DisplayName("starterPage()")
    class StarterPageTest {

        @Test
        @DisplayName("Should return main html page")
        void shouldReturnMainHtmlPage() throws Exception {

            mockMvc.perform(
                            get("/daily/main")
                    ).andExpect(status().isOk())
                    .andExpect(view().name("main/main_page"));

        }

        @Test
        @DisplayName("Should contain 3 model attributes")
        void shouldContain3ModelAttributes() throws Exception {

            mockMvc.perform(
                            get("/daily/main")
                    ).andExpect(status().isOk())
                    .andExpect(model().attributeExists("active_goals"))
                    .andExpect(model().attributeExists("done_goals"))
                    .andExpect(model().attributeExists("activity_types"))
                    .andExpect(model().size(3));

            verify(dailyPlanService).getActiveGoals();
            verify(dailyPlanService).getDoneGoals();
            verify(timerService).getAllActivityTypes();

        }

    }

    @Nested
    @DisplayName("errorPage()")
    class ErrorPageTest {

        @Test
        @DisplayName("Should return error html page")
        void shouldReturnErrorHtmlPage() throws Exception {

            mockMvc.perform(
                            get("/daily/error")
                    ).andExpect(status().isOk())
                    .andExpect(view().name("other/error_page"));

        }
    }

    @Nested
    @DisplayName("getActiveGoals()")
    class GetActiveGoalsTest {

        @Test
        @DisplayName("Should Receive json response with active goals")
        void shouldReceiveJsonResponseWithActiveGoals() throws Exception {


            when(dailyPlanService.getActiveGoals()).thenReturn(List.of(
                    new GoalDTO(
                            UUID.randomUUID(),
                            "Goal test 1",
                            false
                    )
            ));


            mockMvc.perform(
                            get("/daily/active")
                    ).andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        }
    }

    @Nested
    @DisplayName("getDoneGoals()")
    class GetDoneGoalsTest {

        @Test
        @DisplayName("Should Receive json response with accomplished goals")
        void shouldReceiveJsonResponseWithDoneGoals() throws Exception {

            when(dailyPlanService.getDoneGoals()).thenReturn(List.of(
                    new GoalDTO(
                            UUID.randomUUID(),
                            "Goal test 1",
                            true
                    )
            ));


            mockMvc.perform(
                            get("/daily/done")
                    ).andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        }
    }

    @Nested
    @DisplayName("save()")
    class SaveTest {

        @Test
        @DisplayName("Should return saved GoalDTO")
        void shouldReturnSavedGoalDTO() throws Exception {

            UUID id = UUID.randomUUID();

            GoalDTO response = new GoalDTO(
                    id,
                    "Goal test 1",
                    false
            );

            when(dailyPlanService.save(any(GoalDTO.class)))
                    .thenReturn(response);

            mockMvc.perform(
                            post("/daily/save")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                              "id": null,
                                              "goalText": "Goal test 1",
                                              "doneFlag": false
                                            }
                                            """)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id.toString()))
                    .andExpect(jsonPath("$.goalText").value("Goal test 1"))
                    .andExpect(jsonPath("$.doneFlag").value(false));

            verify(dailyPlanService).save(any(GoalDTO.class));
        }

        @Test
        @DisplayName("Should return bad request when goal text is blank")
        void shouldReturnBadRequestWhenGoalTextBlank() throws Exception {

            mockMvc.perform(
                            post("/daily/save")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                              "id": null,
                                              "goalText": "",
                                              "doneFlag": false
                                            }
                                            """)
                    )
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(dailyPlanService);
        }

        @Test
        @DisplayName("Should return bad request when goal text contains only spaces")
        void shouldReturnBadRequestWhenGoalTextContainsOnlySpaces() throws Exception {

            mockMvc.perform(
                            post("/daily/save")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                              "id": null,
                                              "goalText": "     ",
                                              "doneFlag": false
                                            }
                                            """)
                    )
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(dailyPlanService);
        }

        @Test
        @DisplayName("Should return bad request when goal text exceeds max length")
        void shouldReturnBadRequestWhenGoalTextTooLong() throws Exception {

            String longText = "a".repeat(51);

            mockMvc.perform(
                            post("/daily/save")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                            {
                                              "id": null,
                                              "goalText": "%s",
                                              "doneFlag": false
                                            }
                                            """.formatted(longText))
                    )
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(dailyPlanService);
        }

    }

    @Nested
    @DisplayName("toggle()")
    class ToggleTest {

        @Test
        @DisplayName("Should return Goal DTO after toggle method")
        void shouldReturnGoalDTOAfterToggleMethod() throws Exception {

            UUID id = UUID.randomUUID();

            GoalDTO response = new GoalDTO(
                    id,
                    "Goal test 1",
                    false
            );

            when(dailyPlanService.toggleFlag(any(ToggleFlagDTO.class)))
                    .thenReturn(response);

            mockMvc.perform(
                            post("/daily/toggle")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                                                                        {
                                                                                          "id": "%s",
                                                                                          "doneFlag": false
                                                                                        }
                                            """.formatted(id))
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id.toString()))
                    .andExpect(jsonPath("$.goalText").value("Goal test 1"))
                    .andExpect(jsonPath("$.doneFlag").value(false));

            verify(dailyPlanService).toggleFlag(any(ToggleFlagDTO.class));
        }

        @Test
        @DisplayName("Should contain null id in request and throw error")
        void shouldContainNullIdInRequest() throws Exception {

            mockMvc.perform(
                            post("/daily/toggle")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                                                                        {
                                                                                          "id": null,
                                                                                          "doneFlag": false
                                                                                        }
                                            """)
                    )
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(dailyPlanService);
        }

    }


}
