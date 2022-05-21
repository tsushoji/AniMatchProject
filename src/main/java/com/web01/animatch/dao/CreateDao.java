package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web01.animatch.dto.AutoLoginInfo;
import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.Pet;
import com.web01.animatch.dto.Store;
import com.web01.animatch.dto.User;

/**
 * CreateDaoクラス
 * @author Tsuji
 * @version 1.0
 */
public class CreateDao extends BaseDao {

 //メンバー
 /**
  * DBコネクションオブジェクト
  */
 private Connection con;

 //定数
 /**
  * IDデフォルト値
  */
 private static final int DEFAULT_ID = 1000000000;

 /**
  * 引数付きコンストラクタ
  *
  * @param con DBコネクションオブジェクト
  */
 public CreateDao(Connection con) {
  this.con = con;
  try {
   this.con.setAutoCommit(false);
  } catch (SQLException e) {
   e.printStackTrace();
  }
 }

 /**
  * 飼い主DB登録
  * @param user ユーザオブジェクト
  * @return DB登録成功失敗
  * 呼び出し元でトランザクション管理
  */
 public boolean registOwner(User user) throws SQLException {
  Pet pet = user.getPet();
  Integer petId = null;

  List<HashMap<String, Object>> userDataList = new ArrayList<>();
  List<HashMap<String, Object>> petDataList = new ArrayList<>();

  // t_pet
  petDataList.add(createSqlParatemerMap(null, Types.INTEGER));
  petDataList.add(createSqlParatemerMap(pet.getImage(), Types.BLOB));
  petDataList.add(createSqlParatemerMap(pet.getNickName(), Types.VARCHAR));
  petDataList.add(createSqlParatemerMap(pet.getSex(), Types.VARCHAR));
  petDataList.add(createSqlParatemerMap(pet.getType(), Types.VARCHAR));
  petDataList.add(createSqlParatemerMap(pet.getWeight(), Types.FLOAT));
  petDataList.add(createSqlParatemerMap(pet.getRemarks(), Types.VARCHAR));
  petDataList.add(createSqlParatemerMap(pet.getIsDeleted(), Types.INTEGER));
  petDataList.add(createSqlParatemerMap(Timestamp.valueOf(pet.getInsertedTime()), Types.TIMESTAMP));
  petDataList.add(createSqlParatemerMap(Timestamp.valueOf(pet.getUpdatedTime()), Types.TIMESTAMP));

  try (PreparedStatement pstmt = createInsetStatement("t_pet", petDataList, true);) {
   pstmt.executeUpdate();

   ResultSet rs = pstmt.getGeneratedKeys();
   // getGeneratedKeys()により、Auto_IncrementされたIDを取得する
   if (rs.next()) {
    petId = rs.getInt(1);
   }

  } catch (SQLException e) {
   throw e;
  }

  // t_user
  userDataList.add(createSqlParatemerMap(null, Types.INTEGER));
  userDataList.add(createSqlParatemerMap(user.getUserName(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getPassword(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getSex(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(new Date(user.getBirthday().getTime()), Types.DATE));
  userDataList.add(createSqlParatemerMap(user.getPostalCode(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getStreetAddress(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getEmailAddress(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getTelephoneNumber(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(petId, Types.INTEGER));
  userDataList.add(createSqlParatemerMap(Integer.valueOf(DEFAULT_ID), Types.INTEGER));
  userDataList.add(createSqlParatemerMap(user.getIsDeleted(), Types.INTEGER));
  userDataList.add(createSqlParatemerMap(Timestamp.valueOf(user.getInsertedTime()), Types.TIMESTAMP));
  userDataList.add(createSqlParatemerMap(Timestamp.valueOf(user.getUpdatedTime()), Types.TIMESTAMP));

  // Auto_IncrementされたIDをSQLPrameterに渡し、t_userをinsertする必要があるため、t_petをinsert後、t_userをinsert
  try (PreparedStatement pstmt = createInsetStatement("t_user", userDataList, false);) {

   pstmt.executeUpdate();

  } catch (SQLException e) {
   throw e;
  }

  return true;
 }

 /**
  * トリマーDB登録
  * @param user ユーザオブジェクト
  * @return DB登録成功失敗
  */
 public boolean registTrimmer(User user) throws SQLException {
  Store store = user.getStore();
  Integer storeId = null;
  List<BusinessHours> businessHoursList = user.getStore().getBusinessHoursList();

  List<HashMap<String, Object>> userDataList = new ArrayList<>();
  List<HashMap<String, Object>> storeDataList = new ArrayList<>();
  Map<Integer, List<HashMap<String, Object>>> businessHoursDataMap = new HashMap<>();

  // t_store
  storeDataList.add(createSqlParatemerMap(null, Types.INTEGER));
  storeDataList.add(createSqlParatemerMap(store.getImage(), Types.BLOB));
  storeDataList.add(createSqlParatemerMap(store.getStoreName(), Types.VARCHAR));
  storeDataList.add(createSqlParatemerMap(store.getEmployeesNumber(), Types.INTEGER));
  storeDataList.add(createSqlParatemerMap(store.getCourseInfo(), Types.VARCHAR));
  storeDataList.add(createSqlParatemerMap(store.getCommitment(), Types.VARCHAR));
  storeDataList.add(createSqlParatemerMap(store.getIsDeleted(), Types.INTEGER));
  storeDataList.add(createSqlParatemerMap(Timestamp.valueOf(store.getInsertedTime()), Types.TIMESTAMP));
  storeDataList.add(createSqlParatemerMap(Timestamp.valueOf(store.getUpdatedTime()), Types.TIMESTAMP));

  try (PreparedStatement pstmt = createInsetStatement("t_store", storeDataList, true);) {

   pstmt.executeUpdate();

   ResultSet rs = pstmt.getGeneratedKeys();
   // getGeneratedKeys()により、Auto_IncrementされたIDを取得する
   if (rs.next()) {
    storeId = rs.getInt(1);
   }
  } catch (SQLException e) {
   throw e;
  }

  // t_user、t_business_hours
  userDataList.add(createSqlParatemerMap(null, Types.INTEGER));
  userDataList.add(createSqlParatemerMap(user.getUserName(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getPassword(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getSex(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(new Date(user.getBirthday().getTime()), Types.DATE));
  userDataList.add(createSqlParatemerMap(user.getPostalCode(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getStreetAddress(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getEmailAddress(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(user.getTelephoneNumber(), Types.VARCHAR));
  userDataList.add(createSqlParatemerMap(Integer.valueOf(DEFAULT_ID), Types.INTEGER));
  userDataList.add(createSqlParatemerMap(storeId, Types.INTEGER));
  userDataList.add(createSqlParatemerMap(user.getIsDeleted(), Types.INTEGER));
  userDataList.add(createSqlParatemerMap(Timestamp.valueOf(user.getInsertedTime()), Types.TIMESTAMP));
  userDataList.add(createSqlParatemerMap(Timestamp.valueOf(user.getUpdatedTime()), Types.TIMESTAMP));

  if (businessHoursList != null) {
   for (int i = 0; i < businessHoursList.size(); i++) {
    List<HashMap<String, Object>> businessHoursDataList = new ArrayList<>();
    businessHoursDataList.add(createSqlParatemerMap(storeId, Types.INTEGER));
    businessHoursDataList
      .add(createSqlParatemerMap(businessHoursList.get(i).getBusinessDay(), Types.VARCHAR));
    businessHoursDataList.add(createSqlParatemerMap(
      Time.valueOf(businessHoursList.get(i).getStartBusinessTime()), Types.TIME));
    businessHoursDataList.add(
      createSqlParatemerMap(Time.valueOf(businessHoursList.get(i).getEndBusinessTime()), Types.TIME));
    businessHoursDataList
      .add(createSqlParatemerMap(businessHoursList.get(i).getComplement(), Types.VARCHAR));
    businessHoursDataList
      .add(createSqlParatemerMap(businessHoursList.get(i).getIsDeleted(), Types.INTEGER));
    businessHoursDataList.add(createSqlParatemerMap(
      Timestamp.valueOf(businessHoursList.get(i).getInsertedTime()), Types.TIMESTAMP));
    businessHoursDataList.add(createSqlParatemerMap(
      Timestamp.valueOf(businessHoursList.get(i).getUpdatedTime()), Types.TIMESTAMP));
    businessHoursDataMap.put(i, businessHoursDataList);
   }
  }

  // Auto_IncrementされたIDをSQLPrameterに渡し、t_user、t_business_hoursをinsertする必要があるため、t_storeをinsert後、t_user、t_business_hoursをinsert
  try (PreparedStatement pstmt = createInsetStatement("t_user", userDataList, false);) {
   pstmt.executeUpdate();
  } catch (SQLException e) {
   throw e;
  }

  PreparedStatement pstmt = null;
  try {
   for (int i = 0; i < businessHoursDataMap.size(); i++) {
    pstmt = createInsetStatement("t_business_hours", businessHoursDataMap.get(i), false);
    pstmt.executeUpdate();
   }

  } catch (SQLException e) {
   throw e;
  } finally {
   if (pstmt != null) {
    pstmt.close();
   }
  }

  return true;
 }

 /**
  * 飼い主DB登録
  * @param autoLoginInfo 自動ログイン情報オブジェクト
  * @return DB登録成功失敗
  * 呼び出し元でトランザクション管理
  */
 public boolean registAutoLoginInfo(AutoLoginInfo autoLoginInfo) throws SQLException {

  List<HashMap<String, Object>> autoLoginDataList = new ArrayList<>();

  // t_auto_login
  autoLoginDataList.add(createSqlParatemerMap(null, Types.INTEGER));
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getUserId(), Types.INTEGER));
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getToken(), Types.VARCHAR));
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getDigest(), Types.VARCHAR));
  autoLoginDataList.add(createSqlParatemerMap(autoLoginInfo.getIsDeleted(), Types.INTEGER));
  autoLoginDataList.add(createSqlParatemerMap(Timestamp.valueOf(autoLoginInfo.getInsertedTime()), Types.TIMESTAMP));
  autoLoginDataList.add(createSqlParatemerMap(Timestamp.valueOf(autoLoginInfo.getUpdatedTime()), Types.TIMESTAMP));

  try (PreparedStatement pstmt = createInsetStatement("t_auto_login", autoLoginDataList, true);) {
   pstmt.executeUpdate();

  } catch (SQLException e) {
   throw e;
  }

  return true;
 }

 /**
  * INSERTSQLステートメントオブジェクト作成
  * @param tableName テーブル名
  * @param list SQLパラメータリスト
  * @param isRtnGeneratedKeys 生成されたキーを検索可能にするか
  * @return SQLステートメントオブジェクト
  */
 private PreparedStatement createInsetStatement(String tableName, List<HashMap<String, Object>> list,
   boolean isRtnGeneratedKeys) throws SQLException {
  String sql = "INSERT INTO " + tableName + " VALUES";

  ArrayList<String> params = new ArrayList<>();
  for (int i = 0; i < list.size(); i++) {
   params.add("?");
  }
  sql += "(" + String.join(",", params) + ")";

  PreparedStatement pstmt = null;
  if (isRtnGeneratedKeys) {
   pstmt = this.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
  } else {
   pstmt = this.con.prepareStatement(sql);
  }

  for (int i = 0; i < list.size(); i++) {
   HashMap<String, Object> data = list.get(i);
   setSqlParameter(pstmt, (i + 1), data.get("value"), (Integer) data.get("dataType"));
  }

  return pstmt;
 }
}
