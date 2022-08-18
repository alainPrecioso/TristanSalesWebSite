package com.aprec.tristan.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {
	
	
	@GetMapping("/")
	public String home() {
		return "index";
	}

	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	String login() {
		return "login";
	}
	
	@GetMapping("/register")
	String register() {
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
