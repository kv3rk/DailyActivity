package com.daily.plan.Timer.Controller;

import com.daily.plan.Timer.DTO.TimerDTO;
import com.daily.plan.Timer.Service.TimerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/daily")
public class TimerController {
    private final TimerService timerService;

    public TimerController(TimerService timerService) {
        this.timerService = timerService;
    }

    @PostMapping("/save/timer/activity")
    @ResponseBody
    public void saveTimerActivity(@RequestBody TimerDTO timerDTO) {

        log.info("Entered endpoint [\"daily/save/timer/activity\"]");

        timerService.saveTimerActivity(timerDTO);
    }
}
