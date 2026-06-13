package com.daily.plan.DailyPlan.Builder;

import com.daily.plan.DailyPlan.Entity.GoalEntity;

import java.time.LocalDate;

public class GoalBuilder {

    private LocalDate goalDate = LocalDate.now();
    private String goalText = "Goal";
    private Boolean doneFlag = false;

    public GoalBuilder withDate(LocalDate date) {
        this.goalDate = date;
        return this;
    }

    public GoalBuilder withDoneFlag(Boolean flag) {
        this.doneFlag = flag;
        return this;
    }

    public GoalBuilder withText(String text) {
        this.goalText = text;
        return this;
    }

    public GoalEntity build() {

        GoalEntity goalEntity = new GoalEntity();

        goalEntity.setGoalText(goalText);
        goalEntity.setGoalDate(goalDate);
        goalEntity.setDoneFlag(doneFlag);

        return goalEntity;
    }
}