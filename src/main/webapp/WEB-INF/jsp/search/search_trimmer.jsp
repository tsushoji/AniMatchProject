<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

	<head>
		<title>search</title>

		<%@ include file="../head.jsp" %>

		<!-- plugin multipicker -->
    	<link rel="stylesheet" type="text/css" href="/animatch/styles/plugin/multipicker.min.css">

		<link rel="stylesheet" type="text/css" href="/animatch/styles/search.css" />
    </head>

	<body>
		<!--ヘッダー-->
    	<header>
			<jsp:include page="../header.jsp"/>
		</header>

		<!--メイン-->
	    <main>

	        <div class="container-fluid">

	            <div class="row">
	                <div class="col">
	                    <h1 class="mt-2"><img src="/animatch/images/icon_beauty_salon.png" alt="お店アイコン" class="main-search-header-img">お店専用</h1>
	                    <p><span>100</span>件見つかりました。</p>
	                </div>
	            </div>

	            <div class="row">

	                <div class="col-md-3 mr-0 mb-md-3 main-search-left">

	                    <h5 class="mt-3 px-0">絞り込み</h5>

	                    <form class="main-search-left-form">

	                        <div class="form-group">
	                            <label class="control-label" for="prefectures">お住まい&ndash;都道府県</label>
	                            <select name="prefectures" id="prefectures" class="custom-select form-control">
	                                <option value="">未選択</option>
	                                <option value="1">北海道</option>
	                                <option value="2">青森県</option>
	                                <option value="3">岩手県</option>
	                                <option value="4">宮城県</option>
	                                <option value="5">秋田県</option>
	                                <option value="6">山形県</option>
	                                <option value="7">福島県</option>
	                                <option value="8">茨城県</option>
	                                <option value="9">栃木県</option>
	                                <option value="10">群馬県</option>
	                                <option value="11">埼玉県</option>
	                                <option value="12">千葉県</option>
	                                <option value="13">東京都</option>
	                                <option value="14">神奈川県</option>
	                                <option value="15">新潟県</option>
	                                <option value="16">富山県</option>
	                                <option value="17">石川県</option>
	                                <option value="18">福井県</option>
	                                <option value="19">山梨県</option>
	                                <option value="20">長野県</option>
	                                <option value="21">岐阜県</option>
	                                <option value="22">静岡県</option>
	                                <option value="23">愛知県</option>
	                                <option value="24">三重県</option>
	                                <option value="25">滋賀県</option>
	                                <option value="26">京都府</option>
	                                <option value="27">大阪府</option>
	                                <option value="28">兵庫県</option>
	                                <option value="29">奈良県</option>
	                                <option value="30">和歌山県</option>
	                                <option value="31">鳥取県</option>
	                                <option value="32">島根県</option>
	                                <option value="33">岡山県</option>
	                                <option value="34">広島県</option>
	                                <option value="35">山口県</option>
	                                <option value="36">徳島県</option>
	                                <option value="37">香川県</option>
	                                <option value="38">愛媛県</option>
	                                <option value="39">高知県</option>
	                                <option value="40">福岡県</option>
	                                <option value="41">佐賀県</option>
	                                <option value="42">長崎県</option>
	                                <option value="43">熊本県</option>
	                                <option value="44">大分県</option>
	                                <option value="45">宮崎県</option>
	                                <option value="46">鹿児島県</option>
	                                <option value="47">沖縄県</option>
	                            </select>
	                        </div>

	                        <div class="form-group">
	                            <label class="control-label" for="cities">お住まい&ndash;市町村</label>
	                            <select name="cities" id="cities" class="custom-select form-control">
	                                <option value="">未選択</option>
	                            </select>
	                        </div>

	                        <div class="form-group mt-3">
	                            <label class="control-label">動物&ndash;種別</label>
	                            <select name="type-pet" class="custom-select form-control">
	                                <option value="">未選択</option>
	                                <option value="1">犬</option>
	                                <option value="2">猫</option>
	                            </select>
	                        </div>

	                        <div class="form-group">
	                            <a class="main-search-left-clear">リセット</a>
	                        </div>

	                        <div class="form-group">
	                            <input type="submit" class="btn btn-outline-primary col-md-12 col-sm-6 main-search-left-select-btn" value="絞り込む">
	                        </div>

	                    </form>

	                </div>

	                <div class="col-md-9 ml-0 mb-3 px-0 main-search-right">

	                    <form class="main-search-right-form">

	                        <div class="input-group my-3 col-11">
	                            <input type="text" class="form-control" placeholder="キーワードを入力">
	                            <span class="input-group-btn">
	                                <button type="button" class="btn btn-outline-primary"><img src="/animatch/images/icon_search.png" alt="検索アイコン"></button>
	                            </span>
	                        </div>

	                    </form>

	                    <div class="row main-search-right-list is-show-details-trimmer">

	                        <div class="col-3">
	                            <img src="" alt="新規会員登録したペット画像">
	                        </div>

	                        <div class="col-9">

	                            <h6><a href="../details/details-trimmer.html">新規会員登録した飼い主ニックネーム</a></h6>

	                            <div class="row">

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい都道府県
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい市町村
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録した動物種別
	                                </div>

	                            </div>

	                            <p class="mt-2">新規会員登録した補足</p>

	                        </div>

	                    </div>

	                    <div class="row main-search-right-list is-show-details-trimmer">

	                        <div class="col-3">
	                            <img src="" alt="新規会員登録したペット画像">
	                        </div>

	                        <div class="col-9">

	                            <h6><a href="../details/details-trimmer.html">新規会員登録した飼い主ニックネーム</a></h6>

	                            <div class="row">

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい都道府県
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい市町村
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録した動物種別
	                                </div>

	                            </div>

	                            <p class="mt-2">新規会員登録した補足</p>

	                        </div>

	                    </div>

	                    <div class="row main-search-right-list is-show-details-trimmer">

	                        <div class="col-3">
	                            <img src="" alt="新規会員登録したペット画像">
	                        </div>

	                        <div class="col-9">

	                            <h6><a href="../details/details-trimmer.html">新規会員登録した飼い主ニックネーム</a></h6>

	                            <div class="row">

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい都道府県
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい市町村
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録した動物種別
	                                </div>

	                            </div>

	                            <p class="mt-2">新規会員登録した補足</p>

	                        </div>

	                    </div>

	                    <div class="row main-search-right-list is-show-details-trimmer">

	                        <div class="col-3">
	                            <img src="" alt="新規会員登録したペット画像">
	                        </div>

	                        <div class="col-9">

	                            <h6><a href="../details/details-trimmer.html">新規会員登録した飼い主ニックネーム</a></h6>

	                            <div class="row">

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい都道府県
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい市町村
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録した動物種別
	                                </div>

	                            </div>

	                            <p class="mt-2">新規会員登録した補足</p>

	                        </div>

	                    </div>

	                    <div class="row main-search-right-list is-show-details-trimmer">

	                        <div class="col-3">
	                            <img src="" alt="新規会員登録したペット画像">
	                        </div>

	                        <div class="col-9">

	                            <h6><a href="../details/details-trimmer.html">新規会員登録した飼い主ニックネーム</a></h6>

	                            <div class="row">

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい都道府県
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい市町村
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録した動物種別
	                                </div>

	                            </div>

	                            <p class="mt-2">新規会員登録した補足</p>

	                        </div>

	                    </div>

	                    <div class="row main-search-right-list is-show-details-trimmer">

	                        <div class="col-3">
	                            <img src="" alt="新規会員登録したペット画像">
	                        </div>

	                        <div class="col-9">

	                            <h6><a href="../details/details-trimmer.html">新規会員登録した飼い主ニックネーム</a></h6>

	                            <div class="row">

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい都道府県
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録したお住まい市町村
	                                </div>

	                                <div class="col-sm-4 p-0">
	                                    新規会員登録した動物種別
	                                </div>

	                            </div>

	                            <p class="mt-2">新規会員登録した補足</p>

	                        </div>

	                    </div>

	                </div>

	            </div>

	            <nav aria-label="ページ送りの実例">
	                <ul class="pagination justify-content-center pb-3 mb-0">
	                  <li class="page-item"><a class="page-link" href="#">前へ</a></li>
	                  <li class="page-item"><a class="page-link" href="#">1</a></li>
	                  <li class="page-item"><a class="page-link" href="#">2</a></li>
	                  <li class="page-item"><a class="page-link" href="#">3</a></li>
	                  <li class="page-item"><a class="page-link" href="#">次へ</a></li>
	                </ul>
	            </nav>

	        </div>

	    </main>

	    <!--フッター-->
	    <footer>
	        <div class="d-flex justify-content-end pr-3 footer-top">
	            <a class="move-page-top" href="#">
	                <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン"> <strong>AniMatchの上部へ戻る</strong>
	            </a>
	        </div>

	        <%@ include file="../footer01.jsp" %>

	        <%@ include file="../footer02.jsp" %>
	    </footer>

		<!-- plugin multipicker -->
	    <script src="/animatch/scripts/plugin/multipicker.min.js"></script>

	    <script src="/animatch/scripts/search.js"></script>
		<script src="/animatch/scripts/common.js"></script>

	    <script>
	        $(document).ready(function(){
	            //plugin multipicker
	            $('.plugin-multipicker').multiPicker({
	                'selector' : 'li'
	            });
	        });
	    </script>

	</body>

</html>