package com.web01.animatch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * トリマー情報営業時間Dtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class TrimmerInfoBusinessHours extends BusinessHours{
	/**
	 * 表示営業日
	 */
	private String displayBusinessHours;
	/**
	 * 表示営業開始時間
	 */
	private String displayStartBusinessTime;
	/**
	 * 表示営業終了時間
	 */
	private String displayEndBusinessTime;
}
