package com.aprec.tristan.user.token;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aprec.tristan.user.User;
import com.aprec.tristan.user.registration.token.ConfirmationToken;

@Service
public class PasswordTokenService {

	private final PasswordTokenRepository passwordTokenRepository;

	public PasswordTokenService(PasswordTokenRepository passwordTokenRepository) {
		super();
		this.passwordTokenRepository = passwordTokenRepository;
	}
	
	private void savePasswordToken(PasswordToken passwordToken) {
		passwordTokenRepository.save(passwordToken);
	}
	
	public void deletePasswordToken(String token) {
		passwordTokenRepository.deleteByToken(token);
	}
	
	public Optional<PasswordToken> getToken(String token) {
	        return passwordTokenRepository.findByToken(token);
	}
	
	public String createNewToken(User user) {
		String newToken = UUID.randomUUID().toString();
		passwordTokenRepository
		.updateToken(passwordTokenRepository.findByUser(user).get().getToken(), 
				LocalDateTime.now(), 
				LocalDateTime.now().plusMinutes(15), 
				newToken);
		return newToken;
	}
	
	public String createPasswordToken(User user) {
		PasswordToken passwordToken = new PasswordToken(
				UUID.randomUUID().toString(),
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				user);
		
		savePasswordToken(passwordToken);
		return passwordToken.getToken();
	}

	public void delete(User user) {
		passwordTokenRepository.findByUser(user).ifPresent(passwordTokenRepository::delete);
	}
}
