package com.daily.plan.DailyPlan.GoalRefresh;

import com.daily.plan.DailyPlan.Service.DailyPlanService;
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

    public RefreshingGoal(DailyPlanService dailyPlanService) {
        this.dailyPlanService = dailyPlanService;
    }


    @Async
    @Scheduled(cron = "0 0 4 * * *", zone = "Europe/Moscow")
    public void scheduleTaskUsingCronExpression() {

        log.info("Refreshed goals in DB in [{}]", LocalDateTime.now());

        dailyPlanService.deleteAll();
    }
}
