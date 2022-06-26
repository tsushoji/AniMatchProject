package com.web01.animatch.dto;

import java.util.List;

import lombok.Data;

/**
 * 変更営業時間情報Dtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
public class ChangeBusinessHoursInfo {

 //メンバー
 /**
  * 店舗ID
  */
 private Integer storeId;
 /**
  * 新規登録営業時間リスト
  */
 private List<BusinessHours> createBusinessHoursList;
 /**
  * 更新営業時間リスト
  */
 private List<BusinessHours> updateBusinessHoursList;
 /**
  * 削除営業時間リスト
  */
 private List<BusinessHours> deleteBusinessHoursList;
}
