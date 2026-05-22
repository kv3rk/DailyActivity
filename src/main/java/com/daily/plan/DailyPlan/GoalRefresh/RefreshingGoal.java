package com.daily.plan.DailyPlan.GoalRefresh;

import com.daily.plan.DailyPlan.Service.DailyPlanService;
import com.daily.plan.Timer.Service.TimerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@EnableScheduling
public class RefreshingGoal {

    private final DailyPlanService dailyPlanService;
    private final TimerService timerService;

    public RefreshingGoal(DailyPlanService dailyPlanService, TimerService timerService) {
        this.dailyPlanService = dailyPlanService;
        this.timerService = timerService;
    }


    @Scheduled(cron = "0 0 4 1 1/1 *", zone = "Europe/Moscow")
    public void scheduleTaskUsingCronExpression() {

        log.info("Removed all goals from the db at [{}]", LocalDateTime.now());

        dailyPlanService.deleteAll();

        timerService.deleteAll();
    }
}
