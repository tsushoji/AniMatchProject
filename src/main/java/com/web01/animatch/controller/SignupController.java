package com.web01.animatch.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.service.SignupService;

/**
 * SignupControllerクラス
 * @author Tsuji
 * @version 1.0
 */
@MultipartConfig
public class SignupController extends HttpServlet {

	//定数
	/**
	 * シリアライズバージョンID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ
	 */
    public SignupController() {
        super();
    }

    /**
	 * get送信
	 * @param request リクエストオブジェクト
	 * @param response レスポンスオブジェクト
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SignupService signupService = new SignupService();
		signupService.setInitPropertiesKey(request);
		String path = "/WEB-INF/jsp/signup/signup.jsp";
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
		SignupService signupService = new SignupService(registType);
		if(signupService.regist(request)) {
			String path = "/WEB-INF/jsp/signup/signup_complete.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}else {
			//登録に失敗した場合
			doGet(request, response);
		}
	}
}
