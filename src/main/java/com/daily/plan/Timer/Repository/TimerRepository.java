package com.daily.plan.Timer.Repository;

import com.daily.plan.Timer.Entity.TimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimerRepository extends JpaRepository<TimerEntity, UUID> {
}
