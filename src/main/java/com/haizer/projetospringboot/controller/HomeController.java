package com.haizer.projetospringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String index(Model model)
	{
		try {
			model.addAttribute("msnBemVindo", "Seja Bem Vindo");
			
			return "publica-index";
		}
		catch(Exception e) {
			return e.getMessage();
		}
		
	}
}
