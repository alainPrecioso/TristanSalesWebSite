package com.aprec.tristan.user;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aprec.tristan.user.registration.token.ConfirmationToken;
import com.aprec.tristan.user.registration.token.ConfirmationTokenService;

@Service
public class UserService implements UserDetailsService {

	
	private final static String USER_NOT_FOUND_MSG = "user %s not found";
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.confirmationTokenService = confirmationTokenService;
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		return userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
	}


	public String signUpUser(User user) {
		Boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent()
				|| userRepository.findByUsername(user.getUsername()).isPresent();
		if (userExists) {
			throw new IllegalStateException("email or username already taken");
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		ConfirmationToken confirmationToken = new ConfirmationToken(
				UUID.randomUUID().toString(),
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				user);
		
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return confirmationToken.getToken();
		
	}
	
	public String resetConfirmationToken(String username) {
		User user = userRepository.findByUsername(username).get();
		String newToken = UUID.randomUUID().toString();
		confirmationTokenService.reSetToken(
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				newToken,
				user);
		
		return user.getEmail();
		
	}
	
//	public String logInUser(String credential, String password) {
//		UserDetails user = loadUserByUsername(credential);
//		if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
//			return "log";
//		}
//		
//		
//		return "wrong credentials";
//		
//	}
	
	public int enableUser(String email) {
        return userRepository.enableUser(email);
    }


	public String getUserEmail(String username) {
		return userRepository.findByUsername(username).get().getEmail();
	}
	
	
	
}
