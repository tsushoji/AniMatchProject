package com.web01.animatch.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web01.animatch.dto.UserSession;
import com.web01.animatch.service.AuthService;
import com.web01.animatch.service.SessionService;

/**
 * 認証フィルタークラス
 * @author Tsuji
 * @version 1.0
 */
public class AuthFilter implements Filter {

 /**
  * デフォルトコンストラクタ
  */
 public AuthFilter() {
 }

 /**
  * doFilterメソッド終了、タイムアウト後処理
  */
 public void destroy() {
 }

 /**
  * リクエスト/レスポンスのペアがチェーンを通過するたびにコンテナによって、呼び出される処理
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  * @param chain フィルターチェーンオブジェクト
  */
 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
  String path = ((HttpServletRequest) request).getServletPath();
  AuthService authService = new AuthService();
  SessionService sessionService = new SessionService();

  if(judgeSessionPageURL(path) && !(sessionService.isBindingKeySession((HttpServletRequest)request, AuthService.USER_SESSION_KEY_NAME)) && authService.autoLoginAuth((HttpServletRequest)request)) {
   authService.setUserSessionWithCookie((HttpServletRequest)request);
   chain.doFilter(request, response);
   return;
  }

  if(judgeAuthURL(path) && (sessionService.isBindingKeySession((HttpServletRequest)request, AuthService.USER_SESSION_KEY_NAME) == false || ((UserSession)sessionService.getBindingKeySessionValue((HttpServletRequest)request, AuthService.USER_SESSION_KEY_NAME)).getUserId() == null)) {
   if(authService.autoLoginAuth((HttpServletRequest)request)) {
    authService.setUserSessionWithCookie((HttpServletRequest)request);
    chain.doFilter(request, response);
    return;
   }else {
    UserSession userSession = new UserSession();
    userSession.setLoginedURL("/animatch/member/dmessage/list");
    sessionService.bindSession((HttpServletRequest)request, AuthService.USER_SESSION_KEY_NAME, userSession);
    //ログイン画面へリダイレクト
    String URL = "/animatch/login/";
    ((HttpServletResponse)response).sendRedirect(URL);
    return;
   }
  }
  chain.doFilter(request, response);
 }

 /**
  * フィルターがサービスに組み込まれるときに処理
  * @param chain フィルター設定オブジェクト
  */
 public void init(FilterConfig fConfig) throws ServletException {
 }

 /**
  * セッション制御ページURLパターン判定
  * @param path パス
  * @return セッション制御ページURLパターンである場合、true
  * そうでない場合、false
  */
 public boolean judgeSessionPageURL(String path) throws ServletException {
  if (path.equals("/index")) {
   return true;
  }

  if (path.equals("/member/search/owner")) {
   return true;
  }

  if (path.equals("/member/search/trimmer")) {
   return true;
  }

  if (path.startsWith("/member/detail/owner/")) {
   return true;
  }

  if (path.startsWith("/member/detail/trimmer/")) {
   return true;
  }

  return false;
 }

 /**
  * 認証必要URLパターン判定
  * @param path パス
  * @return 認証必要URLパターンである場合、true
  * そうでない場合、false
  */
 public boolean judgeAuthURL(String path) throws ServletException {
  if (path.equals("/member/dmessage/list")) {
   return true;
  }

  if (path.equals("/member/dmessage/detail/")) {
   return true;
  }

  return false;
 }
}
