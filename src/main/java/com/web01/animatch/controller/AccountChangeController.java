package com.web01.animatch.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.dto.UserSession;
import com.web01.animatch.service.AccountChangeService;
import com.web01.animatch.service.AuthService;
import com.web01.animatch.service.SessionService;

/**
 * AccountChangeControllerクラス
 * @author Tsuji
 * @version 1.0
 */
@MultipartConfig
public class AccountChangeController extends HttpServlet {

 //定数
 /**
  * シリアライズバージョンID
  */
 private static final long serialVersionUID = 1L;

 /**
  * デフォルトコンストラクタ
  */
 public AccountChangeController() {
  super();
 }

 /**
  * get送信
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  AccountChangeService AccountChangeService = new AccountChangeService();

  AccountChangeService.setInitPropertiesKey(request);
  UserSession userSession = new UserSession();
  SessionService sessionService = new SessionService((HttpServletRequest)request);
  userSession = (UserSession)sessionService.getBindingKeySessionValue(AuthService.USER_SESSION_KEY_NAME);
  Integer userId = userSession.getUserId();
  Integer petId = userSession.getPetId();
  Integer storeId = userSession.getStoreId();
  AccountChangeService.setRegistedInfoAttributeKey(request, userId, petId, storeId);

  String path = "/WEB-INF/jsp/account/change/change.jsp";
  RequestDispatcher dispatcher = request.getRequestDispatcher(path);
  dispatcher.forward(request, response);
 }

 /**
  * post送信
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  AccountChangeService AccountChangeService = new AccountChangeService(request.getParameter("formRegistType"));
  if (AccountChangeService.change(request)) {
   String path = "/WEB-INF/jsp/change/change_complete.jsp";
   RequestDispatcher dispatcher = request.getRequestDispatcher(path);
   dispatcher.forward(request, response);
  } else {
   //登録に失敗した場合
   doGet(request, response);
  }
 }
}
