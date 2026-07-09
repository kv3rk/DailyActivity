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
    private final String activityType1;
    private final String activityType2;
    private final String activityType3;
    private final String activityType4;
    private final String activityType5;
    private final String activityType6;

    public TimerService(TimerRepository timerRepository,
                        CurrentDateTime currentDateTime,
                        UserRepository userRepository,
                        AuthenticateService authenticateService,
                        @Value("${activity.type.1}") String activityType1,
                        @Value("${activity.type.2}") String activityType2,
                        @Value("${activity.type.3}") String activityType3,
                        @Value("${activity.type.4}") String activityType4,
                        @Value("${activity.type.5}") String activityType5,
                        @Value("${activity.type.6}") String activityType6) {

        this.timerRepository = timerRepository;
        this.currentDateTime = currentDateTime;
        this.userRepository = userRepository;
        this.authenticateService = authenticateService;
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
