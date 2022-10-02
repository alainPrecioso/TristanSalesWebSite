package com.aprec.tristan.controllers;

import com.aprec.tristan.config.exceptions.PasswordRequestException;
import com.aprec.tristan.config.exceptions.RegistrationException;
import com.aprec.tristan.user.registration.RegistrationRequest;
import com.aprec.tristan.user.registration.RegistrationServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aprec.tristan.controllers.Attribute.ALERT;
import static com.aprec.tristan.controllers.Attribute.REQUEST;
import static com.aprec.tristan.controllers.HtmlPage.*;

@Controller
@ControllerAdvice
public class ExceptionController implements ErrorController {

	@Autowired
	private RegistrationServiceInterface registrationService;

	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

	@RequestMapping("/error")
	public HtmlPage handleError(HttpServletResponse response, Exception e) {
		switch (response.getStatus()) {
		case 404:
			return ERROR_404;
		case 500:
			return ERROR_500;
		default:
			return ERROR;
		}
//        int status = response.getStatus();
//        if ( status == HttpStatus.NOT_FOUND.value()) {
//            return ERROR_404;
//        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//            return ERROR_500;
//        }
//        return ERROR;
	}

	@ExceptionHandler(RegistrationException.class)
	public HtmlPage handleRegistrationException(RegistrationException e, 
			HttpServletRequest request,
			Model model)
			throws IOException {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		request.getSession().setAttribute(ALERT.getAttribute(), e.getMessage());
		log.info("handleRegistrationException");
		switch (e.getMessage()) {
		case "emailalreadyconfirmed":
			return LOGIN_ALERT;
		case "tokenexpired":
			registrationService.resendConfirmationMailFromToken(request.getParameter("token"));
			// fallthrough
		case "userexists", "tokennotfound":
			return REGISTER_ALERT;
		default:
			return ERROR_500;
		}
	}

	@ExceptionHandler(PasswordRequestException.class)
	public HtmlPage handlePasswordRequestException(RegistrationException e, 
			HttpServletRequest request, Model model)
			throws IOException {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		log.info("handlePasswordRequestException");
		switch (e.getMessage()) {
		case "tokennotfound", "tokenexpired":
			request.getSession().setAttribute(ALERT.getAttribute(), e.getMessage());
			return REGISTER_ALERT;
		default:
			return ERROR_500;
		}
	}

}
