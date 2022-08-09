package com.aprec.tristan.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aprec.tristan.users.User;
import com.aprec.tristan.users.UserRepository;

@Controller
@RequestMapping(path = "/")
public class RegistrationController {

//	@Autowired
//	private UserRepo userRepo;
	private UserRepository userRepository;
	private RegistrationService registrationService;

	public RegistrationController(UserRepository userRepository, RegistrationService registrationService) {
		super();
		this.userRepository = userRepository;
		this.registrationService = registrationService;
	}

	@PostMapping(path = "/add")
	public String register(@RequestParam(required = true) String username,
			@RequestParam(required = true) String email, @RequestParam String password, Model model) {
		if (isBlankString(username) || isBlankString(email)) {
			return "index";
		}
		
		
		model.addAttribute("error", registrationService.register(
				new RegistrationRequest(username, email, password)
		));
		
		
		return "index";
	}
	
	@GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return "index";
    }

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	
	boolean isBlankString(String string) {
	    return string == null || string.isBlank();
	}

}
