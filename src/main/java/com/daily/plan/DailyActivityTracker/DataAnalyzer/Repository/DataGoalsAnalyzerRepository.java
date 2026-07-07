package com.daily.plan.DailyActivityTracker.DataAnalyzer.Repository;

import com.daily.plan.DailyActivityTracker.DailyPlan.Entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface DataGoalsAnalyzerRepository extends JpaRepository<GoalEntity, UUID> {

    @Query(
            value = "select count(id) from daily_goals where goal_date >= :date",
            nativeQuery = true
    )
    Long countAllGoals(
            @Param("date") LocalDate date
    );

    @Query(
            value = "select count(id) from daily_goals where goal_date >= :date and done_flag = :flag",
            nativeQuery = true
    )
    Long countAllStatusGoals(
            @Param("date") LocalDate date,
            @Param("flag") Boolean flag
    );

}
