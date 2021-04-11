package com.web01.animatch.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * トリマー情報Dtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
public class TrimmerInfo {

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
	 * 店舗ID
	 */
	private Integer storeId;
	/**
	 * 店舗画像
	 */
	private byte[] storeImage;
	/**
	 * 店舗画像base64
	 */
	private String storeImageBase64;
	/**
	 * 店名
	 */
	private String storeName;
	/**
	 * 店舗従業員数
	 */
	private Integer storeEmployeesNumber;
	/**
	 * 店舗コース情報
	 */
	private String storeCourseInfo;
	/**
	 * 店舗こだわり
	 */
	private String storeCommitment;
	/**
	 * 営業時間リスト
	 */
	private List<BusinessHours> businessHoursList;
}
