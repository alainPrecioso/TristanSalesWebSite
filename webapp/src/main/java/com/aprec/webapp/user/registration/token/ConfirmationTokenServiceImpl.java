package com.aprec.webapp.user.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aprec.webapp.user.entities.SiteUser;
import com.aprec.webapp.user.entities.User;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

	private final ConfirmationTokenRepository confirmationTokenRepository;

	public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
		super();
		this.confirmationTokenRepository = confirmationTokenRepository;
	}
	
	private void saveConfirmationToken(ConfirmationToken confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}
	
	public Optional<ConfirmationToken> getToken(String token) {
	        return confirmationTokenRepository.findByToken(token);
	}

	public int setConfirmedAt(String token) {
	        return confirmationTokenRepository.updateConfirmationTime(
	                token, LocalDateTime.now());
	}
	
	public String refreshConfirmationToken(SiteUser user) {
		String newToken = UUID.randomUUID().toString();
		confirmationTokenRepository
		.updateToken(confirmationTokenRepository.findByUser(user).get().getToken(), 
				LocalDateTime.now(), 
				LocalDateTime.now().plusMinutes(15), 
				newToken);
		return newToken;
	}
	
	public String createConfirmationToken(SiteUser user) {
		ConfirmationToken confirmationToken = new ConfirmationToken(
				UUID.randomUUID().toString(),
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				user);
		
		saveConfirmationToken(confirmationToken);
		
		return confirmationToken.getToken();
	}

	public void delete(User user) {
		confirmationTokenRepository.findByUser(user).ifPresent(confirmationTokenRepository::delete);
	}
}
