package com.daily.plan.TgBot.PrepareAnswer;

import com.daily.plan.DataAnalyzer.Service.DailyDataAnalyzerService;
import com.daily.plan.DataAnalyzer.Service.WeeklyDataAnalyzerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("prod")
public class ConcatenatingValues {
    public final DailyDataAnalyzerService dailyDataAnalyzerService;
    private final WeeklyDataAnalyzerService weeklyDataAnalyzerService;

    public ConcatenatingValues(DailyDataAnalyzerService dailyDataAnalyzerService, WeeklyDataAnalyzerService weeklyDataAnalyzerService) {
        this.dailyDataAnalyzerService = dailyDataAnalyzerService;
        this.weeklyDataAnalyzerService = weeklyDataAnalyzerService;
    }

    public String answerDailyRollover() {
        Long totalGoalAmount = dailyDataAnalyzerService.getAmountTodayGoals().orElse(0L);
        Long activeGoalAmount = dailyDataAnalyzerService.getAmountTodayActiveGoals().orElse(0L);
        Long doneGoalAmount = dailyDataAnalyzerService.getAmountTodayDoneGoals().orElse(0L);

        Long percentageCompletion = dailyDataAnalyzerService.calculatePercentageCompletion(
                activeGoalAmount,
                doneGoalAmount
        );

        Long totalActivityAmount = dailyDataAnalyzerService.getAmountTodayActivities().orElse(0L);
        Long timeSpendOnActivities = dailyDataAnalyzerService.getAmountTimeSpendOnActivitiesToday();

        StringBuilder string = new StringBuilder();
        string
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖").append("\n")
                .append("✨DAILY REPORT✨")
                .append("\n✅\n")
                .append("Amount of goals today: ").append(totalGoalAmount).append("\n")
                .append("Percentage completion: ").append(percentageCompletion).append("\uD83D\uDE36")
                .append("\n✅\n")
                .append("Amount of activities today: ").append(totalActivityAmount).append("\n")
                .append("Total time spend on activities: ").append(timeSpendOnActivities).append("\n")
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖");

        log.info("Prepared answer for daily TG mail in size [{}]",
                string.length());

        return String.valueOf(string);
    }

    public String answerWeeklyRollover() {

        Long totalGoalAmount = weeklyDataAnalyzerService.getAmountWeeklyGoals().orElse(0L);
        Long activeGoalAmount = weeklyDataAnalyzerService.getAmountWeeklyActiveGoals().orElse(0L);
        Long doneGoalAmount = weeklyDataAnalyzerService.getAmountWeeklyDoneGoals().orElse(0L);

        Long percentageCompletion = dailyDataAnalyzerService.calculatePercentageCompletion(
                activeGoalAmount,
                doneGoalAmount
        );

        Long totalActivityAmount = weeklyDataAnalyzerService.getAmountWeeklyActivities().orElse(0L);
        Long timeSpendOnActivities = weeklyDataAnalyzerService.getAmountTimeSpendOnActivitiesWeekly();

        StringBuilder string = new StringBuilder();
        string
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖").append("\n")
                .append("\uD83C\uDFC6WEEKLY REPORT\uD83C\uDFC6")
                .append("\n✅\n")
                .append("Amount of goals weekly: ").append(totalGoalAmount).append("\n")
                .append("Percentage completion: ").append(percentageCompletion).append("\uD83C\uDFC6")
                .append("\n✅\n")
                .append("Amount of activities weekly: ").append(totalActivityAmount).append("\n")
                .append("Total time spend on activities: ").append(timeSpendOnActivities).append("\n")
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖➖");

        log.info("Prepared answer for weekly TG mail in size [{}]",
                string.length());

        return String.valueOf(string);
    }
}
