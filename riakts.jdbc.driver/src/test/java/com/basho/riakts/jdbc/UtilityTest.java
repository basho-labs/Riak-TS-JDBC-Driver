package com.basho.riakts.jdbc;

import junit.framework.Assert;
import org.junit.Test;

public class UtilityTest {

	@Test
	public void testValidateRiakUrl() {
		Assert.assertTrue( Utility.validateRiakUrl("riakts://127.0.0.1:8087") );
	}

}
