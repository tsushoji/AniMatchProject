package com.web01.animatch.dto;

import java.util.Date;

import lombok.Data;

/**
 * 飼い主情報Dtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
public class OwnerInfo {

	//メンバー
	/**
	 * ユーザID
	 */
	private Integer userId;
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
	 * ペットID
	 */
	private Integer petId;
	/**
	 * ペット画像
	 */
	private byte[] petImage;
	/**
	 * ペット画像base64
	 */
	private String petImageBase64;
	/**
	 * ペットニックネーム
	 */
	private String petNickName;
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
	private Float petWeight;
	/**
	 * ペット補足
	 */
	private String petRemarks;
}
