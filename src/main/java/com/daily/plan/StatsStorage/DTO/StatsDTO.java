package com.daily.plan.StatsStorage.DTO;

import java.math.BigDecimal;

public record StatsDTO(
        String term,
        Long amountGoals,
        Long percentageCompletion,
        Long amountActivities,
        BigDecimal backend,
        BigDecimal games,
        BigDecimal english,
        BigDecimal timeActivities
) {
}
