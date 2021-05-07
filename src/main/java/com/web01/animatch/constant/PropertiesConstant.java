package com.web01.animatch.constant;

/**
 * プロパティー定数クラス
 * @author Tsuji
 * @version 1.0
 */
public final class PropertiesConstant {

	/**
	 * デフォルトコンストラクタ
	 * privateコンストラクタでインスタンス生成を抑止
	 */
	private PropertiesConstant(){}

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
}
