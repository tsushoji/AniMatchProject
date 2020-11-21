<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

	<head>
		<title>login</title>

		<%@ include file="head1.jsp" %>

		<link rel="stylesheet" type="text/css" href="/animatch/styles/login.css" />
    </head>

	<body class="text-center d-flex flex-column justify-content-center py-5">
		<div class="mx-auto">

			<img class="mb-5" src="/animatch/images/animatch-icon.png" alt="Animatchロゴ">

			<form class="py-5">

		        <input type="text" class="form-control" placeholder="ユーザー名">
		        <input type="password" class="form-control" placeholder="パスワード">

				<div class="text-left mt-2">
		        	<input type="checkbox" id="saved-username-check" class="" name="saved-username-check" value="1">
		            <label for="saved-username-check" class="saved-username-check-label">ユーザー名を保存する</label>
	            </div>

		        <input type="submit" class="btn btn-primary form-control" value="ログイン">

	    	</form>

    	</div>

    	<%@ include file="footer2.jsp" %>
	</body>

</html>