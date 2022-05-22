package com.web01.animatch.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * クッキーサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class CookieService {

 //メンバー
 /**
  * リクエストオブジェクト
  */
 private HttpServletRequest request;

 // 定数
 /**
  * クッキー保存期間
  */
 public static final int SAVE_COOKIE_PERIOD = 60 * 60 * 24 * 14;

 /**
  * 引数付きコンストラクタ
  * @param request リクエストオブジェクト
  */
 public CookieService(HttpServletRequest request) {
  this.request = request;
 }

 /**
  * クッキー追加
  * @param response レスポンスオブジェクト
  * @param key 名前
  * @param value オブジェクト
  */
 public void addCookie(HttpServletResponse response, String key, String value) {
  Cookie cookieAry[] = this.request.getCookies();

  if(cookieAry != null) {
   for(int i = 0; i < cookieAry.length; i++) {
    if(cookieAry[i].getName().equals(key)) {
     cookieAry[i].setValue(value);
     return;
    }
   }
  }

  Cookie cookie = new Cookie(key, value);
  cookie.setMaxAge(SAVE_COOKIE_PERIOD);
  cookie.setPath("/");
  response.addCookie(cookie);
 }

 /**
  * クッキーを削除する
  * @param response レスポンスオブジェクト
  * @param key 名前
  */
 public void deleteCookie(HttpServletResponse response, String key) {
  Cookie cookieAry[] = this.request.getCookies();

  if(cookieAry != null) {
   for(int i = 0; i < cookieAry.length; i++) {
    if(cookieAry[i].getName().equals(key)) {
     Cookie cookie = cookieAry[i];
     cookie.setMaxAge(0);
     cookie.setPath("/");
     response.addCookie(cookie);
    }
   }
  }
 }

 /**
  * クッキーに存在するキーの値を取得
  * @param key キー
  * @return キーの値
  * 存在しない場合、null
  */
 public String getCookieValueWithKey(String key) {
  Cookie cookie[] = this.request.getCookies();

  if(cookie == null) {
   return null;
  }

  for(int i = 0; i < cookie.length; i++) {
   if(cookie[i].getName().equals(key)) {
    return cookie[i].getValue();
   }
  }

  return null;
 }
}
