<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

	<head>
		<title>AniMatch</title>

		<%@ include file="/WEB-INF/jsp/head.jsp" %>

		<link rel="stylesheet" type="text/css" href="/animatch/styles/dmessage.css" />

		<link rel="stylesheet" type="text/css" href="/animatch/styles/common.css" />
    </head>

	<body>
		<!--ヘッダー-->
	    <header>
	        <!--ナビゲーショントグル-->
	        <nav class="navbar navbar-expand-md navbar-light bg-white">

	            <a class="navbar-brand" href="/WEB-INF/jsp/index.html">
	                <img src="/animatch/images/icon_animatch.png" alt="Animatchロゴ">
	            </a>
	            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-supported-content" aria-controls="navbar-supported-content" aria-expanded="false" aria-label="Toggle navigation">
	                <span class="navbar-toggler-icon"></span>
	            </button>

	            <div class="collapse navbar-collapse collapse-item" id="navbar-supported-content">
	                <ul class="navbar-nav ml-auto mr-3">
	                    <li class="nav-item"><img src="/animatch/images/sample_20200501.jpg" alt="自己アカウント画像" class="rounded-circle nav-item-account-img"></li>
	                    <li class="nav-item"><p class="pl-1 pt-3">アカウント名</p></li>
	                </ul>
	            </div>

	        </nav>
	    </header>

	    <!--メイン-->
	    <main>

	        <div class="my-3 text-center">
	            <h4><span class="chat-header-content">相手アカウント名</span></h4>
	        </div>

	        <div id="chat-frame" class="scrollarea">

	            <p class="chat-talk">

	                <span class="talk-icon">
	                    <img src="/animatch/images/sample_20200501.jpg" alt="相手アカウント画像"/>
	                </span>

	                <span class="talk-content">ありがとうございます。</span>

	                <span class="talk-date">既読<br>0:30</span>

	            </p>

	            <p class="chat-talk">

	                <span class="talk-icon">
	                    <img src="/animatch/images/sample_20200501.jpg" alt="相手アカウント画像"/>
	                </span>

	                <span class="talk-content-stamp">
	                    <img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字"/>
	                </span>

	                <span class="talk-date">既読<br>0:30</span>

	            </p>

	            <h5 class="text-center my-2">
	                2020-05-21
	            </h5>

	            <p class="chat-talk mytalk">

	                <span class="talk-content">どういたしまして。</span>

	                <span class="talk-date">未読</span>

	            </p>

	        </div>

	    </main>


	    <footer>

	        <div class="footer-block">

	            <!-- 隠しタグ -->
	            <input type="file" accept=".jpg,.jpeg,.png,.gif" class="message-form-send-file"/>

	            <!-- 隠しタグ -->
	            <div class="message-form-send-file-area">

	                <div class="d-flex">

	                    <div>
	                        <img src="/animatch/images/icon_file_area.png" alt="添付済みファイルアイコン" class="message-form-send-file-area-img">
	                    </div>

	                    <div>
	                        <img src="/animatch/images/icon_close.png" alt="添付済みファイル閉じるアイコン" class="message-form-send-file-area-icon-close">
	                    </div>

	                    <div class="my-auto message-form-send-file-area-name">

	                    </div>

	                </div>

	            </div>

	            <div class="d-flex message-form-block">

	                <textarea class="message-form-area" rows="2" placeholder="メッセージを入力"></textarea>

	                <div class="message-form-btn">

	                    <img src="/animatch/images/icon_mail.png" alt="メッセージアイコン" class="align-top message-form-send-icon-send ml-2">

	                    <img src="/animatch/images/icon_stamp.png" alt="スタンプアイコン" class="align-top message-form-send-icon-stamp">

	                    <img src="/animatch/images/icon_file_form.png" alt="ファイル添付アイコン" class="align-top message-form-send-icon-file">

	                </div>

	            </div>

	            <!-- 隠しタグ -->
	            <div class="message-form-send-stamp-area">

	                <div>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                </div>

	                <div>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                </div>

	                <div>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                    <span class="border"><img src="/animatch/images/sample_pictograph_01.png" alt="スタンプ絵文字" class="message-form-send-stamp-area-img"></span>
	                </div>

	            </div>

	        </div>

	    </footer>

		<script src="/animatch/scripts/dmessage.js"></script>
	</body>

</html>
