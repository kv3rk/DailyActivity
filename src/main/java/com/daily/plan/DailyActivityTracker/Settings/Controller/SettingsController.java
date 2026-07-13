package com.daily.plan.DailyActivityTracker.Settings.Controller;

import com.daily.plan.DailyActivityTracker.Settings.DTO.TelegramDTO;
import com.daily.plan.DailyActivityTracker.Settings.DTO.ThemeDTO;
import com.daily.plan.DailyActivityTracker.Settings.DTO.UserActivitiesDTO;
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
    @ResponseBody
    public String setTelegram() {

        log.info("Entered endpoint [/set/telegram]");

        return settingsService.setTelegram();

    }

    @GetMapping("/get/telegram")
    @ResponseBody
    public TelegramDTO getTelegram() {

        log.info("Entered endpoint [/get/telegram]");

        return settingsService.getTelegram();

    }

    @DeleteMapping("/delete/telegram")
    public void deleteTelegram() {

        log.info("Entered endpoint [/delete/telegram]");

        settingsService.deleteTelegram();
    }

    @PostMapping("/set/theme")
    public void setTheme(@RequestBody @Valid ThemeDTO themeDTO) {

        log.info("Entered endpoint [/set/theme]");

        settingsService.setTheme(themeDTO);

    }

    @GetMapping("/get/theme")
    @ResponseBody
    public ThemeDTO getTheme() {

        log.info("Entered endpoint [/get/theme]");

        return settingsService.getTheme();

    }

    @PostMapping("/set/user/activities")
    public void setUserActivities(@RequestBody @Valid UserActivitiesDTO userActivitiesDTO) {

        log.info("Entered endpoint [/set/user/activities]");

        settingsService.setUserActivities(userActivitiesDTO);

    }

    @GetMapping("/get/user/activities")
    @ResponseBody
    public UserActivitiesDTO getUserActivities() {

        log.info("Entered endpoint [/get/user/activities]");

        return settingsService.getUserActivities();

    }
}
