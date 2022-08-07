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
	
	private UserService userService;
	
	

	public ViewController(UserService userService) {
		super();
		this.userService = userService;
	}




	@RequestMapping("/index")
	public String index() {
		return "index";
	}



	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@PostMapping("/log")
	public String log(@RequestParam String username, @RequestParam String password) {
		userService.logInUser(username, password);
		
		
		return "index";
	}
	
	@GetMapping("/log")
	public String login() {
		//return userService.logInUser(username, password);
		
		
		return "index";
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
