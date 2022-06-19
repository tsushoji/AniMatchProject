package com.web01.animatch.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.service.AccountChangeService;

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
  AccountChangeService.setFirstDisplay(request);

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
   String path = "/WEB-INF/jsp/account/change/change_complete.jsp";
   RequestDispatcher dispatcher = request.getRequestDispatcher(path);
   dispatcher.forward(request, response);
  } else {
   //登録に失敗した場合
   String path = "/WEB-INF/jsp/account/change/change.jsp";
   RequestDispatcher dispatcher = request.getRequestDispatcher(path);
   dispatcher.forward(request, response);
  }
 }
}
