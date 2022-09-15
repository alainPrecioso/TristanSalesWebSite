package com.aprec.tristan.user;

import static com.aprec.tristan.controllers.Attribute.PASSWORD_REQUEST;

import java.security.Principal;
import java.util.Arrays;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aprec.tristan.user.registration.PasswordRequest;

@Validated
@Controller
@RequestMapping(path = "/")
public class UserController {

	private UserRepository userRepository;
	private UserService userService;

	public UserController(UserRepository userRepository, UserService userService) {
		super();
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@GetMapping("/delete")
	public String delete(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response) {
		User user = null;
		model.addAttribute(PASSWORD_REQUEST.getAttribute(), new PasswordRequest());
		if (request.getSession().getAttribute("UserType").equals("gitHub")) {
			user = userRepository.findGitHubUserByUsername(principal.getName()).get();
		}
		if (request.getSession().getAttribute("UserType").equals("site")) {
			user = userRepository.findByUsername(principal.getName()).get();
		}
		
		userService.scheduleDelete(user);
//		request.getSession().invalidate();
//		Cookie sessionIdCookie = new Cookie("JSESSIONID", null);
//		sessionIdCookie.setMaxAge(0);
//		response.addCookie(sessionIdCookie);
//		Cookie rememberMeCookie = new Cookie("remember-me", null);
//		rememberMeCookie.setMaxAge(0);
//		response.addCookie(rememberMeCookie);
//		Cookie test = new Cookie("test", "yum");
//		test.setMaxAge(60);
//		response.addCookie(test);
		return "user";
	}

}
