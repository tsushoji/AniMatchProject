package com.web01.animatch.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * セッションサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class SessionService {

 //メンバー
 /**
  * リクエストオブジェクト
  */
 private HttpServletRequest request;

 /**
  * 引数付きコンストラクタ
  * @param request リクエストオブジェクト
  */
 public SessionService(HttpServletRequest request) {
  this.request = request;
 }

 /**
  * セッションにバインドする
  * @param key 名前
  * @param value オブジェクト
  * @return セッションバインド成功失敗
  */
 public boolean bindSession(String key, Object value) {
  HttpSession session = this.request.getSession(false);

  if(session == null) {
   session = this.request.getSession(true);
  }

  if(key == null) {
   return false;
  }

  session.setAttribute(key, value);
  return true;
 }

 /**
  * セッションを無効にする
  */
 public void invalidateSession() {
  HttpSession session = this.request.getSession(false);

  if(session != null) {
   session.invalidate();
  }
 }

 /**
  * セッションにバインドされているキーの値を取得
  * @param key セッションキー
  * @return セッションキーがセッションにバインドされている値
  * バインドされていない場合、null
  */
 public Object getBindingKeySessionValue(String key) {
  HttpSession session = this.request.getSession(false);

  if(session == null) {
   return null;
  }

  return session.getAttribute(key);
 }

 /**
  * キーがセッションにバインドされているか
  * @param key セッションキー
  * @return セッションキーがセッションにバインドされている場合、true
  * そうでない場合、false
  */
 public boolean isBindingKeySession(String key) {
  HttpSession session = this.request.getSession(false);

  if(session == null) {
   return false;
  }

  if(session.getAttribute(key) == null) {
   return false;
  }

  return true;
 }
}
