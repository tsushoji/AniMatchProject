<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="errMsgKeyInitStr" value="msg.err." />
<c:set var="firstTypeKeyInitEnd" value="001" />
<c:set var="secondTypeKeyInitEnd" value="002" />

<c:set var="newLine" value="${System.lineSeparator()}" />

<!DOCTYPE html>

<html>

	<head>
		<title>signup</title>

		<!-- plugin dropify -->
		<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">

		<%@ include file="/WEB-INF/jsp/head.jsp" %>

		<!-- plugin dropify -->
		<link rel="stylesheet" type="text/css" href="/animatch/styles/plugin/dropify.css" />

		<!-- bootstrap datepicker -->
		<link rel="stylesheet" type="text/css" href="/animatch/webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" />
		<!-- plugin multipicker -->
    	<link rel="stylesheet" type="text/css" href="/animatch/styles/plugin/multipicker.min.css">

		<link rel="stylesheet" type="text/css" href="/animatch/styles/signup.css" />

		<link rel="stylesheet" type="text/css" href="/animatch/styles/common.css" />
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
        <form method="post" enctype="multipart/form-data" action="/animatch/signup/">

			<div class="form-group">
	            <label class="required-item" for="regist-type">登録区分</label>
	            <select name="regist-type" id="regist-type" class="form-control">
	                <option value="000">選んでください</option>
	                <c:forEach items="${registTypeMap}" var="registType">
                       	<option value="${registType.key}"
	                       	<c:if test="${not empty formRegistType and formRegistType == registType.key}">selected</c:if>>
	                       	${registType.value}
	                    </option>
                   	</c:forEach>
	            </select>
	        </div>

	        <input type="hidden" id="formRegistType" value="${formRegistType}">

            <div class="form-common">
                <h3 class="mb-3">
                    会員情報
                </h3>
            </div>

			<div class="form-row form-common">

                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">ユーザー名</label>
                    <input type="text" name="user-name" class="form-control" value="<c:out value="${registForm.userName}"/>" required>
					<c:if test="${not empty msgMap['001']}">
						<p>${msgMap["001"]}</p>
					</c:if>
                </div>

                <div class="form-group col-md-4">

                    <label class="control-label required-item">パスワード</label>
                    <input type="password" name="password" class="form-control col-md col-sm-5" value="<c:out value="${registForm.password}"/>" required>
                    <c:if test="${not empty msgMap['003']}">
						<p>${msgMap["003"]}</p>
					</c:if>

                    <label class="control-label mt-2 required-item">再入力</label>
                    <input type="password" name="re-password" class="form-control col-md col-sm-5" value="<c:out value="${registForm.rePassword}"/>" required>
					<c:if test="${not empty msgMap['002']}">
						<p>${msgMap["002"]}</p>
					</c:if>

                </div>

            </div>

            <p class="mb-1 form-common">性別</p>

            <div class="custom-control custom-radio custom-control-inline form-common">
                <input type="radio" id="radio-man" name="radio-user-sex" value="${firstTypeKeyInitEnd}" class="custom-control-input"
                <c:if test="${not empty registForm.sex and registForm.sex == firstTypeKeyInitEnd}">checked</c:if>>
                <label class="custom-control-label custom-radio-label" for="radio-man">${humanSexMap["001"]}</label>
            </div>

            <div class="custom-control custom-radio custom-control-inline form-common">
                <input type="radio" id="radio-woman" name="radio-user-sex" value="${secondTypeKeyInitEnd}" class="custom-control-input"
                <c:if test="${not empty registForm.sex and registForm.sex == secondTypeKeyInitEnd}">checked</c:if>>
                <label class="custom-control-label custom-radio-label" for="radio-woman">${humanSexMap["002"]}</label>
            </div>

            <div class="form-row mt-3 form-common">
                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">生年月日</label>
                    <input type="text" name="birthday" class="form-control plugin-datepicker" value="<c:out value="${registForm.birthday}"/>" required>
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
                    <input type="text" name="postal-code" class="form-control col-md col-sm-3" placeholder="9991234" id="postal-code" maxlength="7" value="<c:out value="${registForm.postalCode}"/>" required>
                    <c:if test="${not empty msgMap['004']}">
						<p>${msgMap["004"]}</p>
					</c:if>
                </div>

                <div class="form-group col-md-3 col-sm-5">
                    <label class="control-label required-item" for="prefectures">住所&ndash;都道府県</label>
                    <select name="prefectures" id="prefectures" class="custom-select form-control">
                        <option value="000">選んでください</option>
                        <c:forEach items="${prefecturesMap}" var="prefectures">
	                       	<option value="${prefectures.key}"
	                       		<c:if test="${not empty registForm.prefectures and registForm.prefectures == prefectures.key}">selected</c:if>>
	                       		${prefectures.value}
	                       	</option>
	                   	</c:forEach>
                    </select>
                    <c:if test="${not empty msgMap['005']}">
						<p>${msgMap["005"]}</p>
					</c:if>
                </div>

                <div class="form-group col-md-3">
                    <label class="control-label required-item" for="cities">住所&ndash;市区町村</label>
                    <input type="text" name="cities" id="cities" class="form-control col-md col-sm-3" value="<c:out value="${registForm.cities}"/>" required>
                    <c:if test="${not empty msgMap['006']}">
						<p>${msgMap["006"]}</p>
					</c:if>
					<c:if test="${not empty msgMap['007']}">
						<p>${msgMap["007"]}</p>
					</c:if>
                </div>

            </div>

            <div class="form-row form-common">
                <div class="form-group col-md-5 col-sm-6">
                    <label class="control-label required-item">メールアドレス</label>
                    <input type="text" name="email-address" class="form-control" placeholder="xxxxxxx@xx.xx.xx" value="<c:out value="${registForm.emailAddress}"/>" required>
                    <c:if test="${not empty msgMap['008']}">
						<p>${msgMap["008"]}</p>
					</c:if>
                    <c:if test="${not empty msgMap['009']}">
						<p>${msgMap["009"]}</p>
					</c:if>
                </div>
            </div>

            <div class="form-row form-common">
                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">電話番号</label>
                    <input type="tel" name="telephone-number" class="form-control" placeholder="09012345678" value="<c:out value="${registForm.telephoneNumber}"/>" required>
                    <c:if test="${not empty msgMap['010']}">
						<p>${msgMap["010"]}</p>
					</c:if>
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
				<c:if test="${not empty msgMap['022']}">
					<p>${msgMap["022"]}</p>
				</c:if>
            </div>

            <div class="form-row form-owner">
                <div class="form-group col-md-4 col-sm-5">
                    <label class="control-label required-item">ニックネーム</label>
                    <input type="text" id="pet-name" name="pet-name" class="form-control" placeholder="シロ" value="<c:out value="${registForm.petName}"/>">
                    <c:if test="${not empty msgMap['011']}">
						<p>${msgMap["011"]}</p>
					</c:if>
                </div>
            </div>

            <p class="mb-1 form-owner">性別</p>

            <div class="custom-control custom-radio custom-control-inline form-owner">
                <input type="radio" id="radio-male" name="radio-pet-sex" class="custom-control-input" value="${firstTypeKeyInitEnd}"
                <c:if test="${not empty registForm.petSex and registForm.petSex == firstTypeKeyInitEnd}">checked</c:if>>
                <label class="custom-control-label custom-radio-label" for="radio-male">${petSexMap["001"]}</label>
            </div>

            <div class="custom-control custom-radio custom-control-inline form-owner">
                <input type="radio" id="radio-female" name="radio-pet-sex" class="custom-control-input" value="${secondTypeKeyInitEnd}"
                <c:if test="${not empty registForm.petSex and registForm.petSex == secondTypeKeyInitEnd}">checked</c:if>>
                <label class="custom-control-label custom-radio-label" for="radio-female">${petSexMap["002"]}</label>
            </div>

            <div class="form-row mt-3 form-owner">
                <div class="form-group col-md-3 col-sm-5">
                    <label class="control-label">種別</label>
                    <select name="pet-type" class="custom-select form-control">
                        <option value="000">選んでください</option>
                        <c:forEach items="${petTypeMap}" var="petType">
                        	<option value="${petType.key}"
                        		<c:if test="${not empty registForm.petType and registForm.petType == petType.key}">selected</c:if>>
                        		${petType.value}
                        	</option>
                    	</c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-row form-owner">
                <div class="form-group col-md-2 col-sm-3">
                    <label class="control-label">体重&ndash;&#13199;</label>
                    <input type="text" name="pet-weight" class="form-control" placeholder="23" value="<c:out value="${registForm.petWeight}"/>">
                    <c:if test="${not empty msgMap['012']}">
						<p>${msgMap["012"]}</p>
					</c:if>
					<c:if test="${not empty msgMap['013']}">
						<p>${msgMap["013"]}</p>
					</c:if>
                </div>
            </div>

            <div class="form-row form-trimmer">
                <div class="form-group col-md-4 col-sm-6">
                    <label class="control-label required-item">店名</label>
                    <input type="text" id="store-name" name="store-name" class="form-control" placeholder="ペットサロン ヤマダ" value="<c:out value="${registForm.storeName}"/>">
                    <c:if test="${not empty msgMap['015']}">
						<p>${msgMap["015"]}</p>
					</c:if>
                </div>
            </div>

            <div class="form-group form-trimmer">
                <label class="control-label">営業時間</label>
                <div>
                    <ul class="plugin-multipicker">
                    	<c:forEach items="${weekdayMap}" var="weekday">
	                       	<li>${fn:substringBefore(weekday.value, '曜日')}</li>
	                   	</c:forEach>
                    </ul>
                    <input type="hidden" id="form-business-hours" value="<c:out value="${registForm.formBusinessHoursInputValue}"/>">
                </div>
            </div>

			<c:forEach items="${weekdayMap}" var="weekday" varStatus="status">

				<div class="form-row form-trimmer-business-hours day-val-${status.index}">

	                <h3 class="pt-3">${weekdayl.value}</h3>

	                <div class="form-group col-lg-2">
	                    <label class="control-label">開始時間</label>
	                    <input type="time" id="business-hours-start-time-${status.index}" name="business-hours-start-time-${status.index}" class="form-control col-lg col-md-2 col-sm-3">
	                    <p id="business-hours-start-time-${status.index}-err-msg" class="is-hidden">${msgMap["016"]}</p>
	                </div>

	                <div class="form-group col-lg-2">
	                    <label class="control-label">終了時間</label>
	                    <input type="time" id="business-hours-end-time-${status.index}" name="business-hours-end-time-${status.index}" class="form-control col-lg col-md-2 col-sm-3">
	                    <p id="business-hours-end-time-${status.index}-err-msg" class="is-hidden">${msgMap["016"]}</p>
	                </div>

	                <div class="form-group col-lg-5">
	                    <label class="control-label">補足</label>
	                    <textarea id="business-hours-remarks-${status.index}" name="business-hours-remarks-${status.index}" class="form-control col-lg col-md-6 col-sm-7" placeholder="第一${weekdayl.value}は休業。"></textarea>
	                    <p id="business-hours-remarks-${status.index}-err-length-msg" class="is-hidden">${msgMap["017"]}</p>
	                    <p id="business-hours-remarks-${status.index}-err-xss-msg" class="is-hidden">${msgMap["024"]}</p>
	                </div>

	            </div>
           	</c:forEach>

           	<c:forEach items="${formBusinessHoursList}" var="formBusinessHours">
           		<c:set var="businessHoursWeekdayNum" value="${formBusinessHours.businessHoursWeekdayNum}" />
				<input type="hidden" id="form-business-hours-start-time-${businessHoursWeekdayNum}" value="<c:out value="${formBusinessHours.businessHoursStartTime}"/>">
				<input type="hidden" id="form-business-hours-end-time-${businessHoursWeekdayNum}" value="<c:out value="${formBusinessHours.businessHoursEndTime}"/>">
				<input type="hidden" id="form-business-hours-remarks-${businessHoursWeekdayNum}" value="<c:out value="${formBusinessHours.businessHoursRemarks}"/>">
				<input type="hidden" id="err-form-business-hours-start-time-${businessHoursWeekdayNum}" value="${formBusinessHours.isErrBusinessHoursStartTime}">
				<input type="hidden" id="err-form-business-hours-end-time-${businessHoursWeekdayNum}" value="${formBusinessHours.isErrBusinessHoursEndTime}">
				<input type="hidden" id="err-length-form-business-hours-remarks-${businessHoursWeekdayNum}" value="${formBusinessHours.isErrLengthBusinessHoursRemarks}">
				<input type="hidden" id="err-xss-form-business-hours-remarks-${businessHoursWeekdayNum}" value="${formBusinessHours.isErrXSSBusinessHoursRemarks}">
           	</c:forEach>

            <div class="form-row form-trimmer">
                <div class="form-group col-md-2 col-sm-3">
                    <label class="control-label">従業員数&ndash;人</label>
                    <input type="text" name="store-employees" class="form-control" placeholder="20" value="<c:out value="${registForm.storeEmployees}"/>">
                    <c:if test="${not empty msgMap['018']}">
						<p>${msgMap["018"]}</p>
					</c:if>
					<c:if test="${not empty msgMap['019']}">
						<p>${msgMap["019"]}</p>
					</c:if>
                </div>
            </div>

            <div class="form-group form-owner">
                <label class="control-label">備考</label>
                <textarea name="pet-remarks" class="form-control" rows="10" placeholder="トリマーに伝えておきたいことを書いてください"><c:out value="${registForm.petRemarks}"/></textarea>
                <c:if test="${not empty msgMap['014']}">
					<p>${msgMap["014"]}</p>
				</c:if>
				<c:if test="${not empty msgMap['023']}">
					<p>${msgMap["023"]}</p>
				</c:if>
            </div>

            <div class="form-group form-trimmer">
                <label class="control-label">コース・値段</label>
                <textarea name="course-info" class="form-control" rows="10" placeholder="サービスの詳細を書いてください"><c:out value="${registForm.courseInfo}"/></textarea>
                <c:if test="${not empty msgMap['020']}">
					<p>${msgMap["020"]}</p>
				</c:if>
				<c:if test="${not empty msgMap['025']}">
					<p>${msgMap["025"]}</p>
				</c:if>
            </div>

            <div class="form-group form-trimmer">
                <label class="control-label">こだわりポイント</label>
                <textarea name="commitment" class="form-control" rows="10" placeholder="セールスポイントを書いてください"><c:out value="${registForm.commitment}"/></textarea>
                <c:if test="${not empty msgMap['021']}">
					<p>${msgMap["021"]}</p>
				</c:if>
				<c:if test="${not empty msgMap['026']}">
					<p>${msgMap["026"]}</p>
				</c:if>
            </div>

            <div class="form-group form-common">
                <input type="submit" class="btn btn-primary" value="新規登録">
            </div>

        </form>

    </div>

    <!--フッター-->
    <footer>
        <div class="d-flex justify-content-end pr-3 footer-top">
            <a class="footer-top-content move-page-top cursor-pointer">
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

	<script>
        $(document).ready(function(){
        	//plugin dropify
            $('.plugin-dropify').dropify();

          	//bootstrap datepicker
            $('.plugin-datepicker').datepicker({
                format: 'yyyy/mm/dd',
                language:'ja',
                orientation: 'right'
            //アクション:要素からフォーカスが外れる
            }).on('blur', function() {
            	//形式があっていない場合
                if(this.value.match(/\d{4}\/\d{2}\/\d{2}/) == null){
                	//入力欄を空白とする
                	this.value = '';
                }
            });

          	//plugin multipicker
          	let formInputVal = $('#form-business-hours').val();
          	if(formInputVal){
          		//入力間違いの場合、入力値を「multipicker」にセット
          		let formInputValAry = formInputVal.split(',');
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
                }).multiPicker('select', formInputValAry);
          	}else{
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
          	}
        });
    </script>

	<script src="/animatch/scripts/common.js"></script>
    <script src="/animatch/scripts/signup.js"></script>
</body>
</html>