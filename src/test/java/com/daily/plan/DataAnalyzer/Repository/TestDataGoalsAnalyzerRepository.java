package com.daily.plan.DataAnalyzer.Repository;

import com.daily.plan.DailyActivityTracker.DailyGoals.Entity.GoalEntity;
import com.daily.plan.DailyActivityTracker.DataAnalyzer.Repository.DataGoalsAnalyzerRepository;
import com.daily.plan.DailyPlan.Builder.GoalBuilder;
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

            GoalEntity goal = new GoalBuilder().build();

            repository.save(goal);

            Long result = repository.countAllGoals(LocalDate.now(), "kv3rk");

            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should not count goal when goal date is before requested date")
        void shouldNotCountGoalWhenDateIsBeforeBoundary() {

            GoalEntity goal = new GoalBuilder()
                    .withDate(LocalDate.now().minusDays(1)).build();

            repository.save(goal);

            Long result = repository.countAllGoals(LocalDate.now(), "kv3rk");

            assertThat(result).isZero();
        }

        @Test
        @DisplayName("Should count goal when goal date is after requested date")
        void shouldCountGoalWhenDateIsAfterBoundary() {

            GoalEntity goal = new GoalBuilder()
                    .withDate(LocalDate.now().plusDays(1)).build();

            repository.save(goal);

            Long result = repository.countAllGoals(LocalDate.now(), "kv3rk");

            assertThat(result).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("countAllStatusGoals()")
    class CountAllStatusGoalsTests {

        @Test
        @DisplayName("Should count accomplished goal")
        void shouldCountAccomplishedGoal() {

            GoalEntity goal = new GoalBuilder()
                    .withDoneFlag(true).build();

            repository.save(goal);

            Long result = repository.countAllStatusGoals(
                    LocalDate.now(),
                    true,
                    "kv3rk"
            );

            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should not count goal when status does not match")
        void shouldNotCountGoalWhenStatusDoesNotMatch() {

            GoalEntity goal = new GoalBuilder().build();

            repository.save(goal);

            Long result = repository.countAllStatusGoals(
                    LocalDate.now(),
                    true, "kv3rk"
            );

            assertThat(result).isZero();
        }

        @Test
        @DisplayName("Should not count goal when date is before boundary")
        void shouldNotCountGoalWhenDateIsBeforeBoundary() {

            GoalEntity goal = new GoalBuilder()
                    .withDate(LocalDate.now().minusDays(1)).build();

            repository.save(goal);

            Long result = repository.countAllStatusGoals(
                    LocalDate.now(),
                    true, "kv3rk"
            );

            assertThat(result).isZero();
        }

        @Test
        @DisplayName("Should count goal when future date matches status")
        void shouldCountFutureGoalWhenStatusMatches() {

            GoalEntity goal = new GoalBuilder()
                    .withDoneFlag(true).withDate(LocalDate.now().plusDays(1)).build();

            repository.save(goal);

            Long result = repository.countAllStatusGoals(
                    LocalDate.now(),
                    true, "kv3rk"
            );

            assertThat(result).isEqualTo(1L);
        }
    }
}
