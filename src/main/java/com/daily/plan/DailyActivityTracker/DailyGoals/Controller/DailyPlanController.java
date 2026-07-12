package com.daily.plan.DailyActivityTracker.DailyGoals.Controller;

import com.daily.plan.DailyActivityTracker.Authenticate.Service.AuthenticateService;
import com.daily.plan.DailyActivityTracker.DailyGoals.DTO.GoalDTO;
import com.daily.plan.DailyActivityTracker.DailyGoals.DTO.ToggleFlagDTO;
import com.daily.plan.DailyActivityTracker.DailyGoals.Service.DailyPlanService;
import com.daily.plan.DailyActivityTracker.ActivityTime.Service.TimerService;
import com.daily.plan.DailyActivityTracker.Settings.Service.SettingsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/daily")
public class DailyPlanController {

    private final DailyPlanService dailyPlanService;
    private final TimerService timerService;
    private final AuthenticateService authenticateService;
    private final SettingsService settingsService;

    public DailyPlanController(DailyPlanService dailyPlanService,
                               TimerService timerService,
                               AuthenticateService authenticateService,
                               SettingsService settingsService) {

        this.dailyPlanService = dailyPlanService;
        this.timerService = timerService;
        this.authenticateService = authenticateService;
        this.settingsService = settingsService;
    }

    @GetMapping("/main")
    public String starterPage(Model model) {

        log.info("Entered endpoint [daily/main]");

        model.addAttribute("active_goals", dailyPlanService.getActiveGoals());
        model.addAttribute("done_goals", dailyPlanService.getDoneGoals());
        model.addAttribute("activity_types", timerService.getAllActivityTypes());
        model.addAttribute("username", authenticateService.getUsername());
        model.addAttribute("theme", "dark");

        return "main/main_page";
    }

    @GetMapping("/settings")
    public String settingsPage(Model model) {

        log.info("Entered endpoint [daily/settings]");

        model.addAttribute("username", authenticateService.getUsername());
        model.addAttribute("theme", "dark");

        return "settings-page/settings-page";
    }

    @GetMapping("/error")
    public String errorPage(Model model) {

        log.info("Entered endpoint [daily/error]");

        model.addAttribute("theme", "dark");

        return "error-page/error_page";
    }

    @PostMapping("/save")
    @ResponseBody
    public GoalDTO save(@Valid @RequestBody GoalDTO goalDTO) {

        log.info("Entered endpoint [daily/save]");

        return dailyPlanService.save(goalDTO);
    }

    @PostMapping("/toggle")
    @ResponseBody
    public GoalDTO toggle(@Valid @RequestBody ToggleFlagDTO toggleFlagDTO) {

        log.info("Entered endpoint [daily/toggle]");

        return dailyPlanService.toggleFlag(toggleFlagDTO);
    }

    @GetMapping("/active")
    @ResponseBody
    public List<GoalDTO> getActiveGoals() {
        return dailyPlanService.getActiveGoals();
    }

    @GetMapping("/done")
    @ResponseBody
    public List<GoalDTO> getDoneGoals() {
        return dailyPlanService.getDoneGoals();
    }
}