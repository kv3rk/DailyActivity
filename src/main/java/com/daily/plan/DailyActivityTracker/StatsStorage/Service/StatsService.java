package com.daily.plan.DailyActivityTracker.StatsStorage.Service;

import com.daily.plan.DailyActivityTracker.DataAnalyzer.DTO.ActivityBigDecimalDTO;
import com.daily.plan.DailyActivityTracker.DataAnalyzer.Service.DailyDataAnalyzerService;
import com.daily.plan.DailyActivityTracker.DataAnalyzer.Service.WeeklyDataAnalyzerService;
import com.daily.plan.DailyActivityTracker.StatsStorage.DTO.StatsDTO;
import com.daily.plan.DailyActivityTracker.StatsStorage.Entity.StatsEntity;
import com.daily.plan.DailyActivityTracker.StatsStorage.Repository.StatsRepository;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
@Profile({"dev", "prod"})
public class StatsService {
    private final StatsRepository statsRepository;
    private final DailyDataAnalyzerService dailyDataAnalyzerService;
    private final WeeklyDataAnalyzerService weeklyDataAnalyzerService;
    private final UserRepository userRepository;
    private final String activity_type_1;
    private final String activity_type_2;
    private final String activity_type_3;
    private final String activity_type_4;
    private final String activity_type_5;
    private final String activity_type_6;

    public StatsService(StatsRepository statsRepository,
                        DailyDataAnalyzerService dailyDataAnalyzerService,
                        WeeklyDataAnalyzerService weeklyDataAnalyzerService,
                        UserRepository userRepository,
                        @Value("${activity.type.1}") String activityType1,
                        @Value("${activity.type.2}") String activityType2,
                        @Value("${activity.type.3}") String activityType3,
                        @Value("${activity.type.4}") String activityType4,
                        @Value("${activity.type.5}") String activityType5,
                        @Value("${activity.type.6}") String activityType6) {

        this.statsRepository = statsRepository;
        this.dailyDataAnalyzerService = dailyDataAnalyzerService;
        this.weeklyDataAnalyzerService = weeklyDataAnalyzerService;
        this.userRepository = userRepository;
        this.activity_type_1 = activityType1;
        this.activity_type_2 = activityType2;
        this.activity_type_3 = activityType3;
        this.activity_type_4 = activityType4;
        this.activity_type_5 = activityType5;
        this.activity_type_6 = activityType6;
    }

    public StatsDTO createStatsDTO(StatsEntity statsEntity) {

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
                                activity_type_5, statsEntity.getActivity_type_5().doubleValue(),
                                activity_type_6, statsEntity.getActivity_type_6().doubleValue()
                        )
                ),
                statsEntity.getTimeActivities()
        );

        return statsDTO;

    }

    public void mappingActivityTypesToEntity(ActivityBigDecimalDTO dto,
                                             StatsEntity statsEntity) {

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

        } else if (dto.getActivityType().equals(activity_type_6)) {

            statsEntity.setActivity_type_6(dto.getTimer());
        }

    }

    @Transactional
    public List<User> usersWithTelegram() {

        List<User> users = userRepository.findAllByTelegramIsNotNullAndTelegramNot("");

        log.info("{}", users);

        return users;

    }

    @Transactional
    public StatsDTO saveDaily(User user) {

        StatsEntity statsEntity = new StatsEntity();

        statsEntity.setTerm("daily");

        statsEntity.setAmountGoals(
                dailyDataAnalyzerService.getAmountTodayGoals(
                        user.getUsername()
                ));

        statsEntity.setPercentageCompletion(
                dailyDataAnalyzerService.calculatePercentageCompletion(
                        dailyDataAnalyzerService.getAmountTodayDoneGoals(
                                user.getUsername()
                        ),
                        dailyDataAnalyzerService.getAmountTodayGoals(
                                user.getUsername()
                        ))
        );

        statsEntity.setAmountActivities(
                dailyDataAnalyzerService.getAmountTodayActivities(
                        user.getUsername()
                ));

        dailyDataAnalyzerService.getInfoOfAllTodayActivities(
                        user.getUsername()
                )
                .forEach(
                        dto -> mappingActivityTypesToEntity(dto, statsEntity)
                );


        statsEntity.setTimeActivities(
                dailyDataAnalyzerService.getAmountTimeSpendOnActivitiesToday(
                        user.getUsername()
                )
        );

        statsEntity.setUsername(user);

        statsRepository.save(
                statsEntity
        );

        log.info("Saved stats of daily activity");

        return createStatsDTO(statsEntity);
    }


    @Transactional
    public StatsDTO saveWeekly(User user) {

        StatsEntity statsEntity = new StatsEntity();

        statsEntity.setTerm("weekly");

        statsEntity.setAmountGoals(
                weeklyDataAnalyzerService.getAmountWeeklyGoals(
                        user.getUsername()
                ));

        statsEntity.setPercentageCompletion(
                weeklyDataAnalyzerService.calculatePercentageCompletion(

                        weeklyDataAnalyzerService.getAmountWeeklyDoneGoals(
                                user.getUsername()
                        ),
                        weeklyDataAnalyzerService.getAmountWeeklyGoals(
                                user.getUsername()
                        ))
        );

        statsEntity.setAmountActivities(
                weeklyDataAnalyzerService.getAmountWeeklyActivities(
                        user.getUsername()
                ));

        weeklyDataAnalyzerService.getInfoOfAllWeeklyActivities(
                        user.getUsername()
                )
                .forEach(
                        dto -> mappingActivityTypesToEntity(dto, statsEntity)
                );


        statsEntity.setTimeActivities(
                weeklyDataAnalyzerService.getAmountTimeSpendOnActivitiesWeekly(
                        user.getUsername()
                )
        );

        statsEntity.setUsername(user);

        statsRepository.save(
                statsEntity
        );

        log.info("Saved stats of weekly activity");

        return createStatsDTO(statsEntity);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public List<StatsDTO> dailyResults() {

        List<StatsDTO> dailyResultList = new ArrayList<>();

        usersWithTelegram().forEach(user -> {

            dailyResultList.add(
                    saveDaily(user)
            );

        });

        return dailyResultList;

    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public List<StatsDTO> weeklyResults() {

        List<StatsDTO> weeklyResultList = new ArrayList<>();

        usersWithTelegram().forEach(user -> {

            weeklyResultList.add(
                    saveWeekly(user)
            );

        });

        return weeklyResultList;

    }

}
