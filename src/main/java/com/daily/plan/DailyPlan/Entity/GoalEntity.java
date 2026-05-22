package com.daily.plan.DailyPlan.Entity;

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

    @CreationTimestamp
    private LocalDate goalDate;

    @Column(nullable = false)
    private String goalText;

    private Boolean doneFlag = false;
}
