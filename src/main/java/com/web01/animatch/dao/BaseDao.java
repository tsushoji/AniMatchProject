package com.web01.animatch.dao;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;

/**
 * BaseDaoクラス
 * @author Tsuji
 * @version 1.0
 */
public class BaseDao {

	/**
	 * デフォルトコンストラクタ
	 */
	public BaseDao() {}

	/**
	 * SQLパラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
	protected void setSqlParameter(PreparedStatement pstmt, int paramIndex, Object value, int dataType) throws SQLException {

		if (value == null) {
			pstmt.setNull(paramIndex, dataType);
			return;
		}

		String valStr = String.valueOf(value);
		if(dataType == Types.INTEGER) {
			pstmt.setInt(paramIndex, Integer.valueOf(valStr));
		} else if(dataType == Types.FLOAT) {
			pstmt.setFloat(paramIndex, Float.valueOf(valStr));
		} else if(dataType == Types.VARCHAR) {
			pstmt.setString(paramIndex, valStr);
		} else if(dataType == Types.DATE) {
			pstmt.setDate(paramIndex, Date.valueOf(valStr));
		} else if(dataType == Types.TIME) {
			pstmt.setTime(paramIndex, Time.valueOf(valStr));
		} else if(dataType == Types.BLOB) {
			pstmt.setBinaryStream(paramIndex, new ByteArrayInputStream(valStr.getBytes()));
		} else if(dataType == Types.TIMESTAMP) {
			pstmt.setTimestamp(paramIndex, Timestamp.valueOf(valStr));
		} else {
			System.out.println("存在しない型です");
		}
	}

	/**
	 * SQLパラメータマップオブジェクト作成
	 * @param value 値
	 * @param dataType データタイプ
	 * @return SQLパラメータマップオブジェクト
	 */
	protected HashMap<String, Object> createSqlParatemerMap(Object value, Integer dataType) {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("value", value);
		ret.put("dataType", dataType);
		return ret;
	}
}