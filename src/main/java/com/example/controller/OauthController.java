package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping({"/oauth"})
@Controller
public class OauthController extends AbstractController {
	
	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String getCodeFromFacebook(HttpServletRequest request, Model model,
            @RequestParam(value = "code", required = true) String code) {
		log.info ("Oauth facebook");
		try {
			if (code == null) {
				return "redirect:/login";
			}
			requestFacebook(code, model);
			
			return "oauth-facebook";
		} catch (Exception ex) {
			return "redirect:/login";
		}
	}
}
