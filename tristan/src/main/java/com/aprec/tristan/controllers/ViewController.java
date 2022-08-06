package com.aprec.tristan.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aprec.tristan.users.UserRepository;
import com.aprec.tristan.users.UserService;

@Controller
public class ViewController {
	
	private UserRepository userRepository;
	private UserService userService;
	
	

	public ViewController(UserRepository userRepository, UserService userService) {
		super();
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@PostMapping(path = "/log")
	public String log(@RequestParam String username, @RequestParam String password) {
		
		userService.loadUserByUsername(username);
		
		
		return "index";
	}







	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping("/test")
	public String welcome() {
		return "test";
	}
}
