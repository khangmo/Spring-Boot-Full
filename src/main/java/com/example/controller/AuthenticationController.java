package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.Constant;
import com.example.common.JsonReturn;
import com.example.modal.UserCreateForm;
import com.example.user.imlement.UserImplement;

@Controller
public class AuthenticationController extends AbstractController{
	
	@Autowired
	UserImplement userImplement;
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) throws Exception {
		log.info("Return login page");
		model.addAttribute("clientId", facebookAppId);
		model.addAttribute("requestUrl", requestUrl);
		model.addAttribute("rootUrl", rootUrl);
	    return "login";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/user-manager", method = RequestMethod.GET)
    public String getUserCreatePage(Model model) throws Exception {
		log.info("Listing Users");
		
		pagingUser(1, model);
        return "user-manager";
    }
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/user-manager/p{page}", method = RequestMethod.GET)
	public String getUserCreatePage(Model model, @PathVariable(value = "page") Integer page) throws Exception {
		log.info("Listing Users:" + page);
		pagingUser(page, model);
        return "user-manager";
	}
	
	public void pagingUser (int page, Model model) throws Exception {
		long sumUsers = userImplement.countingUser();
		double nrOfPages = (double) sumUsers / Constant.PAGING.SIZE_ON_PAGE;
		long pages = (long)Math.ceil(nrOfPages == 0 ? nrOfPages + 1 : nrOfPages);
		
		model.addAttribute("users", userImplement.getAllUsers(page - 1));
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", page);
		model.addAttribute("roles", getAuthoritys());
	}

	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/create-user", method = RequestMethod.POST)
	public String createUser(Model model, UserCreateForm form) {
		log.info("Create New User");
		try {
			userImplement.createNewUser(form);
			model.addAttribute("result", Constant.MESSAGE.CREATE_USER_SUCCESS);
		} catch (Exception ex) {
			log.error("Create User Fail", ex);
			model.addAttribute("result", Constant.MESSAGE.CREATE_USER_FAIL);
		}
		model.addAttribute("roles", getAuthoritys());
		return "redirect:/user-manager";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/delete-users", method = RequestMethod.POST) 
	public String deleteUsers (Model model, @RequestParam(value = "users", required = false) String[] users) throws Exception {
		log.info("Delete Users:");
		if (users != null) {
			for (String user: users) {
				log.info(user);
				userImplement.deleteUserByUsername(user);
			}
		}
		model.addAttribute("roles", getAuthoritys());
		return "redirect:/user-manager";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/rollback-users", method = RequestMethod.POST)
	public String rollbackUsers (Model model, @RequestParam(value = "users", required = false) String[] users) throws Exception {
		log.info("Rollback Users:");
		if (users != null) {
			for (String user: users) {
				log.info(user);
				userImplement.rollbackUserByUsername(user);
			}
		}
		model.addAttribute("roles", getAuthoritys());
		return "redirect:/user-manager";
	}
	
	@RequestMapping(value = "/update-user", method = RequestMethod.GET)
	public String updateUser(Model model) {
		log.info("Get update view");
		model.addAttribute("roles", getAuthoritys());
		return "update-user";
	}
	
	@RequestMapping(value = "/update-user", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String updateUser (Model model, 
			@RequestParam(value = "currentPassword", required = true) String currentPassword,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "passwordRepeated", required = true) String passwordRepeated) throws Exception {
		log.info("Update user:" + getUser());
		JsonReturn json = new JsonReturn();
		
		if (currentPassword == null || password == null || passwordRepeated == null) {
			json.setCode(0).setMessage(Constant.MESSAGE.INFO_UPDATE_ERROR);
			return json.toString();
		}
		
		if (!password.equals(passwordRepeated)) {
			json.setCode(1).setMessage(Constant.MESSAGE.PASSWORD_DID_NOT_MATCH);
			return json.toString();
		}

		try {
			userImplement.updateUser(currentPassword, password);
			json.setCode(200).setMessage(Constant.MESSAGE.UPDATE_SUCCESS);
		} catch (AuthenticationException au) {
			json.setCode(4).setMessage(au.getMessage());
		}
		model.addAttribute("roles", getAuthoritys());
		return json.toString();
	}
	
	/**
	 * Used to authenticate base on BASIC AUTHEN
	 * @param model
	 * @return JSON type
	 */
	@RequestMapping(value = "/authen", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String authen(Model model) {
		JsonReturn json = new JsonReturn();
		json.setCode(0).setMessage("Basic authen success.");
		return json.toString();
	}
}
