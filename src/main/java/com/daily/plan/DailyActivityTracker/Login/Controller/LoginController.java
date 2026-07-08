package com.daily.plan.DailyActivityTracker.Login.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/daily")
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {

        log.info("Entered endpoint [/login]");

        return "auth/login_page";
    }

    @GetMapping("/registration")
    public String registrationPage() {

        log.info("Entered endpoint [/registration]");

        return "auth/registration-page";
    }
}
