package com.aprec.webapp.controllers;

import static com.aprec.webapp.controllers.Attribute.REQUEST;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.aprec.webapp.user.registration.RegistrationRequest;

@Controller
public class ListController {

	@PostMapping("/search")
	public String searchSubmit(Model model) {
		model.addAttribute("result", "pas de resultats");
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return "index";
	}
	
	

}
