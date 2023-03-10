package com.keval.JwtWithDatabase.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.keval.JwtWithDatabase.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
	public User getByEmail(String email);
}
