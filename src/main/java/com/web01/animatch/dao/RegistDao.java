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
import java.util.List;

import com.web01.animatch.dto.BusinessHours;
import com.web01.animatch.dto.Pet;
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
	private static final int USER_COL_COUNT = 14;
	/**
	 * ペットテーブルカラム数
	 */
	private static final int PET_COL_COUNT = 10;
	/**
	 * 店舗テーブルカラム数
	 */
	private static final int STORE_COL_COUNT = 9;
	/**
	 * 営業時間テーブルカラム数
	 */
	private static final int BUSINESS_HOURS_COL_COUNT = 8;
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
		this.con.setAutoCommit(false);
		String sql = "INSERT INTO t_user VALUES(";
		for(int i = 0; i < USER_COL_COUNT - 1; i++) {
			sql += "?, ";
		}
		sql += "?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		setSQLIntegerParameter(pstmt, 1, user.getUserId());
		setSQLVarcharParameter(pstmt, 2, user.getUserName());
		setSQLVarcharParameter(pstmt, 3, user.getPassword());
		setSQLVarcharParameter(pstmt, 4, user.getSex());
		setSQLDateParameter(pstmt, 5, new Date(user.getBirthday().getTime()));
		setSQLVarcharParameter(pstmt, 6, user.getPostalCode());
		setSQLVarcharParameter(pstmt, 7, user.getStreetAddress());
		setSQLVarcharParameter(pstmt, 8, user.getEmailAddress());
		setSQLVarcharParameter(pstmt, 9, user.getTelephoneNumber());
		setSQLIntegerParameter(pstmt, 10, pet.getPetId());
		pstmt.setInt(11, DEFAULT_ID);
		pstmt.setInt(12, user.getIsDeleted());
		pstmt.setTimestamp(13, Timestamp.valueOf(user.getInsertedTime()));
		pstmt.setTimestamp(14, Timestamp.valueOf(user.getUpdatedTime()));

		pstmt.executeUpdate();

		sql = "INSERT INTO t_pet VALUES(";
		for(int i = 0; i < PET_COL_COUNT - 1; i++) {
			sql += "?, ";
		}
		sql += "?)";
		pstmt = con.prepareStatement(sql);

		setSQLIntegerParameter(pstmt, 1, pet.getPetId());
		setSQLBlobParameter(pstmt, 2, pet.getImage());
		setSQLVarcharParameter(pstmt, 3, pet.getNickName());
		setSQLVarcharParameter(pstmt, 4, pet.getSex());
		setSQLVarcharParameter(pstmt, 5, pet.getType());
		setSQLFloatParameter(pstmt, 6, pet.getWeight());
		setSQLVarcharParameter(pstmt, 7, pet.getRemarks());
		pstmt.setInt(8, pet.getIsDeleted());
		pstmt.setTimestamp(9, Timestamp.valueOf(pet.getInsertedTime()));
		pstmt.setTimestamp(10, Timestamp.valueOf(pet.getUpdatedTime()));

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
		this.con.setAutoCommit(false);
		String sql = "INSERT INTO t_user VALUES(";
		for(int i = 0; i < USER_COL_COUNT - 1; i++) {
			sql += "?, ";
		}
		sql += "?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		setSQLIntegerParameter(pstmt, 1, user.getUserId());
		setSQLVarcharParameter(pstmt, 2, user.getUserName());
		setSQLVarcharParameter(pstmt, 3, user.getPassword());
		setSQLVarcharParameter(pstmt, 4, user.getSex());
		setSQLDateParameter(pstmt, 5, new Date(user.getBirthday().getTime()));
		setSQLVarcharParameter(pstmt, 6, user.getPostalCode());
		setSQLVarcharParameter(pstmt, 7, user.getStreetAddress());
		setSQLVarcharParameter(pstmt, 8, user.getEmailAddress());
		setSQLVarcharParameter(pstmt, 9, user.getTelephoneNumber());
		pstmt.setInt(10, DEFAULT_ID);
		setSQLIntegerParameter(pstmt, 11, store.getStoreId());
		pstmt.setInt(12, user.getIsDeleted());
		pstmt.setTimestamp(13, Timestamp.valueOf(user.getInsertedTime()));
		pstmt.setTimestamp(14, Timestamp.valueOf(user.getUpdatedTime()));

		pstmt.executeUpdate();

		sql = "INSERT INTO t_store VALUES(";
		for(int i = 0; i < STORE_COL_COUNT - 1; i++) {
			sql += "?, ";
		}
		sql += "?)";
		pstmt = con.prepareStatement(sql);

		int storeId = store.getStoreId();
		setSQLIntegerParameter(pstmt, 1, storeId);
		setSQLBlobParameter(pstmt, 2, store.getImage());
		setSQLVarcharParameter(pstmt, 3, store.getStoreName());
		setSQLIntegerParameter(pstmt, 4, store.getEmployeesNumber());
		setSQLVarcharParameter(pstmt, 5, store.getCourseInfo());
		setSQLVarcharParameter(pstmt, 6, store.getCommitment());
		pstmt.setInt(7, store.getIsDeleted());
		pstmt.setTimestamp(8, Timestamp.valueOf(store.getInsertedTime()));
		pstmt.setTimestamp(9, Timestamp.valueOf(store.getUpdatedTime()));

		pstmt.executeUpdate();

		if(businessHoursList != null) {
			for(int i = 0; i < businessHoursList.size(); i++) {
				sql = "INSERT INTO t_business_hours VALUES(";
				for(int j = 0; j < BUSINESS_HOURS_COL_COUNT - 1; j++) {
					sql += "?, ";
				}
				sql += "?)";
				pstmt = con.prepareStatement(sql);
				setSQLIntegerParameter(pstmt, 1, storeId);
				setSQLVarcharParameter(pstmt, 2, businessHoursList.get(i).getBusinessDay());
				setSQLTimeParameter(pstmt, 3, Time.valueOf(businessHoursList.get(i).getStartBusinessTime()));
				setSQLTimeParameter(pstmt, 4, Time.valueOf(businessHoursList.get(i).getEndBusinessTime()));
				setSQLVarcharParameter(pstmt, 5, businessHoursList.get(i).getComplement());
				pstmt.setInt(6, businessHoursList.get(i).getIsDeleted());
				pstmt.setTimestamp(7, Timestamp.valueOf(businessHoursList.get(i).getInsertedTime()));
				pstmt.setTimestamp(8, Timestamp.valueOf(businessHoursList.get(i).getUpdatedTime()));

				pstmt.executeUpdate();
			}
		}

		this.con.commit();
		pstmt.close();

		return true;
	}

	/**
	 * SQL数値パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
	private void setSQLIntegerParameter(PreparedStatement pstmt, int paramIndex, Integer value) throws SQLException {
		if(value == null) {
			pstmt.setNull(paramIndex, Types.INTEGER);
		}else {
			pstmt.setInt(paramIndex, value);
		}
	}

	/**
	 * SQL文字列パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
	private void setSQLVarcharParameter(PreparedStatement pstmt, int paramIndex, String value) throws SQLException {
		if(value == null || value.length() == 0) {
			pstmt.setNull(paramIndex, Types.VARCHAR);
		}else {
			pstmt.setString(paramIndex, value);
		}
	}

	/**
	 * SQL日付パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
	private void setSQLDateParameter(PreparedStatement pstmt, int paramIndex, Date value) throws SQLException {
		if(value == null) {
			pstmt.setNull(paramIndex, Types.DATE);
		}else {
			pstmt.setDate(paramIndex, value);
		}
	}

	/**
	 * SQL小数パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
	private void setSQLFloatParameter(PreparedStatement pstmt, int paramIndex, Float value) throws SQLException {
		if(value == null) {
			pstmt.setNull(paramIndex, Types.FLOAT);
		}else {
			pstmt.setFloat(paramIndex, value);
		}
	}

	/**
	 * SQLバイナリーオブジェクトパラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
	private void setSQLBlobParameter(PreparedStatement pstmt, int paramIndex, byte[] value) throws SQLException {
		if(value == null) {
			pstmt.setNull(paramIndex, Types.BLOB);
		}else {
			pstmt.setBinaryStream(paramIndex, new ByteArrayInputStream(value));
		}
	}

	/**
	 * SQL時間パラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
	private void setSQLTimeParameter(PreparedStatement pstmt, int paramIndex, Time value) throws SQLException {
		if(value == null) {
			pstmt.setNull(paramIndex, Types.TIME);
		}else {
			pstmt.setTime(paramIndex, value);
		}
	}
}
