package com.web01.animatch.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.web01.animatch.dto.SearchForm;
import com.web01.animatch.service.SearchService;

/**
 * MemberSearchTrimmerControllerクラス
 * @author Tsuji
 * @version 1.0
 */
public class MemberSearchController extends HttpServlet {

	//定数
	/**
	 * シリアライズバージョンID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ページ番号パラメータ名
	 */
	private static final String URL_PARAM_NAME_TARGET_PAGE = "targetPage";
	/**
	 * ページリンク開始番号パラメータ名
	 */
	private static final String URL_PARAM_NAME_START_PAGE = "startPage";
	/**
	 * GET送信ユーザIDパラメータ名
	 */
	private static final String GET_URL_PARAM_NAME_USER_ID = "userId";
	/**
	 * POST送信ユーザIDパラメータ名
	 */
	private static final String POST_URL_PARAM_NAME_USER_ID = "user-id";
	/**
	 * 都道府県パラメータ名
	 */
	private static final String URL_PARAM_NAME_PREFECTURES = "prefectures";
	/**
	 * 市区町村パラメータ名
	 */
	private static final String URL_PARAM_NAME_CITIES = "cities";
	/**
	 * GET送信動物区分パラメータ名
	 */
	private static final String GET_URL_PARAM_NAME_PET_TYPE = "petType";
	/**
	 * POST送信動物区分パラメータ名
	 */
	private static final String POST_URL_PARAM_NAME_PET_TYPE = "type-pet";
	/**
	 * GET送信動物性別パラメータ名
	 */
	private static final String GET_URL_PARAM_NAME_PET_SEX = "petSex";
	/**
	 * POST送信動物性別パラメータ名
	 */
	private static final String POST_URL_PARAM_NAME_PET_SEX = "sex-pet";
	/**
	 * GET送信曜日パラメータ名
	 */
	private static final String GET_URL_PARAM_NAME_BUSINESS_HOURS_WEEKDAY = "businessHoursWeekday";
	/**
	 * POST送信曜日パラメータ名
	 */
	private static final String POST_URL_PARAM_NAME_BUSINESS_HOURS_WEEKDAY = "business-hours";
	/**
	 * GET送信開始時間パラメータ名
	 */
	private static final String GET_URL_PARAM_NAME_BUSINESS_HOURS_START_TIME = "businessHoursStartTime";
	/**
	 * POST送信開始時間パラメータ名
	 */
	private static final String POST_URL_PARAM_NAME_BUSINESS_HOURS_START_TIME = "businessHours-start-time";
	/**
	 * GET送信終了時間パラメータ名
	 */
	private static final String GET_URL_PARAM_NAME_BUSINESS_HOURS_END_TIME = "businessHoursEndTime";
	/**
	 * POST送信終了時間パラメータ名
	 */
	private static final String POST_URL_PARAM_NAME_BUSINESS_HOURS_END_TIME = "businessHours-end-time";
	/**
	 * GET送信検索内容パラメータ名
	 */
	private static final String GET_URL_PARAM_NAME_SEARCH_CONTENTS = "searchContents";
	/**
	 * POST送信検索内容パラメータ名
	 */
	private static final String POST_URL_PARAM_NAME_SEARCH_CONTENTS = "search-contents";

	/**
	 * デフォルト値
	 */
	private static final String SELECT_DEFAULT_VALUE = "000";


	/**
	 * デフォルトコンストラクタ
	 */
    public MemberSearchController() {
        super();
    }

    /**
	 * get送信
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setSearchDisplay(request, response, false);
	}

	/**
	 * post送信
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setSearchDisplay(request, response, true);
	}

	/**
	 * 検索結果画面セット
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 * @param isPost POST送信であるか否か
	 */
	protected void setSearchDisplay(HttpServletRequest request, HttpServletResponse response, boolean isPost) throws ServletException, IOException {
		String reqURL = request.getRequestURI();
		SearchService searchService = new SearchService(reqURL.substring(reqURL.lastIndexOf("/") + 1, reqURL.length()));
		int targetPage = 1;
		int startPage = 1;
		if(!isPost) {
			String tmpTargetPage = request.getParameter(URL_PARAM_NAME_TARGET_PAGE);
			String tmpStartPage = request.getParameter(URL_PARAM_NAME_START_PAGE);
			if(StringUtils.isNotEmpty(tmpTargetPage) && StringUtils.isNotEmpty(tmpStartPage) && StringUtils.isNumeric(tmpTargetPage) && StringUtils.isNumeric(tmpStartPage)) {
				targetPage = Integer.parseInt(tmpTargetPage);
				startPage = Integer.parseInt(tmpStartPage);
			}
		}

		String tmpUserId;
		if(isPost) {
			tmpUserId = request.getParameter(POST_URL_PARAM_NAME_USER_ID);
		}else {
			tmpUserId = request.getParameter(GET_URL_PARAM_NAME_USER_ID);
		}
		String tmpPrefectures = request.getParameter(URL_PARAM_NAME_PREFECTURES);
		String tmpCities = request.getParameter(URL_PARAM_NAME_CITIES);

		String tmpPetType;
		String tmpPetSex;
		if(isPost) {
			tmpPetType = request.getParameter(POST_URL_PARAM_NAME_PET_TYPE);
			tmpPetSex = request.getParameter(POST_URL_PARAM_NAME_PET_SEX);
		}else {
			tmpPetType = request.getParameter(GET_URL_PARAM_NAME_PET_TYPE);
			tmpPetSex = request.getParameter(GET_URL_PARAM_NAME_PET_SEX);
		}

		String tmpBusinessHoursWeekday;
		if(isPost) {
			tmpBusinessHoursWeekday = request.getParameter(POST_URL_PARAM_NAME_BUSINESS_HOURS_WEEKDAY);
		}else {
			tmpBusinessHoursWeekday = request.getParameter(GET_URL_PARAM_NAME_BUSINESS_HOURS_WEEKDAY);
		}
		String tmpBusinessHoursStartTime;
		if(isPost) {
			tmpBusinessHoursStartTime = request.getParameter(POST_URL_PARAM_NAME_BUSINESS_HOURS_START_TIME);
		}else {
			tmpBusinessHoursStartTime = request.getParameter(GET_URL_PARAM_NAME_BUSINESS_HOURS_START_TIME);
		}
		String tmpBusinessHoursEndTime;
		if(isPost) {
			tmpBusinessHoursEndTime = request.getParameter(POST_URL_PARAM_NAME_BUSINESS_HOURS_END_TIME);
		}else {
			tmpBusinessHoursEndTime = request.getParameter(GET_URL_PARAM_NAME_BUSINESS_HOURS_END_TIME);
		}

		String tmpSearchContents;
		if(isPost) {
			tmpSearchContents = request.getParameter(POST_URL_PARAM_NAME_SEARCH_CONTENTS);
		}else {
			tmpSearchContents = request.getParameter(GET_URL_PARAM_NAME_SEARCH_CONTENTS);
		}

		SearchForm searchForm = new SearchForm();
		if(StringUtils.isNotEmpty(tmpUserId)) {
			searchForm.setUserId(tmpUserId);
		}
		if(StringUtils.isNotEmpty(tmpPrefectures)) {
			searchForm.setPrefectures(tmpPrefectures);
		}else {
			searchForm.setPrefectures(SELECT_DEFAULT_VALUE);
		}

		if(StringUtils.isNotEmpty(tmpCities)) {
			searchForm.setCities(tmpCities);
		}else {
			searchForm.setCities(SELECT_DEFAULT_VALUE);
		}

		if(StringUtils.isNotEmpty(tmpPetType)) {
			searchForm.setPetType(tmpPetType);
		}else {
			searchForm.setPetType(SELECT_DEFAULT_VALUE);
		}

		if(StringUtils.isNotEmpty(tmpPetSex)) {
			searchForm.setPetSex(tmpPetSex);
		}else {
			searchForm.setPetSex(SELECT_DEFAULT_VALUE);
		}

		searchForm.setBusinessHoursInputValue(tmpBusinessHoursWeekday);

		searchForm.setBusinessHoursStartTime(tmpBusinessHoursStartTime);
		searchForm.setBusinessHoursEndTime(tmpBusinessHoursEndTime);

		searchForm.setSearchContents(tmpSearchContents);

		if(searchService.setPageAttribute(request, targetPage, startPage, searchForm)) {
			// ページリンクに使用
			request.setAttribute("requestURL", reqURL);
			String path = "/WEB-INF/jsp/member/search/search.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
	}
}
