package com.daily.plan.StatsStorage.Service;

import com.daily.plan.DataAnalyzer.Service.DailyDataAnalyzerService;
import com.daily.plan.DataAnalyzer.Service.WeeklyDataAnalyzerService;
import com.daily.plan.StatsStorage.DTO.StatsDTO;
import com.daily.plan.StatsStorage.Entity.StatsEntity;
import com.daily.plan.StatsStorage.Repository.StatsRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
@Profile({"dev", "prod"})
public class StatsService {
    private final StatsRepository statsRepository;
    private final DailyDataAnalyzerService dailyDataAnalyzerService;
    private final WeeklyDataAnalyzerService weeklyDataAnalyzerService;
    private final String activity_type_1;
    private final String activity_type_2;
    private final String activity_type_3;
    private final String activity_type_4;
    private final String activity_type_5;

    public StatsService(StatsRepository statsRepository,
                        DailyDataAnalyzerService dailyDataAnalyzerService,
                        WeeklyDataAnalyzerService weeklyDataAnalyzerService,
                        @Value("${activity.type.1}") String activityType1,
                        @Value("${activity.type.2}") String activityType2,
                        @Value("${activity.type.3}") String activityType3,
                        @Value("${activity.type.4}") String activityType4,
                        @Value("${activity.type.5}") String activityType5) {
        this.statsRepository = statsRepository;
        this.dailyDataAnalyzerService = dailyDataAnalyzerService;
        this.weeklyDataAnalyzerService = weeklyDataAnalyzerService;
        this.activity_type_1 = activityType1;
        this.activity_type_2 = activityType2;
        this.activity_type_3 = activityType3;
        this.activity_type_4 = activityType4;
        this.activity_type_5 = activityType5;
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

                            if (dto.getActivityType().equals(activity_type_1)) {

                                statsEntity.setActivity_type_1(dto.getTimer());

                            } else if (dto.getActivityType().equals(activity_type_2)) {

                                statsEntity.setActivity_type_2(dto.getTimer());

                            } else if (dto.getActivityType().equals(activity_type_3)) {

                                statsEntity.setActivity_type_3(dto.getTimer());

                            } else if (dto.getActivityType().equals(activity_type_4)) {

                                statsEntity.setActivity_type_4(dto.getTimer());

                            } else if (dto.getActivityType().equals(activity_type_5)) {

                                statsEntity.setActivity_type_5(dto.getTimer());
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
                                activity_type_1, statsEntity.getActivity_type_1().doubleValue(),
                                activity_type_2, statsEntity.getActivity_type_2().doubleValue(),
                                activity_type_3, statsEntity.getActivity_type_3().doubleValue(),
                                activity_type_4, statsEntity.getActivity_type_4().doubleValue(),
                                activity_type_5, statsEntity.getActivity_type_5().doubleValue()
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

                            if (dto.getActivityType().equals(activity_type_1)) {

                                statsEntity.setActivity_type_1(dto.getTimer());

                            } else if (dto.getActivityType().equals(activity_type_2)) {

                                statsEntity.setActivity_type_2(dto.getTimer());

                            } else if (dto.getActivityType().equals(activity_type_3)) {

                                statsEntity.setActivity_type_3(dto.getTimer());

                            } else if (dto.getActivityType().equals(activity_type_4)) {

                                statsEntity.setActivity_type_4(dto.getTimer());

                            } else if (dto.getActivityType().equals(activity_type_5)) {

                                statsEntity.setActivity_type_5(dto.getTimer());
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
                                activity_type_1, statsEntity.getActivity_type_1().doubleValue(),
                                activity_type_2, statsEntity.getActivity_type_2().doubleValue(),
                                activity_type_3, statsEntity.getActivity_type_3().doubleValue(),
                                activity_type_4, statsEntity.getActivity_type_4().doubleValue(),
                                activity_type_5, statsEntity.getActivity_type_5().doubleValue()
                        )
                ),
                statsEntity.getTimeActivities()
        );

        return statsDTO;
    }

}
