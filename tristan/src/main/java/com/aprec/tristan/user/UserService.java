package com.aprec.tristan.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aprec.tristan.user.registration.token.ConfirmationTokenService;

@Service
public class UserService implements UserDetailsService {

	
	private final static String USER_NOT_FOUND_MSG = "user %s not found";
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	
	public UserService(UserRepository userRepository, 
			BCryptPasswordEncoder bCryptPasswordEncoder, 
			ConfirmationTokenService confirmationTokenService) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.confirmationTokenService = confirmationTokenService;
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository
				.findByCredential(username)
				.orElseThrow(() -> new UsernameNotFoundException(String
						.format(USER_NOT_FOUND_MSG, username)));
	}


	public String signUpUser(User user) {
		Boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent()
				|| userRepository.findByUsername(user.getUsername()).isPresent();
		if (userExists) {
			throw new IllegalStateException("email or username already taken");
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		
		
		return confirmationTokenService.createConfirmationToken(user);
		
	}
	
	
	public String getNewToken(User user) {
		return confirmationTokenService.createNewToken(user);
		
	}
	
	
	public int enableUser(String email) {
        return userRepository.enableUser(email);
    }


	public User getUser(String username) {
		return userRepository.findByUsername(username).get();
	}
	
	
	
}
