package com.web01.animatch.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 店舗Dtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class Store extends BaseDto{

	//メンバー
	/**
	 * 店舗ID
	 */
	private Integer storeId;
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
	private Integer employeesNumber;
	/**
	 * コース情報
	 */
	private String courseInfo;
	/**
	 * こだわり
	 */
	private String commitment;

	/**
	 * 店舗IDgetter
	 * @return 店舗ID
	 */
	public int getStoreId() {
		return storeId;
	}
}
