package com.aprec.tristan.registration;

import org.springframework.stereotype.Service;

import com.aprec.tristan.users.User;
import com.aprec.tristan.users.UserRole;
import com.aprec.tristan.users.UserService;

@Service
public class RegistrationService {
	
	private final UserService userService;
	
	
	
	
	
	public RegistrationService(UserService userService) {
		super();
		this.userService = userService;
	}





	public String register(RegistrationRequest request) {
		
		return userService.signUpUser(
			new User(request.getUsername(), request.getEmail(), request.getPassword(), UserRole.USER));
	}

}
