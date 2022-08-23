package com.aprec.tristan.user.registration;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aprec.tristan.user.User;
import com.aprec.tristan.user.UserRepository;

@Validated
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

	@PostMapping("/add")
	public String register(@RequestParam String username,
			@RequestParam String email, @RequestParam String password, String passwordcheck, Model model) {
		
		
		model.addAttribute("error", registrationService.register(
				new RegistrationRequest(username, email, password, passwordcheck)
		));
		
		
		return "index";
	}
	
	@PostMapping("/addjson")
	public String registerObject(@Valid @ModelAttribute("request") RegistrationRequest request, Model model) {
		
		
		model.addAttribute("error", registrationService.register(request));
		
		
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
