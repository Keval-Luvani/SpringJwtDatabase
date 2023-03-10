package com.keval.JwtWithDatabase.dao;

import java.util.List;

import com.keval.JwtWithDatabase.model.Assignment;

public interface AssignmentDao {
	public List<Assignment> getAssignments();
	public Assignment getAssignment(int assignmentId);
	public void createAssignment(Assignment assignment);
	public void updateAssignment(Assignment assignment);
	public void deleteAssignment(int assignmentId);
	public void deleteAssignments(int createdBy); 
}
