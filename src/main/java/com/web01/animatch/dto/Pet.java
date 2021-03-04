package com.web01.animatch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ペットDtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class Pet extends BaseDto{

	//メンバー
	/**
	 * ペットID
	 */
	private Integer petId;
	/**
	 * 画像
	 */
	private byte[] image;
	/**
	 * ニックネーム
	 */
	private String nickName;
	/**
	 * 性別
	 */
	private String sex;
	/**
	 * 種類
	 */
	private String type;
	/**
	 * 体重
	 */
	private Float weight;
	/**
	 * 補足
	 */
	private String remarks;
}
