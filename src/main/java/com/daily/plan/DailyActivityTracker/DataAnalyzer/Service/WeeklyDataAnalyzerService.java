package com.daily.plan.DailyActivityTracker.DataAnalyzer.Service;

import com.daily.plan.DailyActivityTracker.DataAnalyzer.DTO.ActivityBigDecimalDTO;
import com.daily.plan.DailyActivityTracker.DataAnalyzer.DTO.ActivityDTO;
import com.daily.plan.DailyActivityTracker.DataAnalyzer.Repository.DataActivityAnalyzerRepository;
import com.daily.plan.DailyActivityTracker.DataAnalyzer.Repository.DataGoalsAnalyzerRepository;
import com.daily.plan.DailyActivityTracker.common.unit.CurrentDateTime;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class WeeklyDataAnalyzerService {

    private final DataGoalsAnalyzerRepository goalsAnalyzerRepository;
    private final DataActivityAnalyzerRepository activityAnalyzerRepository;
    private final CurrentDateTime currentDateTime;

    public WeeklyDataAnalyzerService(DataGoalsAnalyzerRepository goalsAnalyzerRepository,
                                     DataActivityAnalyzerRepository activityAnalyzerRepository, CurrentDateTime currentDateTime) {
        this.goalsAnalyzerRepository = goalsAnalyzerRepository;
        this.activityAnalyzerRepository = activityAnalyzerRepository;
        this.currentDateTime = currentDateTime;
    }

    @Transactional
    public Long getAmountWeeklyGoals(String username) {

        Long count = goalsAnalyzerRepository.countAllGoals(
                currentDateTime.getCurrentDate()
                        .minusWeeks(1),
                username
        );

        return count;
    }

    @Transactional
    public Long getAmountWeeklyActiveGoals(String username) {

        Long count = goalsAnalyzerRepository.countAllStatusGoals(
                currentDateTime.getCurrentDate()
                        .minusWeeks(1),
                false,
                username
        );

        return count;
    }

    @Transactional
    public Long getAmountWeeklyDoneGoals(String username) {

        Long count = goalsAnalyzerRepository.countAllStatusGoals(
                currentDateTime.getCurrentDate()
                        .minusWeeks(1),
                true,
                username
        );

        return count;
    }

    @Transactional
    public BigDecimal getAmountTimeSpendOnActivitiesWeekly(String username) {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        currentDateTime.getCurrentDate()
                                .minusWeeks(1),
                        username
                );

        Long count = list.stream()
                .map(ActivityDTO::getTimer)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        BigDecimal result = BigDecimal.valueOf(count)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        return result;
    }

    @Transactional
    public List<ActivityBigDecimalDTO> getInfoOfAllWeeklyActivities(String username) {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        currentDateTime.getCurrentDate()
                                .minusWeeks(1),
                        username
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

        return resultList;

    }

    @Transactional
    public Long getAmountWeeklyActivities(String username) {

        Long count = activityAnalyzerRepository.countAllActivities(
                currentDateTime.getCurrentDate()
                        .minusWeeks(1),
                username
        );

        return count;
    }

    public Long calculatePercentageCompletion(Long done, Long total) {

        Long value = (long) ((double) done / total * 100);

        log.info("Calculated done goals [{}] and total goals [{}] to percentage ratio [{}%]",
                done, total, value);

        return value;
    }

}
