package com.web01.animatch.service;

import java.util.Base64;

/**
 * ベースサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class BaseService {

	/**
	 * デフォルトコンストラクタ
	 */
	public BaseService() {}

	/**
	 * 飼い主用登録オブジェクト属性設定
	 * @param request リクエストオブジェクト
	 * @param user ユーザオブジェクト
	 */
	protected String convertByteAryToBase64(byte[] byteData) {
		String base64String = null;
		if(byteData != null) {
			base64String = Base64.getEncoder().encodeToString(byteData);
		}
		return base64String;
	}
}
