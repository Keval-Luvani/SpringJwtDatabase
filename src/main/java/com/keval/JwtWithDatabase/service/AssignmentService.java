package com.keval.JwtWithDatabase.service;

import java.util.List;

import com.keval.JwtWithDatabase.model.Assignment;

public interface AssignmentService {
	public List<Assignment> getAssignments();
	public Assignment getAssignment(int assignmentId);
	public void createAssignment(Assignment assignment);
	public void updateAssignment(Assignment assignment);
	public void deleteAssignment(int assignmentId);
	public void deleteAssignments(int createdBy);
}
