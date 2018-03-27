<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert New Book</title>
<style><%@include file="/WEB-INF/css/style.css"%></style> 
</head>
<body>
	<h2>Insert details for a book</h2>
	
	<form action="BookServlet?action=insertNewBook" method="Post">
		<p>Title</p>
		<p><input type="text" name="title" placeholder="Title" required/></p>
		<p>Author</p>
		<p><input type="text" name="author" placeholder="Author" required/></p>
		<p>Description</p>
		<p><textarea rows="4" cols="50" name="description" placeholder="description"></textarea></p>
		<p>Price</p>
		<p><input type="text" name="price" placeholder="Price" required/></p>
		<p><input type="submit" value="Insert"/></p>
	</form>
</body>
</html>