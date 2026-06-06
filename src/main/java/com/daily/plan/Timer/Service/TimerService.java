package com.daily.plan.Timer.Service;

import com.daily.plan.Timer.DTO.TimerDTO;
import com.daily.plan.Timer.Entity.TimerEntity;
import com.daily.plan.Timer.Repository.TimerRepository;
import com.daily.plan.common.unit.CurrentDateTime;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TimerService {
    private final TimerRepository timerRepository;
    private final CurrentDateTime currentDateTime;

    public TimerService(TimerRepository timerRepository, CurrentDateTime currentDateTime) {
        this.timerRepository = timerRepository;
        this.currentDateTime = currentDateTime;
    }

    @Transactional
    public void saveTimerActivity(TimerDTO timerDTO) {

        TimerEntity timerEntity = new TimerEntity();

        timerEntity.setActivityType(timerDTO.activityType());
        timerEntity.setComment(timerDTO.comment());
        timerEntity.setTimer(timerDTO.timer());

        timerRepository.save(timerEntity);

        log.info("Saved TimerEntity with values [{}], [{}], [{}]",
                timerDTO.activityType(), timerDTO.comment(),
                timerDTO.timer());
    }

    @Transactional
    public void deleteAll() {

        timerRepository.deleteAllByActivityDateBefore(currentDateTime.getCurrentDate().minusWeeks(2));
    }
}
