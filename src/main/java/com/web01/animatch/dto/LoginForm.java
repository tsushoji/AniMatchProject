package com.web01.animatch.dto;

import lombok.Data;

/**
 * ログインフォームDtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
public class LoginForm {

 //メンバー
 /**
  * ユーザID
  */
 private String userId;
 /**
  * パスワード
  */
 private String password;
}
