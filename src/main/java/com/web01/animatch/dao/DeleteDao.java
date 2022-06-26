package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web01.animatch.dto.BusinessHours;

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
  * 営業時間DB削除
  * @param storeId 店舗ID
  * @param businessHoursList 営業時間リスト
  * @return DB更新した件数
  * 呼び出し元でトランザクション管理
  */
 public int deleteBusinessHours(int storeId, List<BusinessHours> businessHoursList) throws SQLException {
  Map<Integer, List<HashMap<String, Object>>> businessHoursDataMap = new HashMap<>();
  Map<Integer, String> businessHoursWhereMap = new HashMap<>();
  List<HashMap<String, Object>> businessHoursDataList = new ArrayList<>();
  int result = 0;

  // t_business_hours
  for (var i = 0; i < businessHoursList.size(); i++) {
   String weekNum = businessHoursList.get(i).getBusinessDay();
   String whereOfBusinessHours = createWhereOfBusinessHours(storeId, weekNum, businessHoursDataList);
   businessHoursDataMap.put(i, businessHoursDataList);
   businessHoursWhereMap.put(i, whereOfBusinessHours);
  }

  PreparedStatement pstmt = null;
  try {
   for (int i = 0; i < businessHoursWhereMap.size(); i++) {
    pstmt = createDeleteStatement("t_business_hours", businessHoursDataMap.get(i), businessHoursWhereMap.get(i));
    pstmt.executeUpdate();
   }

  } catch (SQLException e) {
   throw e;
  } finally {
   if (pstmt != null) {
    pstmt.close();
   }
  }

  return result;
 }

 /**
  * 営業時間Where句作成
  * @param storeId 店舗ID
  * @param weekNum 曜日番号
  * @param paramDataList SQLパラメータデータリスト
  * @return ユーザIDWhere句
  */
 protected String createWhereOfBusinessHours(int storeId, String weekNum, List<HashMap<String, Object>> paramDataList) {
  String whereOfBusinessHours = "store_id = ?";
  paramDataList.add(createSqlParatemerMap(storeId, Types.INTEGER));
  whereOfBusinessHours = createSqlClauseContent("business_day = ?", whereOfBusinessHours, LogicalOperatorType.AND);
  paramDataList.add(createSqlParatemerMap(weekNum, Types.VARCHAR));

  return whereOfBusinessHours;
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
