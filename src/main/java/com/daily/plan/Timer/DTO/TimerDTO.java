package com.daily.plan.Timer.DTO;

public record TimerDTO(
        String activityType,
        String comment,
        Byte time
) {
}
