package com.daily.plan.DailyActivityTracker.DailyPlan.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record GoalDTO(
        UUID id,

        @NotBlank(message = "Goal cannot be blank")
        @Size(max = 50, message = "Goal text must be shorter than 50 characters")
        String goalText,

        Boolean doneFlag
) {
}
