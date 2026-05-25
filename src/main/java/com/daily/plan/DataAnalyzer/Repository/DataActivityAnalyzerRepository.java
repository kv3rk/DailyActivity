package com.daily.plan.DataAnalyzer.Repository;

import com.daily.plan.DataAnalyzer.DTO.ActivityDTO;
import com.daily.plan.Timer.Entity.TimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataActivityAnalyzerRepository
        extends JpaRepository<TimerEntity, UUID> {

    @Query(
            value = "select count(id) from timer_activity where activity_date >= :date",
            nativeQuery = true
    )
    Optional<Long> countAllActivities(
            @Param("date") LocalDate date
    );

    @Query(
            nativeQuery = true,
            name = "sumOfTimeAllActivities"
    )
    List<ActivityDTO> sumOfTimeAllActivities(
            @Param("date") LocalDate date
    );


}
