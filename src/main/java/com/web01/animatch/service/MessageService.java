package com.web01.animatch.service;

import java.util.Collections;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

/**
 * メッセージサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class MessageService {

	//列挙型
	/**
	 * メッセージ種別
	 */
	public enum Type {

		/**
		 * 通常
		 */
		INFO,
		/**
		 * 警告
		 */
		WARN,
		/**
		 * エラー
		 */
		ERROR;
	}

	//メンバー
	/**
	 * リソース・バンドルオブジェクト
	 */
	private ResourceBundle resBundle;

	//定数
	/**
	 * プロパティファイル名
	 */
	private static final String PROPERTIES_FILE_NAME = "message";
	/**
	 * 通常メッセージプロパティキー
	 */
	private static final String INFO_MESSAGE_INIT_PROPERTIES_KEY = "msg.info.";
	/**
	 * 警告メッセージプロパティキー
	 */
	private static final String WARNING_MESSAGE_INIT_PROPERTIES_KEY = "msg.warning.";
	/**
	 * エラーメッセージプロパティキー
	 */
	private static final String ERROR_MESSAGE_INIT_PROPERTIES_KEY = "msg.error.";

	/**
	 * デフォルトコンストラクタ
	 */
	public MessageService() {
		this.resBundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
	}

	/**
	 * プロパティファイルからメッセージ取得
	 * @param messageType メッセージ種別
	 * @param messageKey メッセージキー
	 * @return 失敗した場合、null 成功した場合、取得したメッセージ
	 */
	public String getMessage(MessageService.Type messageType, String messageKey) {

		String messageInitPropertyKey = getMessageInitPropertyKey(messageType, messageKey);
		if(StringUtils.isEmpty(messageInitPropertyKey)) {
			return null;
		}

		String messagePropertyKey = messageInitPropertyKey + messageKey;
		if(!Collections.list(this.resBundle.getKeys()).contains(messagePropertyKey)) {
			return null;
		}

		return this.resBundle.getString(messagePropertyKey);
	}

	/**
	 * プロパティファイル、引数置換文字列からメッセージ取得
	 * @param messageType メッセージ種別
	 * @param messageKey メッセージキー
	 * @param replacedStrArgs 置換文字列可変引数
	 * @return 失敗した場合、null 成功した場合、取得し、置換したメッセージ
	 */
	public String getMessage(MessageService.Type messageType, String messageKey, String ... replacedStr) {

		String messageInitPropertyKey = getMessageInitPropertyKey(messageType, messageKey);
		if(StringUtils.isEmpty(messageInitPropertyKey)) {
			return null;
		}

		String messagePropertyKey = messageInitPropertyKey + messageKey;
		if(!Collections.list(this.resBundle.getKeys()).contains(messagePropertyKey)) {
			return null;
		}

		//引数の文字列に置換
		String message = this.resBundle.getString(messagePropertyKey);
		for(int i = 0; i < replacedStr.length; i++) {
			message = message.replace("{" + i + "}", replacedStr[i]);
		}

		return message;
	}

	/**
	 * メッセージ種別からメッセージプロパティーキー頭文字列取得
	 * @param messageType メッセージ種別
	 * @param messageKey メッセージキー
	 * @return 失敗した場合、null 成功した場合、メッセージプロパティーキー頭文字列
	 */
	private String getMessageInitPropertyKey(MessageService.Type messageType, String messageKey) {
		String messageInitPropertyKey = null;
		switch(messageType) {
			case INFO:
				messageInitPropertyKey = INFO_MESSAGE_INIT_PROPERTIES_KEY;
				break;

			case WARN:
				messageInitPropertyKey = WARNING_MESSAGE_INIT_PROPERTIES_KEY;
				break;

			case ERROR:
				messageInitPropertyKey = ERROR_MESSAGE_INIT_PROPERTIES_KEY;
				break;

			default:
				break;
		}

		return messageInitPropertyKey;
	}

}
