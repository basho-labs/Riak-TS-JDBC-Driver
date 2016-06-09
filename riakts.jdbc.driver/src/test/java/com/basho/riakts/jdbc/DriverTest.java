package com.basho.riakts.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	public void testExecuteQuery() throws SQLException {
		Connection conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
		Assert.assertFalse( conn.isClosed() );
		
		String sqlStatement = "SELECT * FROM WaterMeterData WHERE time_stamp >= 1464739200000 AND time_stamp < 1464770000000;";
		
		Statement statement = conn.createStatement();
		com.basho.riakts.jdbc.ResultSet rs = (com.basho.riakts.jdbc.ResultSet) statement.executeQuery(sqlStatement);
		
		if (rs != null) {
			while (rs.next()) {
				System.out.println( rs.getString("customer_id") + " - " + rs.getString("meter_id"));
			}
		}
		
		rs.close();
		conn.close();
	}

}
