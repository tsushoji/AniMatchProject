package com.web01.animatch.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		doGet(request, response);
	}
}
