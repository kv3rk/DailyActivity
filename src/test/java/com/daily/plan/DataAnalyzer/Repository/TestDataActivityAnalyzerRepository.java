package com.daily.plan.DataAnalyzer.Repository;

import com.daily.plan.DataAnalyzer.DTO.ActivityDTO;
import com.daily.plan.Timer.Entity.TimerEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tags({
        @Tag("repository"),
        @Tag("DataAnalyzer")
})
public class TestDataActivityAnalyzerRepository {

    @Autowired
    private DataActivityAnalyzerRepository repository;


    @Nested
    @DisplayName("countAllActivities()")
    class CountAllActivitiesTest {

        @Test
        @DisplayName("Should count activities when activities date equals requested date")
        void shouldCountAllActivitiesWhenDateEqualBoundary() {

            TimerEntity timerEntity = new TimerEntity();

            timerEntity.setActivityType("backend");
            timerEntity.setComment("Test 1");
            timerEntity.setTimer(10L);
            timerEntity.setActivityDate(LocalDate.now());

            repository.save(timerEntity);

            Long result = repository.countAllActivities(LocalDate.now());

            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should count activities when activity date after requested date")
        void shouldCountAllActivitiesWhenDateAfterBoundary() {

            TimerEntity timerEntity = new TimerEntity();

            timerEntity.setActivityType("backend");
            timerEntity.setComment("Test 1");
            timerEntity.setTimer(10L);
            timerEntity.setActivityDate(LocalDate.now().plusDays(2));

            repository.save(timerEntity);

            Long result = repository.countAllActivities(LocalDate.now());

            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should not count activities when activity date before requested date")
        void shouldNotCountAllActivitiesWhenDateBeforeBoundary() {

            TimerEntity timerEntity = new TimerEntity();

            timerEntity.setActivityType("backend");
            timerEntity.setComment("Test 1");
            timerEntity.setTimer(10L);
            timerEntity.setActivityDate(LocalDate.now().minusDays(2));

            repository.save(timerEntity);

            Long result = repository.countAllActivities(LocalDate.now());

            assertThat(result).isZero();
        }

    }

    @Nested
    @DisplayName("sumOfTimeAllActivities()")
    class SumOfTimeAllActivitiesTest {

        @Test
        @DisplayName("Should summarize time and count distinct activities when activities date equals requested date")
        void shouldSumAllActivitiesTimeWhenDateEqualBoundary() {

            TimerEntity timerEntity = new TimerEntity();
            TimerEntity timerEntity2 = new TimerEntity();

            timerEntity.setActivityType("backend");
            timerEntity.setComment("Test 1");
            timerEntity.setTimer(10L);
            timerEntity.setActivityDate(LocalDate.now());

            timerEntity2.setActivityType("games");
            timerEntity2.setComment("Test 2");
            timerEntity2.setTimer(20L);
            timerEntity2.setActivityDate(LocalDate.now());

            repository.save(timerEntity);
            repository.save(timerEntity2);

            List<ActivityDTO> resultList = repository.sumOfTimeAllActivities(LocalDate.now());
            Long result = resultList
                    .stream()
                    .mapToLong(ActivityDTO::getTimer)
                    .sum();
            long countGroupedActivities = resultList
                    .size();

            assertThat(result).isEqualTo(30L);
            assertThat(countGroupedActivities).isEqualTo(2);
        }

        @Test
        @DisplayName("Should summarize time and count distinct activities when activity date after requested date")
        void shouldSumAllActivitiesTimeWhenDateAfterBoundary() {

            TimerEntity timerEntity = new TimerEntity();
            TimerEntity timerEntity2 = new TimerEntity();

            timerEntity.setActivityType("backend");
            timerEntity.setComment("Test 1");
            timerEntity.setTimer(10L);
            timerEntity.setActivityDate(LocalDate.now().plusDays(2));

            timerEntity2.setActivityType("games");
            timerEntity2.setComment("Test 2");
            timerEntity2.setTimer(20L);
            timerEntity2.setActivityDate(LocalDate.now().plusDays(2));

            repository.save(timerEntity);
            repository.save(timerEntity2);

            List<ActivityDTO> resultList = repository.sumOfTimeAllActivities(LocalDate.now());
            Long result = resultList
                    .stream()
                    .mapToLong(ActivityDTO::getTimer)
                    .sum();
            long countGroupedActivities = resultList
                    .size();

            assertThat(result).isEqualTo(30L);
            assertThat(countGroupedActivities).isEqualTo(2);
        }

        @Test
        @DisplayName("Should not summarize time and count distinct activities when activity date before requested date")
        void shouldNotSumAllActivitiesTimeWhenDateBeforeBoundary() {

            TimerEntity timerEntity = new TimerEntity();
            TimerEntity timerEntity2 = new TimerEntity();

            timerEntity.setActivityType("backend");
            timerEntity.setComment("Test 1");
            timerEntity.setTimer(10L);
            timerEntity.setActivityDate(LocalDate.now().minusDays(2));

            timerEntity2.setActivityType("games");
            timerEntity2.setComment("Test 2");
            timerEntity2.setTimer(20L);
            timerEntity2.setActivityDate(LocalDate.now().minusDays(2));

            repository.save(timerEntity);
            repository.save(timerEntity2);

            List<ActivityDTO> resultList = repository.sumOfTimeAllActivities(LocalDate.now());
            Long result = resultList
                    .stream()
                    .mapToLong(ActivityDTO::getTimer)
                    .sum();
            long countGroupedActivities = resultList
                    .size();

            assertThat(result).isZero();
            assertThat(countGroupedActivities).isZero();
        }

        @Test
        @DisplayName("Should summarize time and count distinct activities with equal activity types and zero time duration")
        void shouldGroupActivitiesWithSameActivityType() {

            TimerEntity timerEntity = new TimerEntity();
            TimerEntity timerEntity2 = new TimerEntity();

            timerEntity.setActivityType("backend");
            timerEntity.setComment("Test 1");
            timerEntity.setTimer(0L);
            timerEntity.setActivityDate(LocalDate.now());

            timerEntity2.setActivityType("backend");
            timerEntity2.setComment("Test 2");
            timerEntity2.setTimer(0L);
            timerEntity2.setActivityDate(LocalDate.now());

            repository.save(timerEntity);
            repository.save(timerEntity2);

            List<ActivityDTO> resultList = repository.sumOfTimeAllActivities(LocalDate.now());
            Long result = resultList
                    .stream()
                    .mapToLong(ActivityDTO::getTimer)
                    .sum();
            long countGroupedActivities = resultList
                    .size();

            assertThat(result).isZero();
            assertThat(countGroupedActivities).isEqualTo(1);
        }

    }

}
