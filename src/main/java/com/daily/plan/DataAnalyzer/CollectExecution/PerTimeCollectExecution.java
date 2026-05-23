package com.daily.plan.DataAnalyzer.CollectExecution;

import com.daily.plan.DataAnalyzer.Service.DataAnalyzerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@EnableScheduling
public class PerTimeCollectExecution {

    public final DataAnalyzerService dataAnalyzerService;

    public PerTimeCollectExecution(DataAnalyzerService dataAnalyzerService) {
        this.dataAnalyzerService = dataAnalyzerService;
    }

    @Scheduled(cron = "0/15 * * * * *", zone = "Europe/Moscow")
    public void dailyRollover() {

        log.info("""
                        Daily rollover done in time [{}] with values:
                        ALL GOALS [{}]
                        ACTIVE GOALS [{}]
                        ACCOMPLISHED GOALS [{}]
                        AMOUNT OF ACTIVITIES [{}]
                        AMOUNT ACTIVITIES TIME [{}]
                        """,
                LocalDateTime.now(), dataAnalyzerService.getAmountTodayGoals(),
                dataAnalyzerService.getAmountTodayActiveGoals(),
                dataAnalyzerService.getAmountTodayDoneGoals(),
                dataAnalyzerService.getAmountTodayActivities(),
                dataAnalyzerService.getAmountTimeSpendOnActivitiesToday());

    }

}
