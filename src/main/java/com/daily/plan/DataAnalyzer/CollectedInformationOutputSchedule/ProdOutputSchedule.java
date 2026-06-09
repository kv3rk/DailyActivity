package com.daily.plan.DataAnalyzer.CollectedInformationOutputSchedule;

import com.daily.plan.StatsStorage.Service.StatsService;
import com.daily.plan.TgBot.PrepareAnswer.ConcatenatingValues;
import com.daily.plan.TgBot.TgBotLogic.TelegramBotLogic;
import com.daily.plan.common.blueprint.PerTimeCollectExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
@Profile("prod")
public class ProdOutputSchedule implements PerTimeCollectExecution {

    private final ConcatenatingValues concatenatingValues;
    private final TelegramBotLogic telegramBotLogic;
    private final String chatId;
    private final StatsService statsService;

    public ProdOutputSchedule(ConcatenatingValues concatenatingValues,
                              TelegramBotLogic telegramBotLogic,
                              @Value("${telegram.bot.chat.id}") String chatId,
                              StatsService statsService) {
        this.concatenatingValues = concatenatingValues;
        this.telegramBotLogic = telegramBotLogic;
        this.chatId = chatId;
        this.statsService = statsService;
    }

    @Override
    @Scheduled(cron = "0 55 23 1/1 * *", zone = "Europe/Moscow")
    public void dailyRollover() {

        telegramBotLogic.sendToChat(
                chatId,
                concatenatingValues.answerDailyRollover(statsService.saveDaily())
        );
    }

    @Override
    @Scheduled(cron = "0 55 23 * * SUN", zone = "Europe/Moscow")
    public void weeklyRollover() {

        telegramBotLogic.sendToChat(
                chatId,
                concatenatingValues.answerWeeklyRollover(statsService.saveWeekly())
        );
    }

}