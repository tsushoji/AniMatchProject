package com.web01.animatch.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.service.DetailService;

/**
 * MemberSearchDetailControllerクラス
 * @author Tsuji
 * @version 1.0
 */
public class MemberDetailController extends HttpServlet{

	//定数
	/**
	 * シリアライズバージョンID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ
	 */
    public MemberDetailController() {
        super();
    }

    /**
	 * get送信
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqURL = request.getRequestURI();
		String userId = reqURL.substring(reqURL.lastIndexOf("/") + 1, reqURL.length());
		String tmpReqURL = reqURL.substring(0, reqURL.lastIndexOf("/"));
		DetailService detailService = new DetailService(tmpReqURL.substring(tmpReqURL.lastIndexOf("/") + 1, tmpReqURL.length()));
		if(detailService.setDisplayAttribute(request, userId)) {
			String path = "/WEB-INF/jsp/member/detail/detail.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}else {
			//ホーム画面へリダイレクト
			String URL = "/animatch/index";
			response.sendRedirect(URL);
		}
	}
}
