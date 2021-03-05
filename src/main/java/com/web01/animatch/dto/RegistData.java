package com.web01.animatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DB新規登録データDtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class RegistData {

	//メンバー
	/**
	 * 登録データ
	 */
	private Object registData;
	/**
	 * DB登録型
	 * java.sql.Types列挙型想定
	 */
	private int registDataType;
}
