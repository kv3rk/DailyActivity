package com.daily.plan.DailyActivityTracker.DailyPlan.ScheduledTasks;

import com.daily.plan.DailyActivityTracker.DailyPlan.Service.DailyPlanService;
import com.daily.plan.DailyActivityTracker.Timer.Service.TimerService;
import com.daily.plan.DailyActivityTracker.common.unit.CurrentDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
public class MonthlyDatabaseCleanup {

    private final DailyPlanService dailyPlanService;
    private final TimerService timerService;
    private final CurrentDateTime currentDateTime;

    public MonthlyDatabaseCleanup(DailyPlanService dailyPlanService, TimerService timerService, CurrentDateTime currentDateTime) {
        this.dailyPlanService = dailyPlanService;
        this.timerService = timerService;
        this.currentDateTime = currentDateTime;
    }


    @Scheduled(cron = "0 0 4 1 1/1 *", zone = "Europe/Moscow")
    public void scheduleTaskUsingCronExpression() {

        dailyPlanService.deleteAll();

        timerService.deleteAll();

        log.info("Removed all goals from the db at [{}]", currentDateTime.getFormattedTime());
    }
}
