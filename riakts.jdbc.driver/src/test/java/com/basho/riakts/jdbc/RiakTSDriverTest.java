package com.basho.riakts.jdbc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import junit.framework.Assert;

public class RiakTSDriverTest {

	private static String dbUrl = "riakts://127.0.0.1:8087";
	RiakTSDriver driver = null;
	
	@Before
    public void setUp() {
		driver = new RiakTSDriver();
    }

    @After
    public void tearDown() {
    	driver = null;
    }
	
    
	@Test
	public void testGetConnection() {
		
		fail("Not yet implemented");
	}
	
	@Test
	public void testAcceptsUrl() throws SQLException {
		Assert.assertTrue(driver.acceptsURL(dbUrl));
	}

}
