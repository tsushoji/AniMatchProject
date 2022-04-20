package com.web01.animatch.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IndexControllerクラス
 * @author Tsuji
 * @version 1.0
 */
public class IndexController extends HttpServlet {

 //定数
 /**
  * シリアライズバージョンID
  */
 private static final long serialVersionUID = 1L;

 /**
  * デフォルトコンストラクタ
  */
 public IndexController() {
  super();
 }

 /**
  * get送信
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  String path = "/WEB-INF/jsp/index.jsp";
  RequestDispatcher dispatcher = request.getRequestDispatcher(path);
  dispatcher.forward(request, response);
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
