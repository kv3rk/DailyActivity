package com.daily.plan.DailyActivityTracker.StatsStorage.DTO;

import java.math.BigDecimal;
import java.util.Map;

public record StatsDTO(
        String term,
        Long amountGoals,
        Long percentageCompletion,
        Long amountActivities,
        Map<String, Double> activitiesList,
        BigDecimal timeActivities
) {
}
