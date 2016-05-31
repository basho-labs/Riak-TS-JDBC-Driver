package com.basho.riakts.jdbc;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DriverTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAcceptsURL() throws SQLException {
		Driver d = new Driver();
		Assert.assertTrue( d.acceptsURL("riakts://127.0.0.1:8087") );
		d = null;
	}

}
