<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="newLine" value="${System.lineSeparator()}" />

<!DOCTYPE html>

<html>

<head>
<title>change_complete</title>

<%@ include file="/WEB-INF/jsp/head.jsp"%>

<link rel="stylesheet" type="text/css" href="/animatch/styles/signup.css" />

<link rel="stylesheet" type="text/css" href="/animatch/styles/common.css" />
</head>

<body>

 <!--グリッドシステム-->
 <div class="container">

  <div class="mx-auto regist-complete-block">

   <div>
    <img src="/animatch/images/icon_animatch.png" alt="Animatchロゴ">
   </div>

   <h2>&lt;&lt;変更結果&gt;&gt;</h2>

   <p>以下情報でアカウント変更いたしました。</p>

   <div>
    <label class="change-complete-item-name"> &lt;登録区分&gt;</label>
    <p>
     <c:out value="${registTypeName}" />
    </p>
   </div>

   <c:if test="${not empty user.userName}">
    <div>
     <label class="change-complete-item-name"> &lt;ユーザー名&gt;</label>
     <p>
      <c:out value="${beforeAccoutInfo.userName}" />&rarr;<c:out value="${user.userName}" />
     </p>
    </div>
   </c:if>

   <c:if test="${not empty user.password}">
    <div>
     <label class="change-complete-item-name"> &lt;パスワード&gt;</label>
     <p>
      <c:out value="${beforeAccoutInfo.password}" />&rarr;<c:out value="${user.password}" />
     </p>
    </div>
   </c:if>

   <c:if test="${not empty user.sex}">
    <div>
     <label class="change-complete-item-name">&lt;性別&gt;</label>
     <p>
      <c:out value="${beforeAccoutInfo.sex}" />&rarr;<c:out value="${user.sex}" />
     </p>
    </div>
   </c:if>

   <c:if test="${not empty user.birthday}">
    <div>
     <label class="change-complete-item-name">&lt;生年月日&gt;</label>
     <p>
      <fmt:formatDate type="DATE" pattern="yyyy-MM-dd" value="${beforeAccoutInfo.birthday}" />&rarr;<fmt:formatDate type="DATE" pattern="yyyy-MM-dd" value="${user.birthday}" />
     </p>
    </div>
   </c:if>

   <c:if test="${not empty user.postalCode}">
    <div>
     <label class="change-complete-item-name">&lt;郵便番号&gt;</label>
     <p>
      <c:out value="${beforeAccoutInfo.postalCode}" />&rarr;<c:out value="${user.postalCode}" />
     </p>
    </div>
   </c:if>

   <c:if test="${not empty user.streetAddress}">
    <div>
     <label class="change-complete-item-name">&lt;住所&gt;</label>
     <p>
      <c:out value="${beforeAccoutInfo.prefectures}" /><c:out value="${beforeAccoutInfo.cities}" />&rarr;<c:out value="${user.streetAddress}" />
     </p>
    </div>
   </c:if>

   <c:if test="${not empty user.emailAddress}">
    <div>
     <label class="change-complete-item-name">&lt;メールアドレス&gt;</label>
     <p class="line-break">
      <c:out value="${beforeAccoutInfo.emailAddress}" />&rarr;<c:out value="${user.emailAddress}" />
     </p>
    </div>
   </c:if>

   <c:if test="${not empty user.telephoneNumber}">
    <div>
     <label class="change-complete-item-name">&lt;電話番号&gt;</label>
     <p>
      <c:out value="${beforeAccoutInfo.telephoneNumber}" />&rarr;<c:out value="${user.telephoneNumber}" />
     </p>
    </div>
   </c:if>

   <c:if test="${(registType == '001' and not empty petImage) or (registType == '002' and not empty storeImage)}">
    <div>
     <label class="change-complete-item-name">&lt;イメージ画像&gt;</label>
     <p class="regist-complete-item-image">
      <img src="data:image/png;base64,${petImage}" class="resize-img-base64">

      <img src="data:image/png;base64,${storeImage}" class="resize-img-base64">
     </p>
    </div>
   </c:if>

   <c:if test="${registType == '001'}">

    <c:if test="${not empty pet.nickName}">
     <div>
      <label class="change-complete-item-name">&lt;ペットニックネーム&gt;</label>
      <p class="line-break">
       <c:out value="${beforeAccoutInfo.petName}" />&rarr;<c:out value="${pet.nickName}" />
      </p>
     </div>
    </c:if>

    <c:if test="${not empty pet.sex}">
     <div>
      <label class="change-complete-item-name">&lt;ペット性別&gt;</label>
      <p>
       <c:out value="${beforeAccoutInfo.petSex}" />&rarr;<c:out value="${pet.sex}" />
      </p>
     </div>
    </c:if>

    <c:if test="${not empty pet.type}">
     <div>
      <label class="change-complete-item-name">&lt;ペット種別&gt;</label>
      <p>
       <c:out value="${beforeAccoutInfo.petType}" />&rarr;<c:out value="${pet.type}" />
      </p>
     </div>
    </c:if>

    <c:if test="${pet.weight != null}">
     <div>
      <label class="change-complete-item-name">&lt;ペット体重&gt;</label>
      <p>
       <c:if test="${beforeAccoutInfo.petWeight != null}">
        <fmt:formatNumber value="${beforeAccoutInfo.petWeight}" />
       </c:if>
       &rarr;<fmt:formatNumber value="${pet.weight}" />
      </p>
     </div>
    </c:if>

    <c:if test="${not empty pet.remarks}">
     <div>
      <label class="change-complete-item-name">&lt;備考&gt;</label>
      <p class="line-break">
       <c:set var="registedPetRemarks" value="${beforeAccoutInfo.petRemarks}" />
       <c:forEach var="registedPetRemarksStr" items="${fn:split(registedPetRemarks, newLine)}">
        <c:out value="${registedPetRemarksStr}" />
        <br />
       </c:forEach>&rarr;
       <c:set var="petRemarks" value="${pet.remarks}" />
       <c:forEach var="petRemarksStr" items="${fn:split(petRemarks, newLine)}">
        <c:out value="${petRemarksStr}" />
        <br />
       </c:forEach>
      </p>
     </div>
    </c:if>

   </c:if>

   <c:if test="${registType == '002'}">

    <c:if test="${not empty store.storeName}">
     <div>
      <label class="change-complete-item-name">&lt;店名&gt;</label>
      <p class="line-break">
       <c:out value="${beforeAccoutInfo.storeName}" />&rarr;<c:out value="${store.storeName}" />
      </p>
     </div>
    </c:if>

    <c:if test="${fn:length(businessHoursList) > 0}">
     <div>
      <label class="change-complete-item-name">&lt;営業日時&gt;</label>

      <c:forEach items="${businessHoursList}" var="businessHours" varStatus="status">

       <fmt:parseNumber var="listIndex" value="${status.index}" />

       <div>

        <p class="change-complete-item-name under-line">
         <c:out value="${businessHours.businessDay}" />
        </p>

        <c:if test="${not empty businessHours.startBusinessTime}">
         <div>
          <label class="change-complete-item-name">&lt;開始時間&gt;</label>
          <p>
           <c:if test="${not empty beforeBusinessHoursList[listIndex].startBusinessTime}">
            <c:out value="${beforeBusinessHoursList[listIndex].startBusinessTime.getHour()}" />
            :
            <fmt:formatNumber value="${beforeBusinessHoursList[listIndex].startBusinessTime.getMinute()}" minIntegerDigits="2" />&rarr;
           </c:if>&rarr;
           <c:out value="${businessHours.startBusinessTime.getHour()}" />
           :
           <fmt:formatNumber value="${businessHours.startBusinessTime.getMinute()}" minIntegerDigits="2" />
          </p>
         </div>
        </c:if>

        <c:if test="${not empty businessHours.endBusinessTime}">
         <div>
          <label class="change-complete-item-name">&lt;終了時間&gt;</label>
          <p>
           <c:if test="${not empty beforeBusinessHoursList[listIndex].endBusinessTime}">
            <c:out value="${beforeBusinessHoursList[listIndex].endBusinessTime.getHour()}" />
            :
            <fmt:formatNumber value="${beforeBusinessHoursList[listIndex].endBusinessTime.getMinute()}" minIntegerDigits="2" />
           </c:if>&rarr;
           <c:out value="${businessHours.endBusinessTime.getHour()}" />
           :
           <fmt:formatNumber value="${businessHours.endBusinessTime.getMinute()}" minIntegerDigits="2" />
          </p>
         </div>
        </c:if>

        <c:if test="${not empty businessHours.complement}">
         <div>
          <label class="change-complete-item-name">&lt;補足&gt;</label>
          <p class="line-break">
           <c:set var="registedBusinessHoursComplement" value="${beforeBusinessHoursList[listIndex].complement}" />
           <c:forEach var="registedBusinessHoursComplementStr" items="${fn:split(registedBusinessHoursComplement, newLine)}">
            <c:out value="${registedBusinessHoursComplementStr}" />
            <br />
           </c:forEach>&rarr;
           <c:set var="businessHoursComplement" value="${businessHours.complement}" />
           <c:forEach var="businessHoursComplementStr" items="${fn:split(businessHoursComplement, newLine)}">
            <c:out value="${businessHoursComplementStr}" />
            <br />
           </c:forEach>
          </p>
         </div>
        </c:if>

      </div>
     </c:forEach>
    </div>
    </c:if>


    <c:if test="${store.employeesNumber != null}">
     <div>
      <label class="change-complete-item-name">&lt;従業員数&ndash;人&gt;</label>
      <p>
       <c:if test="${beforeAccoutInfo.storeEmployees != null}">
        <fmt:formatNumber value="${beforeAccoutInfo.storeEmployees}" />
       </c:if>&rarr;
       <fmt:formatNumber value="${store.employeesNumber}" />
      </p>
     </div>
    </c:if>

    <c:if test="${not empty store.courseInfo}">
     <div>
      <label class="change-complete-item-name">&lt;コース・値段&gt;</label>
      <p class="line-break">
       <c:set var="registedStoreCourseInfo" value="${beforeAccoutInfo.courseInfo}" />
       <c:forEach var="registedStoreCourseInfoStr" items="${fn:split(registedStoreCourseInfo, newLine)}">
        <c:out value="${registedStoreCourseInfoStr}" />
        <br />
       </c:forEach>&rarr;
       <c:set var="storeCourseInfo" value="${store.courseInfo}" />
       <c:forEach var="storeCourseInfoStr" items="${fn:split(storeCourseInfo, newLine)}">
        <c:out value="${storeCourseInfoStr}" />
        <br />
       </c:forEach>
      </p>
     </div>
    </c:if>

    <c:if test="${not empty store.commitment}">
     <div>
      <label class="change-complete-item-name">&lt;こだわりポイント&gt;</label>
      <p class="line-break">
       <c:set var="registedStoreCommitment" value="${beforeAccoutInfo.commitment}" />
       <c:forEach var="registedStoreCommitmentStr" items="${fn:split(registedStoreCommitment, newLine)}">
        <c:out value="${registedStoreCommitmentStr}" />
        <br />
       </c:forEach>
       <c:set var="storeCommitment" value="${store.commitment}" />
       <c:forEach var="storeCommitmentStr" items="${fn:split(storeCommitment, newLine)}">
        <c:out value="${storeCommitmentStr}" />
        <br />
       </c:forEach>
      </p>
     </div>
    </c:if>

   </c:if>

   <p>
    <a class="btn btn-outline-info" type="submit" href="/animatch/index">ホームへ</a>
   </p>
  </div>
 </div>

 <!--フッター-->
 <footer>
  <div class="d-flex justify-content-end pr-3 footer-top">
   <a class="move-page-top cursor-pointer">
    <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン">
    <strong>アカウント変更結果画面の上部へ戻る</strong>
   </a>
  </div>

  <%@ include file="/WEB-INF/jsp/footer02.jsp"%>
 </footer>

 <script src="/animatch/scripts/common.js"></script>
 <script src="/animatch/scripts/signup.js"></script>
</body>

</html>
