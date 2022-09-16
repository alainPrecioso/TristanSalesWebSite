package com.aprec.tristan.controllers;

import static com.aprec.tristan.controllers.Attribute.REQUEST;
import static com.aprec.tristan.controllers.HtmlPage.INDEX;
import static com.aprec.tristan.controllers.HtmlPage.LOGIN;
import static com.aprec.tristan.controllers.HtmlPage.REGISTER;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aprec.tristan.user.registration.RegistrationRequest;

@Controller
public class ViewController {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ViewController.class);
	
	@GetMapping({"/", "/index", "/home"})
	public HtmlPage home(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return INDEX;
	}

	
	@GetMapping("/login")
	HtmlPage login(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return LOGIN;
	}
	
	@GetMapping("/register")
	HtmlPage register(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return REGISTER;
	}
	
	
	@GetMapping("/logged")
	public String logged() {
		return "logged";
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/userroletest")
	public String userroletest() {
		return "userroletest";
	}
	
	
	@GetMapping("/test")
	public String test(HttpServletRequest request) {
		System.out.println(request.getSession().getAttribute("UserType"));
		throw new IllegalStateException("test");
		//return "test";
	}
}
