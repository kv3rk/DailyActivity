package com.daily.plan.DataAnalyzer.CollectExecution;

import com.daily.plan.StatsStorage.Service.StatsService;
import com.daily.plan.TgBot.PrepareAnswer.ConcatenatingValues;
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

    private final ConcatenatingValues concatenatingValues;
    private final StatsService statsService;

    public DevPerTimeCollectExecution(ConcatenatingValues concatenatingValues, StatsService statsService) {

        this.concatenatingValues = concatenatingValues;
        this.statsService = statsService;
    }


    @Override
    @Scheduled(cron = "0/15 * * * * * ", zone = "Europe/Moscow")
    public void dailyRollover() {

        log.info(
                "\n {}",
                concatenatingValues.answerDailyRollover(statsService.saveDaily())
        );
    }

    @Override
    @Scheduled(cron = "0/15 * * * * *", zone = "Europe/Moscow")
    public void weeklyRollover() {

        log.info(
                "\n {}",
                concatenatingValues.answerWeeklyRollover(statsService.saveWeekly())
        );

    }
}