package com.web01.animatch.dto;

import lombok.Data;

/**
 * ユーザセッションクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
public class UserSession {

 //メンバー
 /**
  * ユーザID
  */
 private Integer userId;
 /**
  * ペットID
  */
 private Integer petId;
 /**
  * 店舗ID
  */
 private Integer storeId;
 /**
  * ログイン完了リダイレクトURL
  */
 private String loginedURL;
}
