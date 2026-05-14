package com.daily.plan.DailyPlan.DTO;

import java.util.UUID;

public record GoalDTO(
        UUID id,
        String goalText,
        Boolean doneFlag
) {
}
