<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	crossorigin="anonymous">
<title>register</title>
</head>
<body class="container">
	<header>
		<h1 class="display-4">新規登録</h1>
	</header>
	<form action="/drinkShopping/LoginServlet?action=register"
		method="post">
		<table class="table">
			<tr>
				<th>名前(氏名)</th>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<th>誕生日(西暦のみ)</th>
				<td><input type="number" name="year"></td>
			</tr>
			<tr>
				<th>電話番号</th>
				<td><input type="text" name="tel"></td>
			</tr>
			<tr>
				<th>郵便番号</th>
				<td><input type="text" name="post_code"></td>
			</tr>
			<tr>
				<th>メールアドレス</th>
				<td><input type="text" name="address"></td>
			</tr>
			<tr>
				<th>ユーザ名(ログイン時に必要)</th>
				<td><input type="text" name="user"></td>
			</tr>
			<tr>
				<th>パスワード(ログイン時に必要)</th>
				<td><input type="password" name="password"></td>
			</tr>
		</table>
		<br> <input type="submit" value="登録" class="btn btn-primary">
	</form>

</body>
</html>