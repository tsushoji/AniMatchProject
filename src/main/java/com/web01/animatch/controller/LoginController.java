package com.web01.animatch.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.dto.UserSession;
import com.web01.animatch.service.AuthService;
import com.web01.animatch.service.SessionService;

/**
 * LoginControllerクラス
 * @author Tsuji
 * @version 1.0
 */
public class LoginController extends HttpServlet {

 //定数
 /**
  * シリアライズバージョンID
  */
 private static final long serialVersionUID = 1L;

 /**
  * デフォルトコンストラクタ
  */
 public LoginController() {
  super();
 }

 /**
  * get送信
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  String path = "/WEB-INF/jsp/login.jsp";
  RequestDispatcher dispatcher = request.getRequestDispatcher(path);
  dispatcher.forward(request, response);
 }

 /**
  * post送信
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  if(new AuthService().loginAuth(request, response)) {
   SessionService sessionService = new SessionService((HttpServletRequest)request);
   UserSession userSession = (UserSession)sessionService.getBindingKeySessionValue(AuthService.USER_SESSION_KEY_NAME);
   String loginedURL  = userSession.getLoginedURL();
   if(loginedURL == null) {
    loginedURL = "/animatch/index";
   }
   // ログイン完了後URLリセット
   userSession.setLoginedURL(null);
   response.sendRedirect(loginedURL);
  }else {
   doGet(request, response);
  }
 }
}
