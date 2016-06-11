package com.basho.riakts.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	    			"(quantum(joined, 365, 'd')), " +
	    			"	joined, name, age " +
	    			") " +
	    		")";
			
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
	    			"(quantum(joined, 365, 'd')), " +
	    			"	badColumn, name, age " +
	    			") " +
	    		")";
			
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
				
		// Convert string format to epoch for TS
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
		Date date = sdf.parse(timeStamp);
		long timeStampEpoch = date.getTime();
		
		String sqlStatement = "INSERT INTO jdbcDriverTest " +
				"(name, age, joined, weight) " +
				"VALUES " +
				"('Craig', 92, " + timeStampEpoch + ", 202.5);";
		
		Statement statement = conn.createStatement();
    	int result = statement.executeUpdate(sqlStatement);
    	// Insert returns 0 on success
    	Assert.assertTrue(result == 0);
	}

	
	@Test
	public void testSqlSelect() throws SQLException, ParseException {
		// Start and end date to search on
		String startDateStr = "06/01/2016 12:30:00.00";
		String endDateStr = "06/10/2016 12:30:00.00";
		
		// Convert string formats to epoch for TS
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
		Date date = sdf.parse(startDateStr);
		long startDate = date.getTime();
		date = sdf.parse(endDateStr);
		long endDate = date.getTime();
		
		String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + startDate +
				"AND joined <= " + endDate + ";";
		
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		Assert.assertTrue(rs != null);
		
		// Print out ResultSet for demonstraiton purposes only, commented out for normal test runs 
//		if (rs != null) {
//			while (rs.next()) {
//				System.out.println( rs.getString("name") + " | " + rs.getLong("age") + 
//						" | " + rs.getTimestamp("joined")  + " | " + rs.getDouble("weight"));
//			}
//		}
		rs.close();
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
