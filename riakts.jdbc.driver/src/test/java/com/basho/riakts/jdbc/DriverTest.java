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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DriverTest {

	Driver d = null;
	Connection conn = null;
	
	@Before
	public void setUp() throws Exception {
		d = new Driver();
		conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
	}

	@After
	public void tearDown() throws Exception {
		conn.close();
		d = null;
	}
	
	
	@Test
	/***
	 * Test creates the jdbcDriverTest table in the Riak TS cluster with one of two
	 * possible valid outcomes:
	 *    1. The table is created successfully and executeUpdate() returns 0
	 *    2. The table already exists and executeUpdate() throws an error that says:
	 *       "Failed to create table jdbcDriverTest: already_active"
	 */
	public void testSqlCreateTableSuccess() {
		try {
			String sqlStatement = "CREATE TABLE jdbcDriverTest " + 
	    		"( " +
	    			"name 			varchar   	not null, " +
	    			"age			sint64   	not null, " +
	    			"joined        	timestamp 	not null, " +
	    			"weight		 	double		not null, " +
	    			"PRIMARY KEY ( " +
	    			"(quantum(joined, 5, 'd')), " +
	    			"	joined, name, age " +
	    			") " +
	    		") WITH (n_val = 1)";
			//System.out.println(sqlStatement);
			
			Statement statement = conn.createStatement();
	    	int result = statement.executeUpdate(sqlStatement);
	    	// Create Table returns 0 on success
	    	Assert.assertTrue(result == 0);
	    }
	    catch (Exception e) {
	    	String error = e.getMessage();
	    	Assert.assertTrue(error.contains("already_active"));
	    }
	}
	
	
	@Test
	/***
	 * This test passes a bad table create DDL to Riak TS to verify that TS
	 * throws an error
	 */
	public void testSqlCreateTableFailure() {
		try {
			String sqlStatement = "CREATE TABLE dontCreateMe " + 
	    		"( " +
	    			"name 			varchar   	not null, " +
	    			"age			sint64   	not null, " +
	    			"joined        	timestamp 	not null, " +
	    			"weight		 	double		not null, " +
	    			"PRIMARY KEY ( " +
	    			"(quantum(joined, 5, 'd')), " +
	    			"	badColumn, name, age " +
	    			") " +
	    		")  WITH (n_val = 1)";
			
			Statement statement = conn.createStatement();
	    	int result = statement.executeUpdate(sqlStatement);
	    	Assert.assertFalse(result > -1);
	    }
	    catch (Exception e) {
	    	Assert.assertTrue( e != null );
	    }
	}	
	
	
	@Test
	/***
	 * Tests to see if the jdbcDriverTest table exists using the DESCRIBE command
	 * also verifies that the right number of rows is returned in the ResultSet
	 * to match the number of columns in the table (5)
	 * @throws SQLException
	 */
	public void testSqlDescribeTable() throws SQLException {
		String sqlStatement = "DESCRIBE jdbcDriverTest;";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		
		Assert.assertTrue(rs != null);
		
		int columnCount = 0;
		while (rs.next()) {
			columnCount++;
		}
		Assert.assertTrue(columnCount == 4);
		
		rs.close();
	}
	
	
	@Test
	public void testSqlInsertData() throws SQLException, ParseException {
		// Create timestamp string for our record
		String timeStamp = "06/06/2016 12:30:00.00";
		
		String sqlStatement = "INSERT INTO jdbcDriverTest " +
				"(name, age, joined, weight) " +
				"VALUES " +
				"('Craig', 92, " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(timeStamp) + ", 202.5);";
		
		Statement statement = conn.createStatement();
    	int result = statement.executeUpdate(sqlStatement);
    	// Insert returns 0 on success
    	Assert.assertTrue(result == 0);
	}
	
	
	private static String[][] PEOPLE = {
			{"Lucy", "22", "06/06/2016 10:30:00.00", "104.0"},
			{"Tom", "35", "06/06/2016 11:30:00.00", "180.5"},
			{"Sarah", "15", "06/06/2016 13:30:00.00", "100.1"},
			{"Mark", "42", "06/06/2016 14:30:00.00", "160.8"},
			{"Anna", "27", "06/06/2016 15:30:00.00", "110.5"},
			{"John", "17", "06/06/2016 16:30:00.00", "170.5"},
			{"Sophia", "12", "06/06/2016 17:30:00.00", "115.9"},
			{"Bob", "32", "06/06/2016 18:30:00.00", "220.1"}
	};
	
	@Test
	public void testSqlInsertMultipleRows() throws SQLException, ParseException {
		for (String[] person : PEOPLE) {
			String sqlStatement = "INSERT INTO jdbcDriverTest " +
					"(name, age, joined, weight) " +
					"VALUES " +
					"('" + person[0] + "', " + person[1] + ", " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(person[2]) + 
					", " + person[3] + ");";
			//System.out.println(sqlStatement);
			Statement statement = conn.createStatement();
	    	int result = statement.executeUpdate(sqlStatement);
	    	// Insert returns 0 on success
	    	Assert.assertTrue(result == 0);			
		}
	}
	
	
	@Test
	public void testSqlSelect() throws SQLException, ParseException {
		// Start and end date to search on
		String startDateStr = "06/01/2016 0:00:00.00";
		String endDateStr = "06/11/2016 23:59:59.59";
		
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				Utility.dateStringMMddyyyyHHmmssSSToEpoch(startDateStr) +
				" AND joined <= " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(endDateStr) + ";";
		//System.out.println(sqlStatement);
		
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		Assert.assertTrue(rs != null);
		
		// Print out ResultSet for demonstration purposes only, commented out for normal test runs 
		if (rs != null) {
			while (rs.next()) {
				System.out.println( rs.getString("name") + " | " + rs.getLong("age") + 
						" | " + rs.getTimestamp("joined")  + " | " + rs.getDouble("weight"));
			}
		}
		rs.close();
	}
	
	
	@Test
	public void testPreparedStatement() throws ParseException, SQLException {
		// Start and end date to search on
		String startDateStr = "06/01/2016 0:00:00.00";
		String endDateStr = "06/11/2016 23:59:59.59";
		
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				Utility.dateStringMMddyyyyHHmmssSSToEpoch(startDateStr) +
				" AND joined <= " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(endDateStr) + ";";
		
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sqlStatement);
		ResultSet rs = statement.executeQuery();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		String columnName1 = rsmd.getColumnName(1);
		
		
		Assert.assertTrue(rs != null);
		
		// Print out ResultSet for demonstration purposes only, commented out for normal test runs 
//		if (rs != null) {
//			while (rs.next()) {
//				System.out.println( rs.getString("name") + " | " + rs.getLong("age") + 
//						" | " + rs.getTimestamp("joined")  + " | " + rs.getDouble("weight"));
//			}
//		}
		rs.close();		
	}
	
	
	@Test
	/***
	 * Tests methods that moves the ResultSet cursor
	 * The tests are based on a ResultSet that returns 9 records
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void testRowPosition() throws ParseException, SQLException {
		// Start and end date to search on
		String startDateStr = "06/06/2016 0:00:00.00";
		String endDateStr = "06/06/2016 23:59:59.59";

		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				Utility.dateStringMMddyyyyHHmmssSSToEpoch(startDateStr) +
				" AND joined <= " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(endDateStr) + ";";
		//System.out.println(sqlStatement);

		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		Assert.assertTrue(rs != null);
		
		// Move to first row in ResultSet
		rs.first();
		Assert.assertTrue(rs.getRow() == 0);
		Assert.assertTrue(rs.isFirst());
		
		// Move to the next row in the ResultSet
		rs.next();
		Assert.assertTrue(rs.getRow() == 1);
		
		// Move to last row in ResultSet 
		rs.last();
		Assert.assertTrue(rs.getRow() == 8);
		Assert.assertTrue(rs.isLast());
		
		// Move to the previous row read
		rs.previous();
		Assert.assertTrue(rs.getRow() == 7);
		
		// Move to row number 4 (which is actually row[3]
		rs.absolute(4);
		Assert.assertTrue(rs.getRow() == 3);
		
		// Move 2 rows forward in the ResultSet
		rs.relative(2);
		Assert.assertTrue(rs.getRow() == 5);
		
		// Move to before the first row of the ResultSet
		rs.beforeFirst();
		Assert.assertTrue(rs.getRow() == -1);
		Assert.assertTrue(rs.isBeforeFirst());
		
		// Move to after the last row of the ResultSet
		rs.afterLast();
		Assert.assertTrue(rs.isAfterLast());
	}
	
	
	@Test 
	public void testConnection() throws SQLException {
		Assert.assertFalse( conn.isClosed() );
	}

	@Test
	public void testAcceptsURL() throws SQLException {
		Assert.assertTrue( d.acceptsURL("riakts://127.0.0.1:8087") );
	}
	
	@Test
	public void testAcceptsProperties() throws SQLException {
		Properties info = new Properties();
		info.setProperty("RiakUrl", "127.0.0.1");
		info.setProperty("RiakPort", "8087");
		Assert.assertTrue( d.acceptsProperties(info) );
	}

}
