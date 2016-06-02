package com.basho.riakts.jdbc;

import java.sql.SQLException;

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
		Assert.assertTrue( true );
		conn.isClosed();
		conn.close();
	}

	@Test
	public void testAcceptsURL() throws SQLException {
		Assert.assertTrue( d.acceptsURL("riakts://127.0.0.1:8087") );
	}

}
