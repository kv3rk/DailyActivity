package com.daily.plan.DailyPlan.Repository;

import com.daily.plan.DailyActivityTracker.DailyGoals.Repository.GoalRepository;
import com.daily.plan.DailyPlan.Builder.GoalBuilder;
import com.daily.plan.DailyActivityTracker.DailyGoals.Entity.GoalEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class TestGoalRepository {
    @Autowired
    private GoalRepository repository;

    @Nested
    @DisplayName("deleteAllByGoalDateBefore()")
    class DeleteAllByGoalDateBeforeTest {

        @Test
        @DisplayName("Should not delete goals with date after requested date")
        void shouldNotDeleteGoalsWithDateAfterRequestedDate() {

            GoalEntity goalEntity = new GoalBuilder()
                    .withDate(LocalDate.now().plusDays(2)).build();

            repository.save(goalEntity);
            repository.deleteAllByGoalDateBefore(LocalDate.now());

            assertTrue(repository.existsById(goalEntity.getId()));

        }

        @Test
        @DisplayName("Should not delete goals with date equal requested date")
        void shouldNotDeleteGoalsWithDateEvenRequestedDate() {

            GoalEntity goalEntity = new GoalBuilder().build();

            repository.save(goalEntity);
            repository.deleteAllByGoalDateBefore(LocalDate.now());


            assertTrue(repository.existsById(goalEntity.getId()));

        }

        @Test
        @DisplayName("Should delete goals with date before requested date")
        void shouldDeleteGoalsWithDateBeforeRequestedDate() {

            GoalEntity goalEntity = new GoalBuilder()
                    .withDate(LocalDate.now().minusDays(2)).build();
            GoalEntity goalEntity2 = new GoalBuilder()
                    .withDate(LocalDate.now().plusDays(2)).build();

            repository.save(goalEntity);
            repository.save(goalEntity2);
            repository.deleteAllByGoalDateBefore(LocalDate.now());

            assertFalse(repository.existsById(goalEntity.getId()));
            assertTrue(repository.existsById(goalEntity2.getId()));

        }

    }

    @Nested
    @DisplayName("findAllByDoneFlagAndGoalDate()")
    class FindAllByDoneFlagAndGoalDateTest {

        @Test
        @DisplayName("Should find all goals with equal parameters as requested")
        void shouldFindListOfGoalsWithEqualParametersAsRequested() {

            GoalEntity goalEntity1 = new GoalBuilder().build();

            repository.save(goalEntity1);
            List<GoalEntity> resultList = repository.findAllByDoneFlagAndGoalDate(
                    false, LocalDate.now()
            );

            assertTrue(resultList.contains(goalEntity1));

        }

        @Test
        @DisplayName("Should not find all goals with different flag parameter then requested")
        void shouldNotFindListOfGoalsWithDifferentFlagParameterThenRequested() {

            GoalEntity goalEntity1 = new GoalBuilder()
                    .withDoneFlag(true).build();

            repository.save(goalEntity1);
            List<GoalEntity> resultList = repository.findAllByDoneFlagAndGoalDate(
                    false, LocalDate.now()
            );

            assertFalse(resultList.contains(goalEntity1));

        }

        @Test
        @DisplayName("Should not find all goals with different date parameter then requested")
        void shouldNotFindListOfGoalsWithDifferentDateParameterThenRequested() {

            GoalEntity goalEntity1 = new GoalBuilder()
                    .withDate(LocalDate.now().minusDays(2)).build();

            repository.save(goalEntity1);
            List<GoalEntity> resultList = repository.findAllByDoneFlagAndGoalDate(
                    false, LocalDate.now()
            );

            assertFalse(resultList.contains(goalEntity1));

        }

        @Test
        @DisplayName("Should find 2 of 3 goals with equal parameters as requested ")
        void shouldFind2Of3GoalsWithEqualParametersAsRequested() {

            GoalEntity goalEntity1 = new GoalBuilder().build();
            GoalEntity goalEntity2 = new GoalBuilder().withText("Goal 2").build();
            GoalEntity goalEntity3 = new GoalBuilder()
                    .withText("Goal 3").withDoneFlag(true).build();

            repository.save(goalEntity1);
            repository.save(goalEntity2);
            repository.save(goalEntity3);
            List<GoalEntity> resultList = repository.findAllByDoneFlagAndGoalDate(
                    false, LocalDate.now()
            );

            assertThat(resultList.size()).isEqualTo(2);

        }


    }

    @Nested
    @DisplayName("existsByGoalDateAndGoalText()")
    class ExistsByGoalDateAndGoalTextTest {

        @Test
        @DisplayName("Should return true with even params as requested")
        void shouldReturnTrueWithEvenParametersAsRequested() {

            GoalEntity goalEntity1 = new GoalBuilder().build();

            repository.save(goalEntity1);
            boolean result = repository.existsByGoalDateAndGoalText(
                    LocalDate.now(), goalEntity1.getGoalText()
            );

            assertTrue(result);

        }

        @Test
        @DisplayName("Should return false with different date param then requested")
        void shouldReturnFalseWithDiffDateParamThenRequested() {

            GoalEntity goalEntity1 = new GoalBuilder()
                    .withDate(LocalDate.now().minusDays(3)).build();

            repository.save(goalEntity1);
            boolean result = repository.existsByGoalDateAndGoalText(
                    LocalDate.now(), goalEntity1.getGoalText()
            );

            assertFalse(result);

        }

        @Test
        @DisplayName("Should return false with different text param then requested")
        void shouldReturnFalseWithDiffTextParamThenRequested() {

            GoalEntity goalEntity1 = new GoalBuilder()
                    .withText("Test goal 1").build();

            repository.save(goalEntity1);
            boolean result = repository.existsByGoalDateAndGoalText(
                    LocalDate.now(), "Test 1"
            );

            assertFalse(result);

        }

        @Test
        @DisplayName("Should return true with even params as requested in 1 of 2 entities")
        void shouldReturnTrueWithEvenParametersAsRequestedIn1Of2Entities() {

            GoalEntity goalEntity1 = new GoalBuilder().build();
            GoalEntity goalEntity2 = new GoalBuilder()
                    .withText("Test goal 2").build();

            repository.save(goalEntity1);
            repository.save(goalEntity2);
            boolean result = repository.existsByGoalDateAndGoalText(
                    LocalDate.now(), goalEntity1.getGoalText()
            );

            assertTrue(result);

        }

    }

}
