package com.web01.animatch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 自動ログイン情報Dtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AutoLoginInfo extends BaseDto {

 //メンバー
 /**
  * 自動ログインID
  */
 private Integer autoLoginId;
 /**
  * ユーザID
  */
 private Integer userId;
 /**
  * トークン
  */
 private String token;
 /**
  * ダイジェスト
  */
 private String digest;
}
