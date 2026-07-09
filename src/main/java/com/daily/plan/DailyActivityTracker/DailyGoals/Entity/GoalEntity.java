package com.daily.plan.DailyActivityTracker.DailyGoals.Entity;

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
@Table(
        name = "daily_goals",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"goal_date", "goal_text"}
                )
        }
)
public class GoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate goalDate = LocalDate.now(
            ZoneId.of("Europe/Moscow")
    );

    @Column(nullable = false)
    private String goalText;

    private Boolean doneFlag = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "username", referencedColumnName = "username", nullable = false)
    private User username;
}
