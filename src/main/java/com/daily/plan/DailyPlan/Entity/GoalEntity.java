package com.daily.plan.DailyPlan.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "daily_goals")
public class GoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CreationTimestamp
    private LocalDate goalDate;
    @NotBlank(message = "Goal cant be blank")
    @Column(unique = true, nullable = false)
    private String goalText;
    @ColumnDefault("false")
    @Column(columnDefinition = "boolean default false")
    private Boolean doneFlag = false;
}
