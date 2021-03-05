package com.web01.animatch.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.Pet;
import com.web01.animatch.dto.RegistData;
import com.web01.animatch.dto.Store;
import com.web01.animatch.dto.User;

/**
 * registDBクラス
 * @author Tsuji
 * @version 1.0
 */
public class RegistDao {

	//メンバー
	/**
	 * DBコネクションオブジェクト
	 */
	private Connection con;

	//定数
	/**
	 * ユーザテーブルカラム数
	 */
//	private static final int USER_COL_COUNT = 14;
	/**
	 * ペットテーブルカラム数
	 */
//	private static final int PET_COL_COUNT = 10;
	/**
	 * 店舗テーブルカラム数
	 */
//	private static final int STORE_COL_COUNT = 9;
	/**
	 * 営業時間テーブルカラム数
	 */
//	private static final int BUSINESS_HOURS_COL_COUNT = 8;
	/**
	 * IDデフォルト値
	 */
	private static final int DEFAULT_ID = 1000000000;

	/**
	 * 引数付きコンストラクタ
	 * @param con DBコネクションオブジェクト
	 */
	public RegistDao(Connection con) {
		this.con = con;
	}

	/**
	 * 最大ユーザID取得
	 * @return ユーザID
	 */
	public int getMaxUserId() throws SQLException {
		int userId = DEFAULT_ID;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM t_user ORDER BY user_id DESC LIMIT 1");

		if(rs.next()) {
			userId = rs.getInt("user_id");
		}

		rs.close();
		stmt.close();

		return userId;
	}

	/**
	 * 最大ペットID取得
	 * @return ペットID
	 */
	public int getMaxPetId() throws SQLException {
		int petId = DEFAULT_ID;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM t_pet ORDER BY pet_id DESC LIMIT 1");

		if(rs.next()) {
			petId = rs.getInt("pet_id");
		}

		rs.close();
		stmt.close();

		return petId;
	}

	/**
	 * 最大店舗ID取得
	 * @return 店舗ID
	 */
	public int getMaxStoreId() throws SQLException {
		int storeId = DEFAULT_ID;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM t_store ORDER BY store_id DESC LIMIT 1");

		if(rs.next()) {
			storeId = rs.getInt("store_id");
		};

		rs.close();
		stmt.close();

		return storeId;
	}

	/**
	 * 飼い主DB登録
	 * @param user ユーザオブジェクト
	 * @return DB登録成功失敗
	 */
	public boolean registOwner(User user) throws SQLException {
		Pet pet = user.getPet();

//		List<Object> userDataList = new ArrayList<>();
//		List<Object> petDataList = new ArrayList<>();
//
//		userDataList.add(user.getUserId());
//		userDataList.add(user.getUserName());
//		userDataList.add(user.getPassword());
//		userDataList.add(user.getSex());
//		userDataList.add(new Date(user.getBirthday().getTime()));
//		userDataList.add(user.getPostalCode());
//		userDataList.add(user.getStreetAddress());
//		userDataList.add(user.getEmailAddress());
//		userDataList.add(user.getTelephoneNumber());
//		userDataList.add(pet.getPetId());
//		userDataList.add(Integer.valueOf(DEFAULT_ID));
//		userDataList.add(user.getIsDeleted());
//		userDataList.add(Timestamp.valueOf(user.getInsertedTime()));
//		userDataList.add(Timestamp.valueOf(user.getUpdatedTime()));
//
//		petDataList.add(pet.getPetId());
//		petDataList.add(pet.getImage());
//		petDataList.add(pet.getNickName());
//		petDataList.add(pet.getSex());
//		petDataList.add(pet.getType());
//		petDataList.add(pet.getWeight());
//		petDataList.add(pet.getRemarks());
//		petDataList.add(pet.getIsDeleted());
//		petDataList.add(Timestamp.valueOf(pet.getInsertedTime()));
//		petDataList.add(Timestamp.valueOf(pet.getUpdatedTime()));


		List<RegistData> userDataList = new ArrayList<>();
		List<RegistData> petDataList = new ArrayList<>();

		userDataList.add(new RegistData(user.getUserId(), Types.INTEGER));
		userDataList.add(new RegistData(user.getUserName(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getPassword(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getSex(), Types.VARCHAR));
		userDataList.add(new RegistData(new Date(user.getBirthday().getTime()), Types.DATE));
		userDataList.add(new RegistData(user.getPostalCode(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getStreetAddress(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getEmailAddress(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getTelephoneNumber(), Types.VARCHAR));
		userDataList.add(new RegistData(pet.getPetId(), Types.INTEGER));
		userDataList.add(new RegistData(Integer.valueOf(DEFAULT_ID), Types.INTEGER));
		userDataList.add(new RegistData(user.getIsDeleted(), Types.INTEGER));
		userDataList.add(new RegistData(Timestamp.valueOf(user.getInsertedTime()), Types.TIMESTAMP));
		userDataList.add(new RegistData(Timestamp.valueOf(user.getUpdatedTime()), Types.TIMESTAMP));

		petDataList.add(new RegistData(pet.getPetId(), Types.INTEGER));
		petDataList.add(new RegistData(pet.getImage(), Types.BLOB));
		petDataList.add(new RegistData(pet.getNickName(), Types.VARCHAR));
		petDataList.add(new RegistData(pet.getSex(), Types.VARCHAR));
		petDataList.add(new RegistData(pet.getType(), Types.VARCHAR));
		petDataList.add(new RegistData(pet.getWeight(), Types.FLOAT));
		petDataList.add(new RegistData(pet.getRemarks(), Types.VARCHAR));
		petDataList.add(new RegistData(pet.getIsDeleted(), Types.INTEGER));
		petDataList.add(new RegistData(Timestamp.valueOf(pet.getInsertedTime()), Types.TIMESTAMP));
		petDataList.add(new RegistData(Timestamp.valueOf(pet.getUpdatedTime()), Types.TIMESTAMP));

		this.con.setAutoCommit(false);

//		String sql = "INSERT INTO t_user VALUES(";
//		for(int i = 0; i < USER_COL_COUNT - 1; i++) {
//			sql += "?, ";
//		}
//		sql += "?)";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//
//		setSQLIntegerParameter(pstmt, 1, user.getUserId());
//		setSQLVarcharParameter(pstmt, 2, user.getUserName());
//		setSQLVarcharParameter(pstmt, 3, user.getPassword());
//		setSQLVarcharParameter(pstmt, 4, user.getSex());
//		setSQLDateParameter(pstmt, 5, new Date(user.getBirthday().getTime()));
//		setSQLVarcharParameter(pstmt, 6, user.getPostalCode());
//		setSQLVarcharParameter(pstmt, 7, user.getStreetAddress());
//		setSQLVarcharParameter(pstmt, 8, user.getEmailAddress());
//		setSQLVarcharParameter(pstmt, 9, user.getTelephoneNumber());
//		setSQLIntegerParameter(pstmt, 10, pet.getPetId());
//		pstmt.setInt(11, DEFAULT_ID);
//		pstmt.setInt(12, user.getIsDeleted());
//		pstmt.setTimestamp(13, Timestamp.valueOf(user.getInsertedTime()));
//		pstmt.setTimestamp(14, Timestamp.valueOf(user.getUpdatedTime()));

//		int userDataParamCount = userDataList.size();
//		String sql = "INSERT INTO t_user VALUES(";
//		for(int i = 1; i < userDataParamCount; i++) {
//			sql += "?, ";
//		}
//		sql += "?)";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//
//		for (int i = 0; i < userDataParamCount; i++) {
//			setSqlParameter(pstmt, (i+1), userDataList.get(i));
//		}

		int userDataParamCount = userDataList.size();
		String sql = "INSERT INTO t_user VALUES(";
		for(int i = 1; i < userDataParamCount; i++) {
			sql += "?, ";
		}
		sql += "?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		for (int i = 0; i < userDataParamCount; i++) {
			setSqlParameter(pstmt, (i+1), userDataList.get(i).getRegistData(), userDataList.get(i).getRegistDataType());
		}

		pstmt.executeUpdate();

//		sql = "INSERT INTO t_pet VALUES(";
//		for(int i = 0; i < PET_COL_COUNT - 1; i++) {
//			sql += "?, ";
//		}
//		sql += "?)";
//		pstmt = con.prepareStatement(sql);
//
//		setSQLIntegerParameter(pstmt, 1, pet.getPetId());
//		setSQLBlobParameter(pstmt, 2, pet.getImage());
//		setSQLVarcharParameter(pstmt, 3, pet.getNickName());
//		setSQLVarcharParameter(pstmt, 4, pet.getSex());
//		setSQLVarcharParameter(pstmt, 5, pet.getType());
//		setSQLFloatParameter(pstmt, 6, pet.getWeight());
//		setSQLVarcharParameter(pstmt, 7, pet.getRemarks());
//		pstmt.setInt(8, pet.getIsDeleted());
//		pstmt.setTimestamp(9, Timestamp.valueOf(pet.getInsertedTime()));
//		pstmt.setTimestamp(10, Timestamp.valueOf(pet.getUpdatedTime()));

//		int petDataParamCount = petDataList.size();
//		sql = "INSERT INTO t_pet VALUES(";
//		for(int i = 1; i < petDataParamCount; i++) {
//			sql += "?, ";
//		}
//		sql += "?)";
//		pstmt = con.prepareStatement(sql);
//
//		for (int i = 0; i < petDataParamCount; i++) {
//			setSqlParameter(pstmt, (i+1), petDataList.get(i));
//		}

		int petDataParamCount = petDataList.size();
		sql = "INSERT INTO t_pet VALUES(";
		for(int i = 1; i < petDataParamCount; i++) {
			sql += "?, ";
		}
		sql += "?)";
		pstmt = con.prepareStatement(sql);

		for (int i = 0; i < petDataParamCount; i++) {
			setSqlParameter(pstmt, (i+1), petDataList.get(i).getRegistData(), petDataList.get(i).getRegistDataType());
		}

		pstmt.executeUpdate();

		this.con.commit();
		pstmt.close();

		return true;
	}

	/**
	 * トリマーDB登録
	 * @param user ユーザオブジェクト
	 * @return DB登録成功失敗
	 */
	public boolean registTrimmer(User user) throws SQLException {
		Store store = user.getStore();
		List<BusinessHours> businessHoursList = user.getStore().getBusinessHoursList();

//		List<Object> userDataList = new ArrayList<>();
//		List<Object> storeDataList = new ArrayList<>();
//		Map<Integer, List<Object>> businessHoursDataMap = new HashMap<>();
//
//		userDataList.add(user.getUserId());
//		userDataList.add(user.getUserName());
//		userDataList.add(user.getPassword());
//		userDataList.add(user.getSex());
//		userDataList.add(new Date(user.getBirthday().getTime()));
//		userDataList.add(user.getPostalCode());
//		userDataList.add(user.getStreetAddress());
//		userDataList.add(user.getEmailAddress());
//		userDataList.add(user.getTelephoneNumber());
//		userDataList.add(Integer.valueOf(DEFAULT_ID));
//		userDataList.add(store.getStoreId());
//		userDataList.add(user.getIsDeleted());
//		userDataList.add(Timestamp.valueOf(user.getInsertedTime()));
//		userDataList.add(Timestamp.valueOf(user.getUpdatedTime()));
//
//		int storeId = store.getStoreId();
//		storeDataList.add(storeId);
//		storeDataList.add(store.getImage());
//		storeDataList.add(store.getStoreName());
//		storeDataList.add(store.getEmployeesNumber());
//		storeDataList.add(store.getCourseInfo());
//		storeDataList.add(store.getCommitment());
//		storeDataList.add(store.getIsDeleted());
//		storeDataList.add(Timestamp.valueOf(store.getInsertedTime()));
//		storeDataList.add(Timestamp.valueOf(store.getUpdatedTime()));
//
//		if(businessHoursList != null) {
//			for(int i = 0; i < businessHoursList.size(); i++) {
//				List<Object> businessHoursDataList = new ArrayList<>();
//				businessHoursDataList.add(storeId);
//				businessHoursDataList.add(businessHoursList.get(i).getBusinessDay());
//				businessHoursDataList.add(Time.valueOf(businessHoursList.get(i).getStartBusinessTime()));
//				businessHoursDataList.add(Time.valueOf(businessHoursList.get(i).getEndBusinessTime()));
//				businessHoursDataList.add(businessHoursList.get(i).getComplement());
//				businessHoursDataList.add(businessHoursList.get(i).getIsDeleted());
//				businessHoursDataList.add(Timestamp.valueOf(businessHoursList.get(i).getInsertedTime()));
//				businessHoursDataList.add(Timestamp.valueOf(businessHoursList.get(i).getUpdatedTime()));
//				businessHoursDataMap.put(i, businessHoursDataList);
//			}
//		}

		List<RegistData> userDataList = new ArrayList<>();
		List<RegistData> storeDataList = new ArrayList<>();
		Map<Integer, List<RegistData>> businessHoursDataMap = new HashMap<>();

		userDataList.add(new RegistData(user.getUserId(), Types.INTEGER));
		userDataList.add(new RegistData(user.getUserName(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getPassword(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getSex(), Types.VARCHAR));
		userDataList.add(new RegistData(new Date(user.getBirthday().getTime()), Types.DATE));
		userDataList.add(new RegistData(user.getPostalCode(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getStreetAddress(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getEmailAddress(), Types.VARCHAR));
		userDataList.add(new RegistData(user.getTelephoneNumber(), Types.VARCHAR));
		userDataList.add(new RegistData(Integer.valueOf(DEFAULT_ID), Types.INTEGER));
		userDataList.add(new RegistData(store.getStoreId(), Types.INTEGER));
		userDataList.add(new RegistData(user.getIsDeleted(), Types.INTEGER));
		userDataList.add(new RegistData(Timestamp.valueOf(user.getInsertedTime()), Types.TIMESTAMP));
		userDataList.add(new RegistData(Timestamp.valueOf(user.getUpdatedTime()), Types.TIMESTAMP));

		int storeId = store.getStoreId();
		storeDataList.add(new RegistData(storeId, Types.INTEGER));
		storeDataList.add(new RegistData(store.getImage(), Types.BLOB));
		storeDataList.add(new RegistData(store.getStoreName(), Types.VARCHAR));
		storeDataList.add(new RegistData(store.getEmployeesNumber(), Types.INTEGER));
		storeDataList.add(new RegistData(store.getCourseInfo(), Types.VARCHAR));
		storeDataList.add(new RegistData(store.getCommitment(), Types.VARCHAR));
		storeDataList.add(new RegistData(store.getIsDeleted(), Types.INTEGER));
		storeDataList.add(new RegistData(Timestamp.valueOf(store.getInsertedTime()), Types.TIMESTAMP));
		storeDataList.add(new RegistData(Timestamp.valueOf(store.getUpdatedTime()), Types.TIMESTAMP));

		if(businessHoursList != null) {
			for(int i = 0; i < businessHoursList.size(); i++) {
				List<RegistData> businessHoursDataList = new ArrayList<>();
				businessHoursDataList.add(new RegistData(storeId, Types.INTEGER));
				businessHoursDataList.add(new RegistData(businessHoursList.get(i).getBusinessDay(), Types.VARCHAR));
				businessHoursDataList.add(new RegistData(Time.valueOf(businessHoursList.get(i).getStartBusinessTime()), Types.TIME));
				businessHoursDataList.add(new RegistData(Time.valueOf(businessHoursList.get(i).getEndBusinessTime()), Types.TIME));
				businessHoursDataList.add(new RegistData(businessHoursList.get(i).getComplement(), Types.VARCHAR));
				businessHoursDataList.add(new RegistData(businessHoursList.get(i).getIsDeleted(), Types.INTEGER));
				businessHoursDataList.add(new RegistData(Timestamp.valueOf(businessHoursList.get(i).getInsertedTime()), Types.TIMESTAMP));
				businessHoursDataList.add(new RegistData(Timestamp.valueOf(businessHoursList.get(i).getUpdatedTime()), Types.TIMESTAMP));
				businessHoursDataMap.put(i, businessHoursDataList);
			}
		}

		this.con.setAutoCommit(false);

//		String sql = "INSERT INTO t_user VALUES(";
//		for(int i = 0; i < USER_COL_COUNT - 1; i++) {
//			sql += "?, ";
//		}
//		sql += "?)";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//
//		setSQLIntegerParameter(pstmt, 1, user.getUserId());
//		setSQLVarcharParameter(pstmt, 2, user.getUserName());
//		setSQLVarcharParameter(pstmt, 3, user.getPassword());
//		setSQLVarcharParameter(pstmt, 4, user.getSex());
//		setSQLDateParameter(pstmt, 5, new Date(user.getBirthday().getTime()));
//		setSQLVarcharParameter(pstmt, 6, user.getPostalCode());
//		setSQLVarcharParameter(pstmt, 7, user.getStreetAddress());
//		setSQLVarcharParameter(pstmt, 8, user.getEmailAddress());
//		setSQLVarcharParameter(pstmt, 9, user.getTelephoneNumber());
//		pstmt.setInt(10, DEFAULT_ID);
//		setSQLIntegerParameter(pstmt, 11, store.getStoreId());
//		pstmt.setInt(12, user.getIsDeleted());
//		pstmt.setTimestamp(13, Timestamp.valueOf(user.getInsertedTime()));
//		pstmt.setTimestamp(14, Timestamp.valueOf(user.getUpdatedTime()));

//		int userDataParamCount = userDataList.size();
//		String sql = "INSERT INTO t_user VALUES(";
//		for(int i = 1; i < userDataParamCount; i++) {
//			sql += "?, ";
//		}
//		sql += "?)";
//		PreparedStatement pstmt = con.prepareStatement(sql);
//
//		for (int i = 0; i < userDataParamCount; i++) {
//			setSqlParameter(pstmt, (i+1), userDataList.get(i));
//		}

		int userDataParamCount = userDataList.size();
		String sql = "INSERT INTO t_user VALUES(";
		for(int i = 1; i < userDataParamCount; i++) {
			sql += "?, ";
		}
		sql += "?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		for (int i = 0; i < userDataParamCount; i++) {
			setSqlParameter(pstmt, (i+1), userDataList.get(i).getRegistData(), userDataList.get(i).getRegistDataType());
		}

		pstmt.executeUpdate();

//		sql = "INSERT INTO t_store VALUES(";
//		for(int i = 0; i < STORE_COL_COUNT - 1; i++) {
//			sql += "?, ";
//		}
//		sql += "?)";
//		pstmt = con.prepareStatement(sql);
//
//		int storeId = store.getStoreId();
//		setSQLIntegerParameter(pstmt, 1, storeId);
//		setSQLBlobParameter(pstmt, 2, store.getImage());
//		setSQLVarcharParameter(pstmt, 3, store.getStoreName());
//		setSQLIntegerParameter(pstmt, 4, store.getEmployeesNumber());
//		setSQLVarcharParameter(pstmt, 5, store.getCourseInfo());
//		setSQLVarcharParameter(pstmt, 6, store.getCommitment());
//		pstmt.setInt(7, store.getIsDeleted());
//		pstmt.setTimestamp(8, Timestamp.valueOf(store.getInsertedTime()));
//		pstmt.setTimestamp(9, Timestamp.valueOf(store.getUpdatedTime()));

//		int storeDataParamCount = storeDataList.size();
//		sql = "INSERT INTO t_store VALUES(";
//		for(int i = 1; i < storeDataParamCount; i++) {
//			sql += "?, ";
//		}
//		sql += "?)";
//		pstmt = con.prepareStatement(sql);
//
//		for (int i = 0; i < storeDataParamCount; i++) {
//			setSqlParameter(pstmt, (i+1), storeDataList.get(i));
//		}

		int storeDataParamCount = storeDataList.size();
		sql = "INSERT INTO t_store VALUES(";
		for(int i = 1; i < storeDataParamCount; i++) {
			sql += "?, ";
		}
		sql += "?)";
		pstmt = con.prepareStatement(sql);

		for (int i = 0; i < storeDataParamCount; i++) {
			setSqlParameter(pstmt, (i+1), storeDataList.get(i).getRegistData(), storeDataList.get(i).getRegistDataType());
		}

		pstmt.executeUpdate();

//		if(businessHoursList != null) {
//			for(int i = 0; i < businessHoursList.size(); i++) {
//				sql = "INSERT INTO t_business_hours VALUES(";
//				for(int j = 0; j < BUSINESS_HOURS_COL_COUNT - 1; j++) {
//					sql += "?, ";
//				}
//				sql += "?)";
//				pstmt = con.prepareStatement(sql);
//
//				setSQLIntegerParameter(pstmt, 1, storeId);
//				setSQLVarcharParameter(pstmt, 2, businessHoursList.get(i).getBusinessDay());
//				setSQLTimeParameter(pstmt, 3, Time.valueOf(businessHoursList.get(i).getStartBusinessTime()));
//				setSQLTimeParameter(pstmt, 4, Time.valueOf(businessHoursList.get(i).getEndBusinessTime()));
//				setSQLVarcharParameter(pstmt, 5, businessHoursList.get(i).getComplement());
//				pstmt.setInt(6, businessHoursList.get(i).getIsDeleted());
//				pstmt.setTimestamp(7, Timestamp.valueOf(businessHoursList.get(i).getInsertedTime()));
//				pstmt.setTimestamp(8, Timestamp.valueOf(businessHoursList.get(i).getUpdatedTime()));
//
//				pstmt.executeUpdate();
//			}
//		}

//		for(int i = 0; i < businessHoursDataMap.size(); i++) {
//			List<Object> businessHoursDataList = businessHoursDataMap.get(i);
//			int businessHoursDataParamCount = businessHoursDataList.size();
//
//			sql = "INSERT INTO t_business_hours VALUES(";
//			for(int j = 1; j < businessHoursDataParamCount; j++) {
//				sql += "?, ";
//			}
//			sql += "?)";
//			pstmt = con.prepareStatement(sql);
//
//
//			for (int k = 0; k < businessHoursDataParamCount; k++) {
//				setSqlParameter(pstmt, (i+1), businessHoursDataList.get(k));
//			}
//
//			pstmt.executeUpdate();
//		}

		for(int i = 0; i < businessHoursDataMap.size(); i++) {
			List<RegistData> businessHoursDataList = businessHoursDataMap.get(i);
			int businessHoursDataParamCount = businessHoursDataList.size();

			sql = "INSERT INTO t_business_hours VALUES(";
			for(int j = 1; j < businessHoursDataParamCount; j++) {
				sql += "?, ";
			}
			sql += "?)";
			pstmt = con.prepareStatement(sql);


			for (int k = 0; k < businessHoursDataParamCount; k++) {
				setSqlParameter(pstmt, (k+1), businessHoursDataList.get(k).getRegistData(), businessHoursDataList.get(k).getRegistDataType());
			}

			pstmt.executeUpdate();
		}

		this.con.commit();
		pstmt.close();

		return true;
	}

	/**
	 * SQLパラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 * @return SQLパラメータに成功した場合、true 失敗した場合、false
	 */
//	private void setSqlParameter(PreparedStatement pstmt, int paramIndex, Object value) throws SQLException {
//		String valStr = String.valueOf(value);
//		if(value instanceof Integer) {
//			setSQLIntegerParameter(pstmt, paramIndex, Integer.valueOf(valStr));
//		} else if(value instanceof Float) {
//			setSQLFloatParameter(pstmt, paramIndex, Float.valueOf(valStr));
//		} else if(value instanceof String) {
//			setSQLVarcharParameter(pstmt, paramIndex, valStr);
//		} else if(value instanceof Date) {
//			setSQLDateParameter(pstmt, paramIndex, Date.valueOf(valStr));
//		} else if(value instanceof Time) {
//			setSQLTimeParameter(pstmt, paramIndex, Time.valueOf(valStr));
//		} else if(value instanceof Timestamp) {
//			pstmt.setTimestamp(paramIndex, Timestamp.valueOf(valStr));
//		}
//	}

	/**
	 * SQLパラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 * @return SQLパラメータに成功した場合、true 失敗した場合、false
	 */
	private void setSqlParameter(PreparedStatement pstmt, int paramIndex, Object value, int type) throws SQLException {
		String valStr = String.valueOf(value);
		if(type == Types.INTEGER) {
			if(value == null) {
				pstmt.setNull(paramIndex, Types.INTEGER);
			}else {
				pstmt.setInt(paramIndex, Integer.valueOf(valStr));
			}
		} else if(type == Types.FLOAT) {
			if(value == null) {
				pstmt.setNull(paramIndex, Types.FLOAT);
			}else {
				pstmt.setFloat(paramIndex, Float.valueOf(valStr));
			}
		} else if(type == Types.VARCHAR) {
			if(value == null) {
				pstmt.setNull(paramIndex, Types.VARCHAR);
			}else {
				pstmt.setString(paramIndex, valStr);
			}
		} else if(type == Types.DATE) {
			if(value == null) {
				pstmt.setNull(paramIndex, Types.DATE);
			}else {
				pstmt.setDate(paramIndex, Date.valueOf(valStr));
			}
		} else if(type == Types.TIME) {
			if(value == null) {
				pstmt.setNull(paramIndex, Types.TIME);
			}else {
				pstmt.setTime(paramIndex, Time.valueOf(valStr));
			}
		} else if(type == Types.BLOB) {
			if(value == null) {
				pstmt.setNull(paramIndex, Types.BLOB);
			}else {
				pstmt.setBinaryStream(paramIndex, new ByteArrayInputStream(valStr.getBytes()));
			}
		} else if(type == Types.TIMESTAMP) {
			pstmt.setTimestamp(paramIndex, Timestamp.valueOf(valStr));
		}
	}

	/**
	 * SQL数値パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
//	private void setSQLIntegerParameter(PreparedStatement pstmt, int paramIndex, Integer value) throws SQLException {
//		if(value == null) {
//			pstmt.setNull(paramIndex, Types.INTEGER);
//		}else {
//			pstmt.setInt(paramIndex, value);
//		}
//	}

	/**
	 * SQL文字列パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
//	private void setSQLVarcharParameter(PreparedStatement pstmt, int paramIndex, String value) throws SQLException {
//		if(value == null || value.length() == 0) {
//			pstmt.setNull(paramIndex, Types.VARCHAR);
//		}else {
//			pstmt.setString(paramIndex, value);
//		}
//	}

	/**
	 * SQL日付パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
//	private void setSQLDateParameter(PreparedStatement pstmt, int paramIndex, Date value) throws SQLException {
//		if(value == null) {
//			pstmt.setNull(paramIndex, Types.DATE);
//		}else {
//			pstmt.setDate(paramIndex, value);
//		}
//	}

	/**
	 * SQL小数パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
//	private void setSQLFloatParameter(PreparedStatement pstmt, int paramIndex, Float value) throws SQLException {
//		if(value == null) {
//			pstmt.setNull(paramIndex, Types.FLOAT);
//		}else {
//			pstmt.setFloat(paramIndex, value);
//		}
//	}

	/**
	 * SQLバイナリーオブジェクトパラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
//	private void setSQLBlobParameter(PreparedStatement pstmt, int paramIndex, byte[] value) throws SQLException {
//		if(value == null) {
//			pstmt.setNull(paramIndex, Types.BLOB);
//		}else {
//			pstmt.setBinaryStream(paramIndex, new ByteArrayInputStream(value));
//		}
//	}

	/**
	 * SQL時間パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
//	private void setSQLTimeParameter(PreparedStatement pstmt, int paramIndex, Time value) throws SQLException {
//		if(value == null) {
//			pstmt.setNull(paramIndex, Types.TIME);
//		}else {
//			pstmt.setTime(paramIndex, value);
//		}
//	}
}
