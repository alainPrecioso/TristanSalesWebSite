package com.aprec.tristan.controllers;

import org.springframework.stereotype.Controller;
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

	@PostMapping(path = "/log")
	public String log(@RequestParam String username, @RequestParam String password) {
		System.out.println("log try");
		userService.loadUserByUsername(username);
		
		
		return "test";
	}







	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	
	@RequestMapping("/userroletest")
	public String userroletest() {
		return "userroletest";
	}
	
	
	
	
	
	
	@RequestMapping("/test")
	public String welcome() {
		return "test";
	}
}
