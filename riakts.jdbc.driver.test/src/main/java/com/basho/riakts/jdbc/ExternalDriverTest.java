package com.basho.riakts.jdbc;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author cvitter
 *
 */
public class ExternalDriverTest {
	static final String JDBC_DRIVER = "com.basho.riakts.jdbc.Driver";  
	static final String DB_URL = "riakts://127.0.0.1:8087";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(DB_URL);
			
			String sql = "SELECT * FROM jdbcDriverTest WHERE joined >= 1465185600000 AND joined <= 1465271999059;";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs != null) {
				while (rs.next()) {
					System.out.println( rs.getString("name") + " | " + rs.getLong("age") + 
						" | " + rs.getTimestamp("joined")  + " | " + rs.getDouble("weight"));
				}
			}
		}
		catch (Exception e) {
			System.out.print(e);
		}

	}

}
