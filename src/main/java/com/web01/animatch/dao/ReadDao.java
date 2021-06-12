package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

		//都道府県、市区町村Where句作成
		String whereOfPrefecturesAndCities = createWhereOfPrefecturesAndCities(searchForm, paramDataList, propertiesService);
		if(StringUtils.isNotEmpty(whereOfPrefecturesAndCities)) {
			whereOfOwnerInfo = whereOfPrefecturesAndCities;
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
			//空白区切りで検索された場合を考慮
			String[] searchContentsAry = searchContents.split(" ");
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
		//検索営業時間曜日List
		List<String> businessHoursWeekdayList = new ArrayList<>();

		//都道府県、市区町村Where句作成
		String whereOfPrefecturesAndCities = createWhereOfPrefecturesAndCities(searchForm, paramDataList, propertiesService);
		if(StringUtils.isNotEmpty(whereOfPrefecturesAndCities)) {
			whereOfTrimmerInfo = whereOfPrefecturesAndCities;
		}

		//検索内容Where句作成
		String searchContents = searchForm.getSearchContents();

		if(StringUtils.isNotEmpty(searchContents)) {
			//空白区切りで検索された場合を考慮
			String[] searchContentsAry = searchContents.split(" ");
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
		if(StringUtils.isNotEmpty(inputBusinessHoursWeekday)) {
			String[] businessHoursWeekdayAry = inputBusinessHoursWeekday.split(",");
			for(String businessHoursWeekday:businessHoursWeekdayAry) {
				businessHoursWeekdayList.add("00" + businessHoursWeekday);
			}
			//重複要素を取り除く
			businessHoursWeekdayList = businessHoursWeekdayList.stream().distinct().collect(Collectors.toList());
		}
		String businessHoursStartTime = searchForm.getBusinessHoursStartTime();
		String businessHoursEndTime = searchForm.getBusinessHoursEndTime();
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
		if((StringUtils.isNotEmpty(inputBusinessHoursWeekday) || StringUtils.isNotEmpty(businessHoursStartTime) || StringUtils.isNotEmpty(businessHoursEndTime)) && storeIdList.size() == 0) {
			isExistTrimmerInfoByStartDataRowNumAndEndDataRowNumAndSearchForm = false;
		}

		return whereOfTrimmerInfo;
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

		ArrayList<String> businessDayParams = new ArrayList<>();
		for(String businessHoursWeekday:businessHoursWeekdayList) {
			businessDayParams.add("?");
			businessHoursStoreIdDataList.add(createSqlParatemerMap(businessHoursWeekday, Types.VARCHAR));
		}
		if(businessDayParams.size() > 0) {
			whereStr = "business_day IN (" + String.join(",", businessDayParams) + ")";
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

		//営業時間曜日が複数入力された場合
		if(businessHoursWeekdayList.size() > 1) {
			HashMap<Integer, List<String>> businessHoursStoreIdMap = new HashMap<>();
			whereStr = "store_id = ?";
			for(Integer businessHoursStoreId:businessHoursStoreIdList) {
				List<String> weekdayList = new ArrayList<>();

				// パラメータデータリストリセット
				businessHoursStoreIdDataList.clear();
				businessHoursStoreIdDataList.add(createSqlParatemerMap(businessHoursStoreId, Types.INTEGER));

				try (PreparedStatement pstmt = createSelectStatement("business_day", "t_business_hours", whereStr, "null", null, businessHoursStoreIdDataList);){
					ResultSet rs = pstmt.executeQuery();

					while(rs.next()) {
						weekdayList.add(rs.getString("business_day"));
					}
				} catch(SQLException e) {
					throw e;
				}

				businessHoursStoreIdMap.put(businessHoursStoreId, weekdayList);
			}

			//営業時間店舗IDリストリセット
			businessHoursStoreIdList.clear();
			for(Map.Entry<Integer, List<String>> entry : businessHoursStoreIdMap.entrySet()){
				if(entry.getValue().containsAll(businessHoursWeekdayList)) {
					businessHoursStoreIdList.add(entry.getKey());
				}
			}
		}

		return businessHoursStoreIdList;
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
