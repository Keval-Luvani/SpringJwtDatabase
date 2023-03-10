package com.keval.JwtWithDatabase.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keval.JwtWithDatabase.model.Assignment;
import com.keval.JwtWithDatabase.model.User;
import com.keval.JwtWithDatabase.model.UserDetailImpl;
import com.keval.JwtWithDatabase.service.AssignmentService;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {
	@Autowired
	AssignmentService assignmentServiceImpl; 
	
	@RequestMapping("/view")
	public String viewAssignment(Model model) {
		model.addAttribute("assignmentList",assignmentServiceImpl.getAssignments());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
	    User user = userDetails.getUser();
	    if((user.getRole().equals("ROLE_TEACHER"))) {
	    	return "../ViewAssignment.jsp";
	    }
		return "../StudentAssignment.jsp";
	}
	
	@PreAuthorize("hasRole('TEACHER')")
	@RequestMapping("/create")
	public String createPage(Model model) {
		model.addAttribute("todayDate",LocalDate.now().toString());
		model.addAttribute("assignment",new Assignment());
		return "../AssignmentEntry.jsp";
	}
	
	@PreAuthorize("hasRole('TEACHER')")
	@RequestMapping("/update{assignmentId}")
	public String updatePage(Model model,@PathVariable int assignmentId) {
		model.addAttribute("assignment",assignmentServiceImpl.getAssignment(assignmentId));
		return "../AssignmentEntry.jsp";
	}
	
	@PreAuthorize("hasRole('TEACHER')")
	@RequestMapping("/delete{assignmentId}")
	public String Assignment(@PathVariable int assignmentId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
	    User user = userDetails.getUser();
	    if(!(user.getId()==assignmentServiceImpl.getAssignment(assignmentId).getCreatedBy())) {
	    	return "../error.jsp";
	    }
		assignmentServiceImpl.deleteAssignment(assignmentId);
		return "redirect:/assignment/view";
	}
	
	@PreAuthorize("hasRole('TEACHER')")
	@RequestMapping(value="/submit", method= RequestMethod.POST)
	public String createAssignment(@ModelAttribute() Assignment assignment) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
	    User user = userDetails.getUser();
		if(assignment.getId()==0) {
			assignment.setCreatedBy(user.getId());
			assignmentServiceImpl.createAssignment(assignment);
		}else {
			if(!(user.getId()==assignment.getCreatedBy())) {
		    	return "../error.jsp";
			}
			assignmentServiceImpl.updateAssignment(assignment);
		}
		return "redirect:/assignment/view";
	}
}
