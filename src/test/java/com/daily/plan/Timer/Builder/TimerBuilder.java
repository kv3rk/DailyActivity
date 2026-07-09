package com.daily.plan.Timer.Builder;

import com.daily.plan.DailyActivityTracker.ActivityTime.Entity.TimerEntity;

import java.time.LocalDate;

public class TimerBuilder {
    private LocalDate activityDate = LocalDate.now();
    private String activityType = "backend";
    private Long timer = 10L;

    public TimerBuilder withDate(LocalDate date) {
        this.activityDate = date;
        return this;
    }

    public TimerBuilder withActivityType(String activityType) {
        this.activityType = activityType;
        return this;
    }

    public TimerBuilder withTimer(Long timer) {
        this.timer = timer;
        return this;
    }

    public TimerEntity build() {

        TimerEntity timerEntity = new TimerEntity();

        timerEntity.setActivityDate(activityDate);
        timerEntity.setActivityType(activityType);
        timerEntity.setTimer(timer);
        String comment = "Test 1";
        timerEntity.setComment(comment);

        return timerEntity;
    }
}
