package com.web01.animatch.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

/**
 * プロパティサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class PropertiesService {

	//列挙型
	/**
	 * プロパティー種別
	 */
	protected enum PropertiesType {

		/**
		 * 都道府県
		 */
		PREFECTURES,
		/**
		 * ペット種別
		 */
		PETTYPE,
		/**
		 * 週
		 */
		WEEKDAY,
		/**
		 * 登録区分
		 */
		REGISTTYPE;
	}

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
	 * @param propertiesType プロパティー種別
	 * @param key キー
	 * @return 失敗した場合、null 成功した場合、取得したプロパティ
	 */
	private String getValue(PropertiesService.PropertiesType propertiesType, String key) {
		String propertiesInitPropertyKey = getPropertiesInitPropertyKey(propertiesType);
		if(StringUtils.isEmpty(propertiesInitPropertyKey)) {
			return null;
		}

		if(!Collections.list(this.resBundle.getKeys()).contains(key)) {
			return null;
		}
		return this.resBundle.getString(key);
	}

	/**
	 * プロパティファイルから複数プロパティ取得
	 * @param propertiesType プロパティー種別
	 * @param initKey キー頭文字
	 * @return 取得したプロパティList
	 */
	private Map<String, String> getValues(PropertiesService.PropertiesType propertiesType, String initKey) {
		Map<String, String> valMap = new TreeMap<>(new Comparator<String>() {
			public int compare(String k1, String k2) {
				return Integer.parseInt(k1) - Integer.parseInt(k2);
			}
		});
		Collections.list(this.resBundle.getKeys()).forEach(key -> {
	       if(key.startsWith(initKey)){
	    	   String val = this.getValue(propertiesType, key);
	    	   if(!StringUtils.isEmpty(val)) {
	    		   valMap.put(key.substring(initKey.length()),val);
	    	   }
		   }
	    });
		return valMap;
	}

	/**
	 * メッセージ種別からメッセージプロパティーキー頭文字列取得
	 * @param propertiesType プロパティー種別
	 * @return 失敗した場合、null 成功した場合、プロパティーキー頭文字列
	 */
	private String getPropertiesInitPropertyKey(PropertiesService.PropertiesType propertiesType) {
		String propertiesInitPropertyKey = null;
		switch(propertiesType) {
			case PREFECTURES:
				propertiesInitPropertyKey = PREFECTURES_KEY_INIT_STR;
				break;

			case PETTYPE:
				propertiesInitPropertyKey = PET_TYPE_KEY_INIT_STR;
				break;

			case WEEKDAY:
				propertiesInitPropertyKey = WEEKDAY_KEY_INIT_STR;
				break;

			case REGISTTYPE:
				propertiesInitPropertyKey = REGIST_TYPE_KEY_INIT_STR;
				break;

			default:
				break;
		}

		return propertiesInitPropertyKey;
	}

	/**
	 * プロパティファイルキーから週プロパティ取得
	 * @param key キー
	 * @return 取得したプロパティー値
	 */
	public String getWeekdayValue(String key) {
		return this.getValue(PropertiesType.WEEKDAY, key);
	}

	/**
	 * プロパティファイルから都道府県プロパティ取得
	 * @return 取得したプロパティーMap
	 */
	public Map<String, String> getPrefecturesValues() {
		return this.getValues(PropertiesType.PREFECTURES, PREFECTURES_KEY_INIT_STR);
	}

	/**
	 * プロパティファイルからペット種別プロパティ取得
	 * @return 取得したプロパティーMap
	 */
	public Map<String, String> getPetTypeValues() {
		return this.getValues(PropertiesType.PETTYPE, PET_TYPE_KEY_INIT_STR);
	}

	/**
	 * プロパティファイルから週プロパティ取得
	 * @return 取得したプロパティーMap
	 */
	public Map<String, String> getWeekdayValues() {
		return this.getValues(PropertiesType.WEEKDAY, WEEKDAY_KEY_INIT_STR);
	}
}
