<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Books</title>
<style><%@include file="/WEB-INF/css/style.css"%></style> 
</head>
<body>
		
	<h2>All Books</h2>
	
	<c:if test="${cart !=null }">
		<a href="BookServlet?action=viewCart">View Shopping Cart</a>
		<br></br>	
	</c:if>
	
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Title</th>
				<th>Author</th>
				<th>Description</th>
				<th>Price</th>
				<th>Update</th>
				<th>Delete</th>
				<th>Buy</th>
			</tr>
				<c:set var="found" value="notFound"/>
				<c:forEach var="book" items="${listOfBooks}" varStatus="status">
			<tr>
			<c:set var="found" value="found"/>
				<td>${book.id}</td>
				<td>${book.title}</td> 
				<td>${book.author}</td>
				<td>${book.description}</td>
				<td>&euro;&nbsp;&nbsp;<fmt:formatNumber type="number"
			 		maxFractionDigits="2" value="${book.price}" /></td>
				<td>
				<a href="BookServlet?action=showUpdateForm&bookID=${book.id}">Update</a>  
				</td>
				<td>
				<!--<a href="BookServlet?action=delete&bookID=${status.index}">Delete</a> -->
				<a href="BookServlet?action=delete&bookID=${book.id}">Delete</a> 
				</td>
				<td>
				<a href="BookServlet?action=addToCart&bookID=${book.id}">Buy</a>  
				</td>
			</tr>
			</c:forEach>
		</table>
		<c:if test="${found =='notFound'}">
			<h3>Book ${searchType} ${search} not found</h3>
		</c:if>	
	<br><br>
	<a href="BookServlet?action=showSearchForm">Search Book</a>	
	<br><br>
	<a href="BookServlet?action=showInsertForm">Insert New Book</a>
		<br><br>
	<a href="BookServlet?action=viewAll">View All Books</a>		
</body>
</html>