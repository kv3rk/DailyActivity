package com.daily.plan.DailyActivityTracker.Timer.Repository;

import com.daily.plan.DailyActivityTracker.Timer.Entity.TimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TimerRepository extends JpaRepository<TimerEntity, UUID> {

    void deleteAllByActivityDateBefore(LocalDate activityDateBefore);
}
