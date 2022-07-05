package com.web01.animatch.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

/**
 * プロパティーサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class PropertiesService {

 //メンバー
 /**
  * リソース・バンドルオブジェクト
  */
 private ResourceBundle resBundle;

 // 定数
 /**
  * プロパティファイル名
  */
 public static final String ANIMATCH_PROPERTIES_NAME = "animatch";
 /**
  * プロパティ都道府県キー先頭文字列
  */
 public static final String PREFECTURES_KEY_INIT_STR = "prefectures.";
 /**
  * プロパティ動物区分キー先頭文字列
  */
 public static final String PET_TYPE_KEY_INIT_STR = "pet.type.";
 /**
  * プロパティ曜日キー先頭文字列
  */
 public static final String WEEKDAY_KEY_INIT_STR = "weekday.";
 /**
  * プロパティ登録区分キー先頭文字列
  */
 public static final String REGIST_TYPE_KEY_INIT_STR = "regist.type.";
 /**
  * プロパティ人間性別キー先頭文字列
  */
 public static final String HUMAN_SEX_KEY_INIT_STR = "human.sex.";
 /**
  * プロパティペット性別キー先頭文字列
  */
 public static final String PET_SEX_KEY_INIT_STR = "pet.sex.";

 /**
  * デフォルトコンストラクタ
  */
 public PropertiesService() {
  this.resBundle = ResourceBundle.getBundle(ANIMATCH_PROPERTIES_NAME);
 }

 /**
  * プロパティファイルから値取得
  * @param key キー
  * @return 失敗した場合、null 成功した場合、値
  */
 public String getValue(String key) {
  if (!Collections.list(this.resBundle.getKeys()).contains(key)) {
   return null;
  }
  return this.resBundle.getString(key);
 }

 /**
  * プロパティファイルからキー取得
  * @param value キー
  * @return 失敗した場合、null 成功した場合、キー
  */
 public String getKey(String value) {
  Enumeration<String> keys = this.resBundle.getKeys();
  while (keys.hasMoreElements()) {
   String key = keys.nextElement();
   if(this.resBundle.getString(key).equals(value)) {
    return key;
   }
  }
  return null;
 }

 /**
  * プロパティファイルから複数プロパティ取得
  * @param keyPrefix キー頭文字
  * @return 取得したプロパティMap
  */
 public Map<String, String> getValues(String keyPrefix) {
  Map<String, String> valMap = new TreeMap<>(new Comparator<String>() {
   public int compare(String k1, String k2) {
    return Integer.parseInt(k1) - Integer.parseInt(k2);
   }
  });
  Collections.list(this.resBundle.getKeys()).forEach(key -> {
   if (key.startsWith(keyPrefix)) {
    String val = this.getValue(key);
    if (!StringUtils.isEmpty(val)) {
     valMap.put(key.substring(keyPrefix.length()), val);
    }
   }
  });
  return valMap;
 }
}
