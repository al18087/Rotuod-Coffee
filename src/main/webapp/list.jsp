<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<title>menu</title>
</head>
<body class="container">
	<header>
		<h1 class="display-4">メニュー</h1>
	</header>
	<br>
	<hr>
	<c:forEach items="${items}" var="item">
		<form action="/drinkShopping/CartServlet?action=add" method="post">
			<table class="table">
				<tr>
					<th>${item.name}</th>
					<td>￥${item.price}</td>
					<td><select name="quantity">
							<option value="1">1
							<option value="2">2
							<option value="3">3
							<option value="4">4
							<option value="5">5
					</select></td>
					<td><input type="submit" value="カートに追加"
						class="btn btn-primary"> <input type="hidden"
						name="product_id" value="${item.productId}"></td>
				</tr>
			</table>
			<input type="hidden" name="product_id" value="${item.productId}">
		</form>
	</c:forEach>

</body>
</html>