package com.daily.plan.DailyActivityTracker.Timer.DTO;

public record TimerDTO(
        String activityType,
        String comment,
        Long timer
) {
}
