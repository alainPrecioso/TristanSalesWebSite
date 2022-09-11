package com.aprec.tristan.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aprec.tristan.user.registration.token.ConfirmationTokenService;
import com.aprec.tristan.user.token.PasswordTokenService;

@Service
public class UserService implements UserDetailsService {

	
	private static final String USER_NOT_FOUND_MSG = "user %s not found";
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	private final PasswordTokenService passwordTokenService;
	
	public UserService(UserRepository userRepository, 
			BCryptPasswordEncoder bCryptPasswordEncoder, 
			ConfirmationTokenService confirmationTokenService,
			PasswordTokenService passwordTokenService
			) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.confirmationTokenService = confirmationTokenService;
		this.passwordTokenService = passwordTokenService;
	}

	//actually loads user by either username or email
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository
				.findByCredential(username)
				.orElseThrow(() -> new UsernameNotFoundException(String
						.format(USER_NOT_FOUND_MSG, username)));
	}

	
	/**
	 * saves the user if it doen't exists yet with a corresponding ConfirmationToken
	 * @param User
	 * @return the token of the ConfirmationToken
	 */
	public String signUpUser(User user) {
		Boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent()
				|| userRepository.findByUsername(user.getUsername()).isPresent();
		if (userExists) {
			//throw new IllegalStateException("userexists");
			return "userexists";
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		
		
		return confirmationTokenService.createConfirmationToken(user);
		
	}
	
	
	/**
	 * calls the refresh of the ConfirmationToken of an User
	 * @param user
	 * @return the token of the ConfirmationToken
	 */
	public String getNewToken(User user) {
		return confirmationTokenService.refreshConfirmationToken(user);
		
	}
	
	
	public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

	
	public User getUser(String credential) {
		return userRepository.findByCredential(credential).orElseThrow(() -> new UsernameNotFoundException(String
				.format(USER_NOT_FOUND_MSG, credential)));
	}
	

	public void updatePassword(User user, String password) {
		userRepository.updatePassword(bCryptPasswordEncoder.encode(password), user.getId());
		
	}


	public boolean checkPassword(String username, String rawPassword) {
		String encodedPassword = getUser(username).getPassword();
		return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	}
	
	public void deleteUser(String username) {
		passwordTokenService.delete(getUser(username));
		confirmationTokenService.delete(getUser(username));
		userRepository.delete(getUser(username));
	}

	
}
