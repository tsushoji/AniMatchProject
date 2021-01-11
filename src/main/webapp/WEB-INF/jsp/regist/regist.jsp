<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="humanSexKeyInitStr" value="human.sex." />
<c:set var="petSexKeyInitStr" value="pet.sex." />
<c:set var="errMsgKeyInitStr" value="msg.err." />
<c:set var="firstTypeKeyInitEnd" value="001" />
<c:set var="secondTypeKeyInitEnd" value="002" />

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

	<fmt:setBundle basename="animatch" var="resource"/>

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
	                <c:forEach items="${registTypeKeyList}" var="registTypeKey" varStatus="status">
                       	<fmt:formatNumber var="registTypeKeyNum" value="${status.index + 1}" minIntegerDigits="3" />
						<fmt:message bundle="${resource}" key="${registTypeKey}" var="registTypeVal" />
                       	<option value="${registTypeKeyNum}">${registTypeVal}</option>
                   	</c:forEach>
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
                    <input type="text" name="user-name" class="form-control" value="${user.password}" required>
                </div>

                <div class="form-group col-md-4">

                    <label class="control-label required-item">パスワード</label>
                    <input type="password" name="password" class="form-control col-md col-sm-5" required>

                    <label class="control-label mt-2 required-item">再入力</label>
                    <input type="password" name="re-password" class="form-control col-md col-sm-5" required>
					<c:if test="${not empty msgKeyList}">
						<c:if test="${fn:contains(msgKeyList, firstTypeKeyInitEnd)}">
							<p><fmt:message bundle="${resource}" key="${errMsgKeyInitStr}${firstTypeKeyInitEnd}" /></p>
						</c:if>
					</c:if>

                </div>

            </div>

            <p class="mb-1 form-common">性別</p>

            <div class="custom-control custom-radio custom-control-inline form-common">
                <input type="radio" id="radio-man" name="radio-user-sex" value="${firstTypeKeyInitEnd}" class="custom-control-input">
                <label class="custom-control-label custom-radio-label" for="radio-man"><fmt:message bundle="${resource}" key="${humanSexKeyInitStr}${firstTypeKeyInitEnd}" /></label>
            </div>

            <div class="custom-control custom-radio custom-control-inline form-common">
                <input type="radio" id="radio-woman" name="radio-user-sex" value="${secondTypeKeyInitEnd}" class="custom-control-input">
                <label class="custom-control-label custom-radio-label" for="radio-woman"><fmt:message bundle="${resource}" key="${humanSexKeyInitStr}${secondTypeKeyInitEnd}" /></label>
            </div>

            <div class="form-row mt-3 form-common">
                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">生年月日</label>
                    <input type="text" name="birthday" class="form-control plugin-datepicker" required>
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
                    <input type="text" name="postal-code" class="form-control col-md col-sm-3" placeholder="9991234" id="postal-code" maxlength="7" required>
                </div>

                <div class="form-group col-md-3 col-sm-5">
                    <label class="control-label required-item" for="prefectures">住所&ndash;都道府県</label>
                    <select name="prefectures" id="prefectures" class="custom-select form-control">
                        <option value="000">選んでください</option>
                        <c:forEach items="${prefecturesKeyList}" var="prefecturesKey" varStatus="status">
	                       	<fmt:formatNumber var="prefecturesKeyNum" value="${status.index + 1}" minIntegerDigits="3" />
							<fmt:message bundle="${resource}" key="${prefecturesKey}" var="prefecturesVal" />
	                       	<option value="${prefecturesKeyNum}">${prefecturesVal}</option>
	                   	</c:forEach>
                    </select>
                </div>

                <div class="form-group col-md-3">
                    <label class="control-label required-item" for="cities">住所&ndash;市区町村</label>
                    <input type="text" name="cities" id="cities" class="form-control col-md col-sm-3" maxlength="6" required>
                </div>

            </div>

            <div class="form-row form-common">
                <div class="form-group col-md-5 col-sm-6">
                    <label class="control-label required-item">メールアドレス</label>
                    <input type="email" name="email-address" class="form-control" placeholder="xxxxxxx@xx.xx.xx" maxlength="254" required>
                </div>
            </div>

            <div class="form-row form-common">
                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">電話番号</label>
                    <input type="tel" name="telephone-number" class="form-control" placeholder="09012345678" maxlength="11" required>
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
                    <label class="control-label required-item">ニックネーム</label>
                    <input type="text" id="pet-name" name="pet-name" class="form-control" placeholder="シロ">
                </div>
            </div>

            <p class="mb-1 form-owner">性別</p>

            <div class="custom-control custom-radio custom-control-inline form-owner">
                <input type="radio" id="radio-male" name="radio-pet-sex" class="custom-control-input" value="${firstTypeKeyInitEnd}">
                <label class="custom-control-label custom-radio-label" for="radio-male"><fmt:message bundle="${resource}" key="${petSexKeyInitStr}${firstTypeKeyInitEnd}" /></label>
            </div>

            <div class="custom-control custom-radio custom-control-inline form-owner">
                <input type="radio" id="radio-female" name="radio-pet-sex" class="custom-control-input" value="${secondTypeKeyInitEnd}">
                <label class="custom-control-label custom-radio-label" for="radio-female"><fmt:message bundle="${resource}" key="${petSexKeyInitStr}${secondTypeKeyInitEnd}" /></label>
            </div>

            <div class="form-row mt-3 form-owner">
                <div class="form-group col-md-3 col-sm-5">
                    <label class="control-label">種別</label>
                    <select name="pet-type" class="custom-select form-control">
                        <option value="000">選んでください</option>
                        <c:forEach items="${petTypeKeyList}" var="petTypeKey" varStatus="status">
                        	<fmt:formatNumber var="petTypeKeyNum" value="${status.index + 1}" minIntegerDigits="3" />
							<fmt:message bundle="${resource}" key="${petTypeKey}" var="petTypeVal" />
                        	<option value="${petTypeKeyNum}">${petTypeVal}</option>
                    	</c:forEach>
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
                    <input type="text" id="store-name" name="store-name" class="form-control" placeholder="ペットサロン ヤマダ">
                </div>
            </div>

            <div class="form-group form-trimmer">
                <label class="control-label">営業時間</label>
                <div>
                    <ul class="plugin-multipicker">
                    	<c:forEach items="${weekdayKeyList}" var="weekdayKey">
							<fmt:message bundle="${resource}" key="${weekdayKey}" var="weekdayVal" />
	                       	<li>${fn:substringBefore(weekdayVal, '曜日')}</li>
	                   	</c:forEach>
                    </ul>
                </div>
            </div>

			<c:forEach items="${weekdayKeyList}" var="weekdayKey" varStatus="status">
				<fmt:message bundle="${resource}" key="${weekdayKey}" var="weekdayVal" />

				<div class="form-row form-trimmer-business-hours day-val-${status.index}">

	                <h3 class="pt-3">${weekdayVal}</h3>

	                <div class="form-group col-lg-2">
	                    <label class="control-label">開始時間</label>
	                    <input type="time" name="business-hours-start-time-${status.index}" class="form-control col-lg col-md-2 col-sm-3">
	                </div>

	                <div class="form-group col-lg-2">
	                    <label class="control-label">終了時間</label>
	                    <input type="time" name="business-hours-end-time-${status.index}" class="form-control col-lg col-md-2 col-sm-3">
	                </div>

	                <div class="form-group col-lg-5">
	                    <label class="control-label">補足</label>
	                    <textarea name="business-hours-remarks-${status.index}" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一${weekdayVal}は休業。"></textarea>
	                </div>

	            </div>
           	</c:forEach>

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