package com.web01.animatch.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.web01.animatch.dao.CreateDao;
import com.web01.animatch.dao.DBConnection;
import com.web01.animatch.dao.DeleteDao;
import com.web01.animatch.dao.ReadDao;
import com.web01.animatch.dao.UpdateDao;
import com.web01.animatch.dto.AutoLoginInfo;
import com.web01.animatch.dto.LoginForm;
import com.web01.animatch.dto.User;
import com.web01.animatch.dto.UserSession;

/**
 * 認証サービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class AuthService {

 /**
  * メッセージマップ
  */
 private Map<String, String> msgMap;
 /**
  * メッセージサービスオブジェクト
  */
 private MessageService messageService;

 //定数
 /**
  * ユーザセッションキー名
  */
 public static final String USER_SESSION_KEY_NAME = "userSession";
 /**
  * ユーザIDクッキーキー名
  */
 public static final String USER_ID_COOKIE_KEY_NAME = "animatchUserId";
 /**
  * ユーザトークンクッキーキー名
  */
 public static final String USER_TOKEN_COOKIE_KEY_NAME = "animatchToken";
 /**
  * ユーザトークンクッキーキー名
  */
 public static final String MESSAGE_DIGEST_ALGORITHM_NAME = "SHA-1";

 /**
  * ログイン認証
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  * @return ログイン認証成功失敗
  */
 public boolean loginAuth(HttpServletRequest request, HttpServletResponse response) {
  LoginForm loginForm = getFormParameterDto(request);

  if(isValidate(loginForm)) {
   int userId = Integer.parseInt(loginForm.getUserId());
   if(!isAuth(request, userId, loginForm.getPassword())) {
    this.msgMap.put("004", this.messageService.getMessage(MessageService.MessageType.ERROR, "010", "ユーザーIDまたはパスワード"));
   }else {
    String savedUserInfo = loginForm.getSavedUserInfo();
    if(!(savedUserInfo != null && savedUserInfo.equals("saved-user-info"))) {
     deleteAutoLoginInfo(request, response, userId);
     return true;
    }
    if(registAutoLoginInfo(request, response, userId)) {
     return true;
    }
   }
   deleteAutoLoginInfo(request, response, userId);
  }

  setAttributeKeyWithCanNotValidate(request, loginForm);
  return false;
 }

 /**
  * 認証
  * @param request リクエストオブジェクト
  * @param userId ユーザID
  * @param password パスワード
  * @return 認証成功失敗
  */
 public boolean isAuth(HttpServletRequest request, int userId, String password) {
  boolean result = false;

  DBConnection con = new DBConnection();
  ReadDao readDao = new ReadDao(con.getConnection());

  try {
   List<User> userList = readDao.findUserByUserIdAndPassword(userId, password);
   if(userList.size() > 0) {
    User user = userList.get(0);
    UserSession userSession = new UserSession();
    userSession.setUserId(user.getUserId());
    userSession.setPetId(user.getPet().getPetId());
    userSession.setStoreId(user.getStore().getStoreId());
    SessionService sessionService = new SessionService();
    UserSession userSessionOld = (UserSession)sessionService.getBindingKeySessionValue((HttpServletRequest)request, AuthService.USER_SESSION_KEY_NAME);
    userSession.setLoginedURL(userSessionOld == null ? null : userSessionOld.getLoginedURL());
    new SessionService().bindSession(request, USER_SESSION_KEY_NAME, userSession);

    result = true;
   }
  }catch(SQLException e) {
   e.printStackTrace();
  }finally {
   con.close();
  }

   return result;
 }

 /**
  * 登録フォームパラメータ取得
  * @param request リクエストオブジェクト
  * @return 登録フォームオブジェクト
  */
 private LoginForm getFormParameterDto(HttpServletRequest request) {
  LoginForm loginForm = new LoginForm();
  loginForm.setUserId(request.getParameter("user-id"));
  loginForm.setPassword(request.getParameter("password"));
  loginForm.setSavedUserInfo(request.getParameter("saved-username-check"));
  return loginForm;
 }

 /**
  * バリデーションチェック
  * @param loginForm ログインフォームオブジェクト
  * @return チェック結果
  */
 private boolean isValidate(LoginForm loginForm) {
  this.msgMap = new HashMap<>();
  this.messageService = new MessageService();

  String userId = loginForm.getUserId();
  if(StringUtils.isEmpty(userId)) {
   this.msgMap.put("001", this.messageService.getMessage(MessageService.MessageType.ERROR, "005", "ユーザーID"));
  }else {
   if(!StringUtils.isNumeric(userId)) {
    this.msgMap.put("002", this.messageService.getMessage(MessageService.MessageType.ERROR, "001", "ユーザーID", "数値"));
   }
  }

  if(StringUtils.isEmpty(loginForm.getPassword())) {
   this.msgMap.put("003", this.messageService.getMessage(MessageService.MessageType.ERROR, "005", "パスワード"));
  }

  if(this.msgMap.size() > 0) {
   return false;
  }

  return true;
 }

 /**
  * バリデーションエラー属性設定
  * @param request リクエストオブジェクト
  * @param loginForm ログインフォームオブジェクト
  */
 private void setAttributeKeyWithCanNotValidate(HttpServletRequest request, LoginForm loginForm) {
  request.setAttribute("loginForm", loginForm);
  request.setAttribute("msgMap", this.msgMap);
 }

 /**
  * 自動ログイン認証
  * @param request リクエストオブジェクト
  * @return 自動ログイン認証成功失敗
  */
 public boolean autoLoginAuth(HttpServletRequest request) {
  CookieService cookieService = new CookieService();
  String tempUserId = cookieService.getCookieValueWithKey(request, USER_ID_COOKIE_KEY_NAME);
  int userId = tempUserId != null && StringUtils.isNumeric(tempUserId)?Integer.parseInt(tempUserId):0;
  if(userId == 0) {
   return false;
  }

  String token = cookieService.getCookieValueWithKey(request, USER_TOKEN_COOKIE_KEY_NAME);
  if(token == null) {
   return false;
  }

  DBConnection con = new DBConnection();
  ReadDao readDao = new ReadDao(con.getConnection());

  try {
   LocalDateTime targetDateTime = LocalDateTime.now().minusSeconds(CookieService.SAVE_COOKIE_PERIOD);
   AutoLoginInfo autoLoginInfo = readDao.findAutoLoginInfoByUserIdAndNow(userId, targetDateTime);
   if(autoLoginInfo != null) {
    MessageDigest messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM_NAME);
    String digest = String.format("%040x", new BigInteger(1, messageDigest.digest(token.getBytes())));
    if(autoLoginInfo.getDigest().equals(digest)) {
     return true;
    }
   }

  }catch(Exception e) {
   e.printStackTrace();
  }finally {
   con.close();
  }

  return false;
 }

 /**
  * 自動ログイン情報登録
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  * @param userId ユーザID
  * @return 自動ログイン登録成功失敗
  */
 private boolean registAutoLoginInfo(HttpServletRequest request, HttpServletResponse response, int userId) {
  DBConnection con = new DBConnection();
  UpdateDao updateDao = new UpdateDao(con.getConnection());
  CreateDao createDao = new CreateDao(con.getConnection());
  boolean result = false;

  try {
   AutoLoginInfo autoLoginInfo = new AutoLoginInfo();
   autoLoginInfo.setUserId(userId);
   String token = new TokenService().generateAutoAuthToken();
   autoLoginInfo.setToken(token);
   MessageDigest messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM_NAME);
   String digest = String.format("%040x", new BigInteger(1, messageDigest.digest(token.getBytes())));
   autoLoginInfo.setDigest(digest);
   LocalDateTime now = LocalDateTime.now();
   autoLoginInfo.setUpdatedTime(now);
   if(updateDao.updateAutoLoginInfo(autoLoginInfo) == 1) {
    result = true;
   }else {
    autoLoginInfo.setIsDeleted(0);
    autoLoginInfo.setInsertedTime(now);
    if(createDao.registAutoLoginInfo(autoLoginInfo)) {
     result = true;
    }
   }

   CookieService cookieService = new CookieService();
   if(result) {
    cookieService.addCookie(request, response, USER_ID_COOKIE_KEY_NAME, String.valueOf(userId));
    cookieService.addCookie(request, response, USER_TOKEN_COOKIE_KEY_NAME, token);
   }else {
    cookieService.deleteCookie(request, response, USER_ID_COOKIE_KEY_NAME);
    cookieService.deleteCookie(request, response, USER_TOKEN_COOKIE_KEY_NAME);
   }


  }catch(Exception e) {
   e.printStackTrace();
  }finally {
   con.close();
  }

  return result;
 }

 /**
  * 自動ログイン情報削除
  * @param request リクエストオブジェクト
  * @param response レスポンスオブジェクト
  * @param userId ユーザID
  * @return 自動ログイン削除成功失敗
  */
 public boolean deleteAutoLoginInfo(HttpServletRequest request, HttpServletResponse response, int userId) {
  DBConnection con = new DBConnection();
  DeleteDao deleteDao = new DeleteDao(con.getConnection());

  try {
   deleteDao.deleteAutoLoginInfo(userId);

   CookieService cookieService = new CookieService();
   cookieService.deleteCookie(request, response, USER_ID_COOKIE_KEY_NAME);
   cookieService.deleteCookie(request, response, USER_TOKEN_COOKIE_KEY_NAME);


  }catch(Exception e) {
   e.printStackTrace();
  }finally {
   con.close();
  }

  return true;
 }

 /**
  * クッキー情報からユーザセッション設定
  * @param request リクエストオブジェクト
  * @return 認証成功失敗
  */
 public boolean setUserSessionWithCookie(HttpServletRequest request) {
  boolean result = false;
  String userId = new CookieService().getCookieValueWithKey(request, USER_ID_COOKIE_KEY_NAME);
  if(userId == null || !StringUtils.isNumeric(userId)) {
   return result;
  }

  DBConnection con = new DBConnection();
  ReadDao readDao = new ReadDao(con.getConnection());

  try {
   List<User> userList = readDao.findUserByUserId(Integer.parseInt(userId));
   if(userList.size() > 0) {
    User user = userList.get(0);
    UserSession userSession = new UserSession();
    userSession = new UserSession();
    userSession.setUserId(user.getUserId());
    userSession.setPetId(user.getPet().getPetId());
    userSession.setStoreId(user.getStore().getStoreId());
    new SessionService().bindSession(request, USER_SESSION_KEY_NAME, userSession);

    result = true;
   }
  }catch(SQLException e) {
   e.printStackTrace();
  }finally {
   con.close();
  }

  return result;
 }
}
