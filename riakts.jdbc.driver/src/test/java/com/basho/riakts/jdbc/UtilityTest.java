/** 
 * Copyright (C) 2016 Craig Vitter - https://github.com/cvitter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.basho.riakts.jdbc;

import java.sql.SQLException;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

public class UtilityTest {

	@Test
	public void testValidateRiakUrl() {
		Assert.assertTrue( Utility.validateRiakUrl("riakts://127.0.0.1:8087") );
	}
	
	@Test
	public void testGetRiakProperties() throws SQLException {
		Properties info = Utility.getRiakPropertiesFromUrl("riakts://127.0.0.1:8087");
		Assert.assertFalse( info.isEmpty() );
	}
	
	@Test
	public void testValidateRiakPropertiesl() {
		Properties info = new Properties();
		info.setProperty("RiakUrl", "127.0.0.1");
		info.setProperty("RiakPort", "8087");
		Assert.assertTrue( Utility.validateRiakProperties(info) );
	}

}
