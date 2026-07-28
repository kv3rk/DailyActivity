package com.daily.plan.DailyActivityTracker.TelegramBot.CollectedInformationOutputSchedule;

import com.daily.plan.DailyActivityTracker.StatsStorage.DTO.StatsDTO;
import com.daily.plan.DailyActivityTracker.StatsStorage.Service.StatsService;
import com.daily.plan.DailyActivityTracker.TelegramBot.PrepareAnswer.ConcatenatingValues;
import com.daily.plan.DailyActivityTracker.common.blueprint.PerTimeCollectExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
@Profile("dev")
public class DevOutputSchedule implements PerTimeCollectExecution {

    private final ConcatenatingValues concatenatingValues;
    private final StatsService statsService;

    public DevOutputSchedule(ConcatenatingValues concatenatingValues,
                             StatsService statsService) {

        this.concatenatingValues = concatenatingValues;
        this.statsService = statsService;
    }


    @Override
    @Scheduled(cron = "0/15 * * * * * ", zone = "Europe/Moscow")
    public void dailyRollover() {

        for (StatsDTO dto : statsService.dailyResults()) {

            log.info(
                    "\n {}",
                    concatenatingValues.answerDailyRollover(dto)
            );


        }

    }

    @Override
    @Scheduled(cron = "0/15 * * * * *", zone = "Europe/Moscow")
    public void weeklyRollover() {

        for (StatsDTO dto : statsService.weeklyResults()) {

            log.info(
                    "\n {}",
                    concatenatingValues.answerWeeklyRollover(dto)
            );


        }

    }

}