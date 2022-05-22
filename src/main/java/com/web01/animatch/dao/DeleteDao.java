package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DeleteDaoクラス
 * @author Tsuji
 * @version 1.0
 */
public class DeleteDao extends BaseDao {

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
 public DeleteDao(Connection con) {
  this.con = con;
  try {
   this.con.setAutoCommit(false);
  } catch (SQLException e) {
   e.printStackTrace();
  }
 }

 /**
  * 自動ログイン情報削除
  * @param autoLoginInfo 自動ログイン情報オブジェクト
  * @return DB更新した件数
  * 呼び出し元でトランザクション管理
  */
 public int deleteAutoLoginInfo(int userId) throws SQLException {
  List<HashMap<String, Object>> autoLoginDataList = new ArrayList<>();
  int result = 0;

  // t_auto_login
  String whereOfuserId = createWhereOfUserId(userId, autoLoginDataList);

  try (PreparedStatement pstmt = createDeleteStatement("t_auto_login", autoLoginDataList, whereOfuserId);) {

   result = pstmt.executeUpdate();

  } catch (SQLException e) {
   throw e;
  }

  return result;
 }

 /**
  * DELETESQLステートメントオブジェクト作成
  * @param tableName テーブル名
  * @param list SQLパラメータリスト
  * @param where where句
  * @return SQLステートメントオブジェクト
  */
 private PreparedStatement createDeleteStatement(String tableName, List<HashMap<String, Object>> list, String where) throws SQLException {
  String sql = "DELETE FROM " + tableName;

  sql += " WHERE " + where;

  PreparedStatement pstmt = this.con.prepareStatement(sql);

  for (int i = 0; i < list.size(); i++) {
   HashMap<String, Object> data = list.get(i);
   setSqlParameter(pstmt, (i + 1), data.get("value"), (Integer) data.get("dataType"));
  }

  return pstmt;
 }
}
