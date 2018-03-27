<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
<style><%@include file="/WEB-INF/css/style.css"%></style> 
</head>
<body>
	<h3>Search by Title or Author</h3>
	<form action="BookServlet?action=searchBook" method="Post">
		<p>Enter Search Text</p>
		<p>
		<input type="text" name="search" placeholder="Type here" required/>		
		<select name = "searchType">
  		<option value="title">Title</option>
  		<option value="author">Author</option>
		</select>
		<input type="submit" value="Search"/>
		</p>
	</form>
</body>
</html>