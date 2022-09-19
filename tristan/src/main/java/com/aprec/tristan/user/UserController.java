package com.aprec.tristan.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequestMapping(path = "/")
public class UserController {

	private UserServiceInterface userService;

	public UserController(UserServiceInterface userService) {
		super();
		this.userService = userService;
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String user(HttpServletRequest request) {
		
		return "user";
	}

	@GetMapping("/delete")
	public String delete() {
		
		userService.scheduleDelete(userService.getLoggedUser());
		return "user";
	}

}
