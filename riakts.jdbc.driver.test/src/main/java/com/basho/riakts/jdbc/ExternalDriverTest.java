/** 
 * Copyright (C) 2016 Craig Vitter - https://github.com/cvitter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		java.sql.Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL);
			
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
