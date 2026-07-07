package com.daily.plan.DailyActivityTracker.Timer.Service;

import com.daily.plan.DailyActivityTracker.Timer.DTO.TimerDTO;
import com.daily.plan.DailyActivityTracker.Timer.Entity.TimerEntity;
import com.daily.plan.DailyActivityTracker.Timer.Repository.TimerRepository;
import com.daily.plan.DailyActivityTracker.common.unit.CurrentDateTime;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TimerService {
    private final TimerRepository timerRepository;
    private final CurrentDateTime currentDateTime;
    private final String activityType1;
    private final String activityType2;
    private final String activityType3;
    private final String activityType4;
    private final String activityType5;
    private final String activityType6;

    public TimerService(TimerRepository timerRepository,
                        CurrentDateTime currentDateTime,
                        @Value("${activity.type.1}") String activityType1,
                        @Value("${activity.type.2}") String activityType2,
                        @Value("${activity.type.3}") String activityType3,
                        @Value("${activity.type.4}") String activityType4,
                        @Value("${activity.type.5}") String activityType5,
                        @Value("${activity.type.6}") String activityType6) {
        this.timerRepository = timerRepository;
        this.currentDateTime = currentDateTime;
        this.activityType1 = activityType1;
        this.activityType2 = activityType2;
        this.activityType3 = activityType3;
        this.activityType4 = activityType4;
        this.activityType5 = activityType5;
        this.activityType6 = activityType6;
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

    public List<String> getAllActivityTypes() {

        List<String> activityList = new ArrayList<>(
                List.of(
                        activityType1, activityType2, activityType3,
                        activityType4, activityType5, activityType6
                )
        );

        return activityList;
    }
}
