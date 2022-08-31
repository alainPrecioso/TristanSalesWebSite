package com.aprec.tristan.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aprec.tristan.user.registration.RegistrationRequest;

@Controller
public class ViewController {
	
	
	@GetMapping(value= {"/", "/index"})
	public String home(Model model) {
		model.addAttribute("request", new RegistrationRequest());
		//throw new IllegalStateException("testa");
		return "index";
	}

	
	@GetMapping("/login")
	String login(Model model) {
		model.addAttribute("request", new RegistrationRequest());
		return "login";
	}
	
	@GetMapping("/register")
	String register(Model model) {
		model.addAttribute("request", new RegistrationRequest());
		return "register";
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
