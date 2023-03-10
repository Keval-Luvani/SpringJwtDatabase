package com.keval.JwtWithDatabase.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keval.JwtWithDatabase.helper.JwtUtil;
import com.keval.JwtWithDatabase.model.User;
import com.keval.JwtWithDatabase.service.UserDetailService;

@Controller
public class AuthorizationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String loginpage(Model model,HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
        String authorizationToken="";
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("jwtToken")) {
	                authorizationToken = cookie.getValue();
	            }
	        }
	    }
	    if(!authorizationToken.equals("")) {
	    	String jwtToken = authorizationToken.substring(6);
	    	String username = jwtUtil.extractUsername(jwtToken);
	    	UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
		    if(jwtUtil.validateToken(jwtToken, userDetails)) {
		    	if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			    	return "redirect:/user/view";
			    }else {
			    	return "redirect:/assignment/view";
			    }
		    }
	    }
	    model.addAttribute("user",new User());
		return "login.jsp";
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String generateToken(@ModelAttribute() User user,HttpServletResponse response) throws Exception {
	
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
		}catch(UsernameNotFoundException unf ) {
			unf.printStackTrace();
			throw new Exception("Bad Credentials");
		}catch(BadCredentialsException bd) {
			bd.printStackTrace();
			throw new Exception("Bad Credentials");
		}
	
		UserDetails userDetails = this.userDetailService.loadUserByUsername(user.getEmail());
		String token = this.jwtUtil.generateToken(userDetails);
		Cookie cookie = new Cookie("jwtToken", "Bearer"+token);
	    cookie.setMaxAge(300); // 24 hours
	    cookie.setHttpOnly(true);
	    cookie.setSecure(false); // Set to true if you're using HTTPS
	    cookie.setPath("/");
	    response.addCookie(cookie);
	    if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	    	return "redirect:/user/view";
	    }else {
	    	return "redirect:/assignment/view";
	    }
	}
	
	@RequestMapping(value = "/signout",method = RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("jwtToken")) {
	            	cookie.setMaxAge(0); // set expiry time to 0 to remove cookie
	                cookie.setPath("/");
	                response.addCookie(cookie);
	            }
	        }
	    }
		return "redirect:/login";
	}
}
