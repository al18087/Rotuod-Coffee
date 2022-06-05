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
<title>login</title>
</head>
<body class="container">
	<header>
		<h1 class="display-4">ログイン</h1>
	</header>

	<form action="/drinkShopping/LoginServlet?action=login" method="post">
		<table class="table">
			<tr>
				<th>ユーザ名</th>
				<td><input type="text" name="user"></td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<th><input type="submit" value="ログイン" class="btn btn-primary"></th>
			</tr>
		</table>
	</form>
	<br>
	<h4>
		<a href="/drinkShopping/register.jsp">新規登録</a>
	</h4>

</body>
</html>