package com.web01.animatch.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 店舗オブジェクトクラス
 * @author Tsuji
 * @version 1.0
 */
public class Store {

	//メンバー
	/**
	 * 店舗ID
	 */
	private int storeId;
	/**
	 * 画像
	 */
	private byte[] image;
	/**
	 * 営業時間リスト
	 */
	private List<BusinessHours> businessHoursList;
	/**
	 * 店名
	 */
	private String storeName;
	/**
	 * 従業員数
	 */
	private int employeesNumber;
	/**
	 * コース情報
	 */
	private String courseInfo;
	/**
	 * こだわり
	 */
	private String commitment;
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
	 * 店舗IDgetter
	 * @return 店舗ID
	 */
	public int getStoreId() {
		return storeId;
	}

	/**
	 * 店舗IDsetter
	 * @param 店舗ID
	 */
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	/**
	 * 画像getter
	 * @return 画像
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * 画像setter
	 * @param 画像
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * 営業時間リストgetter
	 * @return 画像
	 */
	public List<BusinessHours> getBusinessHoursList() {
		return businessHoursList;
	}

	/**
	 * 営業時間リストsetter
	 * @param ペットID
	 */
	public void setBusinessHoursList(List<BusinessHours> businessHoursList) {
		this.businessHoursList = businessHoursList;
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
	 * 従業員数getter
	 * @return 従業員数
	 */
	public int getEmployeesNumber() {
		return employeesNumber;
	}

	/**
	 * 従業員数setter
	 * @param 従業員数
	 */
	public void setEmployeesNumber(int employeesNumber) {
		this.employeesNumber = employeesNumber;
	}

	/**
	 * コース情報getter
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

	/**
	 * 削除ステータスgetter
	 * @return 削除ステータス
	 */
	public int getIsDeleted() {
		return isDeleted;
	}

	/**
	 * 削除ステータス
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
