package com.web01.animatch.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.dto.UserSession;
import com.web01.animatch.service.AuthService;
import com.web01.animatch.service.SessionService;

/**
 * LogoutControllerクラス
 * @author Tsuji
 * @version 1.0
 */
public class LogoutController extends HttpServlet {

 //定数
 /**
  * シリアライズバージョンID
  */
 private static final long serialVersionUID = 1L;

 /**
  * デフォルトコンストラクタ
  */
 public LogoutController() {
  super();
 }

 /**
  * get送信
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  //ホーム画面へリダイレクト
  String URL = "/animatch/index";
  response.sendRedirect(URL);
 }

 /**
  * post送信
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  SessionService sessionService = new SessionService();
  UserSession userSession = (UserSession)sessionService.getBindingKeySessionValue(request, AuthService.USER_SESSION_KEY_NAME);
  if(userSession != null && userSession.getUserId() != null){
   new AuthService().deleteAutoLoginInfo(request, response, userSession.getUserId());
  }
  sessionService.invalidateSession(request);
  doGet(request, response);
 }
}
