<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Insert Data</title>
	</head>
	<body>	
		<form:form action="submit" method="post" modelAttribute="assignment">
			<form:input path="id" type="hidden" />
			<form:label path="title">Title</form:label>
			<form:input path="title" required="required"/><br>
			
			<form:label path="score" >score</form:label>
			<form:input path="score" type="number" required="required"/><br>
			
			<form:input path="createdBy" type="hidden" required="required"/>
			
			<form:label path="dueDate">Due Date</form:label>
			<form:input path="dueDate" type="date" min="${todayDate}" required="required" /><br>
			
			<form:button>Submit</form:button>
		</form:form>
	</body>
</html>