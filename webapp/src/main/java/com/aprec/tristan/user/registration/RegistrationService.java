package com.aprec.tristan.user.registration;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.aprec.tristan.user.registration.token.ConfirmationTokenServiceInterface;
import com.aprec.tristan.user.token.PasswordTokenServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import com.aprec.tristan.user.SiteUser;
import com.aprec.tristan.user.UserRole;
import com.aprec.tristan.user.UserServiceInterface;
import com.aprec.tristan.user.registration.email.EmailReader;
import com.aprec.tristan.user.registration.email.EmailSender;
import com.aprec.tristan.user.registration.token.ConfirmationToken;
import com.aprec.tristan.user.token.PasswordToken;

@Service
public class RegistrationService implements RegistrationServiceInterface{
	
	private final UserServiceInterface userService;
	private final ConfirmationTokenServiceInterface confirmationTokenService;
	private final PasswordTokenServiceInterface passwordTokenService;
	private final EmailSender emailSender;
	private final EmailReader emailReader;
    private final String hostName;
    private final LocaleResolver localeResolver;
	
    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);
	
	
	public RegistrationService(UserServiceInterface userService,
			ConfirmationTokenServiceInterface confirmationTokenService,
			PasswordTokenServiceInterface passwordTokenService,
			EmailSender emailService,
			EmailReader emailReader,
			@Value("${host.name}") String hostName,
			LocaleResolver localeResolver
			) {
		super();
		this.userService = userService;
		this.confirmationTokenService = confirmationTokenService;
		this.passwordTokenService = passwordTokenService;
		this.emailSender = emailService;
		this.emailReader = emailReader;
		this.hostName = hostName;
		this.localeResolver = localeResolver;
	}

	

	public String register(RegistrationRequest request) throws IllegalStateException {
		
		String token = userService.signUpUser(
			new SiteUser(request.getUsername(), request.getEmail(), request.getPassword(), UserRole.ROLE_USER));
		String link = hostName + "/confirm?token=" + token;
		log.info("sends confirmation mail");
		log.info(link);
		// TODO uncomment
		emailSender.send(
	                request.getEmail(),
	                buildConfirmationEmail(request.getUsername(), link));
		
		return "registered";
	}
	
	
	public void resendConfirmationMail(String username) {
		SiteUser user = userService.getSiteUser(username);
		String token = userService.getNewToken(user);
			String link = hostName + "/confirm?token=" + token;
			log.info("resends confirmation mail");
			log.info(link);
			emailSender.send(
					user.getEmail(),
		                buildConfirmationEmail(user.getUsername(), link));
			
	}
	
	

	public String confirmToken(String token) throws IllegalStateException {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
				.orElseThrow(() -> new IllegalStateException("tokennotfound"));
		
		
		if (confirmationToken.getConfirmationTime() != null) {
			throw new IllegalStateException("emailalreadyconfirmed");
		}
		
		LocalDateTime expiredAt = confirmationToken.getExpirationTime();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("tokenexpired");
		}

		confirmationTokenService.setConfirmedAt(token);
		userService.enableUser(confirmationToken.getUser().getEmail());
		return "confirmed";
	}
	
	public String confirmPasswordToken(PasswordRequest request) throws IllegalStateException {
		PasswordToken passwordToken = passwordTokenService.getToken(request.getToken())
				.orElseThrow(() -> new IllegalStateException("tokennotfound"));

		if (passwordToken.getExpirationTime().isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("tokenexpired");
		}
		passwordTokenService.deletePasswordToken(request.getToken());
		userService.updatePassword(passwordToken.getUser(), request.getPassword());
		return "passwordchanged";
	}
	
	private String buildConfirmationEmail(String username, String link) {
		Locale locale = getLocale();
		if (locale.getISO3Language().equalsIgnoreCase("eng")) {
			return String.format(emailReader.readFileToString("classpath:email/confirmation_email.txt"), username, link);
		} else {
			return String.format(emailReader.readFileToString("classpath:email/confirmation_email_fr.txt"), username, link);
		}
	}
	
	private String buildPasswordEmail(String username, String link) {
		Locale locale = getLocale();
		switch (locale.getISO3Language()) {
		case "eng" :
			return String.format(emailReader.readFileToString("classpath:email/password_email.txt"), username, link);
		case "fr" :
			//fallthrough
		default :
			return String.format(emailReader.readFileToString("classpath:email/password_email_fr.txt"), username, link);
		}
	}
	
	private Locale getLocale() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return localeResolver.resolveLocale(request);
	}
	
	public String requestNewPassword(String email) {
		SiteUser user = userService.getSiteUser(email);
		
		String token = passwordTokenService.createPasswordToken(user);
		String link = hostName + "/enternewpass?token=" + token; 
		emailSender.send(
				user.getEmail(),
	                buildPasswordEmail(user.getUsername(), link));
		return "passwordmailsent";
	}

	

	public boolean checkPassword(String username, String password) {
		return userService.checkPassword(username, password);
	}



	public void resendConfirmationMailFromToken(String token) {
		resendConfirmationMail(confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"))
				.getUser().getUsername());
	}
}
