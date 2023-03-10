package com.keval.JwtWithDatabase.dao;

import java.util.List;

import com.keval.JwtWithDatabase.model.User;

public interface UserDao {
	public List<User> getUsers();
	public User getUser(int userId);
	public void createUser(User user);
	public void updateUser(User user);
	public void deleteUser(int userId);
}
