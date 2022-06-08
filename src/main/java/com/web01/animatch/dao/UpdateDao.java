package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.web01.animatch.dto.AutoLoginInfo;
import com.web01.animatch.dto.User;

/**
 * UpdateDaoクラス
 * @author Tsuji
 * @version 1.0
 */
public class UpdateDao extends BaseDao {

 //メンバー
 /**
  * DBコネクションオブジェクト
  */
 private Connection con;

 /**
  * 引数付きコンストラクタ
  *
  * @param con DBコネクションオブジェクト
  */
 public UpdateDao(Connection con) {
  this.con = con;
  try {
   this.con.setAutoCommit(false);
  } catch (SQLException e) {
   e.printStackTrace();
  }
 }

 /**
  * 自動ログイン情報更新
  * @param autoLoginInfo 自動ログイン情報オブジェクト
  * @return DB更新した件数
  * 呼び出し元でトランザクション管理
  */
 public int updateAutoLoginInfo(AutoLoginInfo autoLoginInfo) throws SQLException {
  List<String> setColumnList = new ArrayList<>();
  List<HashMap<String, Object>> autoLoginDataList = new ArrayList<>();
  int result = 0;

  // t_auto_login
  setColumnList.add("token");
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getToken(), Types.VARCHAR));
  setColumnList.add("digest");
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getDigest(), Types.VARCHAR));
  setColumnList.add("updated_time");
  autoLoginDataList.add(createSqlParatemerMap(Timestamp.valueOf(autoLoginInfo.getUpdatedTime()), Types.TIMESTAMP));

  String whereOfuserId = createWhereOfUserId(autoLoginInfo.getUserId(), autoLoginDataList);

  try (PreparedStatement pstmt = createUpdateStatement("t_auto_login", setColumnList, autoLoginDataList, whereOfuserId);) {

   result = pstmt.executeUpdate();

  } catch (SQLException e) {
   throw e;
  }

  return result;
 }

 /**
  * 飼い主情報更新
  * @param user ユーザオブジェクト
  * @return DB更新した件数
  * 呼び出し元でトランザクション管理
  */
 public int updateOwnerInfo(User user) throws SQLException {
  List<String> setUserColumnList = new ArrayList<>();
  List<HashMap<String, Object>> userDataList = new ArrayList<>();
  int result = 0;

  // t_user
  setColumnList.add("token");
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getToken(), Types.VARCHAR));
  setColumnList.add("digest");
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getDigest(), Types.VARCHAR));
  setColumnList.add("updated_time");
  autoLoginDataList.add(createSqlParatemerMap(Timestamp.valueOf(autoLoginInfo.getUpdatedTime()), Types.TIMESTAMP));

  String whereOfuserId = createWhereOfUserId(autoLoginInfo.getUserId(), autoLoginDataList);

  try (PreparedStatement pstmt = createUpdateStatement("t_auto_login", setColumnList, autoLoginDataList, whereOfuserId);) {

   result = pstmt.executeUpdate();

  } catch (SQLException e) {
   throw e;
  }

  List<String> setPetColumnList = new ArrayList<>();
  List<HashMap<String, Object>> petDataList = new ArrayList<>();

  // t_pet
  setColumnList.add("token");
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getToken(), Types.VARCHAR));
  setColumnList.add("digest");
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getDigest(), Types.VARCHAR));
  setColumnList.add("updated_time");
  autoLoginDataList.add(createSqlParatemerMap(Timestamp.valueOf(autoLoginInfo.getUpdatedTime()), Types.TIMESTAMP));

  String whereOfuserId = createWhereOfUserId(autoLoginInfo.getUserId(), autoLoginDataList);

  try (PreparedStatement pstmt = createUpdateStatement("t_auto_login", setColumnList, autoLoginDataList, whereOfuserId);) {

   result = pstmt.executeUpdate();

  } catch (SQLException e) {
   throw e;
  }

  return result;
 }

 /**
  * UPDATESQLステートメントオブジェクト作成
  * @param tableName テーブル名
  * @param setColumnList 更新列リスト
  * @param list SQLパラメータリスト
  * @param where where句
  * @return SQLステートメントオブジェクト
  */
 private PreparedStatement createUpdateStatement(String tableName, List<String> setColumnList, List<HashMap<String, Object>> list,
   String where) throws SQLException {
  String sql = "UPDATE " + tableName + " SET";

  for (int i = 0; i < setColumnList.size(); i++) {
   if(i == 0) {
    sql += " " + setColumnList.get(i) + " = ? ";
   }else {
    sql += ", " + setColumnList.get(i) + " = ? ";
   }
  }

  sql += " WHERE " + where;

  PreparedStatement pstmt = this.con.prepareStatement(sql);

  for (int i = 0; i < list.size(); i++) {
   HashMap<String, Object> data = list.get(i);
   setSqlParameter(pstmt, (i + 1), data.get("value"), (Integer) data.get("dataType"));
  }

  return pstmt;
 }
}
