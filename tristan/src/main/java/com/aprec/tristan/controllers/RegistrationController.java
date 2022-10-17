package com.aprec.tristan.controllers;

import com.aprec.tristan.config.exceptions.PasswordRequestException;
import com.aprec.tristan.config.exceptions.RegistrationException;
import com.aprec.tristan.user.registration.PasswordRequest;
import com.aprec.tristan.user.registration.RegistrationRequest;
import com.aprec.tristan.user.registration.RegistrationServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.aprec.tristan.controllers.Attribute.*;
import static com.aprec.tristan.controllers.HtmlPage.*;

@Validated
@Controller
@RequestMapping(path = "/")
public class RegistrationController {

	private final RegistrationServiceInterface registrationService;

	public RegistrationController(RegistrationServiceInterface registrationService) {
		super();
		this.registrationService = registrationService;
	}

	@PostMapping("/newpass")
	public HtmlPage register(@RequestParam String email, Model model) {
		return INDEX_REDIRECT;
	}
	
	
	@PostMapping("/add")
	public HtmlPage register(@Valid @ModelAttribute("request") RegistrationRequest request,
			HttpServletRequest servletRequest,
			Model model) {
		String register;
		try {
			register = registrationService.register(request);
		} catch (IllegalStateException e) {
			throw new RegistrationException(e.getMessage());
		}
		servletRequest.getSession().setAttribute(MESSAGE.getAttribute(), register);
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		
		return INDEX_REDIRECT_MESSAGE;
	}
	

	@GetMapping(path = "/confirm")
    public HtmlPage confirm(@RequestParam("token") String token,
							HttpServletRequest servletRequest,
							Model model) {
		String result ;
		try {
			result = registrationService.confirmToken(token);
			
		} catch (IllegalStateException e){
			throw new RegistrationException(e.getMessage());
		}
		servletRequest.getSession().setAttribute(MESSAGE.getAttribute(), result);
        model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
        return LOGIN_REDIRECT;
    }
	
	@GetMapping("/forgot")
	HtmlPage forgotPassword(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return FORGOT;
	}

	@PostMapping("/passrequest")
	public HtmlPage newPasswordRequest(@RequestParam String email,
									   HttpServletRequest servletRequest,
									   Model model) {
		servletRequest.getSession().setAttribute(MESSAGE.getAttribute(), registrationService.requestNewPassword(email));
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return INDEX_REDIRECT_MESSAGE;
	}

	@GetMapping("/enternewpass")
	public HtmlPage enterNewPassword(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		model.addAttribute(PASSWORD_REQUEST.getAttribute(), new PasswordRequest());
		return NEW_PASSWORD;
	}
	
	@PostMapping("/savenewpass")
	public HtmlPage saveNewPassword(@Valid @ModelAttribute("passrequest") PasswordRequest request,
									HttpServletRequest servletRequest,
									Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		String result;
		try {
			result = registrationService.confirmPasswordToken(request);
		} catch (IllegalStateException e) {
			throw new PasswordRequestException(e.getMessage());
		}
		servletRequest.getSession().setAttribute(MESSAGE.getAttribute(), result);
		return LOGIN_REDIRECT;
	}
	
}
