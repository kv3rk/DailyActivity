package com.daily.plan.TgBot.PrepareAnswer;

import com.daily.plan.StatsStorage.DTO.StatsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@Profile({"dev", "prod"})
public class ConcatenatingValues {

    public String listActivitiesString(Double backend, Double games, Double english) {

        List<Double> result = new ArrayList<>(
                List.of(
                        backend,
                        games,
                        english
                )
        ).stream().filter(x -> x > 0).sorted(Collections.reverseOrder()).toList();

        StringBuilder string = new StringBuilder();

        result.forEach(
                x -> {
                    if (Objects.equals(x, backend)) {
                        string.append("backend: ").append(x).append(" hours\n");
                    } else if (Objects.equals(x, games)) {
                        string.append("games: ").append(x).append(" hours\n");
                    } else if (Objects.equals(x, english)) {
                        string.append("english: ").append(x).append(" hours\n");
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
                .append(listActivitiesString(
                        statsDTO.backend().doubleValue(),
                        statsDTO.games().doubleValue(),
                        statsDTO.english().doubleValue()
                ))
                .append("Total time spend on activities: ").append(statsDTO.timeActivities()).append(" hours").append("\n")
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
                .append("Amount of goals weekly: ").append(statsDTO.amountGoals()).append("\n")
                .append("Percentage completion: ").append(statsDTO.percentageCompletion()).append("%")
                .append("\n✅\n")
                .append("Amount of activities weekly: ").append(statsDTO.amountActivities()).append("\n")
                .append(listActivitiesString(
                        statsDTO.backend().doubleValue(),
                        statsDTO.games().doubleValue(),
                        statsDTO.english().doubleValue()
                ))
                .append("Total time spend on activities: ").append(statsDTO.timeActivities()).append(" hours").append("\n")
                .append("➖➖➖➖➖➖➖➖➖➖➖➖➖➖");

        log.info("Prepared answer for weekly TG mail in size [{}]",
                string.length());

        return String.valueOf(string);
    }
}
