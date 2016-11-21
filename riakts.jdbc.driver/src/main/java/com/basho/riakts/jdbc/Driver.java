/** 
 * Copyright (C) 2016 Basho Technologies Inc
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

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {
	
	// Variables need to be updated on a per release basis
	private final static int MAJOR_VERSION = 0;
	private final static int MINOR_VERSION = 7;
	private final static boolean JDBC_COMPLIANT = false;
	
	/***
	 * Register the driver with DriverManager
	 */
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } 
        catch (SQLException e) {
        }
    }
    
	
	public Connection connect(String url, Properties info) throws SQLException {
		// Validate that either the URL is valid or required 
		// information has been been passed via Properties (URL, Port)
		if (acceptsURL(url) || acceptsProperties(info)) {
			try {
				return new com.basho.riakts.jdbc.Connection(url, info);
			} catch (UnknownHostException e) {
				throw new SQLException(e);
			}
		}
		else {
			return null;
		}
	} // Tested
	

	public boolean acceptsURL(String url) throws SQLException {
		return Utility.validateRiakUrl( url );
	} // Tested
	
	/***
	 * Retrieves whether the driver thinks that it can open a connection via the properties
	 * passed in (For Riak RiakUrl and RiakPort are required properties). 
	 * Typically drivers will return true if they understand the protocols 
	 * specified and false if they do not.
	 * @param info java.util.Properties - list of tag/value pairs
	 * @return true if the driver understands the properties passed; false if not
	 * @throws SQLException
	 */
	public boolean acceptsProperties(Properties info) throws SQLException {
		return Utility.validateRiakProperties( info );
	} // Tested
	
	public int getMajorVersion() {
		return MAJOR_VERSION;
	}

	public int getMinorVersion() {
		return MINOR_VERSION;
	}

	public boolean jdbcCompliant() {
		return JDBC_COMPLIANT;
	}

	// Unsupported Driver methods
	
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}
}
