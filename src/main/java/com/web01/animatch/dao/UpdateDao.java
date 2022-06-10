package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.web01.animatch.dto.AutoLoginInfo;
import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.Pet;
import com.web01.animatch.dto.Store;
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
  int userResult = 0;

  // t_user
  String userName = user.getUserName();
  if(userName != null) {
   setUserColumnList.add("user_name");
   userDataList.add(createSqlParatemerMap(userName, Types.VARCHAR));
  }

  String password = user.getPassword();
  if(password != null) {
   setUserColumnList.add("password");
   userDataList.add(createSqlParatemerMap(password, Types.VARCHAR));
  }

  String sex = user.getSex();
  if(sex != null) {
   setUserColumnList.add("sex");
   userDataList.add(createSqlParatemerMap(sex, Types.VARCHAR));
  }

  Date birthday = user.getBirthday();
  if(birthday != null) {
   setUserColumnList.add("birthday");
   userDataList.add(createSqlParatemerMap(new Date(birthday.getTime()), Types.DATE));
  }

  String postalCode = user.getPostalCode();
  if(postalCode != null) {
   setUserColumnList.add("postal_code");
   userDataList.add(createSqlParatemerMap(postalCode, Types.VARCHAR));
  }

  String streetAddress = user.getStreetAddress();
  if(streetAddress != null) {
   setUserColumnList.add("street_address");
   userDataList.add(createSqlParatemerMap(streetAddress, Types.VARCHAR));
  }

  String emailAddress = user.getEmailAddress();
  if(emailAddress != null) {
   setUserColumnList.add("email_address");
   userDataList.add(createSqlParatemerMap(emailAddress, Types.VARCHAR));
  }

  String telephoneNum = user.getTelephoneNumber();
  if(telephoneNum != null) {
   setUserColumnList.add("telephone_number");
   userDataList.add(createSqlParatemerMap(telephoneNum, Types.VARCHAR));
  }

  LocalDateTime updatedTime = user.getUpdatedTime();

  if(setUserColumnList.size() > 0) {
   setUserColumnList.add("updated_time");
   userDataList.add(createSqlParatemerMap(Timestamp.valueOf(updatedTime), Types.TIMESTAMP));

   String whereOfuserId = createWhereOfUserId(user.getUserId(), userDataList);
   
   try (PreparedStatement pstmt = createUpdateStatement("t_user", setUserColumnList, userDataList, whereOfuserId);) {
  
    userResult = pstmt.executeUpdate();
 
   } catch (SQLException e) {
    throw e;
   }
  }


  List<String> setPetColumnList = new ArrayList<>();
  List<HashMap<String, Object>> petDataList = new ArrayList<>();
  int petResult = 0;

  Pet pet = user.getPet();

  // t_pet
  byte[] petImage = pet.getImage();
  if(petImage.length > 0) {
   setPetColumnList.add("image");
   petDataList.add(createSqlParatemerMap(petImage, Types.BLOB));
  }

  String nickName = pet.getNickName();
  if(nickName != null) {
   setPetColumnList.add("nickname");
   petDataList.add(createSqlParatemerMap(nickName, Types.VARCHAR));
  }

  String petSex = pet.getSex();
  if(petSex != null) {
   setPetColumnList.add("sex");
   petDataList.add(createSqlParatemerMap(petSex, Types.VARCHAR));
  }

  String petType = pet.getType();
  if(petType != null) {
   setPetColumnList.add("type");
   petDataList.add(createSqlParatemerMap(petType, Types.VARCHAR));
  }

  Float petWeight = pet.getWeight();
  if(petWeight != null) {
   setPetColumnList.add("weight");
   petDataList.add(createSqlParatemerMap(petWeight, Types.FLOAT));
  }

  String petRemarks = pet.getRemarks();
  if(petRemarks != null) {
   setPetColumnList.add("remarks");
   petDataList.add(createSqlParatemerMap(petRemarks, Types.VARCHAR));
  }

  if(setPetColumnList.size() > 0) {
   setPetColumnList.add("updated_time");
   petDataList.add(createSqlParatemerMap(Timestamp.valueOf(updatedTime), Types.TIMESTAMP));

   String whereOfpetId = createWhereOfPetId(pet.getPetId(), petDataList);
   
   try (PreparedStatement pstmt = createUpdateStatement("t_pet", setPetColumnList, petDataList, whereOfpetId);) {
  
    petResult = pstmt.executeUpdate();
 
   } catch (SQLException e) {
    throw e;
   }
  }

  return userResult + petResult;
 }

 /**
  * トリマー情報更新
  * @param user ユーザオブジェクト
  * @return DB更新した件数
  * 呼び出し元でトランザクション管理
  */
 public int updateTrimmerInfo(User user) throws SQLException {
  List<String> setUserColumnList = new ArrayList<>();
  List<HashMap<String, Object>> userDataList = new ArrayList<>();
  int userResult = 0;

  // t_user
  String userName = user.getUserName();
  if(userName != null) {
   setUserColumnList.add("user_name");
   userDataList.add(createSqlParatemerMap(userName, Types.VARCHAR));
  }

  String password = user.getPassword();
  if(password != null) {
   setUserColumnList.add("password");
   userDataList.add(createSqlParatemerMap(password, Types.VARCHAR));
  }

  String sex = user.getSex();
  if(sex != null) {
   setUserColumnList.add("sex");
   userDataList.add(createSqlParatemerMap(sex, Types.VARCHAR));
  }

  Date birthday = user.getBirthday();
  if(birthday != null) {
   setUserColumnList.add("birthday");
   userDataList.add(createSqlParatemerMap(new Date(birthday.getTime()), Types.DATE));
  }

  String postalCode = user.getPostalCode();
  if(postalCode != null) {
   setUserColumnList.add("postal_code");
   userDataList.add(createSqlParatemerMap(postalCode, Types.VARCHAR));
  }

  String streetAddress = user.getStreetAddress();
  if(streetAddress != null) {
   setUserColumnList.add("street_address");
   userDataList.add(createSqlParatemerMap(streetAddress, Types.VARCHAR));
  }

  String emailAddress = user.getEmailAddress();
  if(emailAddress != null) {
   setUserColumnList.add("email_address");
   userDataList.add(createSqlParatemerMap(emailAddress, Types.VARCHAR));
  }

  String telephoneNum = user.getTelephoneNumber();
  if(telephoneNum != null) {
   setUserColumnList.add("telephone_number");
   userDataList.add(createSqlParatemerMap(telephoneNum, Types.VARCHAR));
  }

  LocalDateTime updatedTime = user.getUpdatedTime();

  if(setUserColumnList.size() > 0) {
   setUserColumnList.add("updated_time");
   userDataList.add(createSqlParatemerMap(Timestamp.valueOf(updatedTime), Types.TIMESTAMP));

   String whereOfuserId = createWhereOfUserId(user.getUserId(), userDataList);
   
   try (PreparedStatement pstmt = createUpdateStatement("t_user", setUserColumnList, userDataList, whereOfuserId);) {
  
    userResult = pstmt.executeUpdate();
  
   } catch (SQLException e) {
    throw e;
   }
  }


  List<String> setStoreColumnList = new ArrayList<>();
  List<HashMap<String, Object>> storeDataList = new ArrayList<>();
  int storeResult = 0;

  Store store = user.getStore();

  // t_store
  byte[] storeImage = store.getImage();
  if(storeImage.length > 0) {
   setStoreColumnList.add("image");
   storeDataList.add(createSqlParatemerMap(storeImage, Types.BLOB));
  }

  String storeName = store.getStoreName();
  if(storeName != null) {
   setStoreColumnList.add("store_name");
   storeDataList.add(createSqlParatemerMap(storeName, Types.VARCHAR));
  }

  Integer storeEmployeesNum = store.getEmployeesNumber();
  if(storeEmployeesNum != null) {
   setStoreColumnList.add("employees_number");
   storeDataList.add(createSqlParatemerMap(storeEmployeesNum, Types.INTEGER));
  }

  String storeCourceInfo = store.getCourseInfo();
  if(storeCourceInfo != null) {
   setStoreColumnList.add("cource_info");
   storeDataList.add(createSqlParatemerMap(storeCourceInfo, Types.VARCHAR));
  }

  String storeCommitment = store.getCommitment();
  if(storeCommitment != null) {
   setStoreColumnList.add("commitment");
   storeDataList.add(createSqlParatemerMap(storeCommitment, Types.VARCHAR));
  }

  Integer storeId = store.getStoreId();

  if(setStoreColumnList.size() > 0) {
   setStoreColumnList.add("updated_time");
   storeDataList.add(createSqlParatemerMap(Timestamp.valueOf(updatedTime), Types.TIMESTAMP));

   String whereOfStoreId = createWhereOfStoreId(storeId, storeDataList);
   
   try (PreparedStatement pstmt = createUpdateStatement("t_store", setStoreColumnList, storeDataList, whereOfStoreId);) {
   
    storeResult = pstmt.executeUpdate();
 
   } catch (SQLException e) {
    throw e;
   }
  }


  // t_business_hours
  List<BusinessHours> businessHoursList = user.getStore().getBusinessHoursList();
  int businessHoursResult = 0;

  if(businessHoursList != null) {
   for(var i = 0; i < businessHoursList.size(); i++) {
     List<String> setBusinessHoursColumnList = new ArrayList<>();
     List<HashMap<String, Object>> businessHoursDataList = new ArrayList<>();
   
     BusinessHours businessHours = businessHoursList.get(i);

     LocalTime startBusinessTime = businessHours.getStartBusinessTime();
     if(startBusinessTime != null) {
      setBusinessHoursColumnList.add("start_business_time");
      businessHoursDataList.add(createSqlParatemerMap(Time.valueOf(startBusinessTime), Types.TIMESTAMP));
     }

     LocalTime endBusinessTime = businessHours.getEndBusinessTime();
     if(endBusinessTime != null) {
      setBusinessHoursColumnList.add("end_business_time");
      businessHoursDataList.add(createSqlParatemerMap(Time.valueOf(endBusinessTime), Types.TIMESTAMP));
     }

     String complement = businessHours.getComplement();
     if(complement != null) {
      setBusinessHoursColumnList.add("complement");
      businessHoursDataList.add(createSqlParatemerMap(complement, Types.VARCHAR));
     }

     if(setBusinessHoursColumnList.size() == 0) {
      continue;
     }

     setBusinessHoursColumnList.add("updated_time");
     businessHoursDataList.add(createSqlParatemerMap(Timestamp.valueOf(updatedTime), Types.TIMESTAMP));
     
     String whereOfStoreIdAndBusinessDay = createWhereOfStoreIdAndBusinessDay(storeId, businessHours.getBusinessDay(),storeDataList);
     
     try (PreparedStatement pstmt = createUpdateStatement("t_business_hours", setBusinessHoursColumnList, businessHoursDataList, whereOfStoreIdAndBusinessDay);) {

      businessHoursResult += pstmt.executeUpdate();

     } catch (SQLException e) {
      throw e;
     }
   }
  }


  return userResult + storeResult + businessHoursResult;
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
