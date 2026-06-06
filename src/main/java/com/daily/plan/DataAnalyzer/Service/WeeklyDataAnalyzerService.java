package com.daily.plan.DataAnalyzer.Service;

import com.daily.plan.DataAnalyzer.DTO.ActivityBigDecimalDTO;
import com.daily.plan.DataAnalyzer.DTO.ActivityDTO;
import com.daily.plan.DataAnalyzer.Repository.DataActivityAnalyzerRepository;
import com.daily.plan.DataAnalyzer.Repository.DataGoalsAnalyzerRepository;
import com.daily.plan.common.unit.CurrentDateTime;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Optional<Long> getAmountWeeklyGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllGoals(
                currentDateTime.getCurrentDate()
                        .minusWeeks(1)
        );

        log.info("Return amount of all weekly goals in size [{}] in time [{}]",
                count, currentDateTime.getFormattedTime());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountWeeklyActiveGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllStatusGoals(
                currentDateTime.getCurrentDate()
                        .minusWeeks(1),
                false);

        log.info("Return amount of all weekly ACTIVE goals in size [{}] in time [{}]",
                count, currentDateTime.getFormattedTime());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountWeeklyDoneGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllStatusGoals(
                currentDateTime.getCurrentDate()
                        .minusWeeks(1),
                true);

        log.info("Return amount of all weekly ACCOMPLISHED goals in size [{}] in time [{}]",
                count, currentDateTime.getFormattedTime());

        return count;
    }

    @Transactional
    public BigDecimal getAmountTimeSpendOnActivitiesWeekly() {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        currentDateTime.getCurrentDate()
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
                count, currentDateTime.getFormattedTime());

        return result;
    }

    @Transactional
    public List<ActivityBigDecimalDTO> getInfoOfAllWeeklyActivities() {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        currentDateTime.getCurrentDate()
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
                list.size(), currentDateTime.getFormattedTime());

        return resultList;

    }

    @Transactional
    public Optional<Long> getAmountWeeklyActivities() {
        Optional<Long> count = activityAnalyzerRepository.countAllActivities(
                currentDateTime.getCurrentDate()
                        .minusWeeks(1)
        );
        log.info("Return amount ALL TODAY ACTIVITIES in size [{}] in time [{}]",
                count, currentDateTime.getFormattedTime());

        return count;
    }

    public Long calculatePercentageCompletion(Long done, Long total) {

        Long value = (long) ((double) done / total * 100);

        log.info("Calculated done goals [{}] and total goals [{}] to percentage ratio [{}%]",
                done, total, value);

        return value;
    }

}
