package com.daily.plan.TgBot.PrepareAnswer;

import com.daily.plan.StatsStorage.DTO.StatsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@Profile({"dev", "prod"})
public class ConcatenatingValues {

    public String listActivitiesString(Map<String, Double> result) {

        StringBuilder string = new StringBuilder();

        result.forEach(
                (x, y) -> {
                    if (y > 0) {
                        string.append(x).append(": ").append(y)
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
                .append("Amount of goals today: ").append(statsDTO.amountGoals()).append("\n")
                .append("Percentage completion: ").append(statsDTO.percentageCompletion()).append("%")
                .append("\n✅\n")
                .append("Amount of activities today: ").append(statsDTO.amountActivities()).append("\n")
                .append(listActivitiesString(statsDTO.activitiesList()))
                .append("Total time spend on activities: ").append(statsDTO.timeActivities()).append(" hours").append("\n")
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖")
                .append("0\uFE0F⃣").append("1\uFE0F⃣").append("\uD83D\uDD06")
                .append("▶\uFE0F").append("\uD83D\uDD34").append("\uD83D\uDFE9")
                .append("\uD83D\uDD39").append("➡\uFE0F").append("❌").append("⚔\uFE0F")
                .append("9\uFE0F⃣  2\uFE0F⃣  3\uFE0F⃣  4\uFE0F⃣  5\uFE0F⃣  6\uFE0F⃣  7\uFE0F⃣  8\uFE0F⃣");

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
                .append("Amount of goals weekly: ").append(statsDTO.amountGoals()).append("\n")
                .append("Percentage completion: ").append(statsDTO.percentageCompletion()).append("%")
                .append("\n✅\n")
                .append("Amount of activities weekly: ").append(statsDTO.amountActivities()).append("\n")
                .append(listActivitiesString(statsDTO.activitiesList()))
                .append("Total time spend on activities: ").append(statsDTO.timeActivities()).append(" hours").append("\n")
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖");

        log.info("Prepared answer for weekly TG mail in size [{}]",
                string.length());

        return String.valueOf(string);
    }
}
