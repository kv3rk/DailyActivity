package com.daily.plan.DailyActivityTracker.DataAnalyzer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDTO {
    private String activityType;
    private Long timer;

    public ActivityDTO(String activityType, Long timer) {
        this.activityType = activityType;
        this.timer = timer;
    }
}
