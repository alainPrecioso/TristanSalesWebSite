package com.aprec.webapp.controllers;

import com.aprec.webapp.user.registration.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.aprec.webapp.controllers.Attributes.REQUEST;
import static com.aprec.webapp.controllers.HtmlPage.*;

@Controller
public class ViewController {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @GetMapping({"/", "/index", "/home"})
    public HtmlPage home(Model model) {
        model.addAttribute(REQUEST, new RegistrationRequest());
        return INDEX;
    }

    @GetMapping("/login")
    HtmlPage login(Model model) {
        model.addAttribute(REQUEST, new RegistrationRequest());
        return LOGIN;
    }

    @GetMapping("/register")
    HtmlPage register(Model model) {
        model.addAttribute(REQUEST, new RegistrationRequest());
        return REGISTER;
    }
}
