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
    <label class="regist-complete-item-name"> &lt;登録区分&gt;</label>
    <p>
     <c:out value="${registTypeName}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name"> &lt;ユーザー名&gt;</label>
    <p>
     <c:out value="${user.userName}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name"> &lt;パスワード&gt;</label>
    <p>
     <c:out value="${user.password}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name">&lt;性別&gt;</label>
    <p>
     <c:out value="${user.sex}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name">&lt;生年月日&gt;</label>
    <p>
     <fmt:formatDate type="DATE" pattern="yyyy-MM-dd" value="${user.birthday}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name">&lt;郵便番号&gt;</label>
    <p>
     <c:out value="${user.postalCode}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name">&lt;住所&gt;</label>
    <p>
     <c:out value="${user.streetAddress}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name">&lt;メールアドレス&gt;</label>
    <p class="line-break">
     <c:out value="${user.emailAddress}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name">&lt;電話番号&gt;</label>
    <p>
     <c:out value="${user.telephoneNumber}" />
    </p>
   </div>

   <div>
    <label class="regist-complete-item-name">&lt;イメージ画像&gt;</label>
    <p class="regist-complete-item-image">
     <c:if test="${registType == '001'}">
      <c:if test="${not empty petImage}">
       <img src="data:image/png;base64,${petImage}" class="resize-img-base64">
      </c:if>
     </c:if>

     <c:if test="${registType == '002'}">
      <c:if test="${not empty storeImage}">
       <img src="data:image/png;base64,${storeImage}" class="resize-img-base64">
      </c:if>
     </c:if>
    </p>
   </div>

   <c:if test="${registType == '001'}">

    <div>
     <label class="regist-complete-item-name">&lt;ペットニックネーム&gt;</label>
     <p class="line-break">
      <c:out value="${pet.nickName}" />
     </p>
    </div>

    <div>
     <label class="regist-complete-item-name">&lt;ペット性別&gt;</label>
     <p>
      <c:out value="${pet.sex}" />
     </p>
    </div>

    <div>
     <label class="regist-complete-item-name">&lt;ペット種別&gt;</label>
     <p>
      <c:out value="${pet.type}" />
     </p>
    </div>

    <div>
     <label class="regist-complete-item-name">&lt;ペット体重&gt;</label>
     <p>
      <c:if test="${pet.weight != null}">
       <fmt:formatNumber value="${pet.weight}" />
      </c:if>
     </p>
    </div>

    <div>
     <label class="regist-complete-item-name">&lt;備考&gt;</label>
     <p class="line-break">
      <c:set var="petRemarks" value="${pet.remarks}" />
      <c:forEach var="petRemarksStr" items="${fn:split(petRemarks, newLine)}">
       <c:out value="${petRemarksStr}" />
       <br />
      </c:forEach>
     </p>
    </div>

   </c:if>

   <c:if test="${registType == '002'}">

    <div>
     <label class="regist-complete-item-name">&lt;店名&gt;</label>
     <p class="line-break">
      <c:out value="${store.storeName}" />
     </p>
    </div>

    <div>
     <label class="regist-complete-item-name">&lt;営業日時&gt;</label>

     <c:forEach items="${businessHoursList}" var="businessHours">

      <div>

       <p class="regist-complete-item-name under-line">
        <c:out value="${businessHours.businessDay}" />
       </p>

       <div>
        <label class="regist-complete-item-name">&lt;開始時間&gt;</label>
        <p>
         <c:out value="${businessHours.startBusinessTime.getHour()}" />
         :
         <fmt:formatNumber value="${businessHours.startBusinessTime.getMinute()}" minIntegerDigits="2" />
        </p>
       </div>

       <div>
        <label class="regist-complete-item-name">&lt;終了時間&gt;</label>
        <p>
         <c:out value="${businessHours.endBusinessTime.getHour()}" />
         :
         <fmt:formatNumber value="${businessHours.endBusinessTime.getMinute()}" minIntegerDigits="2" />
        </p>
       </div>

       <div>
        <label class="regist-complete-item-name">&lt;補足&gt;</label>
        <p class="line-break">
         <c:set var="businessHoursComplement" value="${businessHours.complement}" />
         <c:forEach var="businessHoursComplementStr" items="${fn:split(businessHoursComplement, newLine)}">
          <c:out value="${businessHoursComplementStr}" />
          <br />
         </c:forEach>
        </p>
       </div>

      </div>
     </c:forEach>
    </div>


    <div>
     <label class="regist-complete-item-name">&lt;従業員数&ndash;人&gt;</label>
     <p>
      <c:if test="${store.employeesNumber != null}">
       <fmt:formatNumber value="${store.employeesNumber}" />
      </c:if>
     </p>
    </div>

    <div>
     <label class="regist-complete-item-name">&lt;コース・値段&gt;</label>
     <p class="line-break">
      <c:set var="storeCourseInfo" value="${store.courseInfo}" />
      <c:forEach var="storeCourseInfoStr" items="${fn:split(storeCourseInfo, newLine)}">
       <c:out value="${storeCourseInfoStr}" />
       <br />
      </c:forEach>
     </p>
    </div>

    <div>
     <label class="regist-complete-item-name">&lt;こだわりポイント&gt;</label>
     <p class="line-break">
      <c:set var="storeCommitment" value="${store.commitment}" />
      <c:forEach var="storeCommitmentStr" items="${fn:split(storeCommitment, newLine)}">
       <c:out value="${storeCommitmentStr}" />
       <br />
      </c:forEach>
     </p>
    </div>

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
