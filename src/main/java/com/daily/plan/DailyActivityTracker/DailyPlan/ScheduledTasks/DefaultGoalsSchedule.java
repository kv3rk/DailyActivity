package com.daily.plan.DailyActivityTracker.DailyPlan.ScheduledTasks;

import com.daily.plan.DailyActivityTracker.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyActivityTracker.DailyPlan.Service.DailyPlanService;
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
    private final String defaultGoalNumber11;
    private final String defaultGoalNumber12;
    private final String defaultGoalNumber13;
    private final String defaultGoalNumber14;
    private final String defaultGoalNumber15;
    private final String defaultGoalNumber16;
    private final String defaultGoalNumber17;
    private final String defaultGoalNumber20;
    private final String defaultGoalNumber21;
    private final String defaultGoalNumber22;
    private final String defaultGoalNumber23;
    private final String defaultGoalNumber24;
    private final String defaultGoalNumber25;
    private final String defaultGoalNumber26;

    public DefaultGoalsSchedule(

            DailyPlanService dailyPlanService,

            @Value("${default.goal.number1}") String defaultGoalNumber1,
            @Value("${default.goal.number2}") String defaultGoalNumber2,
            @Value("${default.goal.number3}") String defaultGoalNumber3,
            @Value("${default.goal.number4}") String defaultGoalNumber4,
            @Value("${default.goal.number5}") String defaultGoalNumber5,
            @Value("${default.goal.number6}") String defaultGoalNumber6,
            @Value("${default.goal.number7}") String defaultGoalNumber7,
            @Value("${default.goal.number8}") String defaultGoalNumber8,
            @Value("${default.goal.number11}") String defaultGoalNumber11,
            @Value("${default.goal.number12}") String defaultGoalNumber12,
            @Value("${default.goal.number13}") String defaultGoalNumber13,
            @Value("${default.goal.number14}") String defaultGoalNumber14,
            @Value("${default.goal.number15}") String defaultGoalNumber15,
            @Value("${default.goal.number16}") String defaultGoalNumber16,
            @Value("${default.goal.number17}") String defaultGoalNumber17,
            @Value("${default.goal.number20}") String defaultGoalNumber20,
            @Value("${default.goal.number21}") String defaultGoalNumber21,
            @Value("${default.goal.number22}") String defaultGoalNumber22,
            @Value("${default.goal.number23}") String defaultGoalNumber23,
            @Value("${default.goal.number24}") String defaultGoalNumber24,
            @Value("${default.goal.number25}") String defaultGoalNumber25,
            @Value("${default.goal.number26}") String defaultGoalNumber26) {

        this.dailyPlanService = dailyPlanService;

        this.defaultGoalNumber1 = defaultGoalNumber1;
        this.defaultGoalNumber2 = defaultGoalNumber2;
        this.defaultGoalNumber3 = defaultGoalNumber3;
        this.defaultGoalNumber4 = defaultGoalNumber4;
        this.defaultGoalNumber5 = defaultGoalNumber5;
        this.defaultGoalNumber6 = defaultGoalNumber6;
        this.defaultGoalNumber7 = defaultGoalNumber7;
        this.defaultGoalNumber8 = defaultGoalNumber8;
        this.defaultGoalNumber11 = defaultGoalNumber11;
        this.defaultGoalNumber12 = defaultGoalNumber12;
        this.defaultGoalNumber13 = defaultGoalNumber13;
        this.defaultGoalNumber14 = defaultGoalNumber14;
        this.defaultGoalNumber15 = defaultGoalNumber15;
        this.defaultGoalNumber16 = defaultGoalNumber16;
        this.defaultGoalNumber17 = defaultGoalNumber17;
        this.defaultGoalNumber20 = defaultGoalNumber20;
        this.defaultGoalNumber21 = defaultGoalNumber21;
        this.defaultGoalNumber22 = defaultGoalNumber22;
        this.defaultGoalNumber23 = defaultGoalNumber23;
        this.defaultGoalNumber24 = defaultGoalNumber24;
        this.defaultGoalNumber25 = defaultGoalNumber25;
        this.defaultGoalNumber26 = defaultGoalNumber26;
    }

    public void createGoal(String text) {

        GoalDTO defaultGoalDTO = new GoalDTO(
                null, text, false
        );

        dailyPlanService.save(defaultGoalDTO);

        log.info("Created DEFAULT goal with text [{}]", defaultGoalDTO.goalText());
    }


    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 1/1 * *", zone = "Europe/Moscow")
    public void addDailyScheduledGoal() {

        createGoal(defaultGoalNumber12);
        createGoal(defaultGoalNumber13);
        createGoal(defaultGoalNumber14);
        createGoal(defaultGoalNumber15);
        createGoal(defaultGoalNumber16);
        createGoal(defaultGoalNumber17);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * 1-6", zone = "Europe/Moscow")
    public void addAlmostDailyScheduledGoal() {

        createGoal(defaultGoalNumber1);
        createGoal(defaultGoalNumber2);
        createGoal(defaultGoalNumber26);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * SUN", zone = "Europe/Moscow")
    public void addSundayScheduledGoal() {

        createGoal(defaultGoalNumber6);
        createGoal(defaultGoalNumber24);
        createGoal(defaultGoalNumber25);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * MON,THU", zone = "Europe/Moscow")
    public void addMonANDThuScheduledGoal() {

        createGoal(defaultGoalNumber3);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * TUE,FRI", zone = "Europe/Moscow")
    public void addTueANDFriScheduledGoal() {

        createGoal(defaultGoalNumber4);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * WED,SAT", zone = "Europe/Moscow")
    public void addWedANDSatScheduledGoal() {

        createGoal(defaultGoalNumber5);

    }


    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * MON,WED,FRI,SUN", zone = "Europe/Moscow")
    public void addMonANDWedANDFriANDSunScheduledGoal() {

        createGoal(defaultGoalNumber7);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * TUE,THU,SAT", zone = "Europe/Moscow")
    public void addTueANDThuANDSatScheduledGoal() {

        createGoal(defaultGoalNumber8);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * MON,WED,FRI", zone = "Europe/Moscow")
    public void addMonANDWedANDFriScheduledGoal() {

        createGoal(defaultGoalNumber11);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * MON,FRI", zone = "Europe/Moscow")
    public void addMonAndFriScheduledGoal() {

        createGoal(defaultGoalNumber20);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * TUE,SAT", zone = "Europe/Moscow")
    public void addThuAndSatScheduledGoal() {

        createGoal(defaultGoalNumber21);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * WED", zone = "Europe/Moscow")
    public void addOnlyWednesdayScheduledGoal() {

        createGoal(defaultGoalNumber22);

    }

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 1 0 * * THU", zone = "Europe/Moscow")
    public void addOnlyThursdayScheduledGoal() {

        createGoal(defaultGoalNumber23);

    }

}
