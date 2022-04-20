package com.web01.animatch.dto;

import lombok.Data;

/**
 * 検索フォームDtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
public class SearchForm {

 //メンバー
 /**
  * ユーザID
  */
 private String userId;
 /**
  * 都道府県
  */
 private String prefectures;
 /**
  * 市区町村
  */
 private String cities;
 /**
  * 動物区分
  */
 private String petType;
 /**
  * 動物性別
  */
 private String petSex;
 /**
  * 営業時間インプットフォームValue属性
  */
 private String businessHoursInputValue;
 /**
  * 営業時間開始時間
  */
 private String businessHoursStartTime;
 /**
  * 営業時間終了時間
  */
 private String businessHoursEndTime;
 /**
  * 検索内容
  */
 private String searchContents;
}
