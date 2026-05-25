package com.daily.plan.DataAnalyzer.CollectExecution;

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
public class ProdPerTimeCollectExecution implements PerTimeCollectExecution {

    private final ConcatenatingValues concatenatingValues;
    private final TelegramBotLogic telegramBotLogic;
    private final String chatId;

    public ProdPerTimeCollectExecution(ConcatenatingValues concatenatingValues,
                                       TelegramBotLogic telegramBotLogic,
                                       @Value("${telegram.bot.chat.id}") String chatId) {
        this.concatenatingValues = concatenatingValues;
        this.telegramBotLogic = telegramBotLogic;
        this.chatId = chatId;
    }

    @Override
    @Scheduled(cron = "0/15 * * * * *", zone = "Europe/Moscow")
    public void dailyRollover() {

        telegramBotLogic.sendToChat(
                chatId,
                concatenatingValues.answerDailyRollover()
        );
    }

    @Override
    @Scheduled(cron = "0/20 * * * * *", zone = "Europe/Moscow")
    public void weeklyRollover() {

        telegramBotLogic.sendToChat(
                chatId,
                concatenatingValues.answerWeeklyRollover()
        );
    }

}