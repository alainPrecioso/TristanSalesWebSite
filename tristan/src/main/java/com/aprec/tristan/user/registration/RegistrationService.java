package com.aprec.tristan.user.registration;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.aprec.tristan.user.User;
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
	
	
	
	
	public RegistrationService(UserService userService, 
			ConfirmationTokenService confirmationTokenService, 
			PasswordTokenService passwordTokenService,
			EmailService emailService,
			EmailReader emailReader,
			@Value("${host.name}") String hostName
			) {
		super();
		this.userService = userService;
		this.confirmationTokenService = confirmationTokenService;
		this.passwordTokenService = passwordTokenService;
		this.emailService = emailService;
		this.emailReader = emailReader;
		this.hostName = hostName;
	}

	

	public String register(RegistrationRequest request) {
		
		String token = userService.signUpUser(
			new User(request.getUsername(), request.getEmail(), request.getPassword(), UserRole.ROLE_USER));
		//TODO
//		String link = hostName + "/confirm?token=" + token; 
//		emailService.send(
//	                request.getEmail(),
//	                buildEmail(request.getUsername(), link));
		
		return "registered";
	}
	
	
	public void resendConfirmationMail(String username) {
		User user = userService.getUser(username);
		String token = userService.getNewToken(user);
			String link = hostName + "/confirm?token=" + token; 
			emailService.send(
					user.getEmail(),
		                buildConfirmationEmail(user.getUsername(), link));
			
	}
	
	

	@Transactional
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
				.orElseThrow(() -> new IllegalStateException("token not found"));

		if (confirmationToken.getConfirmationTime() != null) {
			throw new IllegalStateException("email already confirmed");
		}

		LocalDateTime expiredAt = confirmationToken.getExpirationTime();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}

		confirmationTokenService.setConfirmedAt(token);
		userService.enableUser(confirmationToken.getUser().getEmail());
		return "confirmed";
	}
	
	@Transactional
	private User confirmPasswordToken(String token) {
		PasswordToken passwordToken = passwordTokenService.getToken(token)
				.orElseThrow(() -> new IllegalStateException("token not found"));


		LocalDateTime expiredAt = passwordToken.getExpirationTime();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}
		passwordTokenService.deletePasswordToken(token);;
		return passwordToken.getUser();
	}
	
	private String buildConfirmationEmail(String username, String link) {
		Locale locale = LocaleContextHolder.getLocale();
		if (locale.getISO3Language().equalsIgnoreCase("eng")) {
			return String.format(emailReader.readFileToString("classpath:email/confirmation_email.txt"), username, link);
		} else {
			return String.format(emailReader.readFileToString("classpath:email/confirmation_email_fr.txt"), username, link);
		}
	}
	
	private String buildPasswordEmail(String username, String link) {
		Locale locale = LocaleContextHolder.getLocale();
		if (locale.getISO3Language().equalsIgnoreCase("eng")) {
			return String.format(emailReader.readFileToString("classpath:email/password_email.txt"), username, link);
		} else {
			return String.format(emailReader.readFileToString("classpath:email/password_email_fr.txt"), username, link);
		}
	}
	
	public String requestNewPassword(String email) {
		User user = userService.findUser(email);
		
		String token = passwordTokenService.createPasswordToken(user);
		String link = hostName + "/enternewpass?token=" + token; 
		emailService.send(
				user.getEmail(),
	                buildPasswordEmail(user.getUsername(), link));
		return "passwordmailsent";
	}



	public String updatePassword(PasswordRequest request) {
		User user = confirmPasswordToken(request.getToken());
		userService.updatePassword(user, request.getPassword());
		return "passwordchanged";
	}
}
