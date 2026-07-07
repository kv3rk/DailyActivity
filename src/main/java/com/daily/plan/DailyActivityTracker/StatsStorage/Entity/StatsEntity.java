package com.daily.plan.DailyActivityTracker.StatsStorage.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "stats_storage")
public class StatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String term;

    private Long amountGoals;

    private Long percentageCompletion;

    private Long amountActivities;

    @Column(precision = 10, scale = 2)
    private BigDecimal activity_type_1 = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal activity_type_2 = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal activity_type_3 = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal activity_type_4 = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal activity_type_5 = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal activity_type_6 = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal timeActivities;
}
