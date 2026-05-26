package com.daily.plan.DataAnalyzer.Service;

import com.daily.plan.DataAnalyzer.DTO.ActivityBigDecimalDTO;
import com.daily.plan.DataAnalyzer.DTO.ActivityDTO;
import com.daily.plan.DataAnalyzer.Repository.DataActivityAnalyzerRepository;
import com.daily.plan.DataAnalyzer.Repository.DataGoalsAnalyzerRepository;
import com.daily.plan.common.mapper.GoalMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class WeeklyDataAnalyzerService {

    private final DataGoalsAnalyzerRepository goalsAnalyzerRepository;
    private final DataActivityAnalyzerRepository activityAnalyzerRepository;
    private final GoalMapper goalMapper;

    public WeeklyDataAnalyzerService(DataGoalsAnalyzerRepository goalsAnalyzerRepository, DataActivityAnalyzerRepository activityAnalyzerRepository, GoalMapper goalMapper) {
        this.goalsAnalyzerRepository = goalsAnalyzerRepository;
        this.activityAnalyzerRepository = activityAnalyzerRepository;
        this.goalMapper = goalMapper;
    }

    @Transactional
    public Optional<Long> getAmountWeeklyGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllGoals(
                LocalDate.now()
                        .minusWeeks(1)
        );

        log.info("Return amount of all weekly goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountWeeklyActiveGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllStatusGoals(
                LocalDate.now()
                        .minusWeeks(1),
                false);

        log.info("Return amount of all weekly ACTIVE goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountWeeklyDoneGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllStatusGoals(
                LocalDate.now()
                        .minusWeeks(1),
                true);

        log.info("Return amount of all weekly ACCOMPLISHED goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public BigDecimal getAmountTimeSpendOnActivitiesWeekly() {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        LocalDate.now()
                                .minusWeeks(1)
                );

        Long count = list.stream()
                .map(ActivityDTO::getTimer)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        BigDecimal result = BigDecimal.valueOf(count)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        log.info("Return amount of time spending on all activities weekly with value [{}] minutes in time [{}]",
                count, LocalDateTime.now());

        return result;
    }

    @Transactional
    public List<ActivityBigDecimalDTO> getInfoOfAllWeeklyActivities() {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        LocalDate.now()
                                .minusWeeks(1)
                );

        List<ActivityBigDecimalDTO> resultList = new ArrayList<>();

        list.forEach(
                dto -> {

                    resultList.addLast(
                            new ActivityBigDecimalDTO(
                                    dto.getActivityType(),
                                    BigDecimal.valueOf(dto.getTimer())
                                            .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP)
                            )
                    );
                }
        );

        log.info("Return list of weekly activities in size [{}] in time [{}]",
                list.size(), LocalDateTime.now());

        return resultList;

    }

    @Transactional
    public Optional<Long> getAmountWeeklyActivities() {
        Optional<Long> count = activityAnalyzerRepository.countAllActivities(
                LocalDate.now()
                        .minusWeeks(1)
        );
        log.info("Return amount ALL TODAY ACTIVITIES in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

}
