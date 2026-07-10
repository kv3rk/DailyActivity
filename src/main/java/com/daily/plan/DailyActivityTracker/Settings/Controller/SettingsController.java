package com.daily.plan.DailyActivityTracker.Settings.Controller;

import com.daily.plan.DailyActivityTracker.Settings.DTO.TelegramDTO;
import com.daily.plan.DailyActivityTracker.Settings.DTO.VolumeDTO;
import com.daily.plan.DailyActivityTracker.Settings.Service.SettingsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/daily/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PostMapping("/set/volume")
    public void setVolume(@RequestBody @Valid VolumeDTO volumeDTO) {

        log.info("Entered endpoint [/set/volume]");

        settingsService.setVolume(volumeDTO);

    }

    @GetMapping("/get/volume")
    @ResponseBody
    public VolumeDTO getVolume() {

        log.info("Entered endpoint [/get/volume]");

        return settingsService.getVolume();

    }

    @PostMapping("/set/telegram")
    public void setTelegram(@RequestBody @Valid TelegramDTO telegramDTO) {

        log.info("Entered endpoint [/set/telegram]");

        settingsService.setTelegram(telegramDTO);

    }

    @GetMapping("/get/telegram")
    @ResponseBody
    public TelegramDTO getTelegram() {

        log.info("Entered endpoint [/get/telegram]");

        return settingsService.getTelegram();

    }

    @DeleteMapping("/delete/telegram")
    public void deleteTelegram(){

        log.info("Entered endpoint [/delete/telegram]");

        settingsService.deleteTelegram();
    }
}
