<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert Data</title>
</head>
<body>
	<form:form action="/login" method="post" modelAttribute="user">
		<form:label path="email">email</form:label>
		<form:input path="email" type="email" required="required" /><br>
		
		<form:label path="password">password</form:label>
		<form:input path="password" type="password" required="required" /><br>
	
		<form:button>Login</form:button>
	</form:form>
</body>
</html>