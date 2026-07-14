package com.daily.plan.DailyActivityTracker.Settings.Service;

import com.daily.plan.DailyActivityTracker.Authenticate.Service.AuthenticateService;
import com.daily.plan.DailyActivityTracker.Settings.DTO.*;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import com.daily.plan.DailyActivityTracker.UserActivities.Entity.UserActivity;
import com.daily.plan.DailyActivityTracker.UserActivities.Repository.UserActivityRepository;
import com.daily.plan.DailyActivityTracker.common.mapper.UserActivityMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SettingsService {

    private final UserRepository userRepository;
    private final AuthenticateService authenticateService;
    private final UserActivityRepository userActivityRepository;
    private final UserActivityMapper userActivityMapper;


    public SettingsService(UserRepository userRepository,
                           AuthenticateService authenticateService,
                           UserActivityRepository userActivityRepository,
                           UserActivityMapper userActivityMapper) {

        this.userRepository = userRepository;
        this.authenticateService = authenticateService;
        this.userActivityRepository = userActivityRepository;
        this.userActivityMapper = userActivityMapper;
    }

    @Transactional
    public void setVolume(VolumeDTO volumeDTO) {

        User user = userRepository.findByUsername(authenticateService.getUsername());

        user.setVolume(volumeDTO.volume());

        log.info("Set volume [{}] for user [{}]",
                volumeDTO.volume(), user.getUsername());

    }

    @Transactional
    public VolumeDTO getVolume() {

        User user = userRepository.findByUsername(authenticateService.getUsername());

        VolumeDTO volumeDTO = new VolumeDTO(
                user.getVolume()
        );

        return volumeDTO;
    }

    @Transactional
    public String setTelegram() {

        User user = userRepository.findByUsername(authenticateService.getUsername());

        String generatedUUID = String.valueOf(UUID.randomUUID());

        user.setTelegram(generatedUUID);

        userRepository.save(user);

        log.info("Set telegram UUID [{}] for user [{}]",
                generatedUUID, user.getUsername());

        return generatedUUID;

    }

    @Transactional
    public TelegramDTO getTelegram() {

        User user = userRepository.findByUsername(authenticateService.getUsername());

        TelegramDTO telegramDTO = new TelegramDTO(
                user.getTelegram()
        );

        return telegramDTO;
    }

    @Transactional
    public void deleteTelegram() {

        User user = userRepository.findByUsername(authenticateService.getUsername());

        user.setTelegram(null);

        log.info("Removed telegram for user [{}]",
                user.getUsername());
    }

    @Transactional
    public ThemeDTO getTheme() {

        User user = userRepository.findByUsername(authenticateService.getUsername());

        ThemeDTO themeDTO = new ThemeDTO(
                user.getTheme()
        );

        return themeDTO;
    }

    @Transactional
    public void setTheme(ThemeDTO themeDTO) {

        User user = userRepository.findByUsername(authenticateService.getUsername());

        user.setTheme(themeDTO.theme());

        userRepository.save(user);

        log.info("Set theme to [{}] for user [{}]",
                themeDTO.theme(), user.getUsername());
    }

    @Transactional
    public UserActivityDTOForStats getUserActivities() {

        UserActivityDTOForStats userActivityDTOForStats = userActivityMapper.userActivityToDTO(
                userActivityRepository.findByUsername(
                        userRepository.findByUsername(
                                authenticateService.getUsername()
                        )
                )
        );

        return userActivityDTOForStats;
    }

    @Transactional
    public UserActivityDTOForFront getUserActivitiesForFront() {

        UserActivityDTOForStats userActivityDTOForStats = userActivityMapper.userActivityToDTO(
                userActivityRepository.findByUsername(
                        userRepository.findByUsername(
                                authenticateService.getUsername()
                        )
                )
        );

        UserActivityDTOForFront userActivityDTOForFront = new UserActivityDTOForFront(
                List.of(
                        userActivityDTOForStats.activity1(),
                        userActivityDTOForStats.activity2(),
                        userActivityDTOForStats.activity3()
                )
        );

        return userActivityDTOForFront;
    }

    @Transactional
    public void setUserActivities(UserActivityDTOForStats userActivityDTOForStats) {

        UserActivity userActivity = userActivityRepository.findByUsername(
                userRepository.findByUsername(
                        authenticateService.getUsername()
                )
        );

        userActivity.setActivity1(userActivityDTOForStats.activity1());
        userActivity.setActivity2(userActivityDTOForStats.activity2());
        userActivity.setActivity3(userActivityDTOForStats.activity3());

        userActivityRepository.save(userActivity);

        log.info("Set activities for user [{}]",
                authenticateService.getUsername());
    }

}
