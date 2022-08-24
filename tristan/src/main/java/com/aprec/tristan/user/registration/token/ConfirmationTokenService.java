package com.aprec.tristan.user.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aprec.tristan.user.UserSite;

@Service
public class ConfirmationTokenService {

	private final ConfirmationTokenRepository confirmationTokenRepository;

	public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
		super();
		this.confirmationTokenRepository = confirmationTokenRepository;
	}
	
	public void saveConfirmationToken(ConfirmationToken confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}
	
	public Optional<ConfirmationToken> getToken(String token) {
	        return confirmationTokenRepository.findByToken(token);
	}

	public int setConfirmedAt(String token) {
	        return confirmationTokenRepository.updateConfirmationTime(
	                token, LocalDateTime.now());
	}
	
	public void reSetToken(LocalDateTime creationTime, 
			LocalDateTime expirationTime, 
			String newToken,
			UserSite user) {
		
		confirmationTokenRepository.updateToken(confirmationTokenRepository.findByUser(user).get().getToken(), creationTime, expirationTime, newToken);
	}
}
