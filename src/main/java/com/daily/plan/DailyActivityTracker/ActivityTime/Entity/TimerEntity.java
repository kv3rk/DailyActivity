package com.daily.plan.DailyActivityTracker.ActivityTime.Entity;

import com.daily.plan.DailyActivityTracker.DataAnalyzer.DTO.ActivityDTO;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
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
                where activity_date >= :date and username = :username
                group by activity_type
                order by timer desc
                """,
        resultSetMapping = "ActivityTypeAndTimeMapping"
)
public class TimerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate activityDate = LocalDate.now(
            ZoneId.of("Europe/Moscow")
    );

    private String activityType;

    private String comment;

    private Long timer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "username", referencedColumnName = "username")
    private User username;
}
