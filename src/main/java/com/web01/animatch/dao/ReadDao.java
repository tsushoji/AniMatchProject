package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * ページング用トリマー情報データ存在
	 */
	private boolean isExistTrimmerInfoByStartDataRowNumAndEndDataRowNumAndSearchForm = true;

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

		String whereStr = createWhereOfOwnerInfo(searchForm, registFormDataList, propertiesService);

		try (PreparedStatement pstmt = createSelectStatement(null, "v_owner_info", whereStr, null, null, registFormDataList);){
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
		PropertiesService propertiesService = new PropertiesService();
		List<HashMap<String, Object>> registFormDataList = new ArrayList<>();

		String whereStr = createWhereOfTrimmerInfo(searchForm, registFormDataList, propertiesService);

		//営業時間入力条件に合わなかった場合
		if(!isExistTrimmerInfoByStartDataRowNumAndEndDataRowNumAndSearchForm) {
			return trimmerInfoList;
		}

		try (PreparedStatement pstmt = createSelectStatement(null, "v_trimmer_info", whereStr, null, null, registFormDataList);){
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
					trimmerInfo.setStoreName(rs.getString("store_name"));
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
	 * 飼い主情報Where句作成
	 * @param searchForm 検索フォームオブジェクト
	 * @param paramDataList SQLパラメータデータリスト
	 * @param propertiesService プロパティサービスオブジェクト
	 * @return 飼い主情報Where句
	 */
	private String createWhereOfOwnerInfo(SearchForm searchForm, List<HashMap<String, Object>> paramDataList, PropertiesService propertiesService) {
		String whereOfOwnerInfo = null;

		//ユーザIDWhere句作成
		String whereOfUserId = createWhereOfUserId(searchForm, paramDataList);
		whereOfOwnerInfo = createSqlClauseContent(whereOfUserId, whereOfOwnerInfo, LogicalOperatorType.AND);

		//都道府県、市区町村Where句作成
		String whereOfPrefecturesAndCities = createWhereOfPrefecturesAndCities(searchForm, paramDataList, propertiesService);
		if(StringUtils.isNotEmpty(whereOfPrefecturesAndCities)) {
			whereOfOwnerInfo = createSqlClauseContent(whereOfPrefecturesAndCities, whereOfOwnerInfo, LogicalOperatorType.AND);
		}

		//動物区分Where句作成
		String petType = searchForm.getPetType();
		if(StringUtils.isNotEmpty(petType) && !petType.equals("000")) {
			whereOfOwnerInfo = createSqlClauseContent("pet_type = ?", whereOfOwnerInfo, LogicalOperatorType.AND);
			paramDataList.add(createSqlParatemerMap(petType, Types.VARCHAR));
		}

		//動物性別Where句作成
		String petSex = searchForm.getPetSex();
		if(StringUtils.isNotEmpty(petSex) && !petSex.equals("000")) {
			whereOfOwnerInfo = createSqlClauseContent("pet_sex = ?", whereOfOwnerInfo, LogicalOperatorType.AND);
			paramDataList.add(createSqlParatemerMap(petSex, Types.VARCHAR));
		}

		//検索内容Where句作成
		String searchContents = searchForm.getSearchContents();
		if(StringUtils.isNotEmpty(searchContents)) {
			//半角、全角スペース区切りで検索された場合を考慮
			String[] searchContentsAry = searchContents.replaceAll("　", " ").split(" ");
			for(String contents:searchContentsAry) {
				String whereOfSearchContents = null;
				String searchContentsParam = "%" + contents + "%";
				whereOfSearchContents = createSqlClauseContent("pet_nickname LIKE ?", whereOfSearchContents, LogicalOperatorType.OR);
				paramDataList.add(createSqlParatemerMap(searchContentsParam, Types.VARCHAR));

				whereOfSearchContents = createSqlClauseContent("pet_remarks LIKE ?", whereOfSearchContents, LogicalOperatorType.OR);
				paramDataList.add(createSqlParatemerMap(searchContentsParam, Types.VARCHAR));

				if(StringUtils.isNotEmpty(whereOfSearchContents)) {
					whereOfOwnerInfo = createSqlClauseContent(" ( " + whereOfSearchContents + " ) ", whereOfOwnerInfo, LogicalOperatorType.AND);
				}
			}
		}

		return whereOfOwnerInfo;
	}

	/**
	 * トリマー情報Where句作成
	 * @param searchForm 検索フォームオブジェクト
	 * @param paramDataList SQLパラメータデータリスト
	 * @param propertiesService プロパティサービスオブジェクト
	 * @return トリマー情報Where句
	 * @throws SQLException
	 */
	private String createWhereOfTrimmerInfo(SearchForm searchForm, List<HashMap<String, Object>> paramDataList, PropertiesService propertiesService) throws SQLException {
		String whereOfTrimmerInfo = null;

		//ユーザIDWhere句作成
		String whereOfUserId = createWhereOfUserId(searchForm, paramDataList);
		whereOfTrimmerInfo = createSqlClauseContent(whereOfUserId, whereOfTrimmerInfo, LogicalOperatorType.AND);

		//都道府県、市区町村Where句作成
		String whereOfPrefecturesAndCities = createWhereOfPrefecturesAndCities(searchForm, paramDataList, propertiesService);
		if(StringUtils.isNotEmpty(whereOfPrefecturesAndCities)) {
			whereOfTrimmerInfo = createSqlClauseContent(whereOfPrefecturesAndCities, whereOfTrimmerInfo, LogicalOperatorType.AND);
		}

		//検索内容Where句作成
		String searchContents = searchForm.getSearchContents();

		if(StringUtils.isNotEmpty(searchContents)) {
			//半角、全角スペース区切りで検索された場合を考慮
			String[] searchContentsAry = searchContents.replaceAll("　", " ").split(" ");
			for(String contents:searchContentsAry) {
				String whereOfSearchContents = null;
				String searchContentsParam = "%" + contents + "%";
				whereOfSearchContents = createSqlClauseContent("store_name LIKE ?", whereOfSearchContents, LogicalOperatorType.OR);
				paramDataList.add(createSqlParatemerMap(searchContentsParam, Types.VARCHAR));

				whereOfSearchContents = createSqlClauseContent("store_commitment LIKE ?", whereOfSearchContents, LogicalOperatorType.OR);
				paramDataList.add(createSqlParatemerMap(searchContentsParam, Types.VARCHAR));

				if(StringUtils.isNotEmpty(whereOfSearchContents)) {
					whereOfTrimmerInfo = createSqlClauseContent(" ( " + whereOfSearchContents + " ) ", whereOfTrimmerInfo, LogicalOperatorType.AND);
				}
			}
		}
		//営業時間Where句作成
		String inputBusinessHoursWeekday = searchForm.getBusinessHoursInputValue();
		List<String> businessHoursWeekdayList = new ArrayList<>();
		if(StringUtils.isNotEmpty(inputBusinessHoursWeekday)) {
			String[] businessHoursWeekdayAry = inputBusinessHoursWeekday.split(",");
			for(String businessHoursWeekday:businessHoursWeekdayAry) {
				businessHoursWeekdayList.add("00" + businessHoursWeekday);
			}
		}
		String businessHoursStartTime = searchForm.getBusinessHoursStartTime();
		String businessHoursEndTime = searchForm.getBusinessHoursEndTime();
		if(StringUtils.isNotEmpty(inputBusinessHoursWeekday) || StringUtils.isNotEmpty(businessHoursStartTime) || StringUtils.isNotEmpty(businessHoursEndTime)) {
			//該当する店舗IDリスト
			List<Integer> storeIdList = findBusinessHoursStoreIdByStoreId(businessHoursWeekdayList, businessHoursStartTime, businessHoursEndTime);
			ArrayList<String> storeIdParams = new ArrayList<>();
			for(int storeId:storeIdList) {
				storeIdParams.add("?");
				paramDataList.add(createSqlParatemerMap(storeId, Types.INTEGER));
			}
			if(storeIdParams.size() > 0) {
				whereOfTrimmerInfo = createSqlClauseContent("store_id IN (" + String.join(",", storeIdParams) + ")", whereOfTrimmerInfo, LogicalOperatorType.AND);
			}
			if(storeIdList.size() == 0) {
				isExistTrimmerInfoByStartDataRowNumAndEndDataRowNumAndSearchForm = false;
			}
		}

		return whereOfTrimmerInfo;
	}

	/**
	 * ユーザIDWhere句作成
	 * @param searchForm 検索フォームオブジェクト
	 * @param paramDataList SQLパラメータデータリスト
	 * @return ユーザIDWhere句
	 */
	private String createWhereOfUserId(SearchForm searchForm, List<HashMap<String, Object>> paramDataList) {
		String whereOfUserId = null;

		String userId = searchForm.getUserId();
		if(StringUtils.isNotEmpty(userId) && StringUtils.isNumeric(userId)) {
			whereOfUserId = "user_id = ?";
			paramDataList.add(createSqlParatemerMap(Integer.parseInt(userId), Types.INTEGER));
		}

		return whereOfUserId;
	}

	/**
	 * 都道府県、市区町村Where句作成
	 * @param searchForm 検索フォームオブジェクト
	 * @param paramDataList SQLパラメータデータリスト
	 * @param propertiesService プロパティサービスオブジェクト
	 * @return 都道府県、市区町村Where句
	 */
	private String createWhereOfPrefecturesAndCities(SearchForm searchForm, List<HashMap<String, Object>> paramDataList, PropertiesService propertiesService) {
		String whereOfPrefecturesAndCities = null;
		int paramDataListSize = 0;

		String prefecturesKey = searchForm.getPrefectures();
		if(StringUtils.isNotEmpty(prefecturesKey) && !prefecturesKey.equals("000")) {
			String prefectures = propertiesService.getValue(PropertiesService.PREFECTURES_KEY_INIT_STR + prefecturesKey);
			whereOfPrefecturesAndCities = "street_address LIKE ?";
			paramDataList.add(createSqlParatemerMap(prefectures + "%", Types.VARCHAR));
			String cities = searchForm.getCities();
			if(StringUtils.isNotEmpty(cities) && !cities.equals("000")) {
				paramDataListSize = paramDataList.size();
				whereOfPrefecturesAndCities = "street_address = ?";
				if(paramDataListSize > 0) {
					paramDataList.remove(paramDataListSize - 1);
				}
				paramDataList.add(createSqlParatemerMap(prefectures + cities, Types.VARCHAR));
			}
		}

		return whereOfPrefecturesAndCities;
	}

	/**
	 * 営業時間店舗ID抽出
	 * @param businessHoursWeekdayList 曜日
	 * @param startBusinessHoursTime 開始時間
	 * @param endBusinessHoursTime 終了時間
	 * @return 営業時間店舗IDリスト
	 */
	private List<Integer> findBusinessHoursStoreIdByStoreId(List<String> businessHoursWeekdayList, String startBusinessHoursTime, String endBusinessHoursTime) throws SQLException {
		List<Integer> businessHoursStoreIdList = new ArrayList<>();
		List<HashMap<String, Object>> businessHoursStoreIdDataList = new ArrayList<>();
		String whereStr = null;
		String existsSQL = "EXISTS(SELECT * FROM t_business_hours AS t_business_hours_*1 WHERE t_business_hours_*1.store_id = t_business_hours.store_id AND t_business_hours_*1.business_day = ?)";
		List<String> businessHoursWeekdayMasterList = new ArrayList<>(Arrays.asList("000", "001", "002", "003", "004", "005", "006"));

		// 曜日が入力されている場合
		if(businessHoursWeekdayList.size() > 0) {
			for(String businessHoursWeekdayMaster:businessHoursWeekdayMasterList) {
				String tmpExistsSQL;
				if(businessHoursWeekdayList.contains(businessHoursWeekdayMaster)) {
					tmpExistsSQL = existsSQL.replace("*1", businessHoursWeekdayMaster);
				}else {
					tmpExistsSQL = " NOT " + existsSQL.replace("*1", businessHoursWeekdayMaster);
				}
				businessHoursStoreIdDataList.add(createSqlParatemerMap(businessHoursWeekdayMaster, Types.VARCHAR));

				whereStr = createSqlClauseContent(tmpExistsSQL, whereStr, LogicalOperatorType.AND);
			}
		}

		if(StringUtils.isNotEmpty(startBusinessHoursTime)) {
			String[] startBusinessHoursTimeAry = startBusinessHoursTime.split(":");
			whereStr = createSqlClauseContent("start_business_time >= TIME(?)", whereStr, LogicalOperatorType.AND);
			businessHoursStoreIdDataList.add(createSqlParatemerMap(Time.valueOf(LocalTime.of(Integer.parseInt(startBusinessHoursTimeAry[0]), Integer.parseInt(startBusinessHoursTimeAry[1]))), Types.TIME));
		}

		if(StringUtils.isNotEmpty(endBusinessHoursTime)) {
			String[] endBusinessHoursTimeAry = endBusinessHoursTime.split(":");
			whereStr = createSqlClauseContent("end_business_time <= TIME(?)", whereStr, LogicalOperatorType.AND);
			businessHoursStoreIdDataList.add(createSqlParatemerMap(Time.valueOf(LocalTime.of(Integer.parseInt(endBusinessHoursTimeAry[0]), Integer.parseInt(endBusinessHoursTimeAry[1]))), Types.TIME));
		}

		try (PreparedStatement pstmt = createSelectStatement("DISTINCT store_id", "t_business_hours", whereStr, "null", null, businessHoursStoreIdDataList);){
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				businessHoursStoreIdList.add(rs.getInt("store_id"));
			}
		} catch(SQLException e) {
			throw e;
		}

		return businessHoursStoreIdList;
	}

	/**
	 * 会員詳細用飼い主情報抽出
	 * @param userId ユーザID
	 * @return 飼い主情報オブジェクト
	 */
	public OwnerInfo findOwnerInfoByUserId(int userId) throws SQLException {
		OwnerInfo ownerInfo = new OwnerInfo();
		List<HashMap<String, Object>> registFormDataList = new ArrayList<>();
		String whereStr = null;

		whereStr = createSqlClauseContent("user_id = ?", whereStr, LogicalOperatorType.AND);
		registFormDataList.add(createSqlParatemerMap(userId, Types.INTEGER));

		try (PreparedStatement pstmt = createSelectStatement(null, "v_owner_info", whereStr, null, null, registFormDataList);){
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
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
			}
		} catch(SQLException e) {
			throw e;
		}
		return ownerInfo;
	}

	/**
	 * 会員詳細用トリマー情報抽出
	 * @param userId ユーザID
	 * @return トリマー情報オブジェクト
	 */
	public TrimmerInfo findTrimmerInfoByUserId(int userId) throws SQLException {
		TrimmerInfo trimmerInfo = new TrimmerInfo();
		List<HashMap<String, Object>> registFormDataList = new ArrayList<>();
		String whereStr = null;

		whereStr = createSqlClauseContent("user_id = ?", whereStr, LogicalOperatorType.AND);
		registFormDataList.add(createSqlParatemerMap(userId, Types.INTEGER));

		try (PreparedStatement pstmt = createSelectStatement(null, "v_trimmer_info", whereStr, null, null, registFormDataList);){
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
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
				trimmerInfo.setStoreName(rs.getString("store_name"));
				trimmerInfo.setStoreEmployeesNumber(rs.getInt("store_employees_number") == 0?null:rs.getInt("store_employees_number"));
				trimmerInfo.setStoreCourseInfo(rs.getString("store_course_info"));
				trimmerInfo.setStoreCommitment(rs.getString("store_commitment"));
				trimmerInfo.setTrimmerInfoBusinessHoursList(findBusinessHoursByStoreId(storeId));
			}
		} catch(SQLException e) {
			throw e;
		}
		return trimmerInfo;
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
