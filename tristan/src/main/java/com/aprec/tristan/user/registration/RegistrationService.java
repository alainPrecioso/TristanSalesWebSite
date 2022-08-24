package com.aprec.tristan.user.registration;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.aprec.tristan.user.UserSite;
import com.aprec.tristan.user.UserRole;
import com.aprec.tristan.user.UserService;
import com.aprec.tristan.user.registration.email.EmailReader;
import com.aprec.tristan.user.registration.email.EmailService;
import com.aprec.tristan.user.registration.token.ConfirmationToken;
import com.aprec.tristan.user.registration.token.ConfirmationTokenService;

@Service
public class RegistrationService {
	
	private final UserService userService;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailService emailService;
	private final EmailReader emailReader;
    private final String hostName;
	
	
	
	
	public RegistrationService(UserService userService, 
			ConfirmationTokenService confirmationTokenService, 
			EmailService emailService,
			EmailReader emailReader,
			@Value("${host.name}") String hostName
			) {
		super();
		this.userService = userService;
		this.confirmationTokenService = confirmationTokenService;
		this.emailService = emailService;
		this.emailReader = emailReader;
		this.hostName = hostName;
	}

	

	public String register(RegistrationRequest request) {
		
		String token = userService.signUpUser(
			new UserSite(request.getUsername(), request.getEmail(), request.getPassword(), UserRole.ROLE_USER));
		String link = hostName + "/confirm?token=" + token; 
		//TODO
//		emailService.send(
//	                request.getEmail(),
//	                buildEmail(request.getUsername(), link));
		
		return "user saved";
	}
	
	public void resendMail(String credential) {
		UserSite user = userService.getUser(credential);
		String token = userService.resetConfirmationToken(user);
			String link = hostName + "/confirm?token=" + token; 
			emailService.send(
					user.getEmail(),
		                buildEmail(user.getUsername(), link));
			
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
	
	private String buildEmail(String name, String link) {
		Locale locale = LocaleContextHolder.getLocale();
		if (locale.getISO3Language().equalsIgnoreCase("eng")) {
			return String.format(emailReader.readFileToString("classpath:email/email.txt"), name, link);
		} else {
			return String.format(emailReader.readFileToString("classpath:email/email_fr.txt"), name, link);
		}
	}
}
