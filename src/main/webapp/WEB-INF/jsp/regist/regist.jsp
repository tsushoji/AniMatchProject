<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

	<head>
		<title>regist</title>

		<!-- plugin dropify -->
		<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">

		<%@ include file="/WEB-INF/jsp/head.jsp" %>

		<!-- plugin dropify -->
		<link rel="stylesheet" type="text/css" href="/animatch/styles/plugin/dropify.css" />

		<!-- bootstrap datepicker -->
		<link rel="stylesheet" type="text/css" href="/animatch/webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" />
		<!-- plugin multipicker -->
    	<link rel="stylesheet" type="text/css" href="/animatch/styles/plugin/multipicker.min.css">

		<link rel="stylesheet" type="text/css" href="/animatch/styles/regist.css" />
	</head>
<body>

	<!--グリッドシステム-->
    <div class="container">

        <div class="text-center">

            <div>
                <img src="/animatch/images/icon_animatch.png" alt="Animatchロゴ">
            </div>

            <h2>
                &lt;&lt;新規会員登録&gt;&gt;
            </h2>

            <p>
                新規会員にご登録をいただくと、無料でAniMatchでのサービスをご利用いただくことができます。<br>
                以下登録フォームにお客様情報をご入力ください。
            </p>

        </div>

        <!--入力フォーム-->
        <form method="post" enctype="multipart/form-data" action="/animatch/regist/regist">

			<div class="form-group">
	            <label class="required-item" for="regist-type">登録区分</label>
	            <select name="regist-type" id="regist-type" class="form-control">
	                <option value="000">選んでください</option>
	                <option value="001">飼い主</option>
	                <option value="002">トリマー</option>
	            </select>
	        </div>

            <div class="form-common">
                <h3 class="mb-3">
                    会員情報
                </h3>
            </div>

			<div class="form-row form-common">

                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">ユーザー名</label>
                    <input type="text" name="user-name" class="form-control">
                </div>

                <div class="form-group col-md-4">

                    <label class="control-label required-item">パスワード</label>
                    <input type="password" name="password" class="form-control col-md col-sm-5">

                    <label class="control-label mt-2 required-item">再入力</label>
                    <input type="password" name="re-password" class="form-control col-md col-sm-5">

                </div>

            </div>

            <p class="mb-1 form-common">性別</p>

            <div class="custom-control custom-radio custom-control-inline form-common">
                <input type="radio" id="radio-man" name="radio-user-sex" value="001" class="custom-control-input">
                <label class="custom-control-label custom-radio-label" for="radio-man">男性</label>
            </div>

            <div class="custom-control custom-radio custom-control-inline form-common">
                <input type="radio" id="radio-woman" name="radio-user-sex" value="002" class="custom-control-input">
                <label class="custom-control-label custom-radio-label" for="radio-woman">女性</label>
            </div>

            <div class="form-row mt-3 form-common">
                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">生年月日</label>
                    <input type="text" name="birthday" class="form-control plugin-datepicker">
                </div>
            </div>

            <div class="form-owner">
                <h3 class="mb-3">
                    お住まい情報
                </h3>
            </div>

            <div class="form-trimmer">
                <h3 class="mb-3">
                    お店情報
                </h3>
            </div>

            <div class="form-row form-common">

                <div class="form-group col-md-3">
                    <label class="control-label required-item" for="postal-code">郵便番号</label>
                    <input type="text" name="postal-code" class="form-control col-md col-sm-3" placeholder="9991234" id="postal-code" maxlength="7">
                </div>

                <div class="form-group col-md-3 col-sm-5">
                    <label class="control-label required-item" for="prefectures">住所&ndash;都道府県</label>
                    <select name="prefectures" id="prefectures" class="custom-select form-control">
                        <option value="000">選んでください</option>
                        <option value="001">北海道</option>
                        <option value="002">青森県</option>
                        <option value="003">岩手県</option>
                        <option value="004">宮城県</option>
                        <option value="005">秋田県</option>
                        <option value="006">山形県</option>
                        <option value="007">福島県</option>
                        <option value="008">茨城県</option>
                        <option value="009">栃木県</option>
                        <option value="010">群馬県</option>
                        <option value="011">埼玉県</option>
                        <option value="012">千葉県</option>
                        <option value="013">東京都</option>
                        <option value="014">神奈川県</option>
                        <option value="015">新潟県</option>
                        <option value="016">富山県</option>
                        <option value="017">石川県</option>
                        <option value="018">福井県</option>
                        <option value="019">山梨県</option>
                        <option value="020">長野県</option>
                        <option value="021">岐阜県</option>
                        <option value="022">静岡県</option>
                        <option value="023">愛知県</option>
                        <option value="024">三重県</option>
                        <option value="025">滋賀県</option>
                        <option value="026">京都府</option>
                        <option value="027">大阪府</option>
                        <option value="028">兵庫県</option>
                        <option value="029">奈良県</option>
                        <option value="030">和歌山県</option>
                        <option value="031">鳥取県</option>
                        <option value="032">島根県</option>
                        <option value="033">岡山県</option>
                        <option value="034">広島県</option>
                        <option value="035">山口県</option>
                        <option value="036">徳島県</option>
                        <option value="037">香川県</option>
                        <option value="038">愛媛県</option>
                        <option value="039">高知県</option>
                        <option value="040">福岡県</option>
                        <option value="041">佐賀県</option>
                        <option value="042">長崎県</option>
                        <option value="043">熊本県</option>
                        <option value="044">大分県</option>
                        <option value="045">宮崎県</option>
                        <option value="046">鹿児島県</option>
                        <option value="047">沖縄県</option>
                    </select>
                </div>

                <div class="form-group col-md-3">
                    <label class="control-label required-item" for="cities">住所&ndash;市区町村</label>
                    <input type="text" name="cities" id="cities" class="form-control col-md col-sm-3" maxlength="6">
                </div>

            </div>

            <div class="form-row form-common">
                <div class="form-group col-md-5 col-sm-6">
                    <label class="control-label required-item">メールアドレス</label>
                    <input type="email" name="email-address" class="form-control" placeholder="xxxxxxx@xx.xx.xx" maxlength="254">
                </div>
            </div>

            <div class="form-row form-common">
                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">電話番号</label>
                    <input type="tel" name="telephone-number" class="form-control" placeholder="09012345678" maxlength="11">
                </div>
            </div>

            <div class="form-owner">
                <h3 class="mb-3">
                    ペット情報
                </h3>
            </div>

            <div class="form-group form-common">
                <label class="control-label">イメージ画像</label>
                <input type="file" name="file" class="plugin-dropify">
            </div>

            <div class="form-row form-owner">
                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label">ニックネーム</label>
                    <input type="text" name="pet-name" class="form-control" placeholder="シロ">
                </div>
            </div>

            <p class="mb-1 form-owner">性別</p>

            <div class="custom-control custom-radio custom-control-inline form-owner">
                <input type="radio" id="radio-male" name="radio-pet-sex" class="custom-control-input" value="001">
                <label class="custom-control-label custom-radio-label" for="radio-male">オス</label>
            </div>

            <div class="custom-control custom-radio custom-control-inline form-owner">
                <input type="radio" id="radio-female" name="radio-pet-sex" class="custom-control-input" value="002">
                <label class="custom-control-label custom-radio-label" for="radio-female">メス</label>
            </div>

            <div class="form-row mt-3 form-owner">
                <div class="form-group col-md-3 col-sm-5">
                    <label class="control-label">種別</label>
                    <select name="pet-type" class="custom-select form-control">
                        <option value="000">選んでください</option>
                        <option value="001">犬</option>
                        <option value="002">猫</option>
                    </select>
                </div>
            </div>

            <div class="form-row form-owner">
                <div class="form-group col-md-2 col-sm-3">
                    <label class="control-label">体重&ndash;&#13199;</label>
                    <input type="text" name="pet-weight" class="form-control" placeholder="23" maxlength="6">
                </div>
            </div>

            <div class="form-row form-trimmer">
                <div class="form-group col-md-4 col-sm-6">
                    <label class="control-label required-item">店名</label>
                    <input type="text" name="store-name" class="form-control" placeholder="ペットサロン ヤマダ">
                </div>
            </div>

            <div class="form-group form-trimmer">
                <label class="control-label">営業時間</label>
                <div>
                    <ul class="plugin-multipicker">
                        <li>日</li>
                        <li>月</li>
                        <li>火</li>
                        <li>水</li>
                        <li>木</li>
                        <li>金</li>
                        <li>土</li>
                    </ul>
                </div>
            </div>

            <div class="form-row form-trimmer-business-hours day-val-0">

                <h3 class="pt-3">日曜日</h3>

                <div class="form-group col-lg-1">
                    <label class="control-label">開始時間</label>
                    <input type="time" name="business-hours-start-time-0" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-1">
                    <label class="control-label">終了時間</label>
                    <input type="time" name="business-hours-end-time-0" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-5">
                    <label class="control-label">補足</label>
                    <textarea name="business-hours-remarks-0" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一日曜日は休業。"></textarea>
                </div>

            </div>

            <div class="form-row form-trimmer-business-hours day-val-1">

                <h3 class="pt-3">月曜日</h3>

                <div class="form-group col-lg-1">
                    <label class="control-label">開始時間</label>
                    <input type="time" name="business-hours-start-time-1" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-1">
                    <label class="control-label">終了時間</label>
                    <input type="time" name="business-hours-end-time-1" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-5">
                    <label class="control-label">補足</label>
                    <textarea name="business-hours-remarks-1" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一月曜日は休業。"></textarea>
                </div>

            </div>

            <div class="form-row form-trimmer-business-hours day-val-2">

                <h3 class="pt-3">火曜日</h3>

                <div class="form-group col-lg-1">
                    <label class="control-label">開始時間</label>
                    <input type="time" name="business-hours-start-time-2" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-1">
                    <label class="control-label">終了時間</label>
                    <input type="time" name="business-hours-end-time-2" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-5">
                    <label class="control-label">補足</label>
                    <textarea name="business-hours-remarks-2" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一火曜日は休業。"></textarea>
                </div>

            </div>

            <div class="form-row form-trimmer-business-hours day-val-3">

                <h3 class="pt-3">水曜日</h3>

                <div class="form-group col-lg-1">
                    <label class="control-label">開始時間</label>
                    <input type="time" name="business-hours-start-time-3" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-1">
                    <label class="control-label">終了時間</label>
                    <input type="time" name="business-hours-end-time-3" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-5">
                    <label class="control-label">補足</label>
                    <textarea name="business-hours-remarks-3" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一水曜日は休業。"></textarea>
                </div>

            </div>

            <div class="form-row form-trimmer-business-hours day-val-4">

                <h3 class="pt-3">木曜日</h3>

                <div class="form-group col-lg-1">
                    <label class="control-label">開始時間</label>
                    <input type="time" name="business-hours-start-time-4" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-1">
                    <label class="control-label">終了時間</label>
                    <input type="time" name="business-hours-end-time-4" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-5">
                    <label class="control-label">補足</label>
                    <textarea name="business-hours-remarks-4" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一木曜日は休業。"></textarea>
                </div>

            </div>

            <div class="form-row form-trimmer-business-hours day-val-5">

                <h3 class="pt-3">金曜日</h3>

                <div class="form-group col-lg-1">
                    <label class="control-label">開始時間</label>
                    <input type="time" name="business-hours-start-time-5" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-1">
                    <label class="control-label">終了時間</label>
                    <input type="time" name="business-hours-end-time-5" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-5">
                    <label class="control-label">補足</label>
                    <textarea name="business-hours-remarks-5" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一金曜日は休業。"></textarea>
                </div>

            </div>

            <div class="form-row form-trimmer-business-hours day-val-6">

                <h3 class="pt-3">土曜日</h3>

                <div class="form-group col-lg-1">
                    <label class="control-label">開始時間</label>
                    <input type="time" name="business-hours-start-time-6" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-1">
                    <label class="control-label">終了時間</label>
                    <input type="time" name="business-hours-end-time-6" class="form-control col-lg col-md-2 col-sm-3">
                </div>

                <div class="form-group col-lg-5">
                    <label class="control-label">補足</label>
                    <textarea name="business-hours-remarks-6" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一土曜日は休業。"></textarea>
                </div>

            </div>

            <div class="form-row form-trimmer">
                <div class="form-group col-md-2 col-sm-3">
                    <label class="control-label">従業員数&ndash;人</label>
                    <input type="text" name="store-employees" class="form-control" placeholder="20" maxlength="6">
                </div>
            </div>

            <div class="form-group form-owner">
                <label class="control-label">備考</label>
                <textarea name="pet-remarks" class="form-control" rows="10" placeholder="トリマーに伝えておきたいことを書いてください"></textarea>
            </div>

            <div class="form-group form-trimmer">
                <label class="control-label">コース・値段</label>
                <textarea name="course-info" class="form-control" rows="10" placeholder="サービスの詳細を書いてください"></textarea>
            </div>

            <div class="form-group form-trimmer">
                <label class="control-label">こだわりポイント</label>
                <textarea name="commitment" class="form-control" rows="10" placeholder="セールスポイントを書いてください"></textarea>
            </div>

            <div class="form-group form-common">
                <input type="submit" class="btn btn-primary" value="新規登録">
            </div>

        </form>

    </div>

    <!--フッター-->
    <footer>
        <div class="d-flex justify-content-end pr-3 footer-top">
            <a class="footer-top-content move-page-top" href="#">
                <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン"> <strong>新規会員登録の上部へ戻る</strong>
            </a>
        </div>

        <%@ include file="/WEB-INF/jsp/footer02.jsp" %>
    </footer>
    <!-- plugin dropify -->
    <script src="/animatch/scripts/plugin/dropify.js"></script>

    <!-- bootstrap datepicker -->
    <script type="text/javascript" src="/animatch/webjars/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="/animatch/webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="/animatch/webjars/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.ja.min.js"></script>
    <!-- plugin multipicker -->
    <script src="/animatch/scripts/plugin/multipicker.min.js"></script>

	<script src="/animatch/scripts/regist.js"></script>
	<script src="/animatch/scripts/common.js"></script>

	<script>
        $(document).ready(function(){
        	//plugin dropify
            $('.plugin-dropify').dropify();

          	//bootstrap datepicker
            $('.plugin-datepicker').datepicker({
                format: 'yyyy/mm/dd',
                language:'ja',
                orientation: 'bottom'
            });

          	//plugin multipicker
            $('.plugin-multipicker').multiPicker({
                'selector' : 'li',
                'inputName' : 'business-hours',
                //アクション:「営業時間」の営業日選択値が変更される
                'onSelect' : function (elm, val) {
                        $('.day-val-' + val).show();
                    },

                'onUnselect' : function (elm, val) {
                        $('.day-val-' + val).hide();
                    }
            });
        });
    </script>
</body>
</html>