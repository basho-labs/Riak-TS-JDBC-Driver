package com.basho.riakts.jdbc;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import com.basho.riak.client.api.RiakClient;

public class Driver implements java.sql.Driver {
	
	private static String RIAKTS_URL_PREFIX = "riakts://";
	
	RiakClient client = null;
	DatabaseMetaData databaseMetaData = null;

	public Connection connect(String url, Properties info) throws SQLException {
		// Default connection values - work with standard local Riak TS installs
		int riakPort = 8087;
		String riakUrl = "127.0.0.1";
		
		try {
			RiakClient client = RiakClient.newClient(riakPort, riakUrl);
		} 
		catch (Exception e) {
			throw new SQLException( "Unable to connect to Riak TS: " + e );
		} 
		
		return null;
	}

	public boolean acceptsURL(String url) throws SQLException {
		// Supported URL Format: riakts://127.0.0.1:8087
		
		return false;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public int getMajorVersion() {
		return 0;
	}

	public int getMinorVersion() {
		return 1;
	}

	public boolean jdbcCompliant() {
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

}
