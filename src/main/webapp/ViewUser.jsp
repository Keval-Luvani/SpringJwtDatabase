<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
   	<head>
       <title>Users</title>
    </head>
    <body>
       <%@ include file="/logout.jsp" %>
       <h1>User Data</h1>
       <table BORDER="1">
	      <tr>
		      <th>ID</th>
		      <th>Name</th>
		      <th>Email</th>
		      <th>Role</th>
		      <th>Action</th>
	      </tr>
	      <c:forEach items="${userList}" var="user" varStatus="row">
	      	<c:if test="${!user.getRole().equals('ROLE_ADMIN')}">
	    		<tr>
	        	   <td>${row.count}</td>
			       <td>${user.getName()}</td>	
			       <td>${user.getEmail()} </td>
			       <td>${user.getRole()} </td>
			       <td>
			       	 <a href='<c:url value="update${user.getId()}"></c:url>'>Update</a>
			         <a href='<c:url value="delete${user.getId()}"></c:url>'>Delete</a>
			       </td>  
	    		</tr>
    		</c:if>
		  </c:forEach>
	   </table>
       <a href='<c:url value="register"></c:url>'>Register User</a>	
     </body>
</html>