package com.daily.plan.DailyActivityTracker.DataAnalyzer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityBigDecimalDTO {
    private String activityType;
    private BigDecimal timer;

    public ActivityBigDecimalDTO(String activityType, BigDecimal timer) {
        this.activityType = activityType;
        this.timer = timer;
    }
}

