package com.web01.animatch.dto;

import lombok.Data;

/**
 * 営業時間フォームDtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
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
	 * 営業時間フォーム時間補足文字数エラーステータス
	 */
	/*
	* EL式に埋め込むため、String型
	* 0：flase、1：true
	*/
	private String isErrLengthBusinessHoursRemarks;
	/**
	 * 営業時間フォーム時間補足XSSエラーステータス
	 */
	/*
	* EL式に埋め込むため、String型
	* 0：flase、1：true
	*/
	private String isErrXSSBusinessHoursRemarks;
}
