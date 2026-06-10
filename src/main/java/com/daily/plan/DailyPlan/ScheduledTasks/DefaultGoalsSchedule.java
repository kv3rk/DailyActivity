package com.daily.plan.DailyPlan.ScheduledTasks;

import com.daily.plan.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyPlan.Service.DailyPlanService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final String defaultGoalNumber1;
    private final String defaultGoalNumber2;
    private final String defaultGoalNumber3;
    private final String defaultGoalNumber4;
    private final String defaultGoalNumber5;
    private final String defaultGoalNumber6;
    private final String defaultGoalNumber7;
    private final String defaultGoalNumber8;
    private final String defaultGoalNumber9;
    private final String defaultGoalNumber10;
    private final String defaultGoalNumber11;
    private final String defaultGoalNumber12;

    public DefaultGoalsSchedule(DailyPlanService dailyPlanService,

                                @Value("${default.goal.number1}") String defaultGoalNumber1,
                                @Value("${default.goal.number2}") String defaultGoalNumber2,
                                @Value("${default.goal.number3}") String defaultGoalNumber3,
                                @Value("${default.goal.number4}") String defaultGoalNumber4,
                                @Value("${default.goal.number5}") String defaultGoalNumber5,
                                @Value("${default.goal.number6}") String defaultGoalNumber6,
                                @Value("${default.goal.number7}") String defaultGoalNumber7,
                                @Value("${default.goal.number8}") String defaultGoalNumber8,
                                @Value("${default.goal.number9}") String defaultGoalNumber9,
                                @Value("${default.goal.number10}") String defaultGoalNumber10,
                                @Value("${default.goal.number11}") String defaultGoalNumber11,
                                @Value("${default.goal.number12}") String defaultGoalNumber12) {
        this.dailyPlanService = dailyPlanService;
        this.defaultGoalNumber1 = defaultGoalNumber1;
        this.defaultGoalNumber2 = defaultGoalNumber2;
        this.defaultGoalNumber3 = defaultGoalNumber3;
        this.defaultGoalNumber4 = defaultGoalNumber4;
        this.defaultGoalNumber5 = defaultGoalNumber5;
        this.defaultGoalNumber6 = defaultGoalNumber6;
        this.defaultGoalNumber7 = defaultGoalNumber7;
        this.defaultGoalNumber8 = defaultGoalNumber8;
        this.defaultGoalNumber9 = defaultGoalNumber9;
        this.defaultGoalNumber10 = defaultGoalNumber10;
        this.defaultGoalNumber11 = defaultGoalNumber11;
        this.defaultGoalNumber12 = defaultGoalNumber12;
    }

    public void createGoal(String text) {

        GoalDTO defaultGoalDTO = new GoalDTO(
                null, text, false
        );

        dailyPlanService.save(defaultGoalDTO);

        log.info("Created DEFAULT goal with text [{}]", defaultGoalDTO.goalText());
    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 1/1 * 1-6", zone = "Europe/Moscow")
    public void addNumber1ScheduledGoal() {

        createGoal(defaultGoalNumber1);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 1/1 * 1-6", zone = "Europe/Moscow")
    public void addNumber2ScheduledGoal() {

        createGoal(defaultGoalNumber2);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * MON,THU", zone = "Europe/Moscow")
    public void addNumber3ScheduledGoal() {

        createGoal(defaultGoalNumber3);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * TUE,FRI", zone = "Europe/Moscow")
    public void addNumber4ScheduledGoal() {

        createGoal(defaultGoalNumber4);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * WED,SAT", zone = "Europe/Moscow")
    public void addNumber5ScheduledGoal() {

        createGoal(defaultGoalNumber5);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * SUN", zone = "Europe/Moscow")
    public void addNumber6ScheduledGoal() {

        createGoal(defaultGoalNumber6);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 1/1 * MON,WED,FRI,SUN", zone = "Europe/Moscow")
    public void addNumber7ScheduledGoal() {

        createGoal(defaultGoalNumber7);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 1/1 * 1-6", zone = "Europe/Moscow")
    public void addNumber8ScheduledGoal() {

        createGoal(defaultGoalNumber8);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 1/1 * TUE,THU,SAT", zone = "Europe/Moscow")
    public void addNumber9ScheduledGoal() {

        createGoal(defaultGoalNumber9);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 1/1 * MON,WED,FRI", zone = "Europe/Moscow")
    public void addNumber10ScheduledGoal() {

        createGoal(defaultGoalNumber10);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 1/1 * MON,WED,FRI", zone = "Europe/Moscow")
    public void addNumber11ScheduledGoal() {

        createGoal(defaultGoalNumber11);

    }

    @Transactional
    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0/30 * * * * *", zone = "Europe/Moscow")
    public void addNumber12ScheduledGoal() {

        createGoal(defaultGoalNumber12);
        createGoal(defaultGoalNumber12);
        createGoal(defaultGoalNumber12);

    }

}
