package com.aprec.tristan.user.registration;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationRestController {
	private RegistrationService registrationService;

	
	
	public RegistrationRestController(RegistrationService registrationService) {
		super();
		this.registrationService = registrationService;
	}



	@PostMapping(path = "/add")
	public String register(@RequestBody RegistrationRequest request) {
		return registrationService.register(request);
	}

}
