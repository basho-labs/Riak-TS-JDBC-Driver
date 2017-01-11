/** 
 * Copyright (C) 2016 Basho Technologies Inc
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

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DriverTest {

	private static Driver _driver = null;
	private static Connection _conn = null;
	
	@Before
	public void setUp() throws Exception {
		_driver = new Driver();
		_conn = (Connection) _driver.connect("riakts://127.0.0.1:8087", null);
	}

	@After
	public void tearDown() throws Exception {
		_conn.close();
		_driver = null;
	}
	
	
	@Test
	/***
	 * Test creates the jdbcDriverTest table in the Riak TS cluster with one of two
	 * possible valid outcomes:
	 *    1. The table is created successfully and executeUpdate() returns 0
	 *    2. The table already exists and executeUpdate() throws an error that says:
	 *       "Failed to create table jdbcDriverTestTable: already_active"
	 * Version 0.8: Added blob column introduced in Riak TS 1.5
	 */
	public void testSqlCreateTableSuccess() {
		try {
			String sqlStatement = "CREATE TABLE jdbcDriverTest " + 
	    		"( " +
	    			"name 			varchar   	not null, " +
	    			"age			sint64   	not null, " +
	    			"joined        	timestamp 	not null, " +
	    			"weight		 	double		not null, " +
	    			"active		 	boolean		not null, " +
	    			"blobText	 	blob, " +
	    			"PRIMARY KEY ( " +
	    			"(quantum(joined, 5, 'd')), " +
	    			"	joined, name, age " +
	    			") " +
	    		") WITH (n_val = 1)";
			//System.out.println(sqlStatement);
			
			Statement statement = _conn.createStatement();
	    	int result = statement.executeUpdate(sqlStatement);
	    	// Create Table returns 0 on success
	    	assertTrue(result == 0);
	    }
	    catch (Exception e) {
	    	String error = e.getMessage();
	    	assertTrue(error.contains("already_active"));
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
	    			"active		 	boolean		not null, " +
	    			"PRIMARY KEY ( " +
	    			"(quantum(joined, 5, 'd')), " +
	    			"	badColumn, name, age " +
	    			") " +
	    		")  WITH (n_val = 1)";
			
			Statement statement = _conn.createStatement();
	    	int result = statement.executeUpdate(sqlStatement);
	    	assertFalse(result > -1);
	    }
	    catch (Exception e) {
	    	assertTrue( e != null );
	    }
	}	
	
	@Test
	/***
	 * Tests the close method of Statement
	 * @throws SQLException
	 */
	public void testStatementClose() throws SQLException {
		Statement statement = _conn.createStatement();
		assertFalse( statement.isClosed() );
		statement.close();
		assertTrue( statement.isClosed() );
	}
	
	@Test
	/***
	 * Tests the close method of PreparedStatement
	 * @throws SQLException
	 */
	public void testPreparedStatementClose() throws SQLException {
		PreparedStatement statement = (PreparedStatement) _conn.prepareStatement("");
		assertFalse( statement.isClosed() );
		statement.close();
		assertTrue( statement.isClosed() );
	}
	
	
	@Test
	/***
	 * Tests to see if the jdbcDriverTest table exists using the DESCRIBE command
	 * also verifies that the right number of rows is returned in the ResultSet
	 * to match the number of columns in the table (6)
	 * @throws SQLException
	 * Version 0.8: Updated column count to reflect addition of blobText column in jdbcDriverTest
	 * @throws SQLException
	 */
	public void testSqlDescribeTable() throws SQLException {
		String sqlStatement = "DESCRIBE jdbcDriverTest;";
		Statement statement = _conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		
		assertTrue(rs != null);
		
		int columnCount = 0;
		while (rs.next()) {
			columnCount++;
		}
		assertTrue(columnCount == 6);
		
		rs.close();
	}
	
	
	@Test
	/****
	 * Test insertion of data via SQL INSERT INTO command, successful insert
	 * returns a 0 value
	 * Version 0.8: Added blobText column to insert, change to ISO 8061 time format
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSqlInsertData() throws SQLException, ParseException {
		String sqlStatement = "INSERT INTO jdbcDriverTest " +
				"(name, age, joined, weight, active, blobText) " +
				"VALUES " +
				"('Craig', 92, '2016-06-06 12:30:00', 202.5, true, null);";
		//System.out.println(sqlStatement);
		
		Statement statement = _conn.createStatement();
    	int result = statement.executeUpdate(sqlStatement);
    	// Insert returns 0 on success
    	assertTrue(result == 0);
	}
	
	
	private static String[][] PEOPLE = {
			{"Lucy", "22", "2016-06-06 10:30:00", "104.0", "true", "xxxxxxxxxxx"},
			{"Tom", "35", "2016-06-06 11:30:00", "180.5", "true", "xxxxxxxxxxx"},
			{"Sarah", "15", "2016-06-06 13:30:00", "100.1", "true", ""},
			{"Mark", "42", "2016-06-06 14:30:00", "160.8", "true", "xxxxxxxxxxx"},
			{"Anna", "27", "2016-06-06 15:30:00", "110.5", "true", "xxxxxxxxxxx"},
			{"John", "17", "2016-06-06 16:30:00", "170.5", "true", ""},
			{"Sophia", "12", "2016-06-06 17:30:00", "115.9", "true", "xxxxxxxxxxx"},
			{"Bob", "32", "2016-06-06 18:30:00", "220.1", "true", "xxxxxxxxxxx"},
			{"Julie", "44", "2016-06-06 22:30:00", "132.5", "true", "xxxxxxxxxxx"}
	};
	
	@Test
	/***
	 * Another insert test example, primarily used to add additional
	 * data to the test table as opposed to actually testing inserts
	 * Version 0.8: Added blobText column to insert, change to ISO 8061 time format
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSqlInsertMultipleRows() throws SQLException, ParseException {
		for (String[] person : PEOPLE) {
			String sqlStatement = "INSERT INTO jdbcDriverTest " +
					"(name, age, joined, weight, active, blobText) " +
					"VALUES " +
					"('" + person[0] + "', " + person[1] + ", '" + person[2] + "', " + 
					 person[3] + ", " + person[4] + ", '" + person[5] + "');";
			//System.out.println(sqlStatement);
			Statement statement = _conn.createStatement();
	    	int result = statement.executeUpdate(sqlStatement);
	    	// Insert returns 0 on success
	    	assertTrue(result == 0);			
		}
	}
	
	
	@Test
	/***
	 * Tests java.sql.Statement.executeQuery( sql )
	 * Version 0.8: Updated to use ISO 8061 time format, test retrieval of blobs
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSqlSelect() throws SQLException, ParseException {
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= '2016-06-05 10:00:00'" + 
				" AND joined <= '2016-06-08 10:30:00';";
		Statement statement = _conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		assertTrue(rs != null);
		
		// Following code is helpful for testing the library after refactoring to ensure
		// that we haven't broken the supported data types
//		if (rs != null) {
//			while (rs.next()) {
//				String name = rs.getString("name");
//				long age = rs.getLong("age");
//				Timestamp ts = rs.getTimestamp("joined");
//				double weight = rs.getDouble("weight");
//				boolean active = rs.getBoolean("active");
//				Blob blobText = rs.getBlob("blobText");
//
//				String bloblString = null;
//				try { bloblString = new String(blobText.getBytes(1l, (int) blobText.length())); } catch (Exception ex) { }
//				System.out.println( name + " | " + age +  " | " + ts  + " | " + weight+ " | " + bloblString);
//			}
//		}
		
		// Verify that we retrieved the right data types
		assertTrue(rs.getMetaData().getColumnTypeName(1).equalsIgnoreCase("java.sql.Types.VARCHAR"));
		assertTrue(rs.getMetaData().getColumnTypeName(2).equalsIgnoreCase("java.sql.Types.BIGINT"));
		assertTrue(rs.getMetaData().getColumnTypeName(3).equalsIgnoreCase("java.sql.Types.TIMESTAMP"));
		assertTrue(rs.getMetaData().getColumnTypeName(4).equalsIgnoreCase("java.sql.Types.DOUBLE"));
		assertTrue(rs.getMetaData().getColumnTypeName(5).equalsIgnoreCase("java.sql.Types.BOOLEAN"));
		assertTrue(rs.getMetaData().getColumnTypeName(6).equalsIgnoreCase("java.sql.Types.BLOB"));
		rs.close();
	}
	
	
	@Test
	/***
	 * Tests statement.execute( sql ) and statement.getResultSet()
	 * executes a query and returns the result set retrieved by the query
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSqlSelectWithExecute() throws SQLException, ParseException {
		// Start and end date to search on
		String startDateStr = "06/01/2016 0:00:00.00";
		String endDateStr = "06/11/2016 23:59:59.59";
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				Utility.dateStringMMddyyyyHHmmssSSToEpoch(startDateStr) +
				" AND joined <= " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(endDateStr) + ";";
		//System.out.println(sqlStatement);
		
		Statement statement = _conn.createStatement();
		boolean success = statement.execute(sqlStatement);
		assertTrue(success);
		ResultSet rs = statement.getResultSet();
		assertTrue(rs != null);
		rs.close();
	}
	
	
	@Test
	/***
	 * Tests using java.sql.PreparedStatement( sql) and .executeQuery()
	 * @throws SQLException
	 */
	public void testPreparedStatement() throws ParseException, SQLException {
		// Start and end date to search on
		String startDateStr = "06/01/2016 0:00:00.00";
		String endDateStr = "06/11/2016 23:59:59.59";
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				Utility.dateStringMMddyyyyHHmmssSSToEpoch(startDateStr) +
				" AND joined <= " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(endDateStr) + ";";
		
		PreparedStatement statement = (PreparedStatement) _conn.prepareStatement(sqlStatement);
		ResultSet rs = statement.executeQuery();
		assertTrue(rs != null);
		rs.close();		
	}
	
	@Test
	/***
	 * Test does not pass sql statement into prepareStatement() constructor to
	 * test executeQuery( sql ) works properly
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void testPreparedStatementExecuteQueryWithSql() throws ParseException, SQLException {
		// Start and end date to search on
		String startDateStr = "06/01/2016 0:00:00.00";
		String endDateStr = "06/11/2016 23:59:59.59";
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				Utility.dateStringMMddyyyyHHmmssSSToEpoch(startDateStr) +
				" AND joined <= " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(endDateStr) + ";";
		
		PreparedStatement statement = (PreparedStatement) _conn.prepareStatement("");
		ResultSet rs = statement.executeQuery(sqlStatement);
		assertTrue(rs != null);
		rs.close();		
	}
	
	@Test
	/***
	 * Tests statement.execute( ) and statement.getResultSet()
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void testPreparedStatementExecute() throws ParseException, SQLException {
		// Start and end date to search on
		String startDateStr = "06/01/2016 0:00:00.00";
		String endDateStr = "06/11/2016 23:59:59.59";
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				Utility.dateStringMMddyyyyHHmmssSSToEpoch(startDateStr) +
				" AND joined <= " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(endDateStr) + ";";
		
		PreparedStatement statement = (PreparedStatement) _conn.prepareStatement(sqlStatement);
		boolean success = statement.execute();
		assertTrue(success);
		ResultSet rs = statement.getResultSet();
		assertTrue(rs != null);
		rs.close();	
	}
	
	@Test
	/***
	 * Tests statement.execute( sql ) and statement.getResultSet()
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void testPreparedStatementExecuteWithSql() throws ParseException, SQLException {
		// Start and end date to search on
		String startDateStr = "06/01/2016 0:00:00.00";
		String endDateStr = "06/11/2016 23:59:59.59";
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				Utility.dateStringMMddyyyyHHmmssSSToEpoch(startDateStr) +
				" AND joined <= " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(endDateStr) + ";";
		
		PreparedStatement statement = (PreparedStatement) _conn.prepareStatement("");
		boolean success = statement.execute(sqlStatement);
		assertTrue(success);
		ResultSet rs = statement.getResultSet();
		assertTrue(rs != null);
		rs.close();	
	}
	
	@Test
	/***
	 * Test insert using PreparedStatement and executeUpdate()
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testPreparedStatementSqlInsert() throws SQLException, ParseException {
		// Create timestamp string for our record
		String timeStamp = "06/06/2016 22:30:00.00";
		String sqlStatement = "INSERT INTO jdbcDriverTest " +
				"(name, age, joined, weight) " +
				"VALUES " +
				"('Craig', 92, " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(timeStamp) + ", 202.5);";
		
		PreparedStatement statement = (PreparedStatement) _conn.prepareStatement(sqlStatement);
    	int result = statement.executeUpdate();
    	// Insert returns 0 on success
    	assertTrue(result == 0);
	}
	
	@Test
	/***
	 * Test insert using PreparedStatement and executeUpdate(sql)
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testPreparedStatementSqlInsert2() throws SQLException, ParseException {
		String sqlStatement = "INSERT INTO jdbcDriverTest " +
				"(name, age, joined, weight, active) " +
				"VALUES " +
				"('Julie', 44, '2016-06-06 22:30:00', 132.5, true);";
		
		PreparedStatement statement = (PreparedStatement) _conn.prepareStatement("");
    	int result = statement.executeUpdate(sqlStatement);
    	// Insert returns 0 on success
    	assertTrue(result == 0);
	}
	
	
	@Test
	/***
	 * Tests methods that moves the ResultSet cursor
	 * The tests are based on a ResultSet that returns 10 records
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

		Statement statement = _conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		assertTrue(rs != null);
		
		// Move to first row in ResultSet
		rs.first();
		assertTrue(rs.getRow() == 0);
		assertTrue(rs.isFirst());
		
		// Move to the next row in the ResultSet
		rs.next();
		assertTrue(rs.getRow() == 1);
		
		// Move to last row in ResultSet 
		rs.last();
		assertTrue(rs.getRow() == 9);
		assertTrue(rs.isLast());
		
		// Move to the previous row read
		rs.previous();
		assertTrue(rs.getRow() == 8);
		
		// Move to row number 4 (which is actually row[3]
		rs.absolute(4);
		assertTrue(rs.getRow() == 3);
		
		// Move 2 rows forward in the ResultSet
		rs.relative(2);
		assertTrue(rs.getRow() == 5);
		
		// Move to before the first row of the ResultSet
		rs.beforeFirst();
		assertTrue(rs.getRow() == -1);
		assertTrue(rs.isBeforeFirst());
		
		// Move to after the last row of the ResultSet
		rs.afterLast();
		assertTrue(rs.isAfterLast());
	}
	
	
	@Test
	public void testGetBuckets() throws ExecutionException, InterruptedException {
		List<String> buckets = _conn.getBuckets("jdbcDriverTest");
		assertTrue( buckets.size() > 0 ); 
	}
	
	
	@Test 
	public void testConnection() throws SQLException {
		assertTrue( _conn != null ); 
		assertTrue( _conn.getMetaData() != null );
		assertFalse( _conn.isClosed() );
	}

	@Test
	public void testAcceptsURL() throws SQLException {
		assertTrue( _driver.acceptsURL("riakts://127.0.0.1:8087") );
	}
	
	@Test
	public void testAcceptsProperties() throws SQLException {
		Properties info = new Properties();
		info.setProperty("RiakUrl", "127.0.0.1");
		info.setProperty("RiakPort", "8087");
		assertTrue( _driver.acceptsProperties(info) );
	}

}
