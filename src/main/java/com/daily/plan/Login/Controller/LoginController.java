package com.daily.plan.Login.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {

        log.info("Entered endpoint [/login]");

        return "auth/login_page";
    }
}
