package com.web01.animatch.dto;

import java.time.LocalTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 営業時間Dtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class BusinessHours extends BaseDto{

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
}
