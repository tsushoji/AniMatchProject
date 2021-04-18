package com.web01.animatch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
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
	 * 検索URLフォーマット
	 */
	private static final String URL_SEARCH_FORMAT = "^/animatch/member/search/(owner|trimmer)$";
	/**
	 * 検索パラメータURLフォーマット
	 */
	private static final String URL_PARAM_SEARCH_FORMAT = "^/animatch/member/search/(owner|trimmer)\\?*$";
	/**
	 * 日付フォーマット
	 */
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	/**
	 * 日付フォーマット
	 */
	private static final String TIME_FORMAT = "kk:mm";

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

		if(Pattern.matches(URL_SEARCH_FORMAT, reqURL)) {
			String path = "/WEB-INF/jsp/member/search/search.jsp";
			SearchService searchService = new SearchService(reqURL.substring(reqURL.lastIndexOf("/") + 1, reqURL.length()));
			searchService.setInit(request);
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}else {
			//URLが誤っている場合、トップページへリダイレクト
			String redURL = "/animatch/index";
			response.sendRedirect(redURL);
		}
	}

	/**
	 * post送信
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqURL = request.getRequestURI();

		//if(Pattern.matches(URL_PARAM_SEARCH_FORMAT, reqURL)) {
		if(true) {
			SearchService searchService = new SearchService(reqURL.substring(reqURL.lastIndexOf("/") + 1, reqURL.length()));
			String searchType = request.getParameter("searchType");
			int tarPage = Integer.parseInt(request.getParameter("tarPage"));

			ObjectMapper mapper = new ObjectMapper();
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			mapper.setDateFormat(df);
			mapper.registerModule(new JavaTimeModule().addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT))));

			// オブジェクトをJson文字列に変更
			String resJson = mapper.writeValueAsString(searchService.getSearchData(request, searchType, tarPage));

			// ヘッダ情報などセット
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "nocache");
			response.setCharacterEncoding("utf-8");

			// JSONを戻す
			PrintWriter out = response.getWriter();
			out.print(resJson);
		}
	}
}
