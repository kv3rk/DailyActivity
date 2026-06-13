package com.daily.plan.DataAnalyzer.Service;

import com.daily.plan.DataAnalyzer.Repository.DataActivityAnalyzerRepository;
import com.daily.plan.DataAnalyzer.Repository.DataGoalsAnalyzerRepository;
import com.daily.plan.common.unit.CurrentDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TestDailyDataAnalyzerService {

    @Mock
    public DataGoalsAnalyzerRepository goalsAnalyzerRepository;
    @Mock
    public DataActivityAnalyzerRepository activityAnalyzerRepository;
    @Mock
    private CurrentDateTime currentDateTime;

    @InjectMocks
    DailyDataAnalyzerService dailyDataAnalyzerService;

    @Test
    @DisplayName("Calculate percentage all values not null")
    @Tag("DataAnalyzer")
    void calculatePercentageCompletionNotNullAllNumbers() {

        assertEquals(50L,
                dailyDataAnalyzerService.calculatePercentageCompletion(2L, 4L),
                "Must complete. Check your code for mistakes");

    }

    @Test
    @DisplayName("Calculate percentage dividend is null")
    @Tag("DataAnalyzer")
    void calculatePercentageCompletionDividendIsNull() {

        assertEquals(0L,
                dailyDataAnalyzerService.calculatePercentageCompletion(0L, 4L),
                "Must be 0 result. Check your code for rightness divide or persistence operations");

    }

    @Test
    @DisplayName("Calculate percentage divider is null")
    @Tag("DataAnalyzer")
    void calculatePercentageCompletionDividerIsNull() {

        assertFalse(50L == dailyDataAnalyzerService
                        .calculatePercentageCompletion(2L, 0L),
                "Shouldn't ever be reached. Total goals cant be less than done goals");

    }

    @Test
    @DisplayName("Correct logic work - get full amount of goals")
    @Tag("DataAnalyzer")
    void getAmountTodayGoalsContainsOneGoal() {

        given(currentDateTime.getCurrentDate()).willReturn(LocalDate.ofEpochDay(2026 - 6 - 8));
        given(goalsAnalyzerRepository.countAllGoals(currentDateTime.getCurrentDate())).willReturn(1L);

        Long result = dailyDataAnalyzerService.getAmountTodayGoals();

        assertThat(result).isNull();
        assertThat(result).isEqualTo(1L);

    }

}
