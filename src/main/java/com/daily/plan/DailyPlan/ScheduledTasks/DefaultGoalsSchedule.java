package com.daily.plan.DailyPlan.ScheduledTasks;

import com.daily.plan.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyPlan.Service.DailyPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
@Profile({"dev", "prod"})
public class DefaultGoalsSchedule {

    private final DailyPlanService dailyPlanService;

    public DefaultGoalsSchedule(DailyPlanService dailyPlanService) {
        this.dailyPlanService = dailyPlanService;
    }

    public void createGoal(String text) {

        GoalDTO defaultGoalDTO = new GoalDTO(
                null, text, false
        );

        dailyPlanService.save(defaultGoalDTO);

        log.info("Created DEFAULT goal with text [{}]", defaultGoalDTO.goalText());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 30 0 1/1 * *", zone = "Europe/Moscow")
    public void addAnkiScheduledGoal() {

        createGoal("Anki");

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 30 0 1/1 * *", zone = "Europe/Moscow")
    public void addProgrammingScheduledGoal() {

        createGoal("Programming");

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 30 0 * * MON,THU", zone = "Europe/Moscow")
    public void addEnglishSpeakingScheduledGoal() {

        createGoal("English Speaking at least 20 min");

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 30 0 * * TUE,FRI", zone = "Europe/Moscow")
    public void addEnglishBookScheduledGoal() {

        createGoal("English Book at least 20 min");

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 30 0 * * WED,SAT", zone = "Europe/Moscow")
    public void addEnglishListeningScheduledGoal() {

        createGoal("English Listening at least 20 min");

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 30 0 * * SUN", zone = "Europe/Moscow")
    public void addCleanLearnMeScheduledGoal() {

        createGoal("Clean Learn Me");

    }
}
