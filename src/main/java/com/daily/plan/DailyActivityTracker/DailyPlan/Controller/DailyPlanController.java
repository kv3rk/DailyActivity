package com.daily.plan.DailyActivityTracker.DailyPlan.Controller;

import com.daily.plan.DailyActivityTracker.Authenticate.Service.AuthenticateService;
import com.daily.plan.DailyActivityTracker.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyActivityTracker.DailyPlan.DTO.ToggleFlagDTO;
import com.daily.plan.DailyActivityTracker.DailyPlan.Service.DailyPlanService;
import com.daily.plan.DailyActivityTracker.Timer.Service.TimerService;
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

    public DailyPlanController(DailyPlanService dailyPlanService,
                               TimerService timerService,
                               AuthenticateService authenticateService) {

        this.dailyPlanService = dailyPlanService;
        this.timerService = timerService;
        this.authenticateService = authenticateService;
    }

    @GetMapping("/main")
    public String starterPage(Model model) {

        log.info("Entered endpoint [daily/main]");

        model.addAttribute("active_goals", dailyPlanService.getActiveGoals());
        model.addAttribute("done_goals", dailyPlanService.getDoneGoals());
        model.addAttribute("activity_types", timerService.getAllActivityTypes());
        model.addAttribute("username", authenticateService.getUsername());

        return "main/main_page";
    }

    @GetMapping("/settings")
    public String settingsPage(Model model) {

        log.info("Entered endpoint [daily/settings]");

        model.addAttribute("username", authenticateService.getUsername());

        return "settings-page/settings-page";
    }

    @GetMapping("/error")
    public String errorPage() {

        log.info("Entered endpoint [daily/error]");

        return "other/error_page";
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