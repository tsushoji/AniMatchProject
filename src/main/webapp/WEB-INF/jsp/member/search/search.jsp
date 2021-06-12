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
<c:set var="oneStr" value="1" />

<c:set var="newLine" value="${System.lineSeparator()}" />

<c:if test="${searchType == firstTypeKeyInitEnd}">
	<c:set var="labelTitlePart" value="お店" />
	<c:set var="paramBusinessHoursWeekday" value="&businessHoursWeekday=" />
	<c:set var="paramBusinessHoursStartTime" value="&businessHoursStartTime=" />
	<c:set var="paramBusinessHoursEndTime" value="&businessHoursEndTime=" />
</c:if>
<c:if test="${searchType == secondTypeKeyInitEnd}">
	<c:set var="labelTitlePart" value="お住まい" />
	<c:set var="paramPetType" value="&petType=" />
	<c:set var="paramPetSex" value="&petSex=" />
</c:if>
<c:set var="paramSearchContents" value="&searchContents=" />
<c:set var="paramTargetPage" value="?targetPage=" />
<c:set var="paramStartPage" value="&startPage=" />
<c:set var="paramPrefectures" value="&prefectures=" />
<c:set var="paramCities" value="&cities=" />
<c:set var="requestURLWithParam" value="${requestURL}${paramTargetPage}" />

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

				<form method="post" action="${requestURL}">

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

		                    <div class="main-search-left-form">

		                        <div class="form-group mt-3">
		                            <label class="control-label" for="prefectures">${labelTitlePart}&ndash;都道府県</label>
		                            <select name="prefectures" id="prefectures" class="custom-select form-control">
		                                <option value="000">未選択</option>
		                                <c:forEach items="${prefecturesMap}" var="prefecture">
		                       				<option value="${prefecture.key}"
		                       					<c:if test="${not empty searchForm.prefectures and searchForm.prefectures == prefecture.key}">selected</c:if>>
		                       					${prefecture.value}
		                       				</option>
		                   				</c:forEach>
		                            </select>
		                        </div>

		                        <div class="form-group">
		                            <label class="control-label" for="cities">${labelTitlePart}&ndash;市町村</label>
		                            <select name="cities" id="cities" class="custom-select form-control">
		                                <option value="000">未選択</option>
		                            </select>
		                            <input type="hidden" id="form-cities" value="<c:out value="${searchForm.cities}"/>">
		                        </div>

								<c:if test="${searchType == firstTypeKeyInitEnd}">
									<div class="form-group">
		                            	<label class="control-label">営業日</label>
		                            	<div>
		                                	<ul class="plugin-multipicker">
		                                    	<c:forEach items="${weekdayMap}" var="weekday">
		                                    		<c:set var="weekdayVal" value="${weekday.value}" />
						                       		<li>${fn:substringBefore(weekdayVal, '曜日')}</li>
						                   		</c:forEach>
		                                	</ul>
		                                	<input type="hidden" id="form-business-hours" value="<c:out value="${searchForm.businessHoursInputValue}"/>">
		                            	</div>
		                        	</div>

		                        	<div class="form-group">
		                            	<label class="control-label">開始時間</label>
		                            	<input type="time" name="form-start-time" class="form-control" value="<c:out value="${searchForm.businessHoursStartTime}"/>">
		                        	</div>

		                        	<div class="form-group">
		                            	<label class="control-label">終了時間</label>
		                            	<input type="time" name="form-end-time" class="form-control" value="<c:out value="${searchForm.businessHoursEndTime}"/>">
		                        	</div>
								</c:if>

								<c:if test="${searchType == secondTypeKeyInitEnd}">
									<div class="form-group mt-3">
		                            	<label class="control-label">動物&ndash;種別</label>
		                            	<select name="type-pet" class="custom-select form-control">
		                                	<option value="000">未選択</option>
		                                	<c:forEach items="${petTypeMap}" var="petType">
	                        					<option value="${petType.key}"
	                        					<c:if test="${not empty searchForm.petType and searchForm.petType == petType.key}">selected</c:if>>
	                        						${petType.value}
	                        					</option>
	                    					</c:forEach>
		                            	</select>
		                        	</div>

									<div class="form-group mt-3">
		                            	<label class="control-label">動物&ndash;性別</label>
		                            	<select name="sex-pet" class="custom-select form-control">
		                                	<option value="000">未選択</option>
		                                	<c:forEach items="${petSexMap}" var="petSex">
	                        					<option value="${petSex.key}"
	                        					<c:if test="${not empty searchForm.petSex and searchForm.petSex == petSex.key}">selected</c:if>>
	                        						${petSex.value}
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

		                    </div>

		                </div>

		                <div class="col-xl-9 ml-0 mb-3 px-0 main-search-right">

		                    <div class="main-search-right-form">

		                        <div class="input-group my-3 col-11">
		                            <input type="text" name="search-contents" class="form-control" placeholder="${searchType == firstTypeKeyInitEnd?'店舗名、アピールポイントについて、キーワードを入力':'ペット名、備考について、キーワードを入力' }" value="<c:out value="${searchForm.searchContents}"/>">
		                            <span class="input-group-btn">
		                                <button type="submit" class="btn btn-outline-primary"><img src="/animatch/images/icon_search.png" alt="検索アイコン"></button>
		                            </span>
		                        </div>

		                    </div>

							<c:if test="${not empty trimmerInfoList and searchType == firstTypeKeyInitEnd}">

								<c:forEach items="${trimmerInfoList}" var="trimmerInfo">

		                     		<div class="row main-search-right-list is-show-details-owner">

				                        <div class="col-3 main-search-right-list-img">
				                        	<c:if test="${not empty trimmerInfo.storeImageBase64}">
				                            	<img src="data:image/png;base64,${trimmerInfo.storeImageBase64}" class="resize-img-base64">
				                        	</c:if>
				                        </div>

				                        <div class="col-9">

				                            <h6 class="mt-2"><c:out value="${trimmerInfo.storeName}"/></h6>

				                            <div class="row mt-2 main-search-right-list-private-info-contents">

				                                <div class="col-sm-3 p-0">
				                                    <c:out value="${trimmerInfo.streetAddress}"/>
				                                </div>

												<c:if test="${not empty trimmerInfo.trimmerInfoBusinessHoursList}">
													<c:forEach items="${trimmerInfo.trimmerInfoBusinessHoursList}" var="trimmerInfoBusinessHours">
														<div class="col-sm-1 p-0">
						                                    <span class="mr-1"><c:out value="${trimmerInfoBusinessHours.displayBusinessHours}"/></span><c:out value="${trimmerInfoBusinessHours.displayStartBusinessTime}"/>&sim;<c:out value="${trimmerInfoBusinessHours.displayEndBusinessTime}"/>
						                                </div>
					                                </c:forEach>
				                                </c:if>

				                            </div>

				                            <p class="mt-2 line-break">
				                            	<c:set var="storeCommitment" value="${trimmerInfo.storeCommitment}" />
						                    	<c:forEach var="storeCommitmentStr" items="${fn:split(storeCommitment, newLine)}" >
													<c:out value="${storeCommitmentStr}"/><br/>
												</c:forEach>
				                            </p>

				                        </div>

										<input type="hidden" class="trimmer-user-id" value="${trimmerInfo.userId}">

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

				                            <h6 class="mt-2"><c:out value="${ownerInfo.petNickName}"/></h6>

				                            <div class="row mt-2 main-search-right-list-private-info-contents">

				                                <div class="col-sm-3 p-0">
				                                    <c:out value="${ownerInfo.streetAddress}"/>
				                                </div>

												<div class="col-sm-1 p-0">
				                                	<c:if test="${not empty ownerInfo.petType}">
				                                		<fmt:message bundle="${resource}" key="${petTypeKeyInitStr}${ownerInfo.petType}" var="ownerInfoPetTypeVal" />
				                                    	<c:out value="${ownerInfoPetTypeVal}"/>
				                                    </c:if>
				                                </div>

				                                <div class="col-sm-1 p-0">
				                                	<c:if test="${not empty ownerInfo.petSex}">
				                                		<fmt:message bundle="${resource}" key="${petSexKeyInitStr}${ownerInfo.petSex}" var="ownerInfoPetSexVal" />
				                                    	<c:out value="${ownerInfoPetSexVal}"/>
				                                    </c:if>
				                                </div>

				                            </div>

				                            <p class="mt-2 line-break">
				                            	<c:set var="petRemarks" value="${ownerInfo.petRemarks}" />
						                    	<c:forEach var="petRemarksStr" items="${fn:split(petRemarks, newLine)}" >
													<c:out value="${petRemarksStr}"/><br/>
												</c:forEach>
				                            </p>

				                        </div>

										<input type="hidden" class="owner-user-id" value="${ownerInfo.userId}">

				                    </div>

			                    </c:forEach>

							</c:if>

		                </div>

		            </div>

				</form>

	            <nav aria-label="ページ送りの実例">
	                <ul class="pagination justify-content-center pb-3 mb-0">
	                  <li id="page-item-pre" class="page-item"><a class="page-link">前へ</a></li>
	                  <fmt:formatNumber var="endPageNum" value="${endPage}"/>
	                  <c:choose>
						  <c:when test="endPageNum < 2">
						    <li class="page-item"><a class="page-link" href="${requestURLWithParam}${oneStr}${paramStartPage}${oneStr}">${oneStr}</a></li>
						  </c:when>
						  <c:otherwise>
						  	<fmt:formatNumber var="displayStartPageIndexNum" value="${displayStartPageIndex}"/>
							<fmt:formatNumber var="displayEndPageIndexNum" value="${displayEndPageIndex}"/>
							<c:set var="pageItemIDInitName" value="page-item-" />
			                <c:forEach begin="${displayStartPageIndexNum}" end="${displayEndPageIndexNum}" varStatus="status">
			                	<fmt:parseNumber var="pageLinkNumStr" value="${status.index}" />
			                	<c:choose>
				                	<c:when test="${searchType == firstTypeKeyInitEnd}">
				                		<li id="${pageItemIDInitName}${pageLinkNumStr}" class="page-item"><a class="page-link" href="${requestURLWithParam}${pageLinkNumStr}${paramStartPage}${displayStartPageIndexNum}${paramSearchContents}<c:out value="${searchForm.searchContents}"/>${paramPrefectures}<c:out value="${searchForm.prefectures}"/>${paramCities}<c:out value="${searchForm.cities}"/>${paramBusinessHoursWeekday}<c:out value="${searchForm.businessHoursInputValue}"/>${paramBusinessHoursStartTime}<c:out value="${searchForm.businessHoursStartTime}"/>${paramBusinessHoursEndTime}<c:out value="${searchForm.businessHoursEndTime}"/>">${status.index}</a></li>
				                	</c:when>
				                	<c:when test="${searchType == secondTypeKeyInitEnd}">
				                		<li id="${pageItemIDInitName}${pageLinkNumStr}" class="page-item"><a class="page-link" href="${requestURLWithParam}${pageLinkNumStr}${paramStartPage}${displayStartPageIndexNum}${paramSearchContents}<c:out value="${searchForm.searchContents}"/>${paramPrefectures}<c:out value="${searchForm.prefectures}"/>${paramCities}<c:out value="${searchForm.cities}"/>${paramPetType}<c:out value="${searchForm.petType}"/>${paramPetSex}<c:out value="${searchForm.petSex}"/>">${status.index}</a></li>
				                	</c:when>
				                	<c:otherwise>
				                		<li id="${pageItemIDInitName}${pageLinkNumStr}" class="page-item"><a class="page-link" href="${requestURLWithParam}${pageLinkNumStr}${paramStartPage}${displayStartPageIndexNum}">${status.index}</a></li>
				                	</c:otherwise>
			                	</c:choose>
			                 </c:forEach>
						  </c:otherwise>
					  </c:choose>
	                  <li id="page-item-next" class="page-item"><a class="page-link">次へ</a></li>
	                </ul>
	            </nav>
	            <input type="hidden" id="current-page" value="${currentPage}">
	            <input type="hidden" id="display-start-page-index" value="${displayStartPageIndexNum}">
	            <input type="hidden" id="display-end-page-index" value="${displayEndPageIndexNum}">
	            <input type="hidden" id="end-page" value="${endPage}">
	            <input type="hidden" id="request-url" value="${requestURL}">

	        </div>

	    </main>

	    <!--フッター-->
	    <footer>
	        <div class="d-flex justify-content-end pr-3 footer-top">
	            <a class="move-page-top cursor-pointer">
	                <img src="/animatch/images/icon_upmove.png" alt="トップへ戻るアイコン"> <strong>会員検索画面の上部へ戻る</strong>
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
	          	let formInputVal = $('#form-business-hours').val();
	          	if(formInputVal){
	          		//再ページ画面遷移時、入力値を「multipicker」にセット
	          		let formInputValAry = formInputVal.split(',');
	          		$('.plugin-multipicker').multiPicker({
	                    'selector' : 'li',
	                    'inputName' : 'business-hours'
	                }).multiPicker('select', formInputValAry);
	          	}else{
	          		$('.plugin-multipicker').multiPicker({
	                    'selector' : 'li',
	                    'inputName' : 'business-hours'
	                });
	          	}
	        });
	    </script>

	</body>

</html>