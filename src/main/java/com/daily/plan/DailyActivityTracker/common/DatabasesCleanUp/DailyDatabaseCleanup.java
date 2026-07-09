package com.daily.plan.DailyActivityTracker.common.DatabasesCleanUp;

import com.daily.plan.DailyActivityTracker.DailyGoals.Service.DailyPlanService;
import com.daily.plan.DailyActivityTracker.ActivityTime.Service.TimerService;
import com.daily.plan.DailyActivityTracker.common.unit.CurrentDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
public class DailyDatabaseCleanup {

    private final DailyPlanService dailyPlanService;
    private final TimerService timerService;
    private final CurrentDateTime currentDateTime;

    public DailyDatabaseCleanup(DailyPlanService dailyPlanService, TimerService timerService, CurrentDateTime currentDateTime) {
        this.dailyPlanService = dailyPlanService;
        this.timerService = timerService;
        this.currentDateTime = currentDateTime;
    }


    @Scheduled(cron = "0 1 0 1/1 * *", zone = "Europe/Moscow")
    public void dailyCleanUp() {

        dailyPlanService.deleteAll();

        timerService.deleteAll();

        log.info("Removed all goals from\n[timer_activities]\n[daily_goals]\n at [{}]",
                currentDateTime.getFormattedTime());
    }
}
