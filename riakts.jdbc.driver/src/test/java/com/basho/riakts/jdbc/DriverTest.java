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
	public void testSqlCreateTable() {
		try {
			Connection conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
			Assert.assertFalse( conn.isClosed() );
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
	    	Assert.assertTrue(result > -1);
			conn.close();
	    }
	    catch (Exception e) {
	    	String error = e.getMessage();
	    	Assert.assertTrue(error.contains("already_active"));
	    }

	}
	
	@Test
	public void testSqlDescribeTable() {
		
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
