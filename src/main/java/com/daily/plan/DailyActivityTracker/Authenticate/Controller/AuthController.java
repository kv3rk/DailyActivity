package com.daily.plan.DailyActivityTracker.Authenticate.Controller;

import com.daily.plan.DailyActivityTracker.Authenticate.Service.AuthenticateService;
import com.daily.plan.DailyActivityTracker.User.DTO.RegistrationUserDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/daily")
public class AuthController {

    private final AuthenticateService authenticateService;

    public AuthController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {

        log.info("Entered endpoint [/login]");

        model.addAttribute("theme", "dark");

        return "auth-pages/login_page";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {

        log.info("Entered endpoint [/registration]");

        model.addAttribute("registrationDTO", new RegistrationUserDTO("", ""));
        model.addAttribute("theme", "dark");

        return "auth-pages/registration-page";
    }

    @PostMapping("/get/credentials")
    public String getCredentials(@ModelAttribute @Valid RegistrationUserDTO registrationUserDTO,
                                 BindingResult bindingResult,
                                 Model model) {

        log.info("Entered endpoint [/get/credentials]");

        if (bindingResult.hasErrors()) {

            return "auth-pages/registration-page";
        }

        boolean registrationResult = authenticateService.registerUser(registrationUserDTO);

        if (!registrationResult){

            model.addAttribute("registrationError", "User with this nickname already exists");

            return "auth-pages/registration-page";
        }

        return "redirect:/daily/login";

    }
}
