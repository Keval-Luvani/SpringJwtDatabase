package com.keval.JwtWithDatabase.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.keval.JwtWithDatabase.helper.JwtUtil;
import com.keval.JwtWithDatabase.service.UserDetailService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private UserDetailService userDetailService; 
 	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
        String authorizationToken="";
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("jwtToken")) {
	                authorizationToken = cookie.getValue();
	            }
	        }
	    }
		
		String username = null;
		String jwtToken = null;
		
		if(authorizationToken!=null && authorizationToken.startsWith("Bearer")) {
			jwtToken = authorizationToken.substring(6);
			try { 
				username = this.jwtUtil.extractUsername(jwtToken);
			}catch (Exception e) {
				e.printStackTrace();
			}
			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {	
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}else {
				System.out.println("token is not valid");
			}
		}
		filterChain.doFilter(request, response);
	}
}
