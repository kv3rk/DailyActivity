package com.daily.plan.DataAnalyzer.CollectExecution;

import com.daily.plan.DataAnalyzer.Service.DailyDataAnalyzerService;
import com.daily.plan.DataAnalyzer.Service.WeeklyDataAnalyzerService;
import com.daily.plan.common.blueprint.PerTimeCollectExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
@Profile("dev")
public class DevPerTimeCollectExecution implements PerTimeCollectExecution {

    public final DailyDataAnalyzerService dailyDataAnalyzerService;
    private final WeeklyDataAnalyzerService weeklyDataAnalyzerService;

    public DevPerTimeCollectExecution(DailyDataAnalyzerService dailyDataAnalyzerService, WeeklyDataAnalyzerService weeklyDataAnalyzerService) {
        this.dailyDataAnalyzerService = dailyDataAnalyzerService;
        this.weeklyDataAnalyzerService = weeklyDataAnalyzerService;
    }


    @Override
    @Scheduled(cron = "0/15 * * * * * ", zone = "Europe/Moscow")
    public void dailyRollover() {

        log.info("""
                        
                        Amount of goals today: [{}]
                        Amount of unaccomplished goals today: [{}]
                        Amount of accomplished goals today: [{}]
                        Amount of activities today: [{}]
                        Total time spend on activities: [{}]
                        """,
                dailyDataAnalyzerService.getAmountTodayGoals().orElse(0L),
                dailyDataAnalyzerService.getAmountTodayActiveGoals().orElse(0L),
                dailyDataAnalyzerService.getAmountTodayDoneGoals().orElse(0L),
                dailyDataAnalyzerService.getAmountTodayActivities().orElse(0L),
                dailyDataAnalyzerService.getAmountTimeSpendOnActivitiesToday()
        );
    }

    @Override
    @Scheduled(cron = "0/15 * * * * *", zone = "Europe/Moscow")
    public void weeklyRollover() {

        log.info("""
                        
                        Amount of goals weekly: [{}]
                        Amount of unaccomplished goals weekly: [{}]
                        Amount of accomplished goals weekly: [{}]
                        Amount of activities weekly: [{}]
                        Total time spend on activities: [{}]
                        """,
                weeklyDataAnalyzerService.getAmountWeeklyGoals().orElse(0L),
                weeklyDataAnalyzerService.getAmountWeeklyActiveGoals().orElse(0L),
                weeklyDataAnalyzerService.getAmountWeeklyDoneGoals().orElse(0L),
                weeklyDataAnalyzerService.getAmountWeeklyActivities().orElse(0L),
                weeklyDataAnalyzerService.getAmountTimeSpendOnActivitiesWeekly()
        );

    }
}