package com.aprec.webapp.controllers;

import com.aprec.webapp.user.registration.RegistrationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import static com.aprec.webapp.controllers.Attributes.REQUEST;

@Controller
public class ListController {

    @PostMapping("/search")
    public String searchSubmit(Model model) {
        model.addAttribute("result", "pas de resultats");
        model.addAttribute(REQUEST, new RegistrationRequest());
        return "index";
    }


}
