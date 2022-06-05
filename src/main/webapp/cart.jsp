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
<title>Cart Infomation</title>
</head>
<body class="container">
	<header>
		<h1 class="display-4">カート情報</h1>
	</header>

	<c:if test="${empty cart.products}">
	現在、カートは空です。
	</c:if>

	<c:if test="${not empty cart.products}">
		<table class="table">
			<tr>
				<th>商品名</th>
				<th>単価</th>
				<th>個数</th>
				<th>小計</th>
				<th>削除</th>
			</tr>
			<c:forEach items="${cart.products}" var="item">
				<tr>
					<td>${item.name}</td>
					<td>${item.price}</td>
					<td>${item.quantity}</td>
					<td>${item.price * item.quantity}</td>
					<td>
						<form action="/drinkShopping/CartServlet?action=delete"
							method="post">
							<input type="hidden" name="item_product_id"
								value="${item.productId}"> <input type="submit"
								value="削除" class="btn btn-primary">
						</form>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5">合計 : ${cart.total}円</td>
			</tr>
		</table>

		<form action="/drinkShopping/ConfirmServlet?action=confirm"
			method="post">
			<input type="submit" value="確認画面" class="btn btn-primary">
		</form>
	</c:if>

</body>
</html>