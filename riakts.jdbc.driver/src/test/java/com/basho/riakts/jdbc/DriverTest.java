package com.basho.riakts.jdbc;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DriverTest extends TestCase {
	
	Driver d;
	
	public void setUp() throws Exception {
		d = new Driver();
	}
	
	public void tearDown() throws Exception {
		d = null;
	}

	public void testConnect() {
//		fail("Not yet implemented");
	}

	public void testAcceptsURL() throws SQLException {
		assertTrue(d.acceptsURL("riakts://127.0.0.1:8087"));
	}

	public void testGetMajorVersion() {
		Assert.assertNotNull(d.getMajorVersion());
	}

	public void testGetMinorVersion() {
		Assert.assertNotNull(d.getMinorVersion());
	}

	public void testJdbcCompliant() {
		Assert.assertFalse(d.jdbcCompliant());
	}

}
