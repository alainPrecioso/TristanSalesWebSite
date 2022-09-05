package com.aprec.tristan.user.auth;

import static com.aprec.tristan.controllers.HtmlPage.ERROR;
import static com.aprec.tristan.controllers.HtmlPage.ERROR_404;
import static com.aprec.tristan.controllers.HtmlPage.ERROR_500;
import static com.aprec.tristan.controllers.HtmlPage.REGISTER_ERROR;

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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aprec.tristan.config.exceptions.RegistrationException;
import com.aprec.tristan.controllers.HtmlPage;
import com.aprec.tristan.user.registration.RegistrationService;

@Controller
@ControllerAdvice
public class ExceptionController implements ErrorController {
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
    private RegistrationService registrationService;
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
	
	@RequestMapping("/error")
    public HtmlPage handleError(HttpServletResponse response) {
        int status = response.getStatus();
        if ( status == HttpStatus.NOT_FOUND.value()) {
            return ERROR_404;
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return ERROR_500;
        }
        return ERROR;
    }
	
	
	@ExceptionHandler(RegistrationException.class)
	public void handleRegistrationException(RegistrationException e, 
			HttpServletRequest request,
			HttpServletResponse response,
			Locale locale
			) throws IOException {
		log.info("handleRegistrationException");
		switch(e.getMessage()) {
			case "tokenexpired":
				registrationService.resendConfirmationMailFromToken(request.getParameter("token"));
				// fallthrough
			case "emailalreadyconfirmed", "userexists", "tokennotfound":
				String errorMessage = messages.getMessage(e.getMessage(), null, locale);
				request.getSession().setAttribute("errormessage", errorMessage);
				response.sendRedirect(REGISTER_ERROR.getPage());
				break;
		  default:
			  response.sendRedirect(ERROR_500.getPage());
			  break;
		}
	}
}
