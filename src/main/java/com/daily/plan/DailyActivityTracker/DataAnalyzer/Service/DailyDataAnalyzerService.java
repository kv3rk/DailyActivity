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
public class DailyDataAnalyzerService {

    public final DataGoalsAnalyzerRepository goalsAnalyzerRepository;
    public final DataActivityAnalyzerRepository activityAnalyzerRepository;
    private final CurrentDateTime currentDateTime;

    public DailyDataAnalyzerService(DataGoalsAnalyzerRepository goalsAnalyzerRepository,
                                    DataActivityAnalyzerRepository activityAnalyzerRepository,
                                    CurrentDateTime currentDateTime) {
        this.goalsAnalyzerRepository = goalsAnalyzerRepository;
        this.activityAnalyzerRepository = activityAnalyzerRepository;
        this.currentDateTime = currentDateTime;
    }

    @Transactional
    public Long getAmountTodayGoals(String username) {

        Long count = goalsAnalyzerRepository.countAllGoals(
                currentDateTime.getCurrentDate(),
                username
        );

        return count;
    }

    @Transactional
    public Long getAmountTodayActiveGoals(String username) {
        Long count = goalsAnalyzerRepository.countAllStatusGoals(
                currentDateTime.getCurrentDate(),
                false,
                username);

        return count;
    }

    @Transactional
    public Long getAmountTodayDoneGoals(String username) {
        Long count = goalsAnalyzerRepository.countAllStatusGoals(
                currentDateTime.getCurrentDate(),
                true,
                username);

        log.info("Return amount of all daily ACCOMPLISHED goals in size [{}] in time [{}]",
                count, currentDateTime.getFormattedTime());

        return count;
    }

    @Transactional
    public BigDecimal getAmountTimeSpendOnActivitiesToday(String username) {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        currentDateTime.getCurrentDate(),
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
    public Long getAmountTodayActivities(String username) {


        Long count = activityAnalyzerRepository.countAllActivities(
                currentDateTime.getCurrentDate(),
                username
        );


        return count;
    }

    @Transactional
    public List<ActivityBigDecimalDTO> getInfoOfAllTodayActivities(String username) {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllActivities(
                        currentDateTime.getCurrentDate(),
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

    public Long calculatePercentageCompletion(Long done, Long total) {

        Long value = (long) ((double) done / total * 100);

        log.info("Calculated done goals [{}] and total goals [{}] to percentage ratio [{}%]",
                done, total, value);

        return value;
    }
}
