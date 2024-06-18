package com.aprec.tristan.user.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aprec.tristan.user.SiteUser;
import com.aprec.tristan.user.User;

@Service
public class ConfirmationTokenService implements ConfirmationTokenServiceInterface {

	private final ConfirmationTokenRepository confirmationTokenRepository;

	public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
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
