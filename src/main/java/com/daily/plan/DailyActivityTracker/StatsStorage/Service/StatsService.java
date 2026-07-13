package com.daily.plan.DailyActivityTracker.StatsStorage.Service;

import com.daily.plan.DailyActivityTracker.DataAnalyzer.DTO.ActivityBigDecimalDTO;
import com.daily.plan.DailyActivityTracker.DataAnalyzer.Service.DailyDataAnalyzerService;
import com.daily.plan.DailyActivityTracker.DataAnalyzer.Service.WeeklyDataAnalyzerService;
import com.daily.plan.DailyActivityTracker.Settings.DTO.UserActivityDTOForStats;
import com.daily.plan.DailyActivityTracker.StatsStorage.DTO.StatsDTO;
import com.daily.plan.DailyActivityTracker.StatsStorage.Entity.StatsEntity;
import com.daily.plan.DailyActivityTracker.StatsStorage.Repository.StatsRepository;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import com.daily.plan.DailyActivityTracker.UserActivities.Repository.UserActivityRepository;
import com.daily.plan.DailyActivityTracker.common.mapper.UserActivityMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
    private final UserActivityRepository userActivityRepository;
    private final UserActivityMapper userActivityMapper;

    public StatsService(StatsRepository statsRepository,
                        DailyDataAnalyzerService dailyDataAnalyzerService,
                        WeeklyDataAnalyzerService weeklyDataAnalyzerService,
                        UserRepository userRepository,
                        UserActivityRepository userActivityRepository,
                        UserActivityMapper userActivityMapper) {

        this.statsRepository = statsRepository;
        this.dailyDataAnalyzerService = dailyDataAnalyzerService;
        this.weeklyDataAnalyzerService = weeklyDataAnalyzerService;
        this.userRepository = userRepository;
        this.userActivityRepository = userActivityRepository;
        this.userActivityMapper = userActivityMapper;
    }

    public StatsDTO createStatsDTO(StatsEntity statsEntity,
                                   User user) {

        UserActivityDTOForStats userActivityDTOForStats = userActivityMapper.userActivityToDTO(
                userActivityRepository.findByUsername(user)
        );

        StatsDTO statsDTO = new StatsDTO(
                statsEntity.getTerm(),
                statsEntity.getAmountGoals(),
                statsEntity.getPercentageCompletion(),
                statsEntity.getAmountActivities(),
                new TreeMap<>(
                        Map.of(
                                userActivityDTOForStats.activity1(), statsEntity.getActivity_type_1().doubleValue(),
                                userActivityDTOForStats.activity2(), statsEntity.getActivity_type_2().doubleValue(),
                                userActivityDTOForStats.activity3(), statsEntity.getActivity_type_3().doubleValue()
                        )
                ),
                statsEntity.getTimeActivities()
        );

        return statsDTO;

    }

    public void mappingActivityTypesToEntity(ActivityBigDecimalDTO dto,
                                             StatsEntity statsEntity,
                                             User user) {

        UserActivityDTOForStats userActivityDTOForStats = userActivityMapper.userActivityToDTO(
                userActivityRepository.findByUsername(user)
        );

        if (dto.getActivityType().equals(userActivityDTOForStats.activity1())) {

            statsEntity.setActivity_type_1(dto.getTimer());

        } else if (dto.getActivityType().equals(userActivityDTOForStats.activity2())) {

            statsEntity.setActivity_type_2(dto.getTimer());

        } else if (dto.getActivityType().equals(userActivityDTOForStats.activity3())) {

            statsEntity.setActivity_type_3(dto.getTimer());

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
                        dto -> mappingActivityTypesToEntity(dto, statsEntity, user)
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

        return createStatsDTO(statsEntity, user);
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
                        dto -> mappingActivityTypesToEntity(dto, statsEntity, user)
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

        return createStatsDTO(statsEntity, user);
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

    @Transactional
    public List<User> usersWithLinkedTelegram() {
        return userRepository.findAllByTelegramIsNotNullAndTelegramNot("")
                .stream()
                .filter(u -> u.getTelegram() != null && !u.getTelegram().contains("-"))
                .toList();
    }

}
