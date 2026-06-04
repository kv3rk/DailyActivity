package com.daily.plan.StatsStorage.Service;

import com.daily.plan.DataAnalyzer.Service.DailyDataAnalyzerService;
import com.daily.plan.DataAnalyzer.Service.WeeklyDataAnalyzerService;
import com.daily.plan.StatsStorage.DTO.StatsDTO;
import com.daily.plan.StatsStorage.Entity.StatsEntity;
import com.daily.plan.StatsStorage.Repository.StatsRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@Profile({"dev", "prod"})
public class StatsService {
    private final StatsRepository statsRepository;
    private final DailyDataAnalyzerService dailyDataAnalyzerService;
    private final WeeklyDataAnalyzerService weeklyDataAnalyzerService;

    public StatsService(StatsRepository statsRepository,
                        DailyDataAnalyzerService dailyDataAnalyzerService,
                        WeeklyDataAnalyzerService weeklyDataAnalyzerService) {
        this.statsRepository = statsRepository;
        this.dailyDataAnalyzerService = dailyDataAnalyzerService;
        this.weeklyDataAnalyzerService = weeklyDataAnalyzerService;
    }

    @Transactional
    public StatsDTO saveDaily() {

        StatsEntity statsEntity = new StatsEntity();

        statsEntity.setTerm("daily");

        statsEntity.setAmountGoals(
                dailyDataAnalyzerService.getAmountTodayGoals().orElse(0L)
        );

        statsEntity.setPercentageCompletion(
                dailyDataAnalyzerService.calculatePercentageCompletion(
                        dailyDataAnalyzerService.getAmountTodayDoneGoals().orElse(0L),
                        dailyDataAnalyzerService.getAmountTodayGoals().orElse(0L)
                )
        );

        statsEntity.setAmountActivities(
                dailyDataAnalyzerService.getAmountTodayActivities().orElse(0L)
        );

        dailyDataAnalyzerService.getInfoOfAllTodayActivities()
                .forEach(
                        dto -> {

                            switch (dto.getActivityType()) {
                                case "backend" -> statsEntity.setBackend(
                                        dto.getTimer()
                                );
                                case "games" -> statsEntity.setGames(
                                        dto.getTimer()
                                );
                                case "english" -> statsEntity.setEnglish(
                                        dto.getTimer()
                                );
                            }

                        }
                );


        statsEntity.setTimeActivities(
                dailyDataAnalyzerService.getAmountTimeSpendOnActivitiesToday()
        );
        statsRepository.save(
                statsEntity
        );

        log.info("Saved stats of daily activity");

        StatsDTO statsDTO = new StatsDTO(
                statsEntity.getTerm(),
                statsEntity.getAmountGoals(),
                statsEntity.getPercentageCompletion(),
                statsEntity.getAmountActivities(),
                new TreeMap<>(
                        Map.of(
                                "backend", statsEntity.getBackend().doubleValue(),
                                "games", statsEntity.getGames().doubleValue(),
                                "english", statsEntity.getEnglish().doubleValue()
                        )
                ),
                statsEntity.getTimeActivities()
        );

        return statsDTO;
    }

    @Transactional
    public StatsDTO saveWeekly() {

        StatsEntity statsEntity = new StatsEntity();

        statsEntity.setTerm("weekly");

        statsEntity.setAmountGoals(
                weeklyDataAnalyzerService.getAmountWeeklyGoals().orElse(0L)
        );

        statsEntity.setPercentageCompletion(
                weeklyDataAnalyzerService.calculatePercentageCompletion(
                        weeklyDataAnalyzerService.getAmountWeeklyDoneGoals().orElse(0L),
                        weeklyDataAnalyzerService.getAmountWeeklyGoals().orElse(0L)
                )
        );

        statsEntity.setAmountActivities(
                weeklyDataAnalyzerService.getAmountWeeklyActivities().orElse(0L)
        );

        weeklyDataAnalyzerService.getInfoOfAllWeeklyActivities()
                .forEach(
                        dto -> {

                            switch (dto.getActivityType()) {
                                case "backend" -> statsEntity.setBackend(
                                        dto.getTimer()
                                );
                                case "games" -> statsEntity.setGames(
                                        dto.getTimer()
                                );
                                case "english" -> statsEntity.setEnglish(
                                        dto.getTimer()
                                );
                            }

                        }
                );


        statsEntity.setTimeActivities(
                weeklyDataAnalyzerService.getAmountTimeSpendOnActivitiesWeekly()
        );

        statsRepository.save(
                statsEntity
        );

        log.info("Saved stats of weekly activity");

        StatsDTO statsDTO = new StatsDTO(
                statsEntity.getTerm(),
                statsEntity.getAmountGoals(),
                statsEntity.getPercentageCompletion(),
                statsEntity.getAmountActivities(),
                new TreeMap<>(
                        Map.of(
                                "backend", statsEntity.getBackend().doubleValue(),
                                "games", statsEntity.getGames().doubleValue(),
                                "english", statsEntity.getEnglish().doubleValue()
                        )
                ),
                statsEntity.getTimeActivities()
        );

        return statsDTO;
    }

}
