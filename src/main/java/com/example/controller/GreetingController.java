package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller that demonstrates tiles mapping, reguest parameters and path variables.
 * 
 * @author KhangNT
 */
@Controller
public class GreetingController extends AbstractController {
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String index(Model model) {
    	log.info("Return Home Page");
    	model.addAttribute("roles", getAuthoritys());
    	return "redirect:/home";
	}
	
    @RequestMapping(value = "/home", method=RequestMethod.GET)
	public String home(Model model) {
    	log.info("Return Home Page");
    	model.addAttribute("roles", getAuthoritys());
	    return "index";
	}
}
