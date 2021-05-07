package com.web01.animatch.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * ベースDtoクラス
 * @author Tsuji
 * @version 1.0
 */
@Data
public class BaseDto {

	//メンバー
	/**
	 * 削除ステータス
	 */
	private Integer isDeleted;

	/**
	 * 新規登録時間
	 */
	private LocalDateTime insertedTime;

	/**
	 * 更新時間
	 */
	private LocalDateTime updatedTime;
}
