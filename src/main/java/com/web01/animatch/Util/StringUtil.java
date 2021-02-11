package com.web01.animatch.Util;

/**
 * StringUtilクラス
 * @author Tsuji
 * @version 1.0
 */
public class StringUtil {

	/**
	 * サイズの設定
	 * @param str 文字列
	 * @return 文字列がnullであるまたは空である場合、true
	 */
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.length() == 0);
	}
}
