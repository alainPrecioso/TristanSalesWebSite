package com.aprec.webapp.user;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

import java.time.LocalDateTime;
import java.util.Optional;

import com.aprec.webapp.user.registration.token.ConfirmationTokenServiceInterface;
import com.aprec.webapp.user.token.PasswordTokenServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;


@Service
public class UserService implements UserDetailsService, UserServiceInterface {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private static final String USER_NOT_FOUND_MSG = "user %s not found";
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenServiceInterface confirmationTokenService;
	private final PasswordTokenServiceInterface passwordTokenService;
	
	public UserService(UserRepository userRepository, 
			BCryptPasswordEncoder bCryptPasswordEncoder,
			ConfirmationTokenServiceInterface confirmationTokenService,
			PasswordTokenServiceInterface passwordTokenService
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
		UserDetails userDetails = userRepository
				.findByCredential(username)
				.orElseThrow(() -> new UsernameNotFoundException(String
						.format(USER_NOT_FOUND_MSG, username)));
		RequestContextHolder.currentRequestAttributes().setAttribute("userType", "site", SCOPE_SESSION);
		return userDetails;
	}

	
	/**
	 * saves the user if it doesn't exist yet with a corresponding ConfirmationToken
	 * @param user a SiteUser
	 * @return the token of the ConfirmationToken
	 */
	@Override
	public String signUpUser(SiteUser user) throws IllegalStateException {
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent()
				|| userRepository.findSiteUserByUsername(user.getUsername()).isPresent();
		if (userExists) {
			throw new IllegalStateException("userexists");
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		
		
		return confirmationTokenService.createConfirmationToken(user);
		
	}
	
	
	/**
	 * calls the refresh of the ConfirmationToken of a User
	 * @param  user		a User
	 * @return the new token of the ConfirmationToken
	 */
	@Override
	public String getNewToken(SiteUser user) {
		return confirmationTokenService.refreshConfirmationToken(user);
		
	}
	
	@Override
	public int enableUser(String email) {
        return userRepository.enableSiteUser(email);
    }

	@Override
	public SiteUser getSiteUser(String credential) {
		return userRepository.findByCredential(credential).orElseThrow(() -> new UsernameNotFoundException(String
				.format(USER_NOT_FOUND_MSG, credential)));
	}
	
	@Override
	public void updatePassword(SiteUser user, String password) {
		userRepository.updatePassword(bCryptPasswordEncoder.encode(password), user);
		
	}

	@Override
	public boolean checkPassword(String username, String rawPassword) {
		return bCryptPasswordEncoder.matches(rawPassword, getSiteUser(username).getPassword());
	}
	
	/**
	 * Deletes the PasswordToken and ConfirmationToken of the User then the User itself
	 */
	@Transactional
	@Override
	public void deleteUser(User user) {
		if (user.getClass().equals(SiteUser.class)) {
			passwordTokenService.delete(user);
		}
		confirmationTokenService.delete(user);
		userRepository.delete(user);
	}
	
	@Override
	public void scheduleDelete() {
		//TODO change scheduled time to 30 days
		userRepository.scheduleDelete(LocalDateTime.now().plusDays(30), getLoggedUser());
	}

	@Async
	//@Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS) //for testing purposes
	@Scheduled(cron = "* * 3 * * *", zone = "Europe/Paris") //every day at 3AM
	@Override
	public void deleteUsers() {
		log.info("scheduledDelete() tick");
		userRepository.findUserByDeleteScheduledTrue().filter(user -> user.getDeleteTime().isBefore(LocalDateTime.now())).forEach(this::deleteUser);
	}
	
	/**
	 * @param username 	the username of a User
	 * @param type 		the specific type of User
	 * @return a User object that correspond to the user with that username and class
	 */
	@Override
	public User getUserWithType(String username, String type) {
		User user = null;
		if (type.equals("gitHub")) {
			user = userRepository.findGitHubUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
		}
		if (type.equals("site")) {
			user = userRepository.findSiteUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
		}
		return user;
	}
	
	/**
	 *	retrieves the username and the user type from the current request to get a User
	 * @return the currently logged User
	 */
	@Override
	public User getLoggedUser() {
		return getUserWithType(SecurityContextHolder.getContext().getAuthentication().getName(),
				(String) Optional.ofNullable(RequestContextHolder.getRequestAttributes()
						.getAttribute("userType", SCOPE_SESSION))
						.orElseThrow(() -> new IllegalStateException("user type not found")));
	}

	@Override
	public void cancelDelete() {
		userRepository.cancelDelete(getLoggedUser());
	}
}
