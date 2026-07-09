package com.daily.plan.DailyActivityTracker.DailyGoals.Repository;

import com.daily.plan.DailyActivityTracker.DailyGoals.Entity.GoalEntity;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, UUID> {

    List<GoalEntity> findAllByDoneFlagAndGoalDateAndUsername(Boolean doneFlag, LocalDate goalDate, User username);

    void deleteAllByGoalDateBefore(LocalDate goalDateBefore);

    boolean existsByGoalDateAndGoalTextAndUsername(
            LocalDate goalDate, String goalText, User username
    );
}
