package com.daily.plan.DataAnalyzer.CollectExecution;

import com.daily.plan.DataAnalyzer.Service.DailyDataAnalyzerService;
import com.daily.plan.DataAnalyzer.Service.WeeklyDataAnalyzerService;
import com.daily.plan.TgBot.TelegramBotLogic;
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

    public final DailyDataAnalyzerService dailyDataAnalyzerService;
    private final WeeklyDataAnalyzerService weeklyDataAnalyzerService;
    private final TelegramBotLogic telegramBotLogic;
    private final String chatId;

    public ProdPerTimeCollectExecution(DailyDataAnalyzerService dailyDataAnalyzerService, WeeklyDataAnalyzerService weeklyDataAnalyzerService,
                                       TelegramBotLogic telegramBotLogic,
                                       @Value("${telegram.bot.chat.id}") String chatId) {
        this.dailyDataAnalyzerService = dailyDataAnalyzerService;
        this.weeklyDataAnalyzerService = weeklyDataAnalyzerService;
        this.telegramBotLogic = telegramBotLogic;
        this.chatId = chatId;
    }

    @Override
    @Scheduled(cron = "0/15 * * * * * ", zone = "Europe/Moscow")
    public void dailyRollover() {

        Long totalGoalAmount = dailyDataAnalyzerService.getAmountTodayGoals().orElse(0L);
        Long activeGoalAmount = dailyDataAnalyzerService.getAmountTodayActiveGoals().orElse(0L);
        Long doneGoalAmount = dailyDataAnalyzerService.getAmountTodayDoneGoals().orElse(0L);
        Long totalActivityAmount = dailyDataAnalyzerService.getAmountTodayActivities().orElse(0L);
        Long timeSpendOnActivities = dailyDataAnalyzerService.getAmountTimeSpendOnActivitiesToday();

        StringBuilder string = new StringBuilder();
        string
                .append("\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0").append("\n")
                .append("✨DAILY REPORT✨").append("\n\uD83D\uDFF0\n")
                .append("Amount of goals today: ").append(totalGoalAmount).append("\n")
                .append("Amount of unaccomplished goals today: ").append(activeGoalAmount).append("\n")
                .append("Amount of accomplished goals today: ").append(doneGoalAmount).append("\n\uD83D\uDFF0\n")
                .append("Amount of activities today: ").append(totalActivityAmount).append("\n")
                .append("Total time spend on activities: ").append(timeSpendOnActivities).append("\n")
                .append("\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0");

        telegramBotLogic.sendToChat(
                chatId,
                String.valueOf(string)
        );
    }

    @Override
    @Scheduled(cron = "0/30 * * * * * ", zone = "Europe/Moscow")
    public void weeklyRollover() {

        Long totalGoalAmount = weeklyDataAnalyzerService.getAmountWeeklyGoals().orElse(0L);
        Long activeGoalAmount = weeklyDataAnalyzerService.getAmountWeeklyActiveGoals().orElse(0L);
        Long doneGoalAmount = weeklyDataAnalyzerService.getAmountWeeklyDoneGoals().orElse(0L);
        Long totalActivityAmount = weeklyDataAnalyzerService.getAmountWeeklyActivities().orElse(0L);
        Long timeSpendOnActivities = weeklyDataAnalyzerService.getAmountTimeSpendOnActivitiesWeekly();

        StringBuilder string = new StringBuilder();
        string
                .append("\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0").append("\n")
                .append("\uD83C\uDFC6WEEKLY REPORT\uD83C\uDFC6").append("\n\uD83D\uDFF0\n")
                .append("Amount of goals weekly: ").append(totalGoalAmount).append("\n")
                .append("Amount of unaccomplished goals weekly: ").append(activeGoalAmount).append("\n")
                .append("Amount of accomplished goals weekly: ").append(doneGoalAmount).append("\n\uD83D\uDFF0\n")
                .append("Amount of activities weekly: ").append(totalActivityAmount).append("\n")
                .append("Total time spend on activities: ").append(timeSpendOnActivities).append("\n")
                .append("\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0\uD83D\uDFF0");

        telegramBotLogic.sendToChat(
                chatId,
                String.valueOf(string)
        );
    }

}