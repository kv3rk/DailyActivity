package com.daily.plan.TgBot.TgBotLogic;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("prod")
public class TelegramBotConfig {

    private final TelegramBotLogic telegramBotLogic;

    @PostConstruct
    public void registerBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBotLogic);
            log.info("TELEGRAM BOT REGISTERED");
        } catch (Exception e) {
            log.error("BOT REGISTRATION FAILED", e);
        }
    }
}
