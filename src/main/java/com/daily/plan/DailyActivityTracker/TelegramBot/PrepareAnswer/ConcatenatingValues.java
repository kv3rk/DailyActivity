package com.daily.plan.DailyActivityTracker.TelegramBot.PrepareAnswer;

import com.daily.plan.DailyActivityTracker.StatsStorage.DTO.StatsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@Profile({"dev", "prod"})
public class ConcatenatingValues {

    private final NumberIntoEmoji numberIntoEmoji;

    public ConcatenatingValues(NumberIntoEmoji numberIntoEmoji) {
        this.numberIntoEmoji = numberIntoEmoji;
    }

    public String listActivitiesString(Map<String, Double> result) {

        StringBuilder string = new StringBuilder();

        result.forEach(
                (x, y) -> {
                    if (y > 0) {
                        string.append("\uD83D\uDD39").append(x)
                                .append(": ").append(numberIntoEmoji.convertNumToEmoji(y))
                                .append(" hours\n");
                    }
                }
        );

        return String.valueOf(string);

    }

    public String answerDailyRollover(StatsDTO statsDTO) {

        StringBuilder string = new StringBuilder();
        string
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖").append("\n")
                .append("✨DAILY REPORT✨")
                .append("\n✅\n")
                .append("Amount of goals today: ").append(numberIntoEmoji.convertNumToEmoji(statsDTO.amountGoals()))
                .append("\n")
                .append("Percentage completion: ").append(numberIntoEmoji.convertNumToEmoji(statsDTO.percentageCompletion())).append("%")
                .append("\n✅\n")
                .append("Amount of activities today: ").append(numberIntoEmoji.convertNumToEmoji(statsDTO.amountActivities())).append("\n")
                .append(listActivitiesString(statsDTO.activitiesList()))
                .append("Total time spend on activities: ").append(numberIntoEmoji.convertNumToEmoji(statsDTO.timeActivities())).append(" hours").append("\n")
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖");

        log.info("Prepared answer for daily TG mail in size [{}]",
                string.length());

        return String.valueOf(string);
    }

    public String answerWeeklyRollover(StatsDTO statsDTO) {

        StringBuilder string = new StringBuilder();
        string
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖").append("\n")
                .append("\uD83C\uDFC6WEEKLY REPORT\uD83C\uDFC6")
                .append("\n✅\n")
                .append("Amount of goals weekly: ").append(numberIntoEmoji.convertNumToEmoji(statsDTO.amountGoals()))
                .append("\n")
                .append("Percentage completion: ").append(numberIntoEmoji.convertNumToEmoji(statsDTO.percentageCompletion())).append("%")
                .append("\n✅\n")
                .append("Amount of activities weekly: ").append(numberIntoEmoji.convertNumToEmoji(statsDTO.amountActivities())).append("\n")
                .append(listActivitiesString(statsDTO.activitiesList()))
                .append("Total time spend on activities: ").append(numberIntoEmoji.convertNumToEmoji(statsDTO.timeActivities())).append(" hours").append("\n")
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖");

        log.info("Prepared answer for weekly TG mail in size [{}]",
                string.length());

        return String.valueOf(string);
    }
}
