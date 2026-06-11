package com.daily.plan.DataAnalyzer.Repository;

import com.daily.plan.DailyPlan.Entity.GoalEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("DataGoalsAnalyzerRepository Tests")
@Tags({
        @Tag("repository"),
        @Tag("DataAnalyzer")
})
class TestDataGoalsAnalyzerRepository {

    @Autowired
    private DataGoalsAnalyzerRepository repository;

    @Nested
    @DisplayName("countAllGoals()")
    class CountAllGoalsTests {

        @Test
        @DisplayName("Should count goal when goal date equals requested date")
        void shouldCountGoalWhenDateEqualsBoundary() {

            GoalEntity goal = new GoalEntity();
            goal.setGoalText("Goal");
            goal.setDoneFlag(false);
            goal.setGoalDate(LocalDate.now());

            repository.save(goal);

            Long result = repository.countAllGoals(LocalDate.now());

            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should not count goal when goal date is before requested date")
        void shouldNotCountGoalWhenDateIsBeforeBoundary() {

            GoalEntity goal = new GoalEntity();
            goal.setGoalText("Goal");
            goal.setDoneFlag(false);
            goal.setGoalDate(LocalDate.now().minusDays(1));

            repository.save(goal);

            Long result = repository.countAllGoals(LocalDate.now());

            assertThat(result).isZero();
        }

        @Test
        @DisplayName("Should count goal when goal date is after requested date")
        void shouldCountGoalWhenDateIsAfterBoundary() {

            GoalEntity goal = new GoalEntity();
            goal.setGoalText("Goal");
            goal.setDoneFlag(false);
            goal.setGoalDate(LocalDate.now().plusDays(1));

            repository.save(goal);

            Long result = repository.countAllGoals(LocalDate.now());

            assertThat(result).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("countAllStatusGoals()")
    class CountAllStatusGoalsTests {

        @Test
        @DisplayName("Should count accomplished goal")
        void shouldCountAccomplishedGoal() {

            GoalEntity goal = new GoalEntity();
            goal.setGoalText("Goal");
            goal.setDoneFlag(true);
            goal.setGoalDate(LocalDate.now());

            repository.save(goal);

            Long result = repository.countAllStatusGoals(
                            LocalDate.now(),
                            true
                    );

            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should not count goal when status does not match")
        void shouldNotCountGoalWhenStatusDoesNotMatch() {

            GoalEntity goal = new GoalEntity();
            goal.setGoalText("Goal");
            goal.setDoneFlag(false);
            goal.setGoalDate(LocalDate.now());

            repository.save(goal);

            Long result = repository.countAllStatusGoals(
                            LocalDate.now(),
                            true
                    );

            assertThat(result).isZero();
        }

        @Test
        @DisplayName("Should not count goal when date is before boundary")
        void shouldNotCountGoalWhenDateIsBeforeBoundary() {

            GoalEntity goal = new GoalEntity();
            goal.setGoalText("Goal");
            goal.setDoneFlag(true);
            goal.setGoalDate(LocalDate.now().minusDays(1));

            repository.save(goal);

            Long result = repository.countAllStatusGoals(
                            LocalDate.now(),
                            true
                    );

            assertThat(result).isZero();
        }

        @Test
        @DisplayName("Should count goal when future date matches status")
        void shouldCountFutureGoalWhenStatusMatches() {

            GoalEntity goal = new GoalEntity();
            goal.setGoalText("Goal");
            goal.setDoneFlag(true);
            goal.setGoalDate(LocalDate.now().plusDays(1));

            repository.save(goal);

            Long result = repository.countAllStatusGoals(
                            LocalDate.now(),
                            true
                    );

            assertThat(result).isEqualTo(1L);
        }
    }
}
