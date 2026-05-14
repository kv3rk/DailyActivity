package com.daily.plan.DailyPlan.Service;

import com.daily.plan.DailyPlan.Repository.GoalRepository;
import org.springframework.stereotype.Service;

@Service
public class DailyPlanService {
    private final GoalRepository goalRepository;

    public DailyPlanService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }


}
