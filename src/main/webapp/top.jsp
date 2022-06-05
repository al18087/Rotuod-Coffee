<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<title>Category Page</title>
</head>
<body class="container">
	<header>
		<h1 class="display-4">Welcome to Rotuod Coffee!</h1>
	</header>
	<h3>購入したいカテゴリを選択してください</h3>
	<br>
	<hr>
	<c:forEach items="${categories}" var="category">
		<a href="/drinkShopping/ShowItemServlet?action=list&id=${category.id}">${category.name}</a> | 
</c:forEach>
</body>
</html>