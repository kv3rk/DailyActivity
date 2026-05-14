package com.daily.plan.DailyPlan.Controller;

import com.daily.plan.DailyPlan.Service.DailyPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/daily")
public class DailyPlanController {
    private final DailyPlanService dailyPlanService;

    public DailyPlanController(DailyPlanService dailyPlanService) {
        this.dailyPlanService = dailyPlanService;
    }

    @GetMapping
    public String starterPage(Model model){

        return "main/main_page";
    }
}
