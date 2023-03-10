package com.keval.JwtWithDatabase.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.keval.JwtWithDatabase.model.Assignment;

@Component
public interface AssignmentReposiroty extends JpaRepository<Assignment, Integer> {

	public void deleteByCreatedBy(int createdBy);

}
