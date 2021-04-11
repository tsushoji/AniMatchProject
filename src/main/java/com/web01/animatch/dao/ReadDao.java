package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.web01.animatch.dto.OwnerInfo;
import com.web01.animatch.dto.TrimmerInfo;

/**
 * ReadDaoクラス
 * @author Tsuji
 * @version 1.0
 */
public class ReadDao extends BaseDao{

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
	public ReadDao(Connection con) {
		this.con = con;
	}

	/**
	 * 飼い主情報抽出
	 * @return 飼い主情報オブジェクトリスト
	 */
	public List<OwnerInfo> findOwnerInfo() throws SQLException {
		List<OwnerInfo> ownerInfoList = new ArrayList<>();

		try (PreparedStatement pstmt = createSelectStatement(null, "v_owner_info", null, null);){
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				OwnerInfo ownerInfo = new OwnerInfo();
				ownerInfo.setUserId(rs.getInt(0) == 0?null:rs.getInt(0));
				ownerInfo.setUserName(rs.getString(1));
				ownerInfo.setPassword(rs.getString(2));
				ownerInfo.setSex(rs.getString(3));
				ownerInfo.setBirthday(rs.getDate(4));
				ownerInfo.setPostalCode(rs.getString(5));
				ownerInfo.setStreetAddress(rs.getString(6));
				ownerInfo.setEmailAddress(rs.getString(7));
				ownerInfo.setTelephoneNumber(rs.getString(8));
				ownerInfo.setPetId(rs.getInt(9) == 0?null:rs.getInt(9));
				ownerInfo.setPetImage(rs.getBytes(10));
				ownerInfo.setPetNickName(rs.getString(11));
				ownerInfo.setPetSex(rs.getString(12));
				ownerInfo.setPetType(rs.getString(13));
				ownerInfo.setPetWeight(rs.getFloat(14) == 0?null:rs.getFloat(14));
				ownerInfo.setPetRemarks(rs.getString(15));
				ownerInfoList.add(ownerInfo);
			}
		} catch(SQLException e) {
			throw e;
		}
		return ownerInfoList;
	}

	/**
	 * トリマー情報抽出
	 * @return トリマー情報オブジェクトリスト
	 */
	public List<TrimmerInfo> findTrimmerInfo() throws SQLException {
		List<TrimmerInfo> trimmerInfoList = new ArrayList<>();

		try (PreparedStatement pstmt = createSelectStatement(null, "v_trimmer_info", null, null);){
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				TrimmerInfo trimmerInfo = new TrimmerInfo();
				trimmerInfo.setUserId(rs.getInt(0) == 0?null:rs.getInt(0));
				trimmerInfo.setUserName(rs.getString(1));
				trimmerInfo.setPassword(rs.getString(2));
				trimmerInfo.setSex(rs.getString(3));
				trimmerInfo.setBirthday(rs.getDate(4));
				trimmerInfo.setPostalCode(rs.getString(5));
				trimmerInfo.setStreetAddress(rs.getString(6));
				trimmerInfo.setEmailAddress(rs.getString(7));
				trimmerInfo.setTelephoneNumber(rs.getString(8));
				trimmerInfo.setStoreId(rs.getInt(9) == 0?null:rs.getInt(9));
				trimmerInfo.setStoreImage(rs.getBytes(10));
				trimmerInfo.setStoreName(rs.getString(11));
				trimmerInfo.setStoreEmployeesNumber(rs.getInt(12) == 0?null:rs.getInt(12));
				trimmerInfo.setStoreCourseInfo(rs.getString(13));
				trimmerInfo.setStoreCommitment(rs.getString(14));
				trimmerInfo.setStoreBusinessDay(rs.getString(15));
				trimmerInfo.setStoreStartBusinessTime(rs.getTimestamp(16)==null?null:rs.getTimestamp(16).toLocalDateTime().toLocalTime());
				trimmerInfo.setStoreEndBusinessTime(rs.getTimestamp(17)==null?null:rs.getTimestamp(17).toLocalDateTime().toLocalTime());
				trimmerInfo.setStoreBusinessComplement(rs.getString(18));
				trimmerInfoList.add(trimmerInfo);
			}
		} catch(SQLException e) {
			throw e;
		}
		return trimmerInfoList;
	}

	/**
	 * テーブル抽出
	 * @param columnStr 検索文字列
	 * @param tableName テーブル名
	 * @param whereStr where句
	 * @param list SQLパラメータリスト
	 * @return SQLステートメントオブジェクト
	 */
	private PreparedStatement createSelectStatement(String columnStr, String tableName, String whereStr, List<HashMap<String, Object>> list) throws SQLException {
		String col = "*";
		if(!StringUtils.isEmpty(columnStr)) {
			col = columnStr;
		}

		String sql = "SELECT " + col + " FROM "+ tableName;

		if(!StringUtils.isEmpty(whereStr)) {
			sql += " WHERE " + whereStr;
		}

		PreparedStatement pstmt = this.con.prepareStatement(sql);

		if(list != null) {
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> data = list.get(i);
				setSqlParameter(pstmt, (i+1), data.get("value"), (Integer)data.get("dataType"));
			}
		}

		return pstmt;
	}
}
