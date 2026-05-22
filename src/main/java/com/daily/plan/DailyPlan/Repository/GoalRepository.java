package com.daily.plan.DailyPlan.Repository;

import com.daily.plan.DailyPlan.Entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<GoalEntity, UUID> {
    List<GoalEntity> findAllByDoneFlagAndGoalDate(Boolean doneFlag, LocalDate goalDate);

    void deleteAllByGoalDateBefore(LocalDate goalDateBefore);

    boolean existsByGoalDateAndGoalText(
            LocalDate goalDate,
            String goalText
    );
}
