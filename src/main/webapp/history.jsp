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
<title>history</title>
</head>
<body class="container">
	<header>
		<h1 class="display-4">購入履歴</h1>
	</header>
	<c:if test="${empty history}">
	まだ購入していません。
	</c:if>
	
	<c:forEach items="${history}" var="items">
		<table class="table">
			<c:forEach items="${items}" var="item">
				<tr>
					<td>${item.name}</td>
					<td>${item.price}</td>
					<td>${item.quantity}</td>
					<td>${item.price * item.quantity}</td>
				</tr>
			</c:forEach>
		</table>
		<hr>
	</c:forEach>
</body>
</html>