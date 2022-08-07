package com.aprec.tristan.users;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	
	private final static String USER_NOT_FOUND_MSG = "user %s not found";
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
	}

	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}


	public String signUpUser(User user) {
		Boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent()
				|| userRepository.findByUsername(user.getUsername()).isPresent();
		if (userExists) {
			return "user exists";
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		return "user saved";
		
	}
	
	public String logInUser(String credential, String password) {
		UserDetails user = loadUserByUsername(credential);
		if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
			return "log";
		}
		
		
		return "wrong credentials";
		
	}
	
	
	
}
