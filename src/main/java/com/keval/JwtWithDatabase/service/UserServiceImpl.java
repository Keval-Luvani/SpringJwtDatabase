package com.keval.JwtWithDatabase.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keval.JwtWithDatabase.dao.UserDao;
import com.keval.JwtWithDatabase.model.User;

@Component
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDaoImpl;
	
	@Autowired
	AssignmentService assignmentService;
	
	public List<User> getUsers() {
		return userDaoImpl.getUsers();
	}

	public User getUser(int userId) {
		return userDaoImpl.getUser(userId);
	}

	public void createUser(User user) {
		userDaoImpl.createUser(user);
	}

	public void updateUser(User user) {
		userDaoImpl.updateUser(user);
	}

	@Transactional
	public void deleteUser(int userId) {
		userDaoImpl.deleteUser(userId);
		assignmentService.deleteAssignments(userId);
	}
}
