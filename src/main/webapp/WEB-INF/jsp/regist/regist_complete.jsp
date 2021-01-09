<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="registTypeKeyInitStr" value="regist.type." />
<c:set var="humanSexKeyInitStr" value="human.sex." />
<c:set var="prefecturesKeyInitStr" value="prefectures." />
<c:set var="petSexKeyInitStr" value="pet.sex." />
<c:set var="petTypeKeyInitStr" value="pet.type." />
<c:set var="weekdayKeyInitStr" value="weekday." />

<!DOCTYPE html>

<html>

	<head>
		<title>regist_complete</title>

		<%@ include file="/WEB-INF/jsp/head.jsp" %>

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
	                以下登録情報でアカウント作成いたしました。
	            </p>

				<div>
		            <label>登録区分</label>
		            <p><fmt:message bundle="${resource}" key="${registTypeKeyInitStr}${registType}" /></p>
		        </div>

	            <div>
	                <label>ユーザー名</label>
	                <p><c:out value="${user.userName}" default=" "/></p>
	            </div>

	            <div>
	                <label>パスワード</label>
	                <p><c:out value="${user.password}" default=" "/></p>
	            </div>

	            <div>
	                <label>性別</label>
	                <p><fmt:message bundle="${resource}" key="${humanSexKeyInitStr}${user.sex}" /></p>
	            </div>

	            <div>
	                <label>生年月日</label>
	                <p><fmt:formatDate type="DATE" pattern="yyyy-MM-dd" value="${user.birthday}" /></p>
	            </div>

	            <div>
	                <label>郵便番号</label>
	                <p><c:out value="${user.postalCode}" default=" "/></p>
	            </div>

				<div>
	                <label>住所</label>
	                <p><c:out value="${user.streetAddress}" default=" "/></p>
	            </div>

	            <div>
	                <label>メールアドレス</label>
	                <p><c:out value="${user.emailAddress}" default=" "/></p>
	            </div>

	            <div>
	                <label>電話番号</label>
	                <p><c:out value="${user.telephoneNumber}" default=" "/></p>
	            </div>

				<div>
	                <label>イメージ画像</label>
	                <p>
	                	<c:if test="${registType == '001'}">
							<img src="data:image/png;base64,${petImage}" class="resize-img-base64">
						</c:if>

						<c:if test="${registType == '002'}">
							<img src="data:image/png;base64,${storeImage}" class="resize-img-base64">
						</c:if>
	                </p>
	            </div>

	            <c:if test="${registType == '001'}">

	            	<div>
		                <label>ペットニックネーム</label>
		                <p><c:out value="${pet.nickName}" default=" "/></p>
		            </div>

		            <div>
		                <label>ペット性別</label>
		                <p><fmt:message bundle="${resource}" key="${petSexKeyInitStr}${pet.sex}" /></p>
		            </div>

		            <div>
		                <label>ペット種別</label>
		                <p><fmt:message bundle="${resource}" key="${petTypeKeyInitStr}${pet.type}" /></p>
		            </div>

		            <div>
		                <label>ペット体重</label>
		                <p><fmt:formatNumber value="${pet.weight}" /></p>
		            </div>

		            <div>
		                <label>備考</label>
		                <c:set var="petRemarks" value="${pet.remarks}" />
		                <p><c:out value="${fn:replace(petRemarks,'\\n', '&lt;br/&gt;')}" default=" " escapeXml="false"/></p>
		            </div>

				</c:if>

				<c:if test="${registType == '002'}">

					<div>
		                <label>店名</label>
		                <p><c:out value="${store.storeName}" default=" "/></p>
		            </div>

					<c:forEach items="${businessHoursList}" var="businessHours">
						<fmt:message bundle="${resource}" key="${weekdayKeyInitStr}${businessHours.businessDay}" var="businessDay" />

						<div>

			                <p>${businessDay}</p>

			                <div>
			                    <label>開始時間</label>
			                    <p>${businessHours.startBusinessTime.getHour()}:<fmt:formatNumber value="${businessHours.startBusinessTime.getMinute()}" minIntegerDigits="2" /></p>
			                </div>

			                <div>
			                    <label>終了時間</label>
			                    <p>${businessHours.endBusinessTime.getHour()}:<fmt:formatNumber value="${businessHours.endBusinessTime.getMinute()}" minIntegerDigits="2" /></p>
			                </div>

			                <div>
			                    <label>補足</label>
			                    <c:set var="businessHoursComplement" value="${businessHours.complement}" />
			                    <p><c:out value="${fn:replace(businessHoursComplement,'\\n', '&lt;br/&gt;')}" default=" " escapeXml="false"/></p>
			                </div>

			            </div>
		           	</c:forEach>

		            <div>
	                    <label>従業員数&ndash;人</label>
	                    <p><fmt:formatNumber value="${store.employeesNumber}" /></p>
	                </div>

	                <div>
		                <label>コース・値段</label>
		                <c:set var="storeCourseInfo" value="${store.courseInfo}" />
		                <p><c:out value="${fn:replace(storeCourseInfo,'\\n', '&lt;br/&gt;')}" default=" " escapeXml="false"/></p>
		            </div>

		            <div>
		                <label>こだわりポイント</label>
		                <c:set var="storeCommitment" value="${store.commitment}" />
		                <p><c:out value="${fn:replace(storeCommitment,'\\n', '&lt;br/&gt;')}" default=" " escapeXml="false"/></p>
		            </div>

				</c:if>

				<p><a class="btn btn-outline-info" type="submit" href="/animatch/index">ホームへ</a></p>
			</div>
	    </div>

	    <!--フッター-->
	    <footer>
	    	<div class="d-flex justify-content-end pr-3 footer-top">
			    <a class="move-page-top" href="#">
			        <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン"> <strong>AniMatchの上部へ戻る</strong>
			    </a>
			</div>

	    	<%@ include file="/WEB-INF/jsp/footer02.jsp" %>
	    </footer>

		<script src="/animatch/scripts/regist.js"></script>
	    <script src="/animatch/scripts/common.js"></script>
	</body>

</html>
