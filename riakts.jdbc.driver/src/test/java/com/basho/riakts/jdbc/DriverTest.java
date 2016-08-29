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

	private Driver _driver = null;
	private Connection _conn = null;
	
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
	    			"active		 	boolean		not null, " +
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
	 * to match the number of columns in the table (5)
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
		assertTrue(columnCount == 5);
		
		rs.close();
	}
	
	
	@Test
	/****
	 * Test insertion of data via SQL INSERT INTO command, successful insert
	 * returns a 0 value
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSqlInsertData() throws SQLException, ParseException {
		// Create timestamp string for our record
		String timeStamp = "06/06/2016 12:30:00.00";
		
		String sqlStatement = "INSERT INTO jdbcDriverTest " +
				"(name, age, joined, weight, active) " +
				"VALUES " +
				"('Craig', 92, " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(timeStamp) + ", 202.5, true);";
		
		Statement statement = _conn.createStatement();
    	int result = statement.executeUpdate(sqlStatement);
    	// Insert returns 0 on success
    	assertTrue(result == 0);
	}
	
	
	private static String[][] PEOPLE = {
			{"Lucy", "22", "06/06/2016 10:30:00.00", "104.0", "true"},
			{"Tom", "35", "06/06/2016 11:30:00.00", "180.5", "true"},
			{"Sarah", "15", "06/06/2016 13:30:00.00", "100.1", "true"},
			{"Mark", "42", "06/06/2016 14:30:00.00", "160.8", "true"},
			{"Anna", "27", "06/06/2016 15:30:00.00", "110.5", "true"},
			{"John", "17", "06/06/2016 16:30:00.00", "170.5", "true"},
			{"Sophia", "12", "06/06/2016 17:30:00.00", "115.9", "true"},
			{"Bob", "32", "06/06/2016 18:30:00.00", "220.1", "true"},
			{"Julie", "44", "06/06/2016 22:30:00.00", "132.5", "true"}
	};
	
	@Test
	/***
	 * Another insert test example, primarily used to add additional
	 * data to the test table as opposed to actually testing inserts
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSqlInsertMultipleRows() throws SQLException, ParseException {
		for (String[] person : PEOPLE) {
			String sqlStatement = "INSERT INTO jdbcDriverTest " +
					"(name, age, joined, weight, active) " +
					"VALUES " +
					"('" + person[0] + "', " + person[1] + ", " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(person[2]) + 
					", " + person[3] + ", " + person[4] + ");";
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
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void testSqlSelect() throws SQLException, ParseException {
		// Start and end date to search on
		String startDateStr = "'2016-06-05 10:00:00'";
		String endDateStr = "'2016-06-08 10:30:00'";
		
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
				startDateStr + " AND joined <= " + endDateStr + ";";
		//System.out.println(sqlStatement);
		
		Statement statement = _conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		assertTrue(rs != null);
		
		if (rs != null) {
			while (rs.next()) {
				// Check get methods for the various supported types to make sure that
				// they don't get broken during code refactoring
				String name = rs.getString("name");
				long age = rs.getLong("age");
				Timestamp ts = rs.getTimestamp("joined");
				double weight = rs.getDouble("weight");
				boolean active = rs.getBoolean("active");
				
				// Print out ResultSet for demonstration purposes only, commented out for normal test runs
//				System.out.println( rs.getString("name") + " | " + rs.getLong("age") + 
//						" | " + rs.getTimestamp("joined")  + " | " + rs.getDouble("weight"));
			}
		}
		
		assertTrue(rs.getMetaData().getColumnTypeName(1).equalsIgnoreCase("java.sql.Types.VARCHAR"));
		assertTrue(rs.getMetaData().getColumnTypeName(2).equalsIgnoreCase("java.sql.Types.BIGINT"));
		assertTrue(rs.getMetaData().getColumnTypeName(3).equalsIgnoreCase("java.sql.Types.TIMESTAMP"));
		assertTrue(rs.getMetaData().getColumnTypeName(4).equalsIgnoreCase("java.sql.Types.DOUBLE"));
		assertTrue(rs.getMetaData().getColumnTypeName(5).equalsIgnoreCase("java.sql.Types.BOOLEAN"));
		
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
		// Create timestamp string for our record
		String timeStamp = "06/06/2016 22:30:00.00";
		
		String sqlStatement = "INSERT INTO jdbcDriverTest " +
				"(name, age, joined, weight, active) " +
				"VALUES " +
				"('Julie', 44, " + Utility.dateStringMMddyyyyHHmmssSSToEpoch(timeStamp) + ", 132.5, true);";
		
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
