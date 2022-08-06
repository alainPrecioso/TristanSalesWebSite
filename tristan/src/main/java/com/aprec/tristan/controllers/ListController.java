package com.aprec.tristan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ListController {

	@PostMapping("/search")
	public String searchSubmit(Model model) {
		model.addAttribute("result", "pas de resultats");
		return "index.html";
	}
	
	

}
