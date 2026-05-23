package com.daily.plan.DataAnalyzer.Repository;

import com.daily.plan.DailyPlan.Entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataGoalsAnalyzerRepository extends JpaRepository<GoalEntity, UUID> {

    @Query(value = "select count(id) from daily_goals where goal_date = :today",
    nativeQuery = true)
    Optional<Long> countAllTodayGoals(@Param("today") LocalDate today);

    @Query(value = "select count(id) from daily_goals where goal_date = :today and done_flag = :flag",
    nativeQuery = true)
    Optional<Long> countAllStatusTodayGoals(@Param("today") LocalDate today, @Param("flag") Boolean flag);

}
