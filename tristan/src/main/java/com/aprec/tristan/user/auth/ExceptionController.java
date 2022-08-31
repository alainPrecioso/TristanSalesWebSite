package com.aprec.tristan.user.auth;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.aprec.tristan.user.registration.RegistrationRequest;

@ControllerAdvice
public class ExceptionController {
	
	@Autowired
    private MessageSource messages;
	
	@ExceptionHandler(value = IllegalStateException.class)
	public void handleException(IllegalStateException e, 
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			) throws IOException {
		String errorMessage = messages.getMessage(e.getMessage(), null, locale);
		request.getSession().setAttribute("errormessage", errorMessage);
		response.sendRedirect("/register?error=true");
	}
}
