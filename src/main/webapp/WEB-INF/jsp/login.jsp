<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<title>login</title>

<%@ include file="/WEB-INF/jsp/head.jsp"%>

<link rel="stylesheet" type="text/css" href="/animatch/styles/login.css" />

<link rel="stylesheet" type="text/css" href="/animatch/styles/common.css" />
</head>

<body class="text-center d-flex flex-column justify-content-center py-5">
 <div class="mx-auto">

  <img src="/animatch/images/icon_animatch.png" alt="Animatchロゴ">

  <form class="py-3" method="post" action="/animatch/login/">

   <input type="text" class="form-control" placeholder="ユーザーID" name="user-id" value="<c:out value="${loginForm.userId}"/>">
   <input type="password" class="form-control" placeholder="パスワード" name="password" value="<c:out value="${loginForm.password}"/>">

   <div class="text-left mt-2">
    <input type="checkbox" id="saved-username-check" class="" name="saved-username-check" value="1">
    <label for="saved-username-check" class="saved-username-check-label">ユーザーIDを保存する</label>
   </div>

   <c:if test="${not empty msgMap['001']}">
    <p>${msgMap["001"]}</p>
   </c:if>
   <c:if test="${not empty msgMap['002']}">
    <p>${msgMap["002"]}</p>
   </c:if>

   <c:if test="${not empty msgMap['003']}">
    <p>${msgMap["003"]}</p>
   </c:if>

   <c:if test="${not empty msgMap['004']}">
    <p>${msgMap["004"]}</p>
   </c:if>

   <input type="submit" class="btn btn-primary form-control" value="ログイン">

  </form>

  <div class="text-center py-3">
   <span class="mr-1">登録がお済でない方は</span><a href="/animatch/signup/" class="under-line"><strong>こちらへ</strong></a>
  </div>
 </div>

 <%@ include file="/WEB-INF/jsp/footer02.jsp"%>
</body>

</html>