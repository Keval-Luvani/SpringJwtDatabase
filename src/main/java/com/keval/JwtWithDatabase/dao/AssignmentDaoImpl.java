package com.keval.JwtWithDatabase.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.keval.JwtWithDatabase.model.Assignment;

@Component
public class AssignmentDaoImpl implements AssignmentDao {
	
	@Autowired
	AssignmentReposiroty assignmentRepository;

	public List<Assignment> getAssignments() {
		return assignmentRepository.findAll();
	}

	public Assignment getAssignment(int assignmentId) {
		 return assignmentRepository.findById(assignmentId).get();
	}

	public void createAssignment(Assignment assignment) {
		assignmentRepository.save(assignment);
	}

	public void updateAssignment(Assignment assignment) {
		assignmentRepository.save(assignment);
	}

	public void deleteAssignment(int assignmentId) {
		assignmentRepository.deleteById(assignmentId);
	}

	public void deleteAssignments(int createdBy) {
		assignmentRepository.deleteByCreatedBy(createdBy);
	}

}
