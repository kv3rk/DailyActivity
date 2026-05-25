package com.daily.plan.DataAnalyzer.Service;

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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class DailyDataAnalyzerService {

    public final DataGoalsAnalyzerRepository goalsAnalyzerRepository;
    public final DataActivityAnalyzerRepository activityAnalyzerRepository;
    public final GoalMapper goalMapper;

    public DailyDataAnalyzerService(DataGoalsAnalyzerRepository goalsAnalyzerRepository,
                                    DataActivityAnalyzerRepository activityAnalyzerRepository,
                                    GoalMapper goalMapper) {
        this.goalsAnalyzerRepository = goalsAnalyzerRepository;
        this.activityAnalyzerRepository = activityAnalyzerRepository;
        this.goalMapper = goalMapper;
    }

    @Transactional
    public Optional<Long> getAmountTodayGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllGoals(LocalDate.now());

        log.info("Return amount of all daily goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountTodayActiveGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllStatusGoals(LocalDate.now(), false);

        log.info("Return amount of all daily ACTIVE goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountTodayDoneGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllStatusGoals(LocalDate.now(), true);

        log.info("Return amount of all daily ACCOMPLISHED goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public BigDecimal getAmountTimeSpendOnActivitiesToday() {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        LocalDate.now()
                );

        Long count = list.stream()
                .map(ActivityDTO::getTimer)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        BigDecimal result = BigDecimal.valueOf(count)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        log.info("Return amount of time spending on all activities today with value [{}] in time [{}]",
                count, LocalDateTime.now());

        return result;
    }

    @Transactional
    public Optional<Long> getAmountTodayActivities() {
        Optional<Long> count = activityAnalyzerRepository.countAllActivities(
                LocalDate.now()
        );
        log.info("Return amount ALL TODAY ACTIVITIES in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    public Long calculatePercentageCompletion(Long active, Long total) {

        Long value = (long) ((double) active / total * 100);

        log.info("Calculated active goals [{}] and total goals [{}] to percentage ratio [{}%]",
                active, total, value);

        return value;
    }
}
