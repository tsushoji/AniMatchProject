package com.web01.animatch.service;

import java.util.Base64;

/**
 * ベースサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class BaseService {

 // 定数
 /**
  * セレクトタグラジオボタンデフォルト値
  */
 protected static final String DEFAULT_SELECT_OR_RADIO_VAL = "000";

 //列挙型
 /**
  * ユーザ種別
  */
 protected enum UserType {

  /**
   * 飼い主
   */
  OWNER("001"),
  /**
   * トリマー
   */
  TRIMMER("002"),
  ;

  //メンバー
  /**
   * 区分ID
   */
  private final String id;

  /**
   * 引数付きコンストラクタ
   * @param id ID
   */
  private UserType(final String id) {
   this.id = id;
  }

  /**
   * 区分IDgetter
   * @return 区分ID
   */
  public String getId() {
   return this.id;
  }

  /**
   * ユーザ種別取得
   * @param enumName enum文字列
   * @return ユーザ種別
   */
  public static UserType getEnumFromEnumName(String enumName) {
   for (UserType val : values()) {
    if (val.toString().equals(enumName)) {
     return val;
    }

   }
   throw new IllegalArgumentException("undefined : " + enumName);
  }

  /**
   * ユーザ種別取得
   * @param Id ID
   * @return ユーザ種別
   */
  public static UserType getEnumFromId(String Id) {
   for (UserType val : values()) {
    if (val.getId().equals(Id)) {
     return val;
    }

   }
   throw new IllegalArgumentException("undefined : " + Id);
  }
 }

 /**
  * バイナリーデータからBase64へ変換
  * @param byteData バイナリーデータ
  * @return Base64文字列
  */
 protected String convertByteAryToBase64(byte[] byteData) {
  String base64String = null;
  if (byteData != null) {
   base64String = Base64.getEncoder().encodeToString(byteData);
  }
  return base64String;
 }
}
