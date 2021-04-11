package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.web01.animatch.dto.BusinessHours;
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

			while(rs.next()) {
				OwnerInfo ownerInfo = new OwnerInfo();
				ownerInfo.setUserId(rs.getInt("user_id") == 0?null:rs.getInt("user_id"));
				ownerInfo.setUserName(rs.getString("user_name"));
				ownerInfo.setPassword(rs.getString("password"));
				ownerInfo.setSex(rs.getString("sex"));
				ownerInfo.setBirthday(rs.getDate("birthday"));
				ownerInfo.setPostalCode(rs.getString("postal_code"));
				ownerInfo.setStreetAddress(rs.getString("street_address"));
				ownerInfo.setEmailAddress(rs.getString("email_address"));
				ownerInfo.setTelephoneNumber(rs.getString("telephone_number"));
				ownerInfo.setPetId(rs.getInt("pet_id") == 0?null:rs.getInt("pet_id"));
				ownerInfo.setPetImage(rs.getBytes("pet_image"));
				ownerInfo.setPetNickName(rs.getString("pet_nickname"));
				ownerInfo.setPetSex(rs.getString("pet_sex"));
				ownerInfo.setPetType(rs.getString("pet_type"));
				ownerInfo.setPetWeight(rs.getFloat("pet_weight") == 0?null:rs.getFloat("pet_weight"));
				ownerInfo.setPetRemarks(rs.getString("pet_remarks"));
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

			while(rs.next()) {
				TrimmerInfo trimmerInfo = new TrimmerInfo();
				int storeId = rs.getInt("store_id");
				trimmerInfo.setUserId(rs.getInt("user_id") == 0?null:rs.getInt("user_id"));
				trimmerInfo.setUserName(rs.getString("user_name"));
				trimmerInfo.setPassword(rs.getString("password"));
				trimmerInfo.setSex(rs.getString("sex"));
				trimmerInfo.setBirthday(rs.getDate("birthday"));
				trimmerInfo.setPostalCode(rs.getString("postal_code"));
				trimmerInfo.setStreetAddress(rs.getString("street_address"));
				trimmerInfo.setEmailAddress(rs.getString("email_address"));
				trimmerInfo.setTelephoneNumber(rs.getString("telephone_number"));
				trimmerInfo.setStoreId(storeId == 0?null:storeId);
				trimmerInfo.setStoreImage(rs.getBytes("store_image"));
				trimmerInfo.setStoreName(rs.getString("store_store_name"));
				trimmerInfo.setStoreEmployeesNumber(rs.getInt("store_employees_number") == 0?null:rs.getInt("store_employees_number"));
				trimmerInfo.setStoreCourseInfo(rs.getString("store_course_info"));
				trimmerInfo.setStoreCommitment(rs.getString("store_commitment"));
				trimmerInfo.setBusinessHoursList(storeId == 0?null:(findBusinessHoursByStoreId(storeId).size() > 0?findBusinessHoursByStoreId(storeId):null));
				trimmerInfoList.add(trimmerInfo);
			}
		} catch(SQLException e) {
			throw e;
		}
		return trimmerInfoList;
	}

	/**
	 * 営業時間抽出
	 * @param storeId 店舗ID
	 * @return 営業時間オブジェクトリスト
	 */
	private List<BusinessHours> findBusinessHoursByStoreId(int storeId) throws SQLException {
		List<BusinessHours> businessHoursList = new ArrayList<>();
		List<HashMap<String, Object>> businessHoursDataList = new ArrayList<>();

		businessHoursDataList.add(createSqlParatemerMap(storeId, Types.INTEGER));

		try (PreparedStatement pstmt = createSelectStatement(null, "t_business_hours", "store_id = ?", businessHoursDataList);){
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				BusinessHours businessHours = new BusinessHours();
				businessHours.setBusinessDay(rs.getString("business_day"));
				businessHours.setStartBusinessTime(rs.getTimestamp("start_business_time") == null?null:rs.getTimestamp("start_business_time").toLocalDateTime().toLocalTime());
				businessHours.setEndBusinessTime(rs.getTimestamp("end_business_time") == null?null:rs.getTimestamp("end_business_time").toLocalDateTime().toLocalTime());
				businessHours.setComplement(rs.getString("complement"));
				businessHoursList.add(businessHours);
			}
		} catch(SQLException e) {
			throw e;
		}
		return businessHoursList;
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
