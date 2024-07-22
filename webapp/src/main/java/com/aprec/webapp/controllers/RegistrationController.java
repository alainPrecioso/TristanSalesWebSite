package com.aprec.webapp.controllers;

import com.aprec.webapp.config.exceptions.PasswordRequestException;
import com.aprec.webapp.config.exceptions.RegistrationException;
import com.aprec.webapp.user.registration.PasswordRequest;
import com.aprec.webapp.user.registration.RegistrationRequest;
import com.aprec.webapp.user.registration.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.aprec.webapp.controllers.Attributes.*;
import static com.aprec.webapp.controllers.HtmlPage.*;

@Validated
@Controller
@RequestMapping(path = "/")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        super();
        this.registrationService = registrationService;
    }

    @ModelAttribute(REGISTRATION_REQUEST)
    public RegistrationRequest registrationRequest() {
        return new RegistrationRequest();
    }

    @GetMapping({"/", "/index", "/home"})
    public HtmlPage home(Model model) {
        return INDEX;
    }

    @GetMapping("/login")
    HtmlPage login(Model model) {
        return LOGIN;
    }

    @PostMapping("/newpass")
    public HtmlPage register(@RequestParam String email, Model model) {
        return INDEX_REDIRECT;
    }


    @GetMapping("/register")
    HtmlPage register(Model model) {
        model.addAttribute("isRegisterPage", true);
        return REGISTER;
    }

    @PostMapping("/register")
    public HtmlPage register(@Valid @ModelAttribute RegistrationRequest request,
                             HttpServletRequest servletRequest,
                             Model model) {
        String register;
        try {
            register = registrationService.register(request);
        } catch (IllegalStateException e) {
            throw new RegistrationException(e.getMessage());
        }
        servletRequest.getSession().setAttribute(MESSAGE, register);

        return INDEX_REDIRECT_MESSAGE;
    }


    @GetMapping(path = "/confirm")
    public HtmlPage confirm(@RequestParam String token,
                            HttpServletRequest servletRequest,
                            Model model) {
        String result;
        try {
            result = registrationService.confirmToken(token);

        } catch (IllegalStateException e) {
            throw new RegistrationException(e.getMessage());

        }
        servletRequest.getSession().setAttribute(MESSAGE, result);
        return LOGIN_REDIRECT;
    }

    @GetMapping("/forgot")
    HtmlPage forgotPassword(Model model) {
        return FORGOT;
    }

    @PostMapping("/passrequest")
    public HtmlPage newPasswordRequest(@RequestParam String email,
                                       HttpServletRequest servletRequest,
                                       Model model) {
        servletRequest.getSession().setAttribute(MESSAGE, registrationService.requestNewPassword(email));
        return INDEX_REDIRECT_MESSAGE;
    }

    @GetMapping("/enternewpass")
    public HtmlPage enterNewPassword(Model model) {
        model.addAttribute(PASSWORD_REQUEST, new PasswordRequest());
        return NEW_PASSWORD;
    }

    @PostMapping("/savenewpass")
    public HtmlPage saveNewPassword(@Valid @ModelAttribute("passrequest") PasswordRequest request,
                                    HttpServletRequest servletRequest,
                                    Model model) {
        String result;
        try {
            result = registrationService.confirmPasswordToken(request);
        } catch (IllegalStateException e) {
            throw new PasswordRequestException(e.getMessage());
        }
        servletRequest.getSession().setAttribute(MESSAGE, result);
        return LOGIN_REDIRECT;
    }

}
