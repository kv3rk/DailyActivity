package com.daily.plan.DailyActivityTracker.DailyGoals.DTO;

import java.util.List;

public record ApiErrorResponse(

        String message,
        List<String> errors

) {
}