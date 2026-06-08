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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class TestWeeklyDataAnalyzerService {

    @Mock
    private DataGoalsAnalyzerRepository goalsAnalyzerRepository;
    @Mock
    private DataActivityAnalyzerRepository activityAnalyzerRepository;
    @Mock
    private CurrentDateTime currentDateTime;

    @InjectMocks
    private WeeklyDataAnalyzerService weeklyDataAnalyzerService;

    @Test
    @DisplayName("Calculate percentage all values not null")
    @Tag("DataAnalyzer")
    void calculatePercentageCompletionNotNullAllNumbers() {

        assertEquals(50L,
                weeklyDataAnalyzerService.calculatePercentageCompletion(2L, 4L),
                "Must complete. Check your code for mistakes");

    }

    @Test
    @DisplayName("Calculate percentage dividend is null")
    @Tag("DataAnalyzer")
    void calculatePercentageCompletionDividendIsNull() {

        assertEquals(0L,
                weeklyDataAnalyzerService.calculatePercentageCompletion(0L, 4L),
                "Must be 0 result. Check your code for rightness divide or persistence operations");

    }

    @Test
    @DisplayName("Calculate percentage divider is null")
    @Tag("DataAnalyzer")
    void calculatePercentageCompletionDividerIsNull() {

        assertFalse(50L == weeklyDataAnalyzerService
                        .calculatePercentageCompletion(2L, 0L),
                "Shouldn't ever be reached. Total goals cant be less than done goals");

    }
}
