<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="petTypeKeyInitStr" value="pet.type." />
<c:set var="petSexKeyInitStr" value="pet.sex." />
<c:set var="firstTypeKeyInitEnd" value="001" />
<c:set var="secondTypeKeyInitEnd" value="002" />

<c:if test="${searchType == firstTypeKeyInitEnd}">
	<c:set var="labelTitlePart" value="お店" />
</c:if>
<c:if test="${searchType == secondTypeKeyInitEnd}">
	<c:set var="labelTitlePart" value="お住まい" />
</c:if>

<!DOCTYPE html>
<html>

	<head>
		<title>search</title>

		<%@ include file="/WEB-INF/jsp/head.jsp" %>

		<!-- plugin multipicker -->
    	<link rel="stylesheet" type="text/css" href="/animatch/styles/plugin/multipicker.min.css">

		<link rel="stylesheet" type="text/css" href="/animatch/styles/search.css" />

		<link rel="stylesheet" type="text/css" href="/animatch/styles/common.css" />
    </head>

	<body>

		<fmt:setBundle basename="animatch" var="resource"/>

		<!--ヘッダー-->
    	<header>
			<jsp:include page="/WEB-INF/jsp/header.jsp"/>
		</header>


	    <!--メイン-->
	    <main>

	        <div class="container-fluid">

	            <div class="row">
	                <div class="col">
	                	<c:if test="${searchType == firstTypeKeyInitEnd}">
	                		<h1 class="mt-2"><img src="/animatch/images/icon_pad.png" alt="犬アイコン" class="main-search-header-img">飼い主専用</h1>
	                	</c:if>
	                	<c:if test="${searchType == secondTypeKeyInitEnd}">
	                		<h1 class="mt-2"><img src="/animatch/images/icon_beauty_salon.png" alt="お店アイコン" class="main-search-header-img">お店専用</h1>
	                	</c:if>
	                    <p><span><c:out value="${searchCount}"/></span>件見つかりました。</p>
	                </div>
	            </div>

	            <div class="row">

	                <div class="col-xl-3 mr-0 mb-xl-3 main-search-left">

	                    <h5 class="mt-3 px-0">絞り込み</h5>

	                    <form class="main-search-left-form">

	                        <div class="form-group mt-3">
	                            <label class="control-label" for="prefectures">${labelTitlePart}&ndash;都道府県</label>
	                            <select name="prefectures" id="prefectures" class="custom-select form-control">
	                                <option value="000">未選択</option>
	                                <c:forEach items="${prefecturesKeyList}" var="prefecturesKey" varStatus="status">
	                       				<fmt:formatNumber var="prefecturesKeyNum" value="${status.index + 1}" minIntegerDigits="3" />
										<fmt:message bundle="${resource}" key="${prefecturesKey}" var="prefecturesVal" />
	                       				<option value="${prefecturesKeyNum}">
	                       					${prefecturesVal}
	                       				</option>
	                   				</c:forEach>
	                            </select>
	                        </div>

	                        <div class="form-group">
	                            <label class="control-label" for="cities">${labelTitlePart}&ndash;市町村</label>
	                            <select name="cities" id="cities" class="custom-select form-control">
	                                <option value="000">未選択</option>
	                            </select>
	                        </div>

							<c:if test="${searchType == firstTypeKeyInitEnd}">
								<div class="form-group">
	                            	<label class="control-label">営業日</label>
	                            	<div>
	                                	<ul class="plugin-multipicker">
	                                    	<c:forEach items="${weekdayKeyList}" var="weekdayKey">
												<fmt:message bundle="${resource}" key="${weekdayKey}" var="weekdayVal" />
					                       		<li>${fn:substringBefore(weekdayVal, '曜日')}</li>
					                   		</c:forEach>
	                                	</ul>
	                            	</div>
	                        	</div>

	                        	<div class="form-group">
	                            	<label class="control-label">開始時間</label>
	                            	<input type="time" name="form-start-time" class="form-control">
	                        	</div>

	                        	<div class="form-group">
	                            	<label class="control-label">終了時間</label>
	                            	<input type="time" name="form-end-time" class="form-control">
	                        	</div>
							</c:if>

							<c:if test="${searchType == secondTypeKeyInitEnd}">
								<div class="form-group mt-3">
	                            	<label class="control-label">動物&ndash;種別</label>
	                            	<select name="type-pet" class="custom-select form-control">
	                                	<option value="000">未選択</option>
	                                	<c:forEach items="${petTypeKeyList}" var="petTypeKey" varStatus="status">
                        					<fmt:formatNumber var="petTypeKeyNum" value="${status.index + 1}" minIntegerDigits="3" />
											<fmt:message bundle="${resource}" key="${petTypeKey}" var="petTypeVal" />
                        					<option value="${petTypeKeyNum}">
                        						${petTypeVal}
                        					</option>
                    					</c:forEach>
	                            	</select>
	                        	</div>
							</c:if>

	                        <div class="form-group">
	                            <a class="main-search-left-clear">リセット</a>
	                        </div>

	                        <div class="form-group">
	                            <input type="submit" class="btn btn-outline-primary col-xl-12 col-sm-6 main-search-left-select-btn" value="絞り込む">
	                        </div>

	                    </form>

	                </div>

	                <div class="col-xl-9 ml-0 mb-3 px-0 main-search-right">

	                    <form class="main-search-right-form">

	                        <div class="input-group my-3 col-11">
	                            <input type="text" class="form-control" placeholder="キーワードを入力">
	                            <span class="input-group-btn">
	                                <button type="button" class="btn btn-outline-primary"><img src="/animatch/images/icon_search.png" alt="検索アイコン"></button>
	                            </span>
	                        </div>

	                    </form>

						<c:if test="${not empty trimmerInfoList and searchType == firstTypeKeyInitEnd}">

							<c:forEach items="${trimmerInfoList}" var="trimmerInfo">

	                     		<div class="row main-search-right-list is-show-details-owner">

			                        <div class="col-3 main-search-right-list-img">
			                        	<c:if test="${not empty trimmerInfo.storeImageBase64}">
			                            	<img src="data:image/png;base64,${trimmerInfo.storeImageBase64}" class="resize-img-base64">
			                        	</c:if>
			                        </div>

			                        <div class="col-9">

			                            <h6 class="mt-2"><a href="/animatch/member/detail/owner"><c:out value="${trimmerInfo.storeName}"/></a></h6>

			                            <div class="row mt-2">

			                                <div class="col-sm-4 p-0">
			                                    <c:out value="${trimmerInfo.streetAddress}"/>
			                                </div>

			                            </div>

			                            <p class="mt-2"><c:out value="${trimmerInfo.storeCommitment}"/></p>

			                        </div>

			                    </div>

	                 		</c:forEach>

						</c:if>

						<c:if test="${not empty ownerInfoList and searchType == secondTypeKeyInitEnd}">

							<c:forEach items="${ownerInfoList}" var="ownerInfo">

								<div class="row main-search-right-list is-show-details-trimmer">

			                        <div class="col-3 main-search-right-list-img">
			                        	<c:if test="${not empty ownerInfo.petImageBase64}">
			                            	<img src="data:image/png;base64,${ownerInfo.petImageBase64}" class="resize-img-base64">
			                        	</c:if>
			                        </div>

			                        <div class="col-9">

			                            <h6 class="mt-2"><a href="/animatch/member/detail/trimmer"><c:out value="${ownerInfo.petNickName}"/></a></h6>

			                            <div class="row mt-2">

			                                <div class="col-sm-4 p-0">
			                                    <c:out value="${ownerInfo.streetAddress}"/>
			                                </div>

											<div class="col-sm-3 p-0">
			                                	<c:if test="${not empty ownerInfo.petType}">
			                                		<fmt:message bundle="${resource}" key="${petTypeKeyInitStr}${ownerInfo.petType}" var="ownerInfoPetTypeVal" />
			                                    	<c:out value="${ownerInfoPetTypeVal}"/>
			                                    </c:if>
			                                </div>

			                                <div class="col-sm-3 p-0">
			                                	<c:if test="${not empty ownerInfo.petSex}">
			                                		<fmt:message bundle="${resource}" key="${petSexKeyInitStr}${ownerInfo.petSex}" var="ownerInfoPetSexVal" />
			                                    	<c:out value="${ownerInfoPetSexVal}"/>
			                                    </c:if>
			                                </div>

			                            </div>

			                            <p class="mt-2"><c:out value="${ownerInfo.petRemarks}"/></p>

			                        </div>

			                    </div>

		                    </c:forEach>

						</c:if>

	                </div>

	            </div>

	            <nav aria-label="ページ送りの実例">
	                <ul class="pagination justify-content-center pb-3 mb-0">
	                  <li class="page-item"><a class="page-link" href="#">前へ</a></li>
	                  <li class="page-item"><a class="page-link" href="#">1</a></li>
	                  <li class="page-item"><a class="page-link" href="#">2</a></li>
	                  <li class="page-item"><a class="page-link" href="#">3</a></li>
	                  <li class="page-item"><a class="page-link" href="#">次へ</a></li>
	                </ul>
	            </nav>

	        </div>

	    </main>

	    <!--フッター-->
	    <footer>
	        <div class="d-flex justify-content-end pr-3 footer-top">
	            <a class="move-page-top" href="#">
	                <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン"> <strong>AniMatchの上部へ戻る</strong>
	            </a>
	        </div>

	        <%@ include file="/WEB-INF/jsp/footer01.jsp" %>

	        <%@ include file="/WEB-INF/jsp/footer02.jsp" %>
	    </footer>

		<!-- plugin multipicker -->
	    <script src="/animatch/scripts/plugin/multipicker.min.js"></script>

		<script src="/animatch/scripts/common.js"></script>
	    <script src="/animatch/scripts/search.js"></script>

	    <script>
	        $(document).ready(function(){
	            //plugin multipicker
	            $('.plugin-multipicker').multiPicker({
	                'selector' : 'li'
	            });
	        });
	    </script>

	</body>

</html>