package com.web01.animatch.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * セッションサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class SessionService {

 /**
  * セッションにバインドする
  * @param request リクエストオブジェクト
  * @param key 名前
  * @param value オブジェクト
  * @return セッションバインド成功失敗
  */
 public boolean bindSession(HttpServletRequest request, String key, Object value) {
  HttpSession session = request.getSession(false);

  if(session == null) {
   session = request.getSession(true);
  }

  if(key == null) {
   return false;
  }

  session.setAttribute(key, value);
  return true;
 }

 /**
  * セッションを無効にする
  * @param request リクエストオブジェクト
  */
 public void invalidateSession(HttpServletRequest request) {
  HttpSession session = request.getSession(false);

  if(session != null) {
   session.invalidate();
  }
 }

 /**
  * セッションにバインドされているキーの値を取得
  * @param request リクエストオブジェクト
  * @param key セッションキー
  * @return セッションキーがセッションにバインドされている値
  * バインドされていない場合、null
  */
 public Object getBindingKeySessionValue(HttpServletRequest request, String key) {
  HttpSession session = request.getSession(false);

  if(session == null) {
   return null;
  }

  return session.getAttribute(key);
 }

 /**
  * キーがセッションにバインドされているか
  * @param request リクエストオブジェクト
  * @param key セッションキー
  * @return セッションキーがセッションにバインドされている場合、true
  * そうでない場合、false
  */
 public boolean isBindingKeySession(HttpServletRequest request, String key) {
  HttpSession session = request.getSession(false);

  if(session == null) {
   return false;
  }

  if(session.getAttribute(key) == null) {
   return false;
  }

  return true;
 }
}
