package com.web01.animatch.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 営業時間オブジェクトクラス
 * @author Tsuji
 * @version 1.0
 */
public class BusinessHours {

	//メンバー
	/**
	 * 営業時間曜日番号
	 */
	private String businessDay;
	/**
	 * 営業開始時間
	 */
	private LocalTime startBusinessTime;
	/**
	 * 営業終了時間
	 */
	private LocalTime endBusinessTime;
	/**
	 * 営業時間補足
	 */
	private String complement;
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
	 * 営業時間曜日番号getter
	 * @return 営業時間曜日番号
	 */
	public String getBusinessDay() {
		return businessDay;
	}

	/**
	 * 営業時間曜日番号setter
	 * @param 営業時間曜日番号
	 */
	public void setBusinessDay(String businessDay) {
		this.businessDay = businessDay;
	}

	/**
	 * 営業開始時間getter
	 * @return 営業開始時間
	 */
	public LocalTime getStartBusinessTime() {
		return startBusinessTime;
	}

	/**
	 * 営業開始時間setter
	 * @param 営業開始時間
	 */
	public void setStartBusinessTime(LocalTime startBusinessTime) {
		this.startBusinessTime = startBusinessTime;
	}

	/**
	 * 営業終了時間getter
	 * @return 営業終了時間
	 */
	public LocalTime getEndBusinessTime() {
		return endBusinessTime;
	}

	/**
	 * 営業終了時間setter
	 * @param 営業終了時間
	 */
	public void setEndBusinessTime(LocalTime endBusinessTime) {
		this.endBusinessTime = endBusinessTime;
	}

	/**
	 * 営業時間補足getter
	 * @return 営業時間補足
	 */
	public String getComplement() {
		return complement;
	}

	/**
	 * 営業時間補足setter
	 * @param 営業時間補足
	 */
	public void setComplement(String complement) {
		this.complement = complement;
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
