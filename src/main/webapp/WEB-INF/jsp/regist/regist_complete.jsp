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

<c:set var="newLine" value="${System.lineSeparator()}" />

<!DOCTYPE html>

<html>

	<head>
		<title>regist_complete</title>

		<%@ include file="/WEB-INF/jsp/head.jsp" %>

		<link rel="stylesheet" type="text/css" href="/animatch/styles/regist.css" />

		<link rel="stylesheet" type="text/css" href="/animatch/styles/common.css" />
    </head>

	<body>

		<fmt:setBundle basename="animatch" var="resource"/>

		<!--グリッドシステム-->
	    <div class="container">

	        <div class="mx-auto regist-complete-block">

	            <div>
	                <img src="/animatch/images/icon_animatch.png" alt="Animatchロゴ">
	            </div>

	            <h2>
	                &lt;&lt;登録結果&gt;&gt;
	            </h2>

	            <p>
	                以下登録情報でアカウント作成いたしました。
	            </p>

				<div>
		            <label class="regist-complete-item-name"> &lt;登録区分&gt;</label>
		            <p><fmt:message bundle="${resource}" key="${registTypeKeyInitStr}${registType}" /></p>
		        </div>

	            <div>
	                <label class="regist-complete-item-name"> &lt;ユーザー名&gt;</label>
	                <p><c:out value="${user.userName}" default=" "/></p>
	            </div>

	            <div>
	                <label class="regist-complete-item-name"> &lt;パスワード&gt;</label>
	                <p><c:out value="${user.password}" default=" "/></p>
	            </div>

	            <div>
	                <label class="regist-complete-item-name">&lt;性別&gt;</label>
	                <p>
	                	<c:if test="${user.sex != null}">
	                		<fmt:message bundle="${resource}" key="${humanSexKeyInitStr}${user.sex}" />
	                	</c:if>
	                </p>
	            </div>

	            <div>
	                <label class="regist-complete-item-name">&lt;生年月日&gt;</label>
	                <p><fmt:formatDate type="DATE" pattern="yyyy-MM-dd" value="${user.birthday}" /></p>
	            </div>

	            <div>
	                <label class="regist-complete-item-name">&lt;郵便番号&gt;</label>
	                <p><c:out value="${user.postalCode}" default=" "/></p>
	            </div>

				<div>
	                <label class="regist-complete-item-name">&lt;住所&gt;</label>
	                <p><c:out value="${user.streetAddress}" default=" "/></p>
	            </div>

	            <div>
	                <label class="regist-complete-item-name">&lt;メールアドレス&gt;</label>
	                <p><c:out value="${user.emailAddress}" default=" "/></p>
	            </div>

	            <div>
	                <label class="regist-complete-item-name">&lt;電話番号&gt;</label>
	                <p><c:out value="${user.telephoneNumber}" default=" "/></p>
	            </div>

				<div>
	                <label class="regist-complete-item-name">&lt;イメージ画像&gt;</label>
	                <p>
	                	<c:if test="${registType == '001'}">
	                		<c:if test="${petImage != null}">
								<img src="data:image/png;base64,${petImage}" class="resize-img-base64">
							</c:if>
						</c:if>

						<c:if test="${registType == '002'}">
							<c:if test="${storeImage != null}">
								<img src="data:image/png;base64,${storeImage}" class="resize-img-base64">
							</c:if>
						</c:if>
	                </p>
	            </div>

	            <c:if test="${registType == '001'}">

	            	<div>
		                <label class="regist-complete-item-name">&lt;ペットニックネーム&gt;</label>
		                <p><c:out value="${pet.nickName}" default=" "/></p>
		            </div>

		            <div>
		                <label class="regist-complete-item-name">&lt;ペット性別&gt;</label>
		                <p>
		                	<c:if test="${pet.sex != null}">
		                		<fmt:message bundle="${resource}" key="${petSexKeyInitStr}${pet.sex}" />
		                	</c:if>
		                </p>
		            </div>

		            <div>
		                <label class="regist-complete-item-name">&lt;ペット種別&gt;</label>
		                <p>
			                <c:if test="${pet.type != null}">
			                	<fmt:message bundle="${resource}" key="${petTypeKeyInitStr}${pet.type}" />
			                </c:if>
		                </p>
		            </div>

		            <div>
		                <label class="regist-complete-item-name">&lt;ペット体重&gt;</label>
		                <p>
			                <c:if test="${pet.weight != 0.0}">
			                	<fmt:formatNumber value="${pet.weight}" />
			                </c:if>
		                </p>
		            </div>

		            <div>
		                <label class="regist-complete-item-name">&lt;備考&gt;</label>
		                <c:set var="petRemarks" value="${pet.remarks}" />
		                <p><c:out value="${fn:replace(petRemarks, newLine, '<br/>')}" default=" " escapeXml="false"/></p>
		            </div>

				</c:if>

				<c:if test="${registType == '002'}">

					<div>
		                <label class="regist-complete-item-name">&lt;店名&gt;</label>
		                <p><c:out value="${store.storeName}" default=" "/></p>
		            </div>

					<div>
						<label class="regist-complete-item-name">&lt;営業日時&gt;</label>

						<c:forEach items="${businessHoursList}" var="businessHours">
							<fmt:message bundle="${resource}" key="${weekdayKeyInitStr}${businessHours.businessDay}" var="businessDay" />

							<div>

				                <p class="regist-complete-item-name under-line">${businessDay}</p>

				                <div>
				                    <label class="regist-complete-item-name">&lt;開始時間&gt;</label>
				                    <p>${businessHours.startBusinessTime.getHour()}:<fmt:formatNumber value="${businessHours.startBusinessTime.getMinute()}" minIntegerDigits="2" /></p>
				                </div>

				                <div>
				                    <label class="regist-complete-item-name">&lt;終了時間&gt;</label>
				                    <p>${businessHours.endBusinessTime.getHour()}:<fmt:formatNumber value="${businessHours.endBusinessTime.getMinute()}" minIntegerDigits="2" /></p>
				                </div>

				                <div>
				                    <label class="regist-complete-item-name">&lt;補足&gt;</label>
				                    <c:set var="businessHoursComplement" value="${businessHours.complement}" />
				                    <p><c:out value="${fn:replace(businessHoursComplement, newLine, '<br/>')}" default=" " escapeXml="false"/></p>
				                </div>

				            </div>
			           	</c:forEach>
					</div>


		            <div>
	                    <label class="regist-complete-item-name">&lt;従業員数&ndash;人&gt;</label>
	                    <p>
	                    	<c:if test="${store.employeesNumber != 0}">
	                    		<fmt:formatNumber value="${store.employeesNumber}" />
	                    	</c:if>
	                    </p>
	                </div>

	                <div>
		                <label class="regist-complete-item-name">&lt;コース・値段&gt;</label>
		                <c:set var="storeCourseInfo" value="${store.courseInfo}" />
		                <p><c:out value="${fn:replace(storeCourseInfo, newLine, '<br/>')}" default=" " escapeXml="false"/></p>
		            </div>

		            <div>
		                <label class="regist-complete-item-name">&lt;こだわりポイント&gt;</label>
		                <c:set var="storeCommitment" value="${store.commitment}" />
		                <p><c:out value="${fn:replace(storeCommitment, newLine, '<br/>')}" default=" " escapeXml="false"/></p>
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
