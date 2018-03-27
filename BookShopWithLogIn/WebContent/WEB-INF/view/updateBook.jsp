<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Book</title>
<style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
<h2>Updating ${book.title}</h2>

	<form action="BookServlet?action=updateBook" method="POST">
	<input type="hidden" name="bookID" value="${book.id}">
	
	<p>Title:</p> 
	<p><input type="text" name="title" value="${book.title}" size="49"></p> 
	
	<p>Author:</p> 
	<p><input type="text" name="author" value="${book.author}" size="49"></p> 
	
	<p>Description:</p> 
	<p><textarea rows="5" cols="50" name="description">${book.description}</textarea></p> 

	<p>Price:</p> 
	<p><input type="text" name="price" 
		value="<fmt:formatNumber type='number' maxFractionDigits='2' 
			value='${book.price}'/>"/>
	</p>
	 
	<p><input type="submit" value="Submit"/></p>
	</form> 
</body>
</html>