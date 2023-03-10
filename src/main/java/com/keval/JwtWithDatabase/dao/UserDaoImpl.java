package com.keval.JwtWithDatabase.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.keval.JwtWithDatabase.model.User;

@Component
public class UserDaoImpl implements UserDao {
	
	@Autowired
	UserRepository userRepository;
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	public List<User> getUsers() {		
		return userRepository.findAll();
	}

	public User getUser(int userId) {
		return userRepository.findById(userId).get();
	}

	public void createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public void deleteUser(int userId) {
		userRepository.deleteById(userId);
	}
}
