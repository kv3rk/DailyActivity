package com.daily.plan.Timer.Entity;

import com.daily.plan.DataAnalyzer.DTO.ActivityDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "timer_activity")

@SqlResultSetMapping(
        name = "ActivityTypeAndTimeMapping",
        classes = @ConstructorResult(
                targetClass = ActivityDTO.class,
                columns = {
                        @ColumnResult(name = "activityType", type = String.class),
                        @ColumnResult(name = "timer", type = Long.class)
                }
        )
)
@NamedNativeQuery(
        name = "sumOfTimeAllActivities",
        query = """
                select activity_type as activityType,
                       sum(timer) as timer
                from timer_activity
                where activity_date >= :date
                group by activity_type
                order by timer desc
                """,
        resultSetMapping = "ActivityTypeAndTimeMapping"
)
public class TimerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CreationTimestamp
    private LocalDate activityDate;
    private String activityType;
    private String comment;
    private Long timer;
}
