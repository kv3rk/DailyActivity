package com.daily.plan.DataAnalyzer.Repository;

import com.daily.plan.DataAnalyzer.DTO.ActivityDTO;
import com.daily.plan.Timer.Builder.TimerBuilder;
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

            TimerEntity timerEntity = new TimerBuilder()
                    .build();

            repository.save(timerEntity);

            Long result = repository.countAllActivities(LocalDate.now());

            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should count activities when activity date after requested date")
        void shouldCountAllActivitiesWhenDateAfterBoundary() {

            TimerEntity timerEntity = new TimerBuilder()
                    .withDate(LocalDate.now().plusDays(2)).build();

            repository.save(timerEntity);

            Long result = repository.countAllActivities(LocalDate.now());

            assertThat(result).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should not count activities when activity date before requested date")
        void shouldNotCountAllActivitiesWhenDateBeforeBoundary() {

            TimerEntity timerEntity = new TimerBuilder()
                    .withDate(LocalDate.now().minusDays(2)).build();

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

            TimerEntity timerEntity = new TimerBuilder().build();
            TimerEntity timerEntity2 = new TimerBuilder()
                    .withTimer(20L).withActivityType("games").build();

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

            TimerEntity timerEntity = new TimerBuilder().build();
            TimerEntity timerEntity2 = new TimerBuilder()
                    .withTimer(20L).withActivityType("games")
                    .withDate(LocalDate.now().plusDays(2)).build();

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

            TimerEntity timerEntity = new TimerBuilder().build();
            TimerEntity timerEntity2 = new TimerBuilder()
                    .withTimer(20L).withActivityType("games")
                    .withDate(LocalDate.now().minusDays(2)).build();

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

            TimerEntity timerEntity = new TimerBuilder()
                    .withTimer(0L).build();
            TimerEntity timerEntity2 = new TimerBuilder()
                    .withTimer(0L).build();

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