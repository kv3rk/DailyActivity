package com.daily.plan.DailyActivityTracker.common.blueprint;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public interface PerTimeCollectExecution {

    @Scheduled(cron = "0 55 23 1/1 * *", zone = "Europe/Moscow")
    public void dailyRollover();

    @Scheduled(cron = "0 55 23 * * 7/7", zone = "Europe/Moscow")
    public void weeklyRollover();
}
