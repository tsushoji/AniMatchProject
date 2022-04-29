package com.web01.animatch.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.web01.animatch.dao.DBConnection;
import com.web01.animatch.dao.ReadDao;
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
  * プロパティファイル名
  */
 public static final String USER_SESSION_KEY_NAME = "userSession";

 /**
  * ログイン認証
  * @param request リクエストオブジェクト
  * @return ログイン認証成功失敗
  */
 public boolean loginAuth(HttpServletRequest request) {
  LoginForm loginForm = getFormParameterDto(request);

  if(isValidate(loginForm)) {
   if(!isAuth(request, Integer.parseInt(loginForm.getUserId()), loginForm.getPassword())) {
    this.msgMap.put("004", this.messageService.getMessage(MessageService.MessageType.ERROR, "010", "ユーザーIDまたはパスワード"));
   }else {
    return true;
   }
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

 /**
  * 登録フォームパラメータ取得
  * @param request リクエストオブジェクト
  * @return 登録フォームオブジェクト
  */
 private LoginForm getFormParameterDto(HttpServletRequest request) {
  LoginForm loginForm = new LoginForm();
  loginForm.setUserId(request.getParameter("user-id"));
  loginForm.setPassword(request.getParameter("password"));

  return loginForm;
 }

 /**
  * バリデーションチェック
  * @param registForm ログインフォームオブジェクト
  * @return チェック結果
  */
 private boolean isValidate(LoginForm loginForm) {
  this.msgMap = new HashMap<>();
  this.messageService = new MessageService();

  String userId = loginForm.getUserId();
  if(StringUtils.isEmpty(userId)) {
   this.msgMap.put("001", this.messageService.getMessage(MessageService.MessageType.ERROR, "005", "ユーザーID"));
  }else {
   if(StringUtils.isNotEmpty(userId) && !StringUtils.isNumeric(userId)) {
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
  * @param registForm ログインフォームオブジェクト
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
  // 追加
  boolean result = false;
  int userId = 0;
  String token = null;

  DBConnection con = new DBConnection();
  ReadDao readDao = new ReadDao(con.getConnection());

  try {
   String digest = readDao.findDigestByUserId(userId);
   if(digest != null) {
    String userDigest = null;
    if(digest.equals(userDigest)) {
     result = true;
    }
   }

  }catch(SQLException e) {
   e.printStackTrace();
  }finally {
   con.close();
  }

  return result;
 }
}
