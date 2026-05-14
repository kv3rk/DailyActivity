package com.daily.plan.DailyPlan.Repository;

import com.daily.plan.DailyPlan.Entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoalRepository extends JpaRepository<GoalEntity, UUID> {
}
