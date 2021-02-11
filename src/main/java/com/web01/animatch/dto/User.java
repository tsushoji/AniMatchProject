package com.web01.animatch.dto;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * ユーザオブジェクトクラス
 * @author Tsuji
 * @version 1.0
 */
public class User {

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

	/**
	 * 削除ステータス
	 */
	private int isDeleted;

	/**
	 * 新規登録時間
	 */
	private LocalDateTime insertedTime;

	/**
	 * 更新時間
	 */
	private LocalDateTime updatedTime;

	/**
	 * ユーザIDgetter
	 * @return ユーザID
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * ユーザIDsetter
	 * @param ユーザID
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * ユーザ名getter
	 * @return ユーザ名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * ユーザ名setter
	 * @param ユーザ名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * パスワードgetter
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードsetter
	 * @param パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 性別getter
	 * @return 性別
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 性別setter
	 * @param 性別
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 誕生日getter
	 * @return 誕生日
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 誕生日setter
	 * @param 誕生日
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 郵便番号getter
	 * @return 郵便番号
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * 郵便番号setter
	 * @param 郵便番号
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * 住所getter
	 * @return 住所
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * 住所setter
	 * @param 住所
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * メールアドレスgetter
	 * @return メールアドレス
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * メールアドレスsetter
	 * @param メールアドレス
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * 電話番号getter
	 * @return 電話番号
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * 電話番号setter
	 * @param 電話番号
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * ペットオブジェクトgetter
	 * @return ペットオブジェクト
	 */
	public Pet getPet() {
		return pet;
	}

	/**
	 * ペットオブジェクトsetter
	 * @param ペットオブジェクト
	 */
	public void setPet(Pet pet) {
		this.pet = pet;
	}

	/**
	 * 店舗オブジェクトgetter
	 * @return 店舗オブジェクト
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * 店舗オブジェクトsetter
	 * @param 店舗オブジェクト
	 */
	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * 削除ステータスgetter
	 * @return 削除ステータス
	 */
	public int getIsDeleted() {
		return isDeleted;
	}

	/**
	 * 削除ステータスsetter
	 * @param 削除ステータス
	 */
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * 新規登録時間getter
	 * @return 新規登録時間
	 */
	public LocalDateTime getInsertedTime() {
		return insertedTime;
	}

	/**
	 * 新規登録時間setter
	 * @param 新規登録時間
	 */
	public void setInsertedTime(LocalDateTime insertedTime) {
		this.insertedTime = insertedTime;
	}

	/**
	 * 更新時間getter
	 * @return 更新時間
	 */
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * 更新時間setter
	 * @param 更新時間
	 */
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
}
