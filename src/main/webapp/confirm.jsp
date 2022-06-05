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
<title>Confirm</title>
</head>
<body class="container">
	<header>
		<h1 class="display-4">注文確認画面</h1>
	</header>

	<table class="table">
		<tr>
			<th>商品名</th>
			<th>単価</th>
			<th>個数</th>
			<th>小計</th>
		</tr>
		<c:forEach items="${cart.products}" var="item">
			<tr>
				<td>${item.name}</td>
				<td>${item.price}</td>
				<td>${item.quantity}</td>
				<td>${item.price * item.quantity}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5">合計 : ${cart.total}円</td>
		</tr>
	</table>

	<form action="/drinkShopping/OrderServlet?action=order" method="post">
		<input type="submit" value="注文確定" class="btn btn-primary">
	</form>

</body>
</html>