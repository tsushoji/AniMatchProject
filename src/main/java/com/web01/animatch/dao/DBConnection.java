package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.web01.animatch.service.PropertiesService;

/**
 * DBコネクションクラス
 * @author Tsuji
 * @version 1.0
 */
public class DBConnection {

 //メンバー
 /**
  * コネクションオブジェクト
  */
 private Connection con = null;
 /**
  * プロパティーサービスオブジェクト
  */
 private PropertiesService propertiesService;

 //定数
 /**
  * プロパティDBコネクションキー先頭文字列
  */
 private static final String DB_CONNECTION_KEY_INIT_STR = "db.connection.";
 /**
  * プロパティDBコネクションURLキー先頭文字列
  */
 private static final String DB_CONNECTION_KEY_URL_STR = "url";
 /**
  * プロパティDBコネクションユーザ名キー先頭文字列
  */
 private static final String DB_CONNECTION_KEY_USER_STR = "user";
 /**
  * プロパティDBコネクションパスワードキー先頭文字列
  */
 private static final String DB_CONNECTION_KEY_PASS_STR = "pass";

 /**
  * デフォルトコンストラクタ
  */
 public DBConnection() {
  this.propertiesService = new PropertiesService();
 }

 /**
  * DBコネクション取得
  * @return DBコネクションオブジェクト
  */
 public Connection getConnection() {
  try {
   Class.forName("com.mysql.cj.jdbc.Driver");
   String url = this.propertiesService.getValue(DB_CONNECTION_KEY_INIT_STR + DB_CONNECTION_KEY_URL_STR);
   String user = this.propertiesService.getValue(DB_CONNECTION_KEY_INIT_STR + DB_CONNECTION_KEY_USER_STR);
   String pass = this.propertiesService.getValue(DB_CONNECTION_KEY_INIT_STR + DB_CONNECTION_KEY_PASS_STR);
   con = DriverManager.getConnection(url, user, pass);
  } catch (SQLException | ClassNotFoundException e) {
   e.printStackTrace();
  }
  return con;
 }

 /**
  * DBオブジェクトを閉じる
  */
 public void close() {
  try {
   if (con != null) {
    con.close();
   }
  } catch (SQLException e) {
   e.printStackTrace();
  }
 }
}
