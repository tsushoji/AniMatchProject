package com.web01.animatch.exception;

import lombok.Getter;

/**
 * MyExceptionクラス
 * @author Tsuji
 * @version 1.0
 */
public class MyException extends Exception {

	//メンバー
	/**
	 * エラーコード
	 */
	@Getter
	private String code;

	/**
	 * エラーメッセージ
	 */
	@Getter
	private String message;

	/**
	 * 引数付きコンストラクタ
	 * @param code エラーコード
	 * @param message エラーメッセージ
	 */
	public MyException(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
