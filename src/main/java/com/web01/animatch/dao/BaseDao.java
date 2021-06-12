package com.web01.animatch.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

/**
 * BaseDaoクラス
 * @author Tsuji
 * @version 1.0
 */
public class BaseDao {

	protected enum LogicalOperatorType {

		/**
		 * 論理積
		 */
		AND,
		/**
		 * 論理和
		 */
		OR,
		;
	}

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
			// 「new ByteArrayInputStream(valStr.getBytes())」で渡すと、画像ファイルを復元できない
			pstmt.setBytes(paramIndex, (byte[])value);
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

	/**
	 * SQL句内容作成
	 * @param value 値
	 * @param clauseContents 句内容
	 * @param logicalOperatorType 論理演算子種別
	 * @return SQL句内容
	 */
	protected String createSqlClauseContent(String value, String clauseContents, LogicalOperatorType logicalOperatorType) {
		if(StringUtils.isNotEmpty(clauseContents)) {
			switch(logicalOperatorType) {
				case AND:
					clauseContents += " " + LogicalOperatorType.AND.toString() + " " + value;
					break;
				case OR:
					clauseContents += " " + LogicalOperatorType.OR.toString() + " " + value;
					break;
				default:
					break;
			}
		}else {
			clauseContents = value;
		}
		return clauseContents;
	}
}
