package com.web01.animatch.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * プロパティサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class PropertiesService {

	//メンバー
	/**
	 * リソース・バンドルオブジェクト
	 */
	private ResourceBundle resBundle;

	// 定数
	/**
	 * プロパティファイル名
	 */
	public static final String ANIMATCH_PROPERTIES_NAME = "animatch";
	/**
	 * プロパティ都道府県キー先頭文字列
	 */
	public static final String PREFECTURES_KEY_INIT_STR = "prefectures.";
	/**
	 * プロパティ動物区分キー先頭文字列
	 */
	public static final String PET_TYPE_KEY_INIT_STR = "pet.type.";
	/**
	 * プロパティ曜日キー先頭文字列
	 */
	public static final String WEEKDAY_KEY_INIT_STR = "weekday.";
	/**
	 * プロパティ登録区分キー先頭文字列
	 */
	public static final String REGIST_TYPE_KEY_INIT_STR = "regist.type.";

	/**
	 * デフォルトコンストラクタ
	 */
	public PropertiesService() {
		this.resBundle = ResourceBundle.getBundle(ANIMATCH_PROPERTIES_NAME);
	}

	/**
	 * プロパティファイルからメッセージ取得
	 * @param key キー
	 * @return 失敗した場合、null 成功した場合、取得したプロパティ
	 */
	public String getValue(String key) {
		if(!Collections.list(this.resBundle.getKeys()).contains(key)) {
			return null;
		}
		return this.resBundle.getString(key);
	}

	/**
	 * プロパティファイルから複数プロパティ取得
	 * @param initKey キー頭文字
	 * @return 取得したプロパティList
	 */
	public Map<String, String> getValues(String initKey) {
		Map<String, String> valMap = new TreeMap<>(new Comparator<String>() {
			public int compare(String k1, String k2) {
				return Integer.parseInt(k1) - Integer.parseInt(k2);
			}
		});
		Collections.list(this.resBundle.getKeys()).forEach(key -> {
	       if(key.startsWith(initKey)){
	    	   valMap.put(key.substring(initKey.length()),this.getValue(key));
		   }
	    });
		return valMap;
	}
}
