package com.web01.animatch.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ユーザDtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class User extends BaseDto{

	//メンバー
	/**
	 * ユーザID
	 */
	private int userId;
	/**
	 * ユーザ名
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
	private Date birthday;
	/**
	 * 郵便番号
	 */
	private String postalCode;
	/**
	 * 住所
	 */
	private String streetAddress;
	/**
	 * メールアドレス
	 */
	private String emailAddress;
	/**
	 * 電話番号
	 */
	private String telephoneNumber;
	/**
	 * ペットオブジェクト
	 */
	private Pet pet;
	/**
	 * 店舗オブジェクト
	 */
	private Store store;
}
