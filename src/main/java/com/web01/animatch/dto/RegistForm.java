package com.web01.animatch.dto;

import java.util.List;

/**
 * ペットフォームオブジェクトクラス
 * @author Tsuji
 * @version 1.0
 */
public class RegistForm {

	//メンバー
	/**
	 * ユーザネーム
	 */
	private String userName;
	/**
	 * パスワード
	 */
	private String password;
	/**
	 * 再入力パスワード
	 */
	private String rePassword;
	/**
	 * 性別
	 */
	private String sex;
	/**
	 * 誕生日
	 */
	private String birthday;
	/**
	 * 郵便番号
	 */
	private String postalCode;
	/**
	 * 都道府県
	 */
	private String prefectures;
	/**
	 * 市区町村
	 */
	private String cities;
	/**
	 * メールアドレス
	 */
	private String emailAddress;
	/**
	 * 電話番号
	 */
	private String telephoneNumber;
	/**
	 * ペットネーム
	 */
	private String petName;
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
	private String petWeight;
	/**
	 * 店名
	 */
	private String storeName;
	/**
	 * 営業時間インプットフォームValue属性
	 */
	private String formBusinessHoursInputValue;
	/**
	 * 営業時間フォームリスト
	 */
	private List<FormBusinessHours> formBusinessHoursList;
	/**
	 * 従業員数
	 */
	private String storeEmployees;
	/**
	 * ペット補足
	 */
	private String petRemarks;
	/**
	 * コース情報
	 */
	private String courseInfo;
	/**
	 * こだわり
	 */
	private String commitment;

	/**
	 * ユーザネームgetter
	 * @return ユーザネーム
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * ユーザネームsetter
	 * @param ユーザネーム
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
	 * 再入力パスワードgetter
	 * @return 再入力パスワード
	 */
	public String getRePassword() {
		return rePassword;
	}

	/**
	 * 再入力パスワードsetter
	 * @param 再入力パスワード
	 */
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
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
	public String getBirthday() {
		return birthday;
	}

	/**
	 * 誕生日setter
	 * @param 誕生日
	 */
	public void setBirthday(String birthday) {
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
	 * 都道府県getter
	 * @return 都道府県
	 */
	public String getPrefectures() {
		return prefectures;
	}

	/**
	 * 都道府県setter
	 * @param 都道府県
	 */
	public void setPrefectures(String prefectures) {
		this.prefectures = prefectures;
	}

	/**
	 * 市区町村getter
	 * @return 市区町村
	 */
	public String getCities() {
		return cities;
	}

	/**
	 * 市区町村setter
	 * @param 市区町村
	 */
	public void setCities(String cities) {
		this.cities = cities;
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
	 * ペットネームgetter
	 * @return ペットネーム
	 */
	public String getPetName() {
		return petName;
	}

	/**
	 * ペットネームsetter
	 * @param ペットネーム
	 */
	public void setPetName(String petName) {
		this.petName = petName;
	}

	/**
	 * ペット性別getter
	 * @return ペット性別
	 */
	public String getPetSex() {
		return petSex;
	}

	/**
	 * ペット性別setter
	 * @param ペット性別
	 */
	public void setPetSex(String petSex) {
		this.petSex = petSex;
	}

	/**
	 * ペット種類getter
	 * @return ペット種類
	 */
	public String getPetType() {
		return petType;
	}

	/**
	 * ペット種類setter
	 * @param ペット種類
	 */
	public void setPetType(String petType) {
		this.petType = petType;
	}

	/**
	 * ペット体重getter
	 * @return ペット体重
	 */
	public String getPetWeight() {
		return petWeight;
	}

	/**
	 * ペット体重setter
	 * @param ペット体重
	 */
	public void setPetWeight(String petWeight) {
		this.petWeight = petWeight;
	}

	/**
	 * 店名getter
	 * @return 店名
	 */
	public String getStoreName() {
		return storeName;
	}

	/**
	 * 店名setter
	 * @param 店名
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	/**
	 * 営業時間インプットフォームValue属性getter
	 * @return 営業時間インプットフォームValue属性
	 */
	public String getFormBusinessHoursInputValue() {
		return formBusinessHoursInputValue;
	}

	/**
	 * 営業時間インプットフォームValue属性setter
	 * @param 営業時間インプットフォームValue属性
	 */
	public void setFormBusinessHoursInputValue(String formBusinessHoursInputValue) {
		this.formBusinessHoursInputValue = formBusinessHoursInputValue;
	}

	/**
	 * 営業時間フォームリストgetter
	 * @return 営業時間フォームリスト
	 */
	public List<FormBusinessHours> getFormBusinessHoursList() {
		return formBusinessHoursList;
	}

	/**
	 * 営業時間フォームリストsetter
	 * @param 営業時間フォームリスト
	 */
	public void setFormBusinessHoursList(List<FormBusinessHours> formBusinessHoursList) {
		this.formBusinessHoursList = formBusinessHoursList;
	}

	/**
	 * 従業員数getter
	 * @return 従業員数
	 */
	public String getStoreEmployees() {
		return storeEmployees;
	}

	/**
	 * 従業員数setter
	 * @param 従業員数
	 */
	public void setStoreEmployees(String storeEmployees) {
		this.storeEmployees = storeEmployees;
	}

	/**
	 * ペット補足getter
	 * @return ペット補足
	 */
	public String getPetRemarks() {
		return petRemarks;
	}

	/**
	 * ペット補足setter
	 * @param ペット補足
	 */
	public void setPetRemarks(String petRemarks) {
		this.petRemarks = petRemarks;
	}

	/**
	 * コース情報
	 * @return コース情報
	 */
	public String getCourseInfo() {
		return courseInfo;
	}

	/**
	 * コース情報setter
	 * @param コース情報
	 */
	public void setCourseInfo(String courseInfo) {
		this.courseInfo = courseInfo;
	}

	/**
	 * こだわりgetter
	 * @return こだわり
	 */
	public String getCommitment() {
		return commitment;
	}

	/**
	 * こだわりsetter
	 * @param こだわり
	 */
	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}
}
