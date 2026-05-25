package com.daily.plan.DataAnalyzer.CollectExecution;

import com.daily.plan.DataAnalyzer.Service.DataAnalyzerService;
import com.daily.plan.TgBot.TelegramBotLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
public class PerTimeCollectExecution {

    public final DataAnalyzerService dataAnalyzerService;
    private final TelegramBotLogic telegramBotLogic;
    private final String chatId;

    public PerTimeCollectExecution(DataAnalyzerService dataAnalyzerService,
                                   TelegramBotLogic telegramBotLogic,
                                   @Value("${telegram.bot.chat.id}") String chatId) {
        this.dataAnalyzerService = dataAnalyzerService;
        this.telegramBotLogic = telegramBotLogic;
        this.chatId = chatId;
    }

    @Scheduled(cron = "0 0 11 1/1 * *", zone = "Europe/Moscow")
    public void dailyRollover() {

        Long totalGoalAmount = dataAnalyzerService.getAmountTodayGoals().orElse(0L);
        Long activeGoalAmount = dataAnalyzerService.getAmountTodayActiveGoals().orElse(0L);
        Long doneGoalAmount = dataAnalyzerService.getAmountTodayDoneGoals().orElse(0L);
        Long totalActivityAmount = dataAnalyzerService.getAmountTodayActivities().orElse(0L);
        Long timeSpendOnActivities = dataAnalyzerService.getAmountTimeSpendOnActivitiesToday();

        StringBuilder string = new StringBuilder();
        string.append("Amount of goals today: ").append(totalGoalAmount).append("\n")
                .append("Amount of unaccomplished goals today: ").append(activeGoalAmount).append("\n")
                .append("Amount of accomplished goals today: ").append(doneGoalAmount).append("\n\n")
                .append("Amount of activities today: ").append(totalActivityAmount).append("\n")
                .append("Total time spend on activities: ").append(timeSpendOnActivities);

        telegramBotLogic.sendToChat(
                chatId,
                String.valueOf(string)
        );
    }

}