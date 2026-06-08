package com.daily.plan.DataAnalyzer.Repository;

import com.daily.plan.DailyPlan.Entity.GoalEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TestDataGoalsAnalyzerRepository {

    @Autowired
    private DataGoalsAnalyzerRepository dataGoalsAnalyzerRepository;

    @Test
    @DisplayName("Normal scenario")
    @Tag("DataGoalsAnalyzerRepository")
    void setDataGoalsAnalyzerRepositoryNormalScenario() {

        GoalEntity goalEntity = new GoalEntity();

        goalEntity.setGoalText("Test 1");
        goalEntity.setDoneFlag(false);

        dataGoalsAnalyzerRepository.save(goalEntity);

        Optional<Long> result = dataGoalsAnalyzerRepository.countAllGoals(
                LocalDate.now()
        );

        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(Optional.of(1L));

    }

}
