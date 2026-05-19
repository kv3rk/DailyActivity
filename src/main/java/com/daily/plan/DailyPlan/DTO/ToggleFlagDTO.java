package com.daily.plan.DailyPlan.DTO;

import java.util.UUID;

public record ToggleFlagDTO(
        UUID id,
        Boolean doneFlag
) {
}
