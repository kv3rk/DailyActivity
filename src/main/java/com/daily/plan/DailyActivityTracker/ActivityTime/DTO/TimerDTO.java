package com.daily.plan.DailyActivityTracker.ActivityTime.DTO;

public record TimerDTO(
        String activityType,
        String comment,
        Long timer
) {
}
