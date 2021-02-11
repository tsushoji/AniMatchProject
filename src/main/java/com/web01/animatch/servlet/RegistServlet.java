package com.web01.animatch.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.logic.RegistLogic;

/**
 * registControllerクラス
 * @author Tsuji
 * @version 1.0
 */
@MultipartConfig
public class RegistServlet extends HttpServlet {

	//定数
	/**
	 * シリアライズバージョンID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ
	 */
    public RegistServlet() {
        super();
    }

    /**
	 * get送信
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegistLogic registLogic = new RegistLogic();
		registLogic.setInitPropertiesKey(request);
		String path = "/WEB-INF/jsp/regist/regist.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	/**
	 * post送信
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//登録区分取得
		String registType = request.getParameter("regist-type");
		RegistLogic registLogic = new RegistLogic(registType);
		if(registLogic.regist(request)) {
			String path = "/WEB-INF/jsp/regist/regist_complete.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}else {
			//登録に失敗した場合
			doGet(request, response);
		}
	}
}
