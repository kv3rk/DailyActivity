package com.daily.plan.DataAnalyzer.CollectExecution;

import com.daily.plan.DataAnalyzer.Service.DataAnalyzerService;
import com.daily.plan.TgBot.TelegramBotLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@EnableScheduling
public class PerTimeCollectExecution {

    public final DataAnalyzerService dataAnalyzerService;
    private final TelegramBotLogic telegramBotLogic;

    public PerTimeCollectExecution(DataAnalyzerService dataAnalyzerService, TelegramBotLogic telegramBotLogic) {
        this.dataAnalyzerService = dataAnalyzerService;
        this.telegramBotLogic = telegramBotLogic;
    }

    @Scheduled(cron = "0/15 * * * * *", zone = "Europe/Moscow")
    public void dailyRollover() {

        Long amount = dataAnalyzerService.getAmountTodayGoals().orElse(0L);

        telegramBotLogic.sendToChat(
                "ТВОЙ_CHAT_ID",
                "Сегодня целей: " + amount
        );
    }

}