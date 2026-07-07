package com.daily.plan.DailyActivityTracker.DailyPlan.DTO;

import java.util.List;

public record ApiErrorResponse(

        String message,
        List<String> errors

) {
}