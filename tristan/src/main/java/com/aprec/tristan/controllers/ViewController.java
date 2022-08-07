package com.aprec.tristan.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aprec.tristan.users.UserService;

@Controller
public class ViewController {
	
	
	


	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	
	@GetMapping("/login")
	String login() {
		return "login";
	}
	
	@GetMapping("/logged")
	public String logged() {
		return "logged";
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping("/userroletest")
	public String userroletest() {
		return "userroletest";
	}
	
	
	@RequestMapping("/test")
	public String test() {
		return "test";
	}
}
