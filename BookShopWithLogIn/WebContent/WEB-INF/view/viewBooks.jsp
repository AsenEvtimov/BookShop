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
<script src="https://use.fontawesome.com/b33f21d3fe.js"></script> 
</head>
<body>
	<div class="table">	
	<h2>All Books</h2>
	<p class = "capitalyze" align = "right">
	<c:if test="${userName !=null }">
		Hello&nbsp;${userName}
		<a href="LoginServlet?action=logOut">Log Out</a>					
	</c:if>
	</p>
	<p align = "right">
	<c:if test="${cart !=null }">		
		<c:forEach var="cartItems" items="${cart}">
			<c:set var = "itemCount" value ="${itemCount+cartItems.value}"/>
			<c:set var = "total" value ="${total+cartItems.value*cartItems.key.price}"/>
		</c:forEach>
		<a href="BookServlet?action=viewCart"><i class="fa fa-shopping-cart cart" aria-hidden="true"></i> Check Out</a>
		Items:&nbsp;${itemCount}
		&nbsp;<i class="fa fa-eur" aria-hidden="true"></i>&nbsp;${total}	
	</c:if>
	</p>
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
				<a href="BookServlet?action=showUpdateForm&bookId=${book.id}"><i class="fa fa-pencil-square-o pencil" ></i></a><!-- Update --> 
				</td>
				<td>
				<a href="BookServlet?action=delete&bookId=${book.id}"><i class="fa fa-trash-o  trash" aria-hidden="true"></i></a> <!-- Delete -->
				</td>
				<td>
				<a href="BookServlet?action=buy&bookId=${book.id}">Buy</a>  
				</td>
			</tr>
			</c:forEach>
		</table>
		<c:if test="${found =='notFound'}">
			<h3>Book ${searchType} ${search} Not Found</h3>
		</c:if>	
	<br><br>
	<a href="BookServlet?action=showSearchForm">Search Book</a>	
	<br><br>
	<a href="BookServlet?action=showInsertForm">Insert New Book</a>
	<br><br>
	<a href="BookServlet?action=viewAll">View All Books</a>
	<br><br>
	<c:if test="${userName == null }">
	<a href="LoginServlet?action=viewLoginForm">Login</a>
	</c:if>	
	</div>		
</body>
</html>