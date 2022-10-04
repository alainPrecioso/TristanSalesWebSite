package com.aprec.tristan.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aprec.tristan.user.UserServiceInterface;


@PreAuthorize("hasRole('USER')")
@Controller
@RequestMapping(path = "/user")
public class UserController {

	private final UserServiceInterface userService;

	public UserController(UserServiceInterface userService) {
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
		return "redirect:";
	}
	
	@GetMapping("/canceldelete")
	public String cancelDelete() {
		
		userService.cancelDelete();
		return "redirect:";
	}

}
