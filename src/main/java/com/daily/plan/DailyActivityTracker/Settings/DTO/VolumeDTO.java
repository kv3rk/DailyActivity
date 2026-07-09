package com.daily.plan.DailyActivityTracker.Settings.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record VolumeDTO(
        @Max(value = 100, message = "Maximum volume can be 100")
        @Min(value = 0, message = "Minimum volume can be 0")
        Byte volume
) {
}
