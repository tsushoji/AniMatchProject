package com.web01.animatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AnimatchConnection {
	private Connection con = null;
	private static final String URL = "jdbc:mysql://localhost:3306/animatch?useUnicode=true&characterEncoding=utf8";
	private static final String USER = "root";
	private static final String PASS = "";

	public Connection getConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);
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
