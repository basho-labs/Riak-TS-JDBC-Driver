package com.basho.riakts.jdbc;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

public class UtilityTest {

	@Test
	public void testValidateRiakUrl() {
		Assert.assertTrue( Utility.validateRiakUrl("riakts://127.0.0.1:8087") );
	}
	
	@Test
	public void testValidateRiakPropertiesl() {
		Properties info = new Properties();
		info.setProperty("RiakUrl", "127.0.0.1");
		info.setProperty("RiakPort", "8087");
		Assert.assertTrue( Utility.validateRiakProperties(info) );
	}

}
