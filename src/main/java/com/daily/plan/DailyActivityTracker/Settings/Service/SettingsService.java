package com.daily.plan.DailyActivityTracker.Settings.Service;

import com.daily.plan.DailyActivityTracker.Authenticate.Service.AuthenticateService;
import com.daily.plan.DailyActivityTracker.Settings.DTO.TelegramDTO;
import com.daily.plan.DailyActivityTracker.Settings.DTO.VolumeDTO;
import com.daily.plan.DailyActivityTracker.User.Entity.User;
import com.daily.plan.DailyActivityTracker.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class SettingsService {

    private final UserRepository userRepository;
    private final AuthenticateService authenticateService;


    public SettingsService(UserRepository userRepository,
                           AuthenticateService authenticateService) {
        this.userRepository = userRepository;
        this.authenticateService = authenticateService;
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
}
