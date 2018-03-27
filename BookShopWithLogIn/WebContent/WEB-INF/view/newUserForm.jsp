<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
<style><%@include file="/WEB-INF/css/style.css"%></style>
<!--  <script src="/WEB-INF/JS/validatePassword.js"></script>-->
</head>
<body>
	
	<h2>Enter New User Details Details</h2>

	<form action="LoginServlet?action=createUser"  method="Post">
	<p>
	User Name<br></br>
	<input type="text" name="newUserName" placeholder="Type username" required/>
	<br></br>Password<br></br>
	<input type="text" id="txtPassword" name = "password" placeholder="Type password" required/>
	<br></br>Re-type<br></br>	
	<input type="text" id="txtConfirmPassword" name = "confirmPassword" placeholder="Confirm password" required/>
	<br></br>
	
	<input type="submit" id = "btnSubmit" value="Create new user" />	
	</p>
	
	<c:if test="${check =='exist'}">
		<p>User Name "${userName}" Already Exist</p>
	</c:if>
	
	<c:if test="${check =='passwordNotMatch'}">
		<p>Password Do Not Match Please Retype password</p>
	</c:if>
	
	</form>	
		
</body>
</html>