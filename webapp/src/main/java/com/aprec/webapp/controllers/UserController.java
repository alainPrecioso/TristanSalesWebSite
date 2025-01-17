package com.aprec.webapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aprec.webapp.user.UserService;


@PreAuthorize("hasRole('USER')")
@Controller
@RequestMapping(path = "/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	
	@GetMapping
	public String user(Model model) {
		model.addAttribute("delete", userService.getLoggedUser().isDeleteScheduled());
		model.addAttribute("daysleft", userService.getLoggedUser().getDaysToDeletion());
		return "user";
	}

	@PostMapping("/delete")
	public String delete() {
		
		userService.scheduleDelete();
		return "redirect:/user";
	}
	
	@GetMapping("/canceldelete")
	public String cancelDelete() {
		
		userService.cancelDelete();
		return "redirect:/user";
	}

}
