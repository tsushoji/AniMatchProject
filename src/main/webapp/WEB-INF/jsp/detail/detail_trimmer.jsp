<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

	<head>
		<title>detail</title>

		<%@ include file="/WEB-INF/jsp/head.jsp" %>

		<link rel="stylesheet" type="text/css" href="/animatch/styles/detail.css" />
    </head>

	<body>
		<!--ヘッダー-->
    	<header>
			<jsp:include page="/WEB-INF/jsp/header.jsp"/>
		</header>

	    <!--メイン-->
	    <main>

	        <div class="container">

	            <div class="row my-1 mx-auto">

	                <div class="col-sm-5 offset-sm-1 main-details-left">

	                    <img src="" alt="新規会員登録した画像">

	                </div>

	                <div class="col-sm-6 main-search-right">

	                    <h3 class="mt-2">新規会員登録したペットニックネーム</h3>

	                    <h6 class="mt-3">都道府県市町村&#058;</h6>

	                    <p class="mt-1 pb-2 main-search-right-underline">新規会員登録した都道府県&gt;新規会員登録した市町村</p>

	                    <h6 class="mt-2">ペットの性別&#058;</h6>

	                    <p class="mt-1 pb-2 main-search-right-underline">ペットの性別</p>

	                    <h6 class="mt-2">ペットの種別&#058;</h6>

	                    <p class="mt-1 pb-2 main-search-right-underline">ペットの種別</p>

	                    <h6 class="mt-2">ペットの体重&#058;</h6>

	                    <p class="mt-1 pb-2 main-search-right-underline">ペットの体重</p>

	                    <h6 class="mt-2">備考&#058;</h6>

	                    <p class="mt-1 pb-2 main-search-right-underline">備考</p>

	                </div>

	            </div>

	            <div class="row mt-3 pb-3">

	                <div class="col text-center">

	                    <div class="form-group form-common">
	                        <input type="submit" class="btn btn-primary is-show-dmessage-trimmer" value="飼い主へダイレクトメッセージ">
	                    </div>

	                </div>

	            </div>

	        </div>


	    </main>

	    <!--フッター-->
	    <footer>
	        <div class="d-flex justify-content-end pr-3 footer-top">
	            <a class="move-page-top" href="#">
	                <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン"> <strong>AniMatchの上部へ戻る</strong>
	            </a>
	        </div>

	        <%@ include file="/WEB-INF/jsp/footer01.jsp" %>

	        <%@ include file="/WEB-INF/jsp/footer02.jsp" %>
	    </footer>
	    <script src="/animatch/scripts/detail.js"></script>
		<script src="/animatch/scripts/common.js"></script>
	</body>

</html>