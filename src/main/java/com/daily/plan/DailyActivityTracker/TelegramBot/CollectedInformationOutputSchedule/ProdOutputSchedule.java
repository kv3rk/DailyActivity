package com.daily.plan.DailyActivityTracker.TelegramBot.CollectedInformationOutputSchedule;

import com.daily.plan.DailyActivityTracker.StatsStorage.Service.StatsService;
import com.daily.plan.DailyActivityTracker.TelegramBot.PrepareAnswer.ConcatenatingValues;
import com.daily.plan.DailyActivityTracker.TelegramBot.Service.TelegramBotLogic;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import com.daily.plan.DailyActivityTracker.common.blueprint.PerTimeCollectExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@EnableScheduling
@Profile("prod")
public class ProdOutputSchedule implements PerTimeCollectExecution {

    private final ConcatenatingValues concatenatingValues;
    private final TelegramBotLogic telegramBotLogic;
    private final StatsService statsService;

    public ProdOutputSchedule(ConcatenatingValues concatenatingValues,
                              TelegramBotLogic telegramBotLogic,
                              StatsService statsService
    ) {
        this.concatenatingValues = concatenatingValues;
        this.telegramBotLogic = telegramBotLogic;
        this.statsService = statsService;
    }

    @Override
    @Scheduled(cron = "0/30 * * * * *", zone = "Europe/Moscow")
    public void dailyRollover() {

        List<User> users = statsService.usersWithLinkedTelegram();

        for (User user : users) {
            String chatId = user.getTelegram();

            if (!isValidChatId(chatId)) {
                log.warn("User [{}] has no linked Telegram chat, skipping daily report", user.getUsername());
                continue;
            }

            var message = concatenatingValues.answerDailyRollover(
                    statsService.saveDaily(user)
            );

            telegramBotLogic.sendToChat(chatId, message);
        }
    }

    @Override
    @Scheduled(cron = "0 55 23 * * SUN", zone = "Europe/Moscow")
    public void weeklyRollover() {

        List<User> users = statsService.usersWithLinkedTelegram();

        for (User user : users) {
            String chatId = user.getTelegram();

            if (!isValidChatId(chatId)) {
                log.warn("User [{}] has no linked Telegram chat, skipping weekly report", user.getUsername());
                continue;
            }

            var message = concatenatingValues.answerWeeklyRollover(
                    statsService.saveWeekly(user)
            );

            telegramBotLogic.sendToChat(chatId, message);
        }
    }

    private boolean isValidChatId(String telegram) {
        if (telegram == null || telegram.isBlank()) {
            return false;
        }
        return !telegram.contains("-");
    }
}