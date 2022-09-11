package com.aprec.tristan.user;

import static com.aprec.tristan.controllers.Attribute.MESSAGE;
import static com.aprec.tristan.controllers.Attribute.PASSWORD_REQUEST;
import static com.aprec.tristan.controllers.Attribute.REQUEST;
import static com.aprec.tristan.controllers.HtmlPage.FORGOT;
import static com.aprec.tristan.controllers.HtmlPage.INDEX;
import static com.aprec.tristan.controllers.HtmlPage.LOGIN;
import static com.aprec.tristan.controllers.HtmlPage.NEW_PASSWORD;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aprec.tristan.config.exceptions.PasswordRequestException;
import com.aprec.tristan.config.exceptions.RegistrationException;
import com.aprec.tristan.controllers.HtmlPage;
import com.aprec.tristan.user.registration.RegistrationRequest;

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
	public HtmlPage delete(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response) {

		userService.deleteUser(principal.getName());
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		request.getSession().invalidate();
		Cookie sessionIdCookie = new Cookie("JSESSIONID", null);
		sessionIdCookie.setMaxAge(0);
		response.addCookie(sessionIdCookie);
		Cookie rememberMeCookie = new Cookie("remember-me", null);
		rememberMeCookie.setMaxAge(0);
		response.addCookie(rememberMeCookie);
		Cookie test = new Cookie("test", "yum");
		test.setMaxAge(0);
		response.addCookie(test);
//		ResponseCookie sessionIdCookie = ResponseCookie.from("JSESSIONID", null).build();
//		ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, sessionIdCookie.toString()).build();
//		ResponseCookie rememberMeIdCookie = ResponseCookie.from("remember-me", null).build();
//		ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, rememberMeIdCookie.toString()).build();
		return INDEX;
	}

}
