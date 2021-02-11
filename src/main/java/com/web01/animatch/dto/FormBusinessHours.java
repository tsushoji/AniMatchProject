package com.web01.animatch.dto;

/**
 * 営業時間フォームオブジェクトクラス
 * @author Tsuji
 * @version 1.0
 */
public class FormBusinessHours {

	//メンバー
	/**
	 * 営業時間フォーム曜日番号
	 */
	private String businessHoursWeekdayNum;
	/**
	 * 営業時間フォーム開始時間
	 */
	private String businessHoursStartTime;
	/**
	 * 営業時間フォーム終了時間
	 */
	private String businessHoursEndTime;
	/**
	 * 営業時間フォーム時間補足
	 */
	private String businessHoursRemarks;
	/**
	 * 営業時間フォーム開始時間エラーステータス
	 */
	/*
	* EL式に埋め込むため、String型
	* 0：flase、1：true
	*/
	private String isErrBusinessHoursStartTime;
	/**
	 * 営業時間フォーム終了時間エラーステータス
	 */
	/*
	* EL式に埋め込むため、String型
	* 0：flase、1：true
	*/
	private String isErrBusinessHoursEndTime;
	/**
	 * 営業時間フォーム時間補足エラーステータス
	 */
	/*
	* EL式に埋め込むため、String型
	* 0：flase、1：true
	*/
	private String isErrBusinessHoursRemarks;

	/**
	 * 営業時間フォーム曜日番号getter
	 * @return 営業時間フォーム曜日番号
	 */
	public String getBusinessHoursWeekdayNum() {
		return businessHoursWeekdayNum;
	}

	/**
	 * 営業時間フォーム曜日番号setter
	 * @param 営業時間フォーム曜日番号
	 */
	public void setBusinessHoursWeekdayNum(String businessHoursWeekdayNum) {
		this.businessHoursWeekdayNum = businessHoursWeekdayNum;
	}

	/**
	 * 営業時間フォーム開始時間getter
	 * @return 営業時間フォーム開始時間
	 */
	public String getBusinessHoursStartTime() {
		return businessHoursStartTime;
	}

	/**
	 * 営業時間フォーム開始時間setter
	 * @param 営業時間フォーム開始時間
	 */
	public void setBusinessHoursStartTime(String businessHoursStartTime) {
		this.businessHoursStartTime = businessHoursStartTime;
	}

	/**
	 * 営業時間フォーム終了時間号getter
	 * @return 営業時間フォーム終了時間
	 */
	public String getBusinessHoursEndTime() {
		return businessHoursEndTime;
	}

	/**
	 * 営業時間フォーム終了時間setter
	 * @param 営業時間フォーム終了時間
	 */
	public void setBusinessHoursEndTime(String businessHoursEndTime) {
		this.businessHoursEndTime = businessHoursEndTime;
	}

	/**
	 * 営業時間フォーム時間補足getter
	 * @return 営業時間フォーム時間補足
	 */
	public String getBusinessHoursRemarks() {
		return businessHoursRemarks;
	}

	/**
	 * 営業時間フォーム時間補足setter
	 * @param 営業時間フォーム時間補足
	 */
	public void setBusinessHoursRemarks(String businessHoursRemarks) {
		this.businessHoursRemarks = businessHoursRemarks;
	}

	/**
	 * 営業時間フォーム開始時間エラーステータスgetter
	 * @return 営業時間フォーム開始時間エラーステータス
	 */
	public String getIsErrBusinessHoursStartTime() {
		return isErrBusinessHoursStartTime;
	}

	/**
	 * 営業時間フォーム開始時間エラーステータスsetter
	 * @param 営業時間フォーム開始時間エラーステータス
	 */
	public void setIsErrBusinessHoursStartTime(String isErrBusinessHoursStartTime) {
		this.isErrBusinessHoursStartTime = isErrBusinessHoursStartTime;
	}

	/**
	 * 営業時間フォーム終了時間エラーステータスgetter
	 * @return 営業時間フォーム終了時間エラーステータス
	 */
	public String getIsErrBusinessHoursEndTime() {
		return isErrBusinessHoursEndTime;
	}

	/**
	 * 営業時間フォーム終了時間エラーステータスsetter
	 * @param 営業時間フォーム終了時間エラーステータス
	 */
	public void setIsErrBusinessHoursEndTime(String isErrBusinessHoursEndTime) {
		this.isErrBusinessHoursEndTime = isErrBusinessHoursEndTime;
	}

	/**
	 * 営業時間フォーム時間補足エラーステータスgetter
	 * @return 営業時間フォーム時間補足エラーステータス
	 */
	public String getIsErrBusinessHoursRemarks() {
		return isErrBusinessHoursRemarks;
	}

	/**
	 * 営業時間フォーム時間補足エラーステータスsetter
	 * @param 営業時間フォーム時間補足エラーステータス
	 */
	public void setIsErrBusinessHoursRemarks(String isErrBusinessHoursRemarks) {
		this.isErrBusinessHoursRemarks = isErrBusinessHoursRemarks;
	}
}
