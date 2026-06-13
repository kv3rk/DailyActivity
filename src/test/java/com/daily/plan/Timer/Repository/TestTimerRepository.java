package com.daily.plan.Timer.Repository;

import com.daily.plan.Timer.Builder.TimerBuilder;
import com.daily.plan.Timer.Entity.TimerEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TestTimerRepository {

    @Autowired
    private TimerRepository repository;

    @Nested
    @DisplayName("findAllByDoneFlagAndGoalDate()")
    class FindAllByDoneFlagAndGoalDateTest {

        @Test
        @DisplayName("Should not delete activities with date after requested date")
        void shouldNotDeleteActivitiesWithDateAfterRequestedDate() {

            TimerEntity timerEntity = new TimerBuilder()
                    .withDate(LocalDate.now().plusDays(2)).build();

            repository.save(timerEntity);
            repository.deleteAllByActivityDateBefore(LocalDate.now());

            assertTrue(repository.existsById(timerEntity.getId()));

        }

        @Test
        @DisplayName("Should not delete activities with date equal requested date")
        void shouldNotDeleteActivitiesWithDateEvenRequestedDate() {

            TimerEntity timerEntity = new TimerBuilder().build();

            repository.save(timerEntity);
            repository.deleteAllByActivityDateBefore(LocalDate.now());


            assertTrue(repository.existsById(timerEntity.getId()));

        }

        @Test
        @DisplayName("Should delete activities with date before requested date")
        void shouldDeleteActivitiesWithDateBeforeRequestedDate() {

            TimerEntity timerEntity = new TimerBuilder()
                    .withDate(LocalDate.now().minusDays(2)).build();
            TimerEntity timerEntity2 = new TimerBuilder()
                    .withDate(LocalDate.now().plusDays(2)).build();

            repository.save(timerEntity);
            repository.save(timerEntity2);
            repository.deleteAllByActivityDateBefore(LocalDate.now());

            assertFalse(repository.existsById(timerEntity.getId()));
            assertTrue(repository.existsById(timerEntity2.getId()));

        }

    }

}
