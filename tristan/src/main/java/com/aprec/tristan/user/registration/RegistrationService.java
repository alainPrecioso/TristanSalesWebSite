package com.aprec.tristan.user.registration;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import com.aprec.tristan.user.SiteUser;
import com.aprec.tristan.user.UserRole;
import com.aprec.tristan.user.UserService;
import com.aprec.tristan.user.registration.email.EmailReader;
import com.aprec.tristan.user.registration.email.EmailService;
import com.aprec.tristan.user.registration.token.ConfirmationToken;
import com.aprec.tristan.user.registration.token.ConfirmationTokenService;
import com.aprec.tristan.user.token.PasswordToken;
import com.aprec.tristan.user.token.PasswordTokenService;

@Service
public class RegistrationService {
	
	private final UserService userService;
	private final ConfirmationTokenService confirmationTokenService;
	private final PasswordTokenService passwordTokenService;
	private final EmailService emailService;
	private final EmailReader emailReader;
    private final String hostName;
    private final LocaleResolver localeResolver;
	
    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);
	
	
	public RegistrationService(UserService userService, 
			ConfirmationTokenService confirmationTokenService, 
			PasswordTokenService passwordTokenService,
			EmailService emailService,
			EmailReader emailReader,
			@Value("${host.name}") String hostName,
			LocaleResolver localeResolver
			) {
		super();
		this.userService = userService;
		this.confirmationTokenService = confirmationTokenService;
		this.passwordTokenService = passwordTokenService;
		this.emailService = emailService;
		this.emailReader = emailReader;
		this.hostName = hostName;
		this.localeResolver = localeResolver;
	}

	

	public String register(RegistrationRequest request) {
		
		String token = userService.signUpUser(
			new SiteUser(request.getUsername(), request.getEmail(), request.getPassword(), UserRole.ROLE_USER));
		if (token.equalsIgnoreCase("userexists")) {
			return "userexists";
		}
		String link = hostName + "/confirm?token=" + token;
		log.info("sends confirmation mail");
		log.info(link);
		// TODO uncomment
//		emailService.send(
//	                request.getEmail(),
//	                buildConfirmationEmail(request.getUsername(), link));
		
		return "registered";
	}
	
	
	public void resendConfirmationMail(String username) {
		SiteUser user = userService.getUser(username);
		String token = userService.getNewToken(user);
			String link = hostName + "/confirm?token=" + token;
			log.info("resends confirmation mail");
			log.info(link);
			emailService.send(
					user.getEmail(),
		                buildConfirmationEmail(user.getUsername(), link));
			
	}
	
	

	public String confirmToken(String token) throws IllegalStateException {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
				.orElseThrow(() -> new IllegalStateException("tokennotfound"));
		
//		Optional<ConfirmationToken>optionalToken = confirmationTokenService.getToken(token);
//		if (optionalToken.isEmpty()) {
//			return "tokennotfound";
//		}
//		ConfirmationToken confirmationToken = optionalToken.get();
		
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
	
	private String confirmPasswordToken(PasswordRequest request) {
//		PasswordToken passwordToken = passwordTokenService.getToken(token)
//				.orElseThrow(() -> new IllegalStateException("token not found"));
		Optional<PasswordToken>optionalToken = passwordTokenService.getToken(request.getToken());
		if (optionalToken.isEmpty()) {
			return "tokennotfound";
		}
		
		PasswordToken passwordToken = optionalToken.get();


		LocalDateTime expiredAt = passwordToken.getExpirationTime();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			return "tokenexpired";
		}
		passwordTokenService.deletePasswordToken(request.getToken());
		userService.updatePassword(passwordToken.getUser(), request.getPassword());
		return "passwordchanged";
	}
	
	@SuppressWarnings("unused")
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
		if (locale.getISO3Language().equalsIgnoreCase("eng")) {
			return String.format(emailReader.readFileToString("classpath:email/password_email.txt"), username, link);
		} else {
			return String.format(emailReader.readFileToString("classpath:email/password_email_fr.txt"), username, link);
		}
	}
	
	private Locale getLocale() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return localeResolver.resolveLocale(request);
	}
	
	public String requestNewPassword(String email) {
		SiteUser user = userService.getUser(email);
		
		String token = passwordTokenService.createPasswordToken(user);
		String link = hostName + "/enternewpass?token=" + token; 
		emailService.send(
				user.getEmail(),
	                buildPasswordEmail(user.getUsername(), link));
		return "passwordmailsent";
	}



	public String updatePassword(PasswordRequest request) {
		return confirmPasswordToken(request);
	}
	

	public boolean checkPassword(String username, String password) {
		return userService.checkPassword(username, password);
	}



	public void resendConfirmationMailFromToken(String token) {
		resendConfirmationMail(confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"))
				.getUser().getUsername());
	}
}
