package com.daily.plan.DailyActivityTracker.TelegramBot.Service;

import com.daily.plan.DailyActivityTracker.User.Entity.User;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@Profile("prod")
public class TelegramBotLogic extends TelegramLongPollingBot {

    private final String username;
    private final UserRepository userRepository;

    public TelegramBotLogic(
            @Value("${telegram.bot.username}") String username,
            @Value("${telegram.bot.token}") String token,
            UserRepository userRepository
    ) {
        super(token);
        this.username = username;
        this.userRepository = userRepository;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    @Transactional
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        var message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.startsWith("/start ")) {
            String uuid = text.replace("/start ", "").trim();
            linkUser(uuid, chatId);
        }

        if (userRepository.existsByTelegram(String.valueOf(chatId))) {
            log.info("User with chatId {} exists", chatId);
        } else {
            log.info("User with this chatId doesnt exists {}. {} has chadId {}",
                    chatId, userRepository.findByUsername("kv3rk").getUsername(),
                    userRepository.findByUsername("kv3rk").getTelegram());
        }
    }

    @Transactional
    public void linkUser(String uuid, Long chatId) {

        if (!userRepository.existsByTelegram(uuid)) {
            log.warn("No user found with telegram UUID [{}]", uuid);
            return;
        }

        User user = userRepository.findByTelegram(uuid);
        user.setTelegram(String.valueOf(chatId));
        userRepository.save(user);

        log.info("Linked user [{}] to chatId [{}]", user.getUsername(), chatId);

        sendToChat(String.valueOf(chatId), "✅ Connected! You (" + user.getUsername() + ") will receive daily and weekly reports. On chatId "
                + chatId + " on telegram "
                + user.getTelegram()
                + " on uuid " + uuid);
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