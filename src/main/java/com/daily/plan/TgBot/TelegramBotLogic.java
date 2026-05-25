package com.daily.plan.TgBot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TelegramBotLogic extends TelegramLongPollingBot {

    private final String username;

    public TelegramBotLogic(
            @Value("${telegram.bot.username}") String username,
            @Value("${telegram.bot.token}") String token
            ) {
        super(token);
        this.username = username;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String chatId = update.getMessage().getChatId().toString();

        log.info("Received from chat {}", chatId);

        sendToChat(chatId, chatId);
    }

    public void sendToChat(String chatId, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .build());

            log.info("Message sent to chat [{}]", chatId);

        } catch (Exception e) {
            log.error("Telegram send error", e);
        }
    }

}