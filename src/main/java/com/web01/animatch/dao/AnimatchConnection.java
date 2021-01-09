package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AnimatchConnection {
	private Connection con = null;
	private static final String DB_CONNECTION_KEY_INIT_STR = "db.connection.";
	private static final String DB_CONNECTION_KEY_URL_STR = "url";
	private static final String DB_CONNECTION_KEY_USER_STR = "user";
	private static final String DB_CONNECTION_KEY_PASS_STR = "pass";
	private static final String PROPERTIES_NAME = "animatch";

	public Connection getConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			ResourceBundle resBundle = ResourceBundle.getBundle(PROPERTIES_NAME);
			String url = resBundle.getString(DB_CONNECTION_KEY_INIT_STR + DB_CONNECTION_KEY_URL_STR);
			String user = resBundle.getString(DB_CONNECTION_KEY_INIT_STR + DB_CONNECTION_KEY_USER_STR);
			String pass = resBundle.getString(DB_CONNECTION_KEY_INIT_STR + DB_CONNECTION_KEY_PASS_STR);
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	public void close() {
		try {
			if( con != null ) {
				con.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
