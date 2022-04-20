<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="firstTypeKeyInitEnd" value="001" />
<c:set var="secondTypeKeyInitEnd" value="002" />

<c:set var="newLine" value="${System.lineSeparator()}" />

<!DOCTYPE html>
<html>

<head>
<title>detail</title>

<%@ include file="/WEB-INF/jsp/head.jsp"%>

<link rel="stylesheet" type="text/css" href="/animatch/styles/detail.css" />

<link rel="stylesheet" type="text/css" href="/animatch/styles/common.css" />
</head>

<body>
 <!--ヘッダー-->
 <header>
  <jsp:include page="/WEB-INF/jsp/header.jsp" />
 </header>

 <!--メイン-->
 <main>

  <div class="container">

   <c:if test="${not empty trimmerInfo and searchDetailTypeId == firstTypeKeyInitEnd}">

    <div class="row my-1 mx-auto">

     <div class="col-md-5 offset-sm-1 main-details-left">

      <c:if test="${not empty trimmerInfo.storeImageBase64}">
       <img src="data:image/png;base64,${trimmerInfo.storeImageBase64}">
      </c:if>

     </div>

     <div class="col-md-6 main-search-right">

      <h3 class="mt-2">
       <c:out value="${trimmerInfo.storeName}" />
      </h3>

      <h6 class="mt-3">都道府県市町村&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:out value="${trimmerInfo.streetAddress}" />
      </p>

      <h6 class="mt-2">従業員数&ndash;人&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:out value="${trimmerInfo.storeEmployeesNumber}" />
      </p>

      <h6 class="mt-2">営業時間&#058;</h6>

      <div class="pb-2 main-search-right-underline">
       <c:if test="${not empty trimmerInfo.trimmerInfoBusinessHoursList}">
        <c:forEach items="${trimmerInfo.trimmerInfoBusinessHoursList}" var="trimmerInfoBusinessHours">
         <p class="mb-0">
          <c:out value="${trimmerInfoBusinessHours.displayBusinessHours}" />
          &ndash;
          <c:out value="${trimmerInfoBusinessHours.displayStartBusinessTime}" />
          &sim;
          <c:out value="${trimmerInfoBusinessHours.displayEndBusinessTime}" />
         </p>
         <p class="mt-0">
          <c:set var="businessHoursComplement" value="${trimmerInfoBusinessHours.complement}" />
          <c:forEach var="businessHoursComplementStr" items="${fn:split(businessHoursComplement, newLine)}">
           <c:out value="${businessHoursComplementStr}" />
           <br />
          </c:forEach>
         </p>
        </c:forEach>
       </c:if>
      </div>

      <h6 class="mt-2">コース・値段&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:set var="storeCourseInfo" value="${trimmerInfo.storeCourseInfo}" />
       <c:forEach var="storeCourseInfoStr" items="${fn:split(storeCourseInfo, newLine)}">
        <c:out value="${storeCourseInfoStr}" />
        <br />
       </c:forEach>
      </p>

      <h6 class="mt-2">こだわりポイント&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:set var="storeCommitment" value="${trimmerInfo.storeCommitment}" />
       <c:forEach var="storeCommitmentStr" items="${fn:split(storeCommitment, newLine)}">
        <c:out value="${storeCommitmentStr}" />
        <br />
       </c:forEach>
      </p>

     </div>

    </div>

    <div class="row mt-3 pb-3">

     <div class="col text-center">

      <div class="form-group form-common">
       <input type="submit" class="btn btn-primary is-show-dmessage-trimmer" value="お店へダイレクトメッセージ">
      </div>

     </div>

    </div>

   </c:if>

   <c:if test="${not empty ownerInfo and searchDetailTypeId == secondTypeKeyInitEnd}">

    <div class="row my-1 mx-auto">

     <div class="col-md-5 offset-sm-1 main-details-left">

      <c:if test="${not empty ownerInfo.petImageBase64}">
       <img src="data:image/png;base64,${ownerInfo.petImageBase64}">
      </c:if>

     </div>

     <div class="col-md-6 main-search-right">

      <h3 class="mt-2">
       <c:out value="${ownerInfo.petNickName}" />
      </h3>

      <h6 class="mt-3">都道府県市町村&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:out value="${ownerInfo.streetAddress}" />
      </p>

      <h6 class="mt-2">ペットの性別&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:out value="${ownerInfo.petSex}" />
      </p>

      <h6 class="mt-2">ペットの種別&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:out value="${ownerInfo.petType}" />
      </p>

      <h6 class="mt-2">ペットの体重&ndash;&#13199;&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:out value="${ownerInfo.petWeight}" />
      </p>

      <h6 class="mt-2">備考&#058;</h6>

      <p class="mt-1 pb-2 main-search-right-underline">
       <c:set var="petRemarks" value="${ownerInfo.petRemarks}" />
       <c:forEach var="petRemarksStr" items="${fn:split(petRemarks, newLine)}">
        <c:out value="${petRemarksStr}" />
        <br />
       </c:forEach>
      </p>

     </div>

    </div>

    <div class="row mt-3 pb-3">

     <div class="col text-center">

      <div class="form-group form-common">
       <input type="submit" class="btn btn-primary is-show-dmessage-trimmer" value="飼い主へダイレクトメッセージ">
      </div>

     </div>

    </div>

   </c:if>

  </div>

 </main>

 <!--フッター-->
 <footer>
  <div class="d-flex justify-content-end pr-3 footer-top">
   <a class="move-page-top" href="#"> <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン">
    <strong>AniMatchの上部へ戻る</strong>
   </a>
  </div>

  <%@ include file="/WEB-INF/jsp/footer01.jsp"%>

  <%@ include file="/WEB-INF/jsp/footer02.jsp"%>
 </footer>
 <script src="/animatch/scripts/detail.js"></script>
 <script src="/animatch/scripts/common.js"></script>
</body>

</html>