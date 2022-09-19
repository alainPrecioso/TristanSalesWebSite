package com.aprec.tristan.controllers;

import static com.aprec.tristan.controllers.Attribute.ALERT;
import static com.aprec.tristan.controllers.Attribute.REQUEST;
import static com.aprec.tristan.controllers.HtmlPage.ERROR;
import static com.aprec.tristan.controllers.HtmlPage.ERROR_404;
import static com.aprec.tristan.controllers.HtmlPage.ERROR_500;
import static com.aprec.tristan.controllers.HtmlPage.REGISTER;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aprec.tristan.config.exceptions.PasswordRequestException;
import com.aprec.tristan.config.exceptions.RegistrationException;
import com.aprec.tristan.user.registration.RegistrationRequest;
import com.aprec.tristan.user.registration.RegistrationService;

@ControllerAdvice
public class ExceptionController implements ErrorController {
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
    private RegistrationService registrationService;
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
	
	@RequestMapping("/error")
    public HtmlPage handleError(HttpServletResponse response, Exception e) {
        int status = response.getStatus();
        if ( status == HttpStatus.NOT_FOUND.value()) {
            return ERROR_404;
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return ERROR_500;
        }
        return ERROR;
    }
	
	
	@ExceptionHandler(RegistrationException.class)
	public HtmlPage handleRegistrationException(RegistrationException e, 
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale,
			Model model
			) throws IOException {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		log.info("handleRegistrationException");
		switch(e.getMessage()) {
			case "tokenexpired":
				registrationService.resendConfirmationMailFromToken(request.getParameter("token"));
				// fallthrough
			case "emailalreadyconfirmed", "userexists", "tokennotfound":
				String errorMessage = messages.getMessage(e.getMessage(), null, locale);
				request.getSession().setAttribute(ALERT.getAttribute(), errorMessage);
				return REGISTER;
		  default:
			  return ERROR_500;
		}
	}
	
	
	@ExceptionHandler(PasswordRequestException.class)
	public HtmlPage handlePasswordRequestException(RegistrationException e, 
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale,
			Model model
			) throws IOException {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		log.info("handlePasswordRequestException");
		switch(e.getMessage()) {
			case "tokenexpired":
				// fallthrough
			case "tokennotfound":
				String errorMessage = messages.getMessage(e.getMessage(), null, locale);
				request.getSession().setAttribute(ALERT.getAttribute(), errorMessage);
				return REGISTER;
		  default:
			  return ERROR_500;
		}
	}
	
}
