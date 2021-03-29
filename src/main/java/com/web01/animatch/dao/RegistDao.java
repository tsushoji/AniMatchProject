package com.web01.animatch.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	 * IDデフォルト値
	 */
	private static final int DEFAULT_ID = 1000000000;

	/**
	 * ユーザテーブル名
	 * ★定数を使っていない箇所があると思います
	 */
	public static final String USER_TABLE_NAME = "t_user";

	/**
	 * ペットテーブル名
	 * ★定数を使っていない箇所があると思います
	 */
	public static final String PET_TABLE_NAME = "t_pet";

	/**
	 * 店舗テーブル名
	 * ★定数を使っていない箇所があると思います
	 */
	public static final String STORE_TABLE_NAME = "t_store";

	/**
	 * 引数付きコンストラクタ
	 *
	 * @param con DBコネクションオブジェクト
	 */
	public RegistDao(Connection con) {
		this.con = con;
		try {
			this.con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 最大ID取得
	 * @param con DBコネクションオブジェクト
	 * @return 最大ID
	 */
	public int getMaxId(String tableName) throws SQLException {
		int id = 0;
		/*★ColumnにはAUTO_INCREMENT属性は付けない感じなのでしょうか。
		 * 登録時にIDの最大値を取得しなくてもよいようにAUTO_INCREMENTを使用をすすめたつもりでした。 */
		String sql = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, tableName);

			// ★違和感ありますがResultSetはCloseしなくてもよいらしいです。
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				id = rs.getInt("AUTO_INCREMENT");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	/**
	 * 飼い主DB登録
	 * @param user ユーザオブジェクト
	 * @return DB登録成功失敗
	 */
	public boolean registOwner(User user) throws SQLException {
		Pet pet = user.getPet();

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

		int userDataParamCount = userDataList.size();
		String sql = "INSERT INTO t_user VALUES(";
		for(int i = 1; i < userDataParamCount; i++) {
			sql += "?, ";
		}
		sql += "?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		try {
			for (int i = 0; i < userDataParamCount; i++) {
				setSqlParameter(pstmt, (i+1), userDataList.get(i).getRegistData(), userDataList.get(i).getRegistDataType());
			}

			pstmt.executeUpdate();

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
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			pstmt.close();
		}

		return true;
	}

	public boolean registOwner2(User user) throws SQLException {
		Pet pet = user.getPet();

		List<HashMap<String, Object>> list = new ArrayList<>();
		list.add(createSqlParatemerMap(user.getUserId(), Types.INTEGER));
		list.add(createSqlParatemerMap(user.getUserName(), Types.VARCHAR));
		list.add(createSqlParatemerMap(user.getPassword(), Types.VARCHAR));
		list.add(createSqlParatemerMap(user.getSex(), Types.VARCHAR));
		list.add(createSqlParatemerMap(new Date(user.getBirthday().getTime()), Types.DATE));
		list.add(createSqlParatemerMap(user.getPostalCode(), Types.VARCHAR));
		list.add(createSqlParatemerMap(user.getStreetAddress(), Types.VARCHAR));
		list.add(createSqlParatemerMap(user.getEmailAddress(), Types.VARCHAR));
		list.add(createSqlParatemerMap(user.getTelephoneNumber(), Types.VARCHAR));
		list.add(createSqlParatemerMap(pet.getPetId(), Types.INTEGER));
		list.add(createSqlParatemerMap(Integer.valueOf(DEFAULT_ID), Types.INTEGER));
		list.add(createSqlParatemerMap(user.getIsDeleted(), Types.INTEGER));
		list.add(createSqlParatemerMap(Timestamp.valueOf(user.getInsertedTime()), Types.TIMESTAMP));
		list.add(createSqlParatemerMap(Timestamp.valueOf(user.getUpdatedTime()), Types.TIMESTAMP));

		List<HashMap<String, Object>> list2 = new ArrayList<>();
		list2.add(createSqlParatemerMap(pet.getPetId(), Types.INTEGER));
		list2.add(createSqlParatemerMap(pet.getImage(), Types.BLOB));
		list2.add(createSqlParatemerMap(pet.getNickName(), Types.VARCHAR));
		list2.add(createSqlParatemerMap(pet.getSex(), Types.VARCHAR));
		list2.add(createSqlParatemerMap(pet.getType(), Types.VARCHAR));
		list2.add(createSqlParatemerMap(pet.getWeight(), Types.FLOAT));
		list2.add(createSqlParatemerMap(pet.getRemarks(), Types.VARCHAR));
		list2.add(createSqlParatemerMap(pet.getIsDeleted(), Types.INTEGER));
		list2.add(createSqlParatemerMap(Timestamp.valueOf(pet.getInsertedTime()), Types.TIMESTAMP));
		list2.add(createSqlParatemerMap(Timestamp.valueOf(pet.getUpdatedTime()), Types.TIMESTAMP));

		try (PreparedStatement pstmt = createInsetStatement("t_user", list);){
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw e;
		}

		try (PreparedStatement pstmt = createInsetStatement("t_pet", list2);){
			pstmt.executeUpdate();

			this.con.commit();
		} catch(SQLException e) {
			throw e;
		}

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

		int userDataParamCount = userDataList.size();
		String sql = "INSERT INTO t_user VALUES(";
		for(int i = 1; i < userDataParamCount; i++) {
			sql += "?, ";
		}
		sql += "?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		try {
			for (int i = 0; i < userDataParamCount; i++) {
				setSqlParameter(pstmt, (i+1), userDataList.get(i).getRegistData(), userDataList.get(i).getRegistDataType());
			}

			pstmt.executeUpdate();

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
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			pstmt.close();
		}

		return true;
	}

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
	 * SQLパラメータ設定
	 * @param pstmt プリコンパイルされたSQL文オブジェクト
	 * @param paramIndex 引数インデックス
	 * @param value 値
	 */
	private void setSqlParameter2(PreparedStatement pstmt, int paramIndex, Object value, int dataType) throws SQLException {

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

	private HashMap<String, Object> createSqlParatemerMap(Object value, Integer dataType) {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("value", value);
		ret.put("dataType", dataType);
		return ret;
	}

	private PreparedStatement createInsetStatement(String tableName, List<HashMap<String, Object>> list) throws SQLException {
		String sql = "INSERT INTO " + tableName + " VALUES";

		ArrayList<String> params = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			params.add("?");
		}
		sql += "(" + String.join(",", params) + ")";

		PreparedStatement pstmt = this.con.prepareStatement(sql);

		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> data = list.get(i);
			setSqlParameter2(pstmt, (i+1), data.get("value"), (Integer)data.get("dataType"));
		}

		return pstmt;
	}
}
