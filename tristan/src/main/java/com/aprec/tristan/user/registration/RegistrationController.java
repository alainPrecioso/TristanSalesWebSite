package com.aprec.tristan.user.registration;

import static com.aprec.tristan.controllers.Attribute.MESSAGE;
import static com.aprec.tristan.controllers.Attribute.REQUEST;
import static com.aprec.tristan.controllers.Attribute.PASSWORD_REQUEST;
import static com.aprec.tristan.controllers.HtmlPage.FORGOT;
import static com.aprec.tristan.controllers.HtmlPage.INDEX;
import static com.aprec.tristan.controllers.HtmlPage.LOGIN;
import static com.aprec.tristan.controllers.HtmlPage.NEW_PASSWORD;

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

import com.aprec.tristan.controllers.HtmlPage;
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
	public HtmlPage register(@RequestParam String email, Model model) {
		
		
		
		
		return INDEX;
	}
	
	@PostMapping("/add")
	public HtmlPage register(@Valid @ModelAttribute("request") RegistrationRequest request, Model model) {
		
		
		model.addAttribute(MESSAGE.getAttribute(), registrationService.register(request));
		
		
		return INDEX;
	}
	
	@GetMapping(path = "/confirm")
    public HtmlPage confirm(@RequestParam("token") String token, Model model) {
        String confirm = registrationService.confirmToken(token);
        if (!confirm.equalsIgnoreCase("confirmed")) {
        	throw new IllegalStateException(confirm);
        }
        model.addAttribute(MESSAGE.getAttribute(), confirm);
        model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
        return INDEX;
    }

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/forgot")
	HtmlPage forgotPassword(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		return FORGOT;
	}

	@PostMapping("/passrequest")
	public HtmlPage newPasswordRequest(@RequestParam String email, Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		model.addAttribute(MESSAGE.getAttribute(), registrationService.requestNewPassword(email));
		return INDEX;
	}

	@GetMapping("/enternewpass")
	public HtmlPage enterNewPassword(Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		model.addAttribute(PASSWORD_REQUEST.getAttribute(), new PasswordRequest());
		return NEW_PASSWORD;
	}
	
	@PostMapping("/savenewpass")
	public HtmlPage saveNewPassword(@Valid @ModelAttribute("passrequest") PasswordRequest request, Model model) {
		model.addAttribute(REQUEST.getAttribute(), new RegistrationRequest());
		model.addAttribute(MESSAGE.getAttribute(), registrationService.updatePassword(request));
		return LOGIN;
	}
	
}
