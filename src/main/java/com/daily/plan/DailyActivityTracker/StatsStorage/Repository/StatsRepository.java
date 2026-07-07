package com.daily.plan.DailyActivityTracker.StatsStorage.Repository;

import com.daily.plan.DailyActivityTracker.StatsStorage.Entity.StatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatsRepository extends JpaRepository<StatsEntity, UUID> {
}
