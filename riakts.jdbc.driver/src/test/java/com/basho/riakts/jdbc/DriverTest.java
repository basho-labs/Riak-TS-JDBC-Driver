package com.basho.riakts.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DriverTest {

	Driver d = null;
	
	@Before
	public void setUp() throws Exception {
		d = new Driver();
	}

	@After
	public void tearDown() throws Exception {
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
			Connection conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
			String sqlStatement = "CREATE TABLE jdbcDriverTest " + 
	    		"( " +
	    			"name 			varchar   	not null, " +
	    			"age			sint64   	not null, " +
	    			"joined        	timestamp 	not null, " +
	    			"weight		 	double		not null, " +
	    			"active 		boolean, " +
	    			"PRIMARY KEY ( " +
	    			"(quantum(joined, 365, 'd')), " +
	    			"	joined, name, age " +
	    			") " +
	    		")";
			
			Statement statement = conn.createStatement();
	    	int result = statement.executeUpdate(sqlStatement);
	    	Assert.assertTrue(result == 0);
			conn.close();
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
			Connection conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
			String sqlStatement = "CREATE TABLE dontCreateMe " + 
	    		"( " +
	    			"name 			varchar   	not null, " +
	    			"age			sint64   	not null, " +
	    			"joined        	timestamp 	not null, " +
	    			"weight		 	double		not null, " +
	    			"active 		boolean, " +
	    			"PRIMARY KEY ( " +
	    			"(quantum(joined, 365, 'd')), " +
	    			"	badColumn, name, age " +
	    			") " +
	    		")";
			
			Statement statement = conn.createStatement();
	    	int result = statement.executeUpdate(sqlStatement);
	    	Assert.assertFalse(result > -1);
			conn.close();
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
		Connection conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
		
		String sqlStatement = "DESCRIBE jdbcDriverTest;";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		
		Assert.assertTrue(rs != null);
		
		int columnCount = 0;
		while (rs.next()) {
			columnCount++;
		}
		Assert.assertTrue(columnCount == 5);
		
		rs.close();
		conn.close();
	}
	
	@Test
	public void testSqlInsertData() {
		
	}

	
	@Test
	public void testSqlSelect() throws SQLException {
		Connection conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
		Assert.assertFalse( conn.isClosed() );
		
		String sqlStatement = "SELECT * FROM WaterMeterData WHERE time_stamp >= 1464739200000 AND time_stamp < 1464770000000;";
		
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlStatement);
		
		if (rs != null) {
			while (rs.next()) {
				System.out.println( rs.getString("customer_id") + " | " + rs.getString("meter_id")  + " | " + rs.getTimestamp("time_stamp") );
			}
		}
		
		rs.close();
		conn.close();
	}
	
	
	@Test 
	public void testConnection() throws SQLException {
		Connection conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
		Assert.assertFalse( conn.isClosed() );
		conn.close();
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
