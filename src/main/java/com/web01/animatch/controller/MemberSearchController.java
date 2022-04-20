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
  String reqURL = request.getRequestURI();
  SearchService searchService = new SearchService(reqURL.substring(reqURL.lastIndexOf("/") + 1, reqURL.length()));
  int targetPage = 1;
  int startPage = 1;
  String tmpTargetPage = request.getParameter("targetPage");
  String tmpStartPage = request.getParameter("startPage");
  if (StringUtils.isNotEmpty(tmpTargetPage) && StringUtils.isNotEmpty(tmpStartPage)
    && StringUtils.isNumeric(tmpTargetPage) && StringUtils.isNumeric(tmpStartPage)) {
   targetPage = Integer.parseInt(tmpTargetPage);
   startPage = Integer.parseInt(tmpStartPage);
  }

  SearchForm searchForm = new SearchForm();

  String tmpUserId = request.getParameter("userId");
  String tmpPrefectures = request.getParameter("prefectures");
  String tmpCities = request.getParameter("cities");

  String tmpPetType = request.getParameter("petType");
  String tmpPetSex = request.getParameter("petSex");

  String tmpBusinessHoursWeekday = request.getParameter("businessHoursWeekday");
  String tmpBusinessHoursStartTime = request.getParameter("businessHoursStartTime");
  String tmpBusinessHoursEndTime = request.getParameter("businessHoursEndTime");

  String tmpSearchContents = request.getParameter("searchContents");

  if (StringUtils.isNotEmpty(tmpUserId)) {
   searchForm.setUserId(tmpUserId);
  }
  if (StringUtils.isNotEmpty(tmpPrefectures)) {
   searchForm.setPrefectures(tmpPrefectures);
  } else {
   searchForm.setPrefectures(SELECT_DEFAULT_VALUE);
  }

  if (StringUtils.isNotEmpty(tmpCities)) {
   searchForm.setCities(tmpCities);
  } else {
   searchForm.setCities(SELECT_DEFAULT_VALUE);
  }

  if (StringUtils.isNotEmpty(tmpPetType)) {
   searchForm.setPetType(tmpPetType);
  } else {
   searchForm.setPetType(SELECT_DEFAULT_VALUE);
  }

  if (StringUtils.isNotEmpty(tmpPetSex)) {
   searchForm.setPetSex(tmpPetSex);
  } else {
   searchForm.setPetSex(SELECT_DEFAULT_VALUE);
  }

  searchForm.setBusinessHoursInputValue(tmpBusinessHoursWeekday);

  searchForm.setBusinessHoursStartTime(tmpBusinessHoursStartTime);
  searchForm.setBusinessHoursEndTime(tmpBusinessHoursEndTime);

  searchForm.setSearchContents(tmpSearchContents);

  if (searchService.setPageAttribute(request, targetPage, startPage, searchForm)) {
   // ページリンクに使用
   request.setAttribute("requestURL", reqURL);
   String path = "/WEB-INF/jsp/member/search/search.jsp";
   RequestDispatcher dispatcher = request.getRequestDispatcher(path);
   dispatcher.forward(request, response);
  }
 }
}
