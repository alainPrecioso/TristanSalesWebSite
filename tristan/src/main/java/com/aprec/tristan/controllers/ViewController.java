package com.aprec.tristan.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aprec.tristan.user.registration.RegistrationRequest;
import static com.aprec.tristan.controllers.HtmlPage.INDEX;
import static com.aprec.tristan.controllers.HtmlPage.LOGIN;
import static com.aprec.tristan.controllers.HtmlPage.REGISTER;
import static com.aprec.tristan.controllers.Attribute.REQUEST;

@Controller
public class ViewController {
	
	
	
	@GetMapping({"/", "/index", "/home"})
	public HtmlPage home(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return INDEX;
	}

	
	@GetMapping("/login")
	HtmlPage login(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return LOGIN;
	}
	
	@GetMapping("/register")
	HtmlPage register(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return REGISTER;
	}
	
	
	@GetMapping("/logged")
	public String logged() {
		return "logged";
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/userroletest")
	public String userroletest() {
		return "userroletest";
	}
	
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
}
