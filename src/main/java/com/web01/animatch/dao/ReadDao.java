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

import com.web01.animatch.dto.OwnerInfo;
import com.web01.animatch.dto.SearchForm;
import com.web01.animatch.dto.TrimmerInfo;
import com.web01.animatch.dto.TrimmerInfoBusinessHours;
import com.web01.animatch.service.PropertiesService;
import com.web01.animatch.service.SearchService;

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
	 * ページング用飼い主情報抽出
	 * @param searchService 検索サービスオブジェクト
	 * @param startDataRowNum 検索開始行数
	 * @param endDataRowNum 検索終了行数
	 * @param searchForm 検索フォームオブジェクト
	 * @return 飼い主情報オブジェクトリスト
	 */
	public List<OwnerInfo> findOwnerInfoByStartDataRowNumAndEndDataRowNumAndSearchForm(SearchService searchService, int startDataRowNum, int endDataRowNum, SearchForm searchForm) throws SQLException {
		List<OwnerInfo> ownerInfoList = new ArrayList<>();
		PropertiesService propertiesService = new PropertiesService();
		List<HashMap<String, Object>> registFormDataList = new ArrayList<>();

		String whereStr = getWhereOfOwnerInfoOrTrimmerInfo(searchForm, registFormDataList, propertiesService);

		try (PreparedStatement pstmt = createSelectStatement(null, "v_owner_info", whereStr, null, null, null);){
			ResultSet rs = pstmt.executeQuery();

			int count = 0;
			while(rs.next()) {

				int culRow = rs.getRow();
				if(culRow >= startDataRowNum && culRow <= endDataRowNum) {
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
				count++;
			}
			searchService.setSearchCount(count);
		} catch(SQLException e) {
			throw e;
		}
		return ownerInfoList;
	}

	/**
	 * ページング用トリマー情報抽出
	 * @param searchService 検索サービスオブジェクト
	 * @param startDataRowNum 検索開始行数
	 * @param endDataRowNum 検索終了行数
	 * @return トリマー情報オブジェクトリスト
	 */
	public List<TrimmerInfo> findTrimmerInfoByStartDataRowNumAndEndDataRowNumAndSearchForm(SearchService searchService, int startDataRowNum, int endDataRowNum, SearchForm searchForm) throws SQLException {
		List<TrimmerInfo> trimmerInfoList = new ArrayList<>();

		try (PreparedStatement pstmt = createSelectStatement(null, "v_trimmer_info", null, null, null, null);){
			ResultSet rs = pstmt.executeQuery();

			int count = 0;
			while(rs.next()) {
				int culRow = rs.getRow();
				if(culRow >= startDataRowNum && culRow <= endDataRowNum) {
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
					trimmerInfo.setTrimmerInfoBusinessHoursList(findBusinessHoursByStoreId(storeId));
					trimmerInfoList.add(trimmerInfo);
				}
				count++;
			}
			searchService.setSearchCount(count);
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
	private List<TrimmerInfoBusinessHours> findBusinessHoursByStoreId(int storeId) throws SQLException {
		List<TrimmerInfoBusinessHours> trimmerInfoBusinessHoursList = new ArrayList<>();
		List<HashMap<String, Object>> trimmerInfoBusinessHoursDataList = new ArrayList<>();

		trimmerInfoBusinessHoursDataList.add(createSqlParatemerMap(storeId, Types.INTEGER));

		try (PreparedStatement pstmt = createSelectStatement(null, "t_business_hours", "store_id = ?", "business_day", null, trimmerInfoBusinessHoursDataList);){
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				TrimmerInfoBusinessHours trimmerInfoBusinessHours = new TrimmerInfoBusinessHours();
				trimmerInfoBusinessHours.setBusinessDay(rs.getString("business_day"));
				trimmerInfoBusinessHours.setStartBusinessTime(rs.getTimestamp("start_business_time") == null?null:rs.getTimestamp("start_business_time").toLocalDateTime().toLocalTime());
				trimmerInfoBusinessHours.setEndBusinessTime(rs.getTimestamp("end_business_time") == null?null:rs.getTimestamp("end_business_time").toLocalDateTime().toLocalTime());
				trimmerInfoBusinessHours.setComplement(rs.getString("complement"));
				trimmerInfoBusinessHoursList.add(trimmerInfoBusinessHours);
			}
		} catch(SQLException e) {
			throw e;
		}

		return trimmerInfoBusinessHoursList;
	}

	/**
	 * ページング用飼い主、トリマーWhere句取得
	 * @param searchForm 検索フォームオブジェクト
	 * @param paramDataList SQLパラメータデータリスト
	 * @param propertiesService プロパティサービスオブジェクト
	 * @return Where句
	 */
	private String getWhereOfOwnerInfoOrTrimmerInfo(SearchForm searchForm, List<HashMap<String, Object>> paramDataList, PropertiesService propertiesService) {
		String whereStr = null;
		int paramDataListSize = 0;

		//都道府県、市区町村Where句取得
		if(StringUtils.isNotEmpty(searchForm.getPrefectures())) {
			String prefectures = propertiesService.getValue(PropertiesService.PREFECTURES_KEY_INIT_STR + searchForm.getPrefectures());
			whereStr = "street_address LIKE ?%";
			paramDataList.add(createSqlParatemerMap(prefectures, Types.VARCHAR));
			if(StringUtils.isNotEmpty(searchForm.getCities())) {
				String cities = searchForm.getCities();
				paramDataListSize = paramDataList.size();
				if(StringUtils.isNotEmpty(whereStr)) {
					whereStr = "street_address = ?";
					if(paramDataListSize > 0) {
						paramDataList.remove(paramDataListSize - 1);
					}
					paramDataList.add(createSqlParatemerMap(prefectures + cities, Types.VARCHAR));
				}else {
					whereStr = "street_address LIKE %?";
					if(paramDataListSize > 0) {
						paramDataList.remove(paramDataListSize - 1);
					}
					paramDataList.add(createSqlParatemerMap(cities, Types.VARCHAR));
				}
			}
		}

		return whereStr;
	}

	/**
	 * テーブル抽出
	 * @param columnStr 検索文字列
	 * @param tableName テーブル名
	 * @param whereStr where句
	 * @param list SQLパラメータリスト
	 * @return SQLステートメントオブジェクト
	 */
	private PreparedStatement createSelectStatement(String columnStr, String tableName, String whereStr, String orderStr, String limitStr, List<HashMap<String, Object>> list) throws SQLException {
		String col = "*";
		if(!StringUtils.isEmpty(columnStr)) {
			col = columnStr;
		}

		String sql = "SELECT " + col + " FROM "+ tableName;

		if(!StringUtils.isEmpty(whereStr)) {
			sql += " WHERE " + whereStr;
		}

		if(!StringUtils.isEmpty(orderStr)) {
			sql += " ORDER BY " + orderStr;
		}

		if(!StringUtils.isEmpty(limitStr)) {
			sql += " LIMIT " + limitStr;
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
