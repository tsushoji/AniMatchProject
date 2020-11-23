<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

	<head>
		<title>AniMatch</title>

		<%@ include file="/WEB-INF/jsp/head.jsp" %>

		<link rel="stylesheet" type="text/css" href="/animatch/styles/index.css" />
    </head>

	<body>
		<!--ヘッダー-->
		<header>
			<jsp:include page="/WEB-INF/jsp/header.jsp"/>
		</header>

	    <!--メイン-->
	    <main>
	        <!--ジャンボトロン-->
	        <div class="jumbotron">

	        </div>

	        <!--グリッドシステム-->
	        <div class="container">
	            <div class="row">

	                <div class="col-md">

	                    <div class="row pt-4 pb-2">
	                        <div class="col-md text-center">
	                            <img src="/animatch/images/icon_beauty_salon.png" alt="お店アイコン">
	                            <h2>お店専用</h2>
	                        </div>
	                    </div>

	                    <div class="row py-2 main-center-block is-show-search-trimmer">
	                        <div class="col-md main-center">
	                            <div class="row">
	                                <div class="col-md text-center">
	                                    <h3 class="main-center-header-content">
	                                        &lt;&lt;ペット検索&gt;&gt;
	                                    </h3>
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-md p-0">
	                                    <figure class="col-md p-0 text-center">
	                                        <img class="main-center-explation-img-content" src="/animatch/images/pets.png" alt="飼い主検索補足画像">
	                                    </figure>
	                                </div>
	                                <div class="col-lg p-0">
	                                    <p class="main-center-explation-content">身近にあなたを必要としているペットを探してみましょう!</p>
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-md text-center">
	                                    <figure>
	                                        <a class="btn btn-primary btn-sm" href="/animatch/search/trimmer" role="button">飼い主を探す<img src="/animatch/images/icon_search.png" alt="検索アイコン"></a>
	                                    </figure>
	                                </div>
	                            </div>
	                        </div>
	                    </div>

	                    <div class="row pt-2 pb-4 main-center-block is-show-dmessage">
	                        <div class="col-md main-center">
	                            <div class="row">
	                                <div class="col-md text-center">
	                                    <h3 class="main-center-header-content">
	                                        &lt;&lt;飼い主へメッセージ&gt;&gt;
	                                    </h3>
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-md p-0">
	                                    <figure class="col-lg p-0 text-center">
	                                        <img class="main-center-explation-img-content" src="/animatch/images/message.png" alt="飼い主へダイレクトメッセージ補足画像">
	                                    </figure>
	                                </div>
	                                <div class="col-lg p-0">
	                                    <p class="main-center-explation-content">実際にトリミングしたいペットの飼い主と連絡して、マッチングしてみましょう!</p>
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-md text-center">
	                                    <figure>
	                                        <a class="btn btn-primary btn-sm" href="/animatch/dmessage/contact" role="button">ダイレクトメッセージ<img src="/animatch/images/icon_direct_message.png" alt="ダイレクトメッセージアイコン"></a>
	                                    </figure>
	                                </div>
	                            </div>
	                        </div>
	                    </div>

	                </div>

	                <div class="col-md">

	                    <div class="row pt-4 pb-2">
	                        <div class="col-md text-center">
	                            <img src="/animatch/images/icon_pad.png" alt="犬アイコン">
	                            <h2>飼い主専用</h2>
	                        </div>
	                    </div>

	                    <div class="row py-2 main-center-block is-show-search-owner">
	                        <div class="col-md main-center">
	                            <div class="row">
	                                <div class="col-md text-center">
	                                    <h3 class="main-center-header-content">
	                                        &lt;&lt;お店検索&gt;&gt;
	                                    </h3>
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-md p-0">
	                                    <figure class="col-lg p-0 text-center">
	                                        <img class="main-center-explation-img-content" src="/animatch/images/trimmer01.png" alt="お店検索補足画像">
	                                    </figure>
	                                </div>
	                                <div class="col-lg p-0">
	                                    <p class="main-center-explation-content">身近にあなたの役に立ちたいと思っているトリマーのお店を探してみましょう!</p>
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-md text-center">
	                                    <figure>
	                                        <a class="btn btn-primary btn-sm" href="/animatch/search/owner" role="button">お店を探す<img src="/animatch/images/icon_search.png" alt="検索アイコン"></a>
	                                    </figure>
	                                </div>
	                            </div>
	                        </div>
	                    </div>

	                    <div class="row pt-2 pb-4 main-center-block is-show-dmessage">
	                        <div class="col-md main-center">
	                            <div class="row">
	                                <div class="col-md text-center">
	                                    <h3 class="main-center-header-content">
	                                        &lt;&lt;お店へメッセージ&gt;&gt;
	                                    </h3>
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-md p-0">
	                                    <figure class="col-lg p-0 text-center">
	                                        <img class="main-center-explation-img-content" src="/animatch/images/trimmer02.png" alt="お店へダイレクトメッセージ補足画像">
	                                    </figure>
	                                </div>
	                                <div class="col-lg p-0">
	                                    <p class="main-center-explation-content">実際にトリミングしてほしいお店と連絡をして、マッチングしてみましょう!</p>
	                                </div>
	                            </div>
	                            <div class="row">
	                                <div class="col-md text-center">
	                                    <figure>
	                                        <a class="btn btn-primary btn-sm" href="/animatch/dmessage/contact" role="button">ダイレクトメッセージ<img src="/animatch/images/icon_direct_message.png" alt="ダイレクトメッセージアイコン"></a>
	                                    </figure>
	                                </div>
	                            </div>
	                        </div>
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

		<script src="/animatch/scripts/index.js"></script>
		<script src="/animatch/scripts/common.js"></script>
	</body>

</html>
