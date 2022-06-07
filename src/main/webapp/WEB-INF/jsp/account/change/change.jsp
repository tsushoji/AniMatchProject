<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="errMsgKeyInitStr" value="msg.err." />
<c:set var="firstTypeKeyInitEnd" value="001" />
<c:set var="secondTypeKeyInitEnd" value="002" />

<!DOCTYPE html>

<html>

<head>
<title>change</title>

<!-- plugin dropify -->
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">

<%@ include file="/WEB-INF/jsp/head.jsp"%>

<!-- plugin show password -->
<script src="https://unpkg.com/bootstrap-show-password@1.2.1/dist/bootstrap-show-password.min.js"></script>

<!-- plugin dropify -->
<link rel="stylesheet" type="text/css" href="/animatch/styles/plugin/dropify.css" />

<!-- bootstrap datepicker -->
<link rel="stylesheet" type="text/css" href="/animatch/webjars/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" />
<!-- plugin multipicker -->
<link rel="stylesheet" type="text/css" href="/animatch/styles/plugin/multipicker.min.css">

<link rel="stylesheet" type="text/css" href="/animatch/styles/change.css" />

<link rel="stylesheet" type="text/css" href="/animatch/styles/common.css" />
</head>
<body>

 <!--グリッドシステム-->
 <div class="container">

  <div class="text-center">

   <div>
    <img src="/animatch/images/icon_animatch.png" alt="Animatchロゴ">
   </div>

   <h2>&lt;&lt;アカウント情報変更&gt;&gt;</h2>

   <p>
    アカウント情報を変更することができます。<br>
    以下登録フォームで変更したい情報をご入力ください。
   </p>

   <h3>登録区分</h3>
   <p><c:out value="${registTypeName}"/></p>

   <input type="hidden" id="formRegistType" value="${formRegistType}">
  </div>

  <!--入力フォーム-->
  <form method="post" class="account-change-form" enctype="multipart/form-data" action="/animatch/signup/">

   <div class="form-common">
    <h3 class="mb-3">会員情報</h3>
   </div>

   <div class="form-row form-common">

    <div class="form-group col-md-4 col-sm-5">
     <label class="control-label required-item">ユーザー名</label>
     <input type="text" id="user-name" name="user-name" class="form-control check-changed-input" value="<c:out value="${accountChangeForm.userName}"/>">
     <c:if test="${not empty msgMap['001']}">
      <p>${msgMap["001"]}</p>
     </c:if>
    </div>

    <div class="form-group col-md-4">

     <label class="control-label required-item">パスワード</label>
     <input type="password" id="password" name="password" class="form-control col-md col-sm-5" value="<c:out value="${accountChangeForm.password}"/>" data-toggle="password">
     <c:if test="${not empty msgMap['003']}">
      <p>${msgMap["003"]}</p>
     </c:if>

    </div>

   </div>

   <p class="mb-1 form-common">性別</p>

   <div class="custom-control custom-radio custom-control-inline form-common">
    <input type="radio" id="radio-man" name="radio-user-sex" value="${firstTypeKeyInitEnd}" class="custom-control-input"
     <c:if test="${not empty accountChangeForm.sex and accountChangeForm.sex == firstTypeKeyInitEnd}">checked</c:if>>
    <label class="custom-control-label custom-radio-label" for="radio-man">${humanSexMap["001"]}</label>
   </div>

   <div class="custom-control custom-radio custom-control-inline form-common">
    <input type="radio" id="radio-woman" name="radio-user-sex" value="${secondTypeKeyInitEnd}" class="custom-control-input"
     <c:if test="${not empty accountChangeForm.sex and accountChangeForm.sex == secondTypeKeyInitEnd}">checked</c:if>>
    <label class="custom-control-label custom-radio-label" for="radio-woman">${humanSexMap["002"]}</label>
   </div>

   <div class="form-row mt-3 form-common">
    <div class="form-group col-md-4 col-sm-5">
     <label class="control-label required-item">生年月日</label>
     <input type="text" id="birthday" name="birthday" class="form-control check-changed-input plugin-datepicker" value="<c:out value="${accountChangeForm.birthday}"/>">
    </div>
   </div>

   <div class="form-owner">
    <h3 class="mb-3">お住まい情報</h3>
   </div>

   <div class="form-trimmer">
    <h3 class="mb-3">お店情報</h3>
   </div>

   <div class="form-row form-common">

    <div class="form-group col-md-3">
     <label class="control-label required-item" for="postal-code">郵便番号</label>
     <input type="text" id="postal-code" name="postal-code" class="form-control col-md col-sm-3 check-changed-input" placeholder="9991234" id="postal-code" maxlength="7" value="<c:out value="${accountChangeForm.postalCode}"/>">
     <c:if test="${not empty msgMap['004']}">
      <p>${msgMap["004"]}</p>
     </c:if>
    </div>

    <div class="form-group col-md-3 col-sm-5">
     <label class="control-label required-item" for="prefectures">住所&ndash;都道府県</label>
     <select name="prefectures" id="prefectures" class="custom-select form-control check-changed-input">
      <option value="000">選んでください</option>
      <c:forEach items="${prefecturesMap}" var="prefectures">
       <option value="${prefectures.key}"
        <c:if test="${not empty accountChangeForm.prefectures and accountChangeForm.prefectures == prefectures.key}">selected</c:if>>
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
     <input type="text" name="cities" id="cities" class="form-control col-md col-sm-3 check-changed-input" value="<c:out value="${accountChangeForm.cities}"/>">
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
     <input type="text" id="email-address" name="email-address" class="form-control check-changed-input" placeholder="xxxxxxx@xx.xx.xx" value="<c:out value="${accountChangeForm.emailAddress}"/>">
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
     <input type="tel" id="telephone-number" name="telephone-number" class="form-control check-changed-input" placeholder="09012345678" value="<c:out value="${accountChangeForm.telephoneNumber}"/>">
     <c:if test="${not empty msgMap['010']}">
      <p>${msgMap["010"]}</p>
     </c:if>
    </div>
   </div>

   <div class="form-owner">
    <h3 class="mb-3">ペット情報</h3>
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
     <input type="text" id="pet-name" name="pet-name" class="form-control check-changed-input" placeholder="シロ" value="<c:out value="${accountChangeForm.petName}"/>">
     <c:if test="${not empty msgMap['011']}">
      <p>${msgMap["011"]}</p>
     </c:if>
    </div>
   </div>

   <p class="mb-1 form-owner">性別</p>

   <div class="custom-control custom-radio custom-control-inline form-owner">
    <input type="radio" id="radio-male" name="radio-pet-sex" class="custom-control-input" value="${firstTypeKeyInitEnd}"
     <c:if test="${not empty accountChangeForm.petSex and accountChangeForm.petSex == firstTypeKeyInitEnd}">checked</c:if>>
    <label class="custom-control-label custom-radio-label" for="radio-male">${petSexMap["001"]}</label>
   </div>

   <div class="custom-control custom-radio custom-control-inline form-owner">
    <input type="radio" id="radio-female" name="radio-pet-sex" class="custom-control-input" value="${secondTypeKeyInitEnd}"
     <c:if test="${not empty accountChangeForm.petSex and accountChangeForm.petSex == secondTypeKeyInitEnd}">checked</c:if>>
    <label class="custom-control-label custom-radio-label" for="radio-female">${petSexMap["002"]}</label>
   </div>

   <div class="form-row mt-3 form-owner">
    <div class="form-group col-md-3 col-sm-5">
     <label class="control-label">種別</label>
     <select name="pet-type" id="pet-type" class="custom-select form-control check-changed-input">
      <option value="000">選んでください</option>
      <c:forEach items="${petTypeMap}" var="petType">
       <option value="${petType.key}"
        <c:if test="${not empty accountChangeForm.petType and accountChangeForm.petType == petType.key}">selected</c:if>>
        ${petType.value}</option>
      </c:forEach>
     </select>
    </div>
   </div>

   <div class="form-row form-owner">
    <div class="form-group col-md-2 col-sm-3">
     <label class="control-label">体重&ndash;&#13199;</label>
     <input type="text" id="pet-weight" name="pet-weight" class="form-control check-changed-input" placeholder="23" value="<c:out value="${accountChangeForm.petWeight}"/>">
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
     <input type="text" id="store-name" name="store-name" class="form-control check-changed-input" placeholder="ペットサロン ヤマダ" value="<c:out value="${accountChangeForm.storeName}"/>">
     <c:if test="${not empty msgMap['015']}">
      <p>${msgMap["015"]}</p>
     </c:if>
    </div>
   </div>

   <div class="form-group form-trimmer">
    <label class="control-label">営業時間</label>
    <div>
     <ul class="plugin-multipicker">
      <c:forEach items="${weekdayMap}" var="weekday" varStatus="status">
       <li id="check-changed-weekday-${status.index}">${fn:substringBefore(weekday.value, '曜日')}</li>
      </c:forEach>
     </ul>
     <input type="hidden" id="form-business-hours"
      value="<c:out value="${accountChangeForm.formBusinessHoursInputValue}"/>">
    </div>
   </div>

   <c:forEach items="${weekdayMap}" var="weekday" varStatus="status">

    <div class="form-row form-trimmer-business-hours day-val-${status.index}">

     <h3 class="pt-3">${weekdayl.value}</h3>

     <div class="form-group col-lg-2">
      <label class="control-label">開始時間</label>
      <input type="time" id="business-hours-start-time-${status.index}" name="business-hours-start-time-${status.index}" class="form-control col-lg col-md-2 col-sm-3 check-changed-input">
      <p id="business-hours-start-time-${status.index}-err-msg" class="is-hidden">${msgMap["016"]}</p>
     </div>

     <div class="form-group col-lg-2">
      <label class="control-label">終了時間</label>
      <input type="time" id="business-hours-end-time-${status.index}" name="business-hours-end-time-${status.index}" class="form-control col-lg col-md-2 col-sm-3 check-changed-input">
      <p id="business-hours-end-time-${status.index}-err-msg" class="is-hidden">${msgMap["016"]}</p>
     </div>

     <div class="form-group col-lg-5">
      <label class="control-label">補足</label>
      <textarea id="business-hours-remarks-${status.index}" name="business-hours-remarks-${status.index}" class="form-control col-lg col-md-6 col-sm-7 check-changed-input" placeholder="第一${weekdayl.value}は休業。"></textarea>
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
     <input type="text" id="store-employees" name="store-employees" class="form-control check-changed-input" placeholder="20" value="<c:out value="${accountChangeForm.storeEmployees}"/>">
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
    <textarea id="pet-remarks" name="pet-remarks" class="form-control check-changed-input" rows="10" placeholder="トリマーに伝えておきたいことを書いてください"><c:out value="${accountChangeForm.petRemarks}" /></textarea>
    <c:if test="${not empty msgMap['014']}">
     <p>${msgMap["014"]}</p>
    </c:if>
    <c:if test="${not empty msgMap['023']}">
     <p>${msgMap["023"]}</p>
    </c:if>
   </div>

   <div class="form-group form-trimmer">
    <label class="control-label">コース・値段</label>
    <textarea id="course-info" name="course-info" class="form-control check-changed-input" rows="10" placeholder="サービスの詳細を書いてください"><c:out value="${accountChangeForm.courseInfo}" /></textarea>
    <c:if test="${not empty msgMap['020']}">
     <p>${msgMap["020"]}</p>
    </c:if>
    <c:if test="${not empty msgMap['025']}">
     <p>${msgMap["025"]}</p>
    </c:if>
   </div>

   <div class="form-group form-trimmer">
    <label class="control-label">こだわりポイント</label>
    <textarea id="commitment" name="commitment" class="form-control check-changed-input" rows="10" placeholder="セールスポイントを書いてください"><c:out value="${accountChangeForm.commitment}" /></textarea>
    <c:if test="${not empty msgMap['021']}">
     <p>${msgMap["021"]}</p>
    </c:if>
    <c:if test="${not empty msgMap['026']}">
     <p>${msgMap["026"]}</p>
    </c:if>
   </div>

   <div class="form-group form-common">
    <input type="submit" class="btn btn-primary" value="更新">
   </div>

   <input type="hidden" id="registed-user-name" value="${userSession.registedAccountForm.userName}">
   <input type="hidden" id="registed-password" value="${userSession.registedAccountForm.password}">
   <input type="hidden" id="registed-radio-user-sex" value="${userSession.registedAccountForm.sex}">
   <input type="hidden" id="registed-birthday" value="${userSession.registedAccountForm.birthday}">
   <input type="hidden" id="registed-postal-code" value="${userSession.registedAccountForm.postalCode}">
   <input type="hidden" id="registed-prefectures" value="${userSession.registedAccountForm.prefectures}">
   <input type="hidden" id="registed-cities" value="${userSession.registedAccountForm.cities}">
   <input type="hidden" id="registed-email-address" value="${userSession.registedAccountForm.emailAddress}">
   <input type="hidden" id="registed-telephone-number" value="${userSession.registedAccountForm.telephoneNumber}">

   <input type="hidden" id="registed-pet-name" value="${userSession.registedAccountForm.petName}">
   <input type="hidden" id="registed-radio-pet-sex" value="${userSession.registedAccountForm.petSex}">
   <input type="hidden" id="registed-pet-type" value="${userSession.registedAccountForm.petType}">
   <input type="hidden" id="registed-pet-weight" value="${userSession.registedAccountForm.petWeight}">
   <input type="hidden" id="registed-pet-remarks" value="${userSession.registedAccountForm.petRemarks}">

   <input type="hidden" id="registed-store-name" value="${userSession.registedAccountForm.storeName}">
   <input type="hidden" id="registed-store-employees" value="${userSession.registedAccountForm.storeEmployees}">
   <input type="hidden" id="registed-form-business-hours" value="${userSession.registedAccountForm.formBusinessHoursInputValue}">
   <c:forEach items="${userSession.registedAccountForm.formBusinessHoursList}" var="registdFormBusinessHoursList">
    <c:set var="registedBusinessHoursWeekdayNum" value="${registdFormBusinessHoursList.businessHoursWeekdayNum}" />
    <input type="hidden" id="registed-business-hours-start-time-${registedBusinessHoursWeekdayNum}" value="<c:out value="${registdFormBusinessHoursList.businessHoursStartTime}"/>">
    <input type="hidden" id="registed-business-hours-end-time-${registedBusinessHoursWeekdayNum}" value="<c:out value="${registdFormBusinessHoursList.businessHoursEndTime}"/>">
    <input type="hidden" id="registed-business-hours-remarks-${registedBusinessHoursWeekdayNum}" value="<c:out value="${registdFormBusinessHoursList.businessHoursRemarks}"/>">
   </c:forEach>
   <input type="hidden" id="registed-course-info" value="${userSession.registedAccountForm.courseInfo}">
   <input type="hidden" id="registed-commitment" value="${userSession.registedAccountForm.commitment}">
  </form>

 </div>

 <!--フッター-->
 <footer>
  <div class="d-flex justify-content-end pr-3 footer-top">
   <a class="footer-top-content move-page-top cursor-pointer"> <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン">
   <strong>アカウント情報変更の上部へ戻る</strong>
   </a>
  </div>

  <%@ include file="/WEB-INF/jsp/footer02.jsp"%>
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
  $(document).ready(function() {
   //plugin dropify
   $('.plugin-dropify').dropify();

   //bootstrap datepicker
   $('.plugin-datepicker').datepicker({
    format : 'yyyy/mm/dd',
    language : 'ja',
    orientation : 'right'
   //アクション:要素からフォーカスが外れる
   }).on('blur', function() {
    //形式があっていない場合
    if (this.value.match(/\d{4}\/\d{2}\/\d{2}/) == null) {
     //入力欄を空白とする
     this.value = '';
    }
   });

   //plugin multipicker
   let formInputVal = $('#form-business-hours').val();
   if (formInputVal) {
    //入力間違いの場合、入力値を「multipicker」にセット
    let formInputValAry = formInputVal.split(',');
    $('.plugin-multipicker').multiPicker({
     'selector' : 'li',
     'inputName' : 'business-hours',
     //アクション:「営業時間」の営業日選択値が変更される
     'onSelect' : function(elm, val) {
      $('.day-val-' + val).show();
     },

     'onUnselect' : function(elm, val) {
      $('.day-val-' + val).hide();
     }
    }).multiPicker('select', formInputValAry);
   } else {
    $('.plugin-multipicker').multiPicker({
     'selector' : 'li',
     'inputName' : 'business-hours',
     //アクション:「営業時間」の営業日選択値が変更される
     'onSelect' : function(elm, val) {
      $('.day-val-' + val).show();
     },

     'onUnselect' : function(elm, val) {
      $('.day-val-' + val).hide();
     }
    });
   }
  });
 </script>

 <script src="/animatch/scripts/common.js"></script>
 <script src="/animatch/scripts/change.js"></script>
</body>
</html>