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

	@PostMapping("/newpass")
	public String register(@RequestParam String email, Model model) {
		
		
		
		
		return "index";
	}
	
	@PostMapping("/add")
	public String register(@Valid @ModelAttribute("request") RegistrationRequest request, Model model) {
		
		
		model.addAttribute("message", registrationService.register(request));
		
		
		return "index";
	}
	
	@GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token, Model model) {
        registrationService.confirmToken(token);
        model.addAttribute("message", registrationService.confirmToken(token));
        return "index";
    }

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/forgot")
	String forgotPassword(Model model) {
		model.addAttribute("request", new RegistrationRequest());
		return "new pass/forgot";
	}

	@PostMapping("/passrequest")
	public String newPasswordRequest(@RequestParam String email, Model model) {
		model.addAttribute("request", new RegistrationRequest());
		model.addAttribute("message", registrationService.requestNewPassword(email));
		return "index";
	}

	@GetMapping("/enternewpass")
	public String enterNewPassword(Model model) {
		model.addAttribute("request", new RegistrationRequest());
		model.addAttribute("passrequest", new PasswordRequest());
		return "new pass/newpass";
	}
	
	@PostMapping("/savenewpass")
	public String saveNewPassword(@Valid @ModelAttribute("passrequest") PasswordRequest request, Model model) {
		model.addAttribute("request", new RegistrationRequest());
		model.addAttribute("message", registrationService.updatePassword(request));
		return "login";
	}
	
}
