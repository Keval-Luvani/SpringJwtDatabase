package com.keval.JwtWithDatabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keval.JwtWithDatabase.model.User;
import com.keval.JwtWithDatabase.service.UserService;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userServiceImpl; 
	
	@RequestMapping("/view")
	public String viewUsers(Model model) {
		model.addAttribute("userList",userServiceImpl.getUsers());
		return "../ViewUser.jsp";
	}
	
	@RequestMapping("/register")
	public String createPage(Model model) {
		model.addAttribute("user",new User());
		return "../UserEntry.jsp";
	}
	
	@RequestMapping("/update{userId}")
	public String updatePage(Model model,@PathVariable int userId) {
		model.addAttribute("user",userServiceImpl.getUser(userId));
		return "../UserEntry.jsp";
	}
	
	@RequestMapping("/delete{userId}")
	public String deleteUser(@PathVariable int userId) {
		userServiceImpl.deleteUser(userId);
		return "redirect:/user/view";
	}
	
	@RequestMapping(value="/submit", method= RequestMethod.POST)
	public String createUser(@ModelAttribute() User user,Model model) {
		if(user.getId()==0) {
			if(userServiceImpl.getUsers().stream().anyMatch(num -> num.getEmail().equals(user.getEmail()))) {
				model.addAttribute("error","user already exists");
				return "../UserEntry.jsp";
			}
			userServiceImpl.createUser(user);
		}else {
			userServiceImpl.updateUser(user);
		}
		return "redirect:/user/view";
	}
}
