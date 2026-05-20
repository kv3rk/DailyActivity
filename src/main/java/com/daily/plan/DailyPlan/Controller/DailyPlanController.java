package com.daily.plan.DailyPlan.Controller;

import com.daily.plan.DailyPlan.DTO.GoalDTO;
import com.daily.plan.DailyPlan.DTO.ToggleFlagDTO;
import com.daily.plan.DailyPlan.Service.DailyPlanService;
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

    public DailyPlanController(DailyPlanService dailyPlanService) {
        this.dailyPlanService = dailyPlanService;
    }

    @GetMapping("/main")
    public String starterPage(Model model) {

        log.info("Entered endpoint [daily/main]");

        model.addAttribute("active_goals", dailyPlanService.getActiveGoals());
        model.addAttribute("done_goals", dailyPlanService.getDoneGoals());

        return "main/main_page";
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