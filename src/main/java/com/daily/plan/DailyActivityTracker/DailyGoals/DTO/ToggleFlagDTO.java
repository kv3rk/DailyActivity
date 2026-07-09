package com.daily.plan.DailyActivityTracker.DailyGoals.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ToggleFlagDTO(

        @NotNull(message = "Goal ID cannot be null")
        UUID id,

        Boolean doneFlag
) {
}
