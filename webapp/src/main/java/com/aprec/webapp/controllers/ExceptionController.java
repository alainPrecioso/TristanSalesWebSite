package com.aprec.webapp.controllers;

import com.aprec.webapp.config.exceptions.PasswordRequestException;
import com.aprec.webapp.config.exceptions.RegistrationException;
import com.aprec.webapp.user.registration.RegistrationRequest;
import com.aprec.webapp.user.registration.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import static com.aprec.webapp.controllers.Attributes.ALERT;
import static com.aprec.webapp.controllers.Attributes.REGISTRATION_REQUEST;
import static com.aprec.webapp.controllers.HtmlPage.*;

@Controller
@ControllerAdvice
public class ExceptionController implements ErrorController {

    @Autowired
    private RegistrationService registrationService;

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
    }

    @ExceptionHandler(RegistrationException.class)
    public HtmlPage handleRegistrationException(RegistrationException e,
                                                HttpServletRequest request,
                                                Model model)
            throws IOException {
        model.addAttribute(REGISTRATION_REQUEST, new RegistrationRequest());
        request.getSession().setAttribute(ALERT, e.getMessage());
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
        model.addAttribute(REGISTRATION_REQUEST, new RegistrationRequest());
        log.info("handlePasswordRequestException");
        switch (e.getMessage()) {
            case "tokennotfound", "tokenexpired":
                request.getSession().setAttribute(ALERT, e.getMessage());
                return REGISTER_ALERT;
            default:
                return ERROR_500;
        }
    }

}
