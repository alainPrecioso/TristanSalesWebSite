package com.aprec.tristan.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aprec.tristan.user.registration.RegistrationRequest;

@Controller
public class UserController {
	private UserService userService;
	
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	
}
