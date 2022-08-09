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
	public String getIndex() {
		return "index";
	}
	
	
	@GetMapping("/login")
	String getLogin() {
		return "index";
	}
	
	@GetMapping("/logged")
	public String logged() {
		return "logged";
	}
	
	@GetMapping("/loginerror")
	public String logError() {
		return "index";
	}
	
	@PostMapping("/loginerror")
	public String logErrorPost() {
		return "index";
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
