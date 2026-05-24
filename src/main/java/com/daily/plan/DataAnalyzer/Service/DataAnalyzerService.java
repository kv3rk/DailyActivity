package com.daily.plan.DataAnalyzer.Service;

import com.daily.plan.DataAnalyzer.DTO.ActivityDTO;
import com.daily.plan.DataAnalyzer.Repository.DataActivityAnalyzerRepository;
import com.daily.plan.DataAnalyzer.Repository.DataGoalsAnalyzerRepository;
import com.daily.plan.TgBot.TelegramBotLogic;
import com.daily.plan.common.mapper.GoalMapper;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class DataAnalyzerService {

    public final DataGoalsAnalyzerRepository goalsAnalyzerRepository;
    public final DataActivityAnalyzerRepository activityAnalyzerRepository;
    public final GoalMapper goalMapper;

    public DataAnalyzerService(DataGoalsAnalyzerRepository goalsAnalyzerRepository, DataActivityAnalyzerRepository activityAnalyzerRepository, GoalMapper goalMapper, TelegramBotLogic telegramBotLogic) {
        this.goalsAnalyzerRepository = goalsAnalyzerRepository;
        this.activityAnalyzerRepository = activityAnalyzerRepository;
        this.goalMapper = goalMapper;
    }

    @Transactional
    public Optional<Long> getAmountTodayGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllTodayGoals(LocalDate.now());

        log.info("Return amount of all daily goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountTodayActiveGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllStatusTodayGoals(LocalDate.now(), false);

        log.info("Return amount of all daily ACTIVE goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountTodayDoneGoals() {
        Optional<Long> count = goalsAnalyzerRepository.countAllStatusTodayGoals(LocalDate.now(), true);

        log.info("Return amount of all daily ACCOMPLISHED goals in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public Long getAmountTimeSpendOnActivitiesToday() {

        List<ActivityDTO> list =
                activityAnalyzerRepository.sumOfTimeAllTodayActivities(
                        LocalDate.now()
                );

        Long count = list.stream()
                .map(ActivityDTO::getTimer)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        log.info("Return amount of time spending on all activities today with value [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

    @Transactional
    public Optional<Long> getAmountTodayActivities() {
        Optional<Long> count = activityAnalyzerRepository.countAllTodayActivities(
                LocalDate.now()
        );
        log.info("Return amount ALL TODAY ACTIVITIES in size [{}] in time [{}]",
                count, LocalDateTime.now());

        return count;
    }

}
