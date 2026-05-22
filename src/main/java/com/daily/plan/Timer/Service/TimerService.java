package com.daily.plan.Timer.Service;

import com.daily.plan.Timer.DTO.TimerDTO;
import com.daily.plan.Timer.Entity.TimerEntity;
import com.daily.plan.Timer.Repository.TimerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class TimerService {
    private final TimerRepository timerRepository;

    public TimerService(TimerRepository timerRepository) {
        this.timerRepository = timerRepository;
    }

    @Transactional
    public void saveTimerActivity(TimerDTO timerDTO) {

        TimerEntity timerEntity = new TimerEntity();

        timerEntity.setActivityType(timerDTO.activityType());
        timerEntity.setComment(timerDTO.comment());
        timerEntity.setTime(timerDTO.time());

        timerRepository.save(timerEntity);

        log.info("Saved TimerEntity with values [{}], [{}], [{}]",
                timerDTO.activityType(), timerDTO.comment(),
                timerDTO.time());
    }

    @Transactional
    public void deleteAll() {

        timerRepository.deleteAllByActivityDateBefore(LocalDate.now().minusWeeks(2));
    }
}
