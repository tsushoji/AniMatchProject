package com.web01.animatch.dao;

import java.sql.Connection;

/**
 * searchDaoクラス
 * @author Tsuji
 * @version 1.0
 */
public class SearchDao {

	//メンバー
	/**
	 * DBコネクションオブジェクト
	 */
	private Connection con;

	/**
	 * 引数付きコンストラクタ
	 *
	 * @param con DBコネクションオブジェクト
	 */
	public SearchDao(Connection con) {
		this.con = con;
	}
}
