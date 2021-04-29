package com.web01.animatch.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

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
	 * 画面遷移ページ番号パラメータ名
	 */
	private static final String URLPARAM_NAME_TARGET_PAGE = "targetPage";
	/**
	 * 画面遷移ページ番号パラメータ名
	 */
	private static final String URLPARAM_NAME_START_PAGE = "startPage";

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
			String searchType = reqURL.substring(reqURL.lastIndexOf("/") + 1, reqURL.length());
			String tmpTargetPage = request.getParameter(URLPARAM_NAME_TARGET_PAGE);
			String tmpStartPage = request.getParameter(URLPARAM_NAME_START_PAGE);
			if((searchType.equals("owner") || searchType.equals("trimmer")) && StringUtils.isNotEmpty(tmpTargetPage) && StringUtils.isNumeric(tmpTargetPage) && StringUtils.isNumeric(tmpStartPage)) {
				SearchService searchService = new SearchService(searchType);
				if(searchService.setPageAttribute(request, Integer.parseInt(tmpTargetPage), Integer.parseInt(tmpStartPage))) {
					// ページリンクに使用
					request.setAttribute("requestURL", reqURL);
					String path = "/WEB-INF/jsp/member/search/search.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(path);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		//URLが誤っている場合、トップページへリダイレクト
		String redURL = "/animatch/index";
		response.sendRedirect(redURL);
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
