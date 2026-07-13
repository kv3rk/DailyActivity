package com.daily.plan.DailyActivityTracker.ActivityTime.Service;

import com.daily.plan.DailyActivityTracker.ActivityTime.DTO.TimerDTO;
import com.daily.plan.DailyActivityTracker.ActivityTime.Entity.TimerEntity;
import com.daily.plan.DailyActivityTracker.ActivityTime.Repository.TimerRepository;
import com.daily.plan.DailyActivityTracker.Authenticate.Service.AuthenticateService;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
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
    private final UserRepository userRepository;
    private final AuthenticateService authenticateService;

    public TimerService(TimerRepository timerRepository,
                        CurrentDateTime currentDateTime,
                        UserRepository userRepository,
                        AuthenticateService authenticateService) {

        this.timerRepository = timerRepository;
        this.currentDateTime = currentDateTime;
        this.userRepository = userRepository;
        this.authenticateService = authenticateService;
    }

    @Transactional
    public void saveTimerActivity(TimerDTO timerDTO) {

        TimerEntity timerEntity = new TimerEntity();

        timerEntity.setActivityType(timerDTO.activityType());
        timerEntity.setComment(timerDTO.comment());
        timerEntity.setTimer(timerDTO.timer());
        timerEntity.setUsername(
                userRepository.findByUsername(authenticateService.getUsername())
        );

        timerRepository.save(timerEntity);

        log.info("Saved TimerEntity with values [{}], [{}] for user [{}]",
                timerDTO.activityType(),
                timerDTO.timer(),
                authenticateService.getUsername()
        );
    }

    @Transactional
    public void deleteAll() {

        timerRepository.deleteAllByActivityDateBefore(
                currentDateTime.getCurrentDate().minusDays(1)
        );
    }

}
