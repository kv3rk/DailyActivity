package com.daily.plan.TgBot;

import com.daily.plan.DataAnalyzer.CollectExecution.PerTimeCollectExecution;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class TelegramBotLogic extends TelegramLongPollingBot {

    private final String username;
    private final PerTimeCollectExecution collectExecution;

    public TelegramBotLogic(
            @Value("${telegram.bot.username}") String username,
            @Value("${telegram.bot.token}") String token,
            PerTimeCollectExecution collectExecution
    ) {
        super(token);
        this.username = username;
        this.collectExecution = collectExecution;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("UPDATE: {}", update);

        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        try {
            execute(SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString())
                    .text(getAmountTodayGoalsTelegram().toString())
                    .build());
        } catch (Exception e) {
            log.error("Telegram error", e);
        }
    }

    public Long getAmountTodayGoalsTelegram() {

        Long executeParam = collectExecution.dailyRollover().orElseThrow();

        log.info("Return in TELEGRAM amount of all today goals");

        return executeParam;
    }

}