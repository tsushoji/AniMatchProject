package com.web01.animatch.dto;

import java.util.List;

import lombok.Data;

/**
 * クラス
 * @author Tsuji
 * @version 1.0
 */
@Data
public class BaseAccountForm {

 //メンバー
 /**
  * ユーザネーム
  */
 private String userName;
 /**
  * パスワード
  */
 private String password;
 /**
  * 性別
  */
 private String sex;
 /**
  * 誕生日
  */
 private String birthday;
 /**
  * 郵便番号
  */
 private String postalCode;
 /**
  * 都道府県
  */
 private String prefectures;
 /**
  * 市区町村
  */
 private String cities;
 /**
  * メールアドレス
  */
 private String emailAddress;
 /**
  * 電話番号
  */
 private String telephoneNumber;
 /**
  * ペットネーム
  */
 private String petName;
 /**
  * ペット性別
  */
 private String petSex;
 /**
  * ペット種類
  */
 private String petType;
 /**
  * ペット体重
  */
 private String petWeight;
 /**
  * 店名
  */
 private String storeName;
 /**
  * 営業時間インプットフォームValue属性
  */
 private String formBusinessHoursInputValue;
 /**
  * 営業時間フォームリスト
  */
 private List<FormBusinessHours> formBusinessHoursList;
 /**
  * 従業員数
  */
 private String storeEmployees;
 /**
  * ペット補足
  */
 private String petRemarks;
 /**
  * コース情報
  */
 private String courseInfo;
 /**
  * こだわり
  */
 private String commitment;
}
