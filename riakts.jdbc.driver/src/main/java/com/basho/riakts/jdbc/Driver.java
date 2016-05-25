package com.basho.riakts.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import com.basho.riak.client.api.RiakClient;
import com.google.common.net.InetAddresses;

public class Driver implements java.sql.Driver {
	
	private static String RIAKTS_URL_PREFIX = "riakts://";
	
	RiakClient client = null;
	DatabaseMetaData databaseMetaData = null;
	
	private int riakPort = 8087;
	private String riakUrl = "127.0.0.1";

	public Connection connect(String url, Properties info) throws SQLException {
		try {
			if (!validateRiakUrl(url)) {
				throw new SQLException( "Unable to connect to Riak TS: Ivalid URL");
			}
			
			// Parse URL and set our port and url properties
			String[] urlParsed = url.replace(RIAKTS_URL_PREFIX, "").split(":");
			riakUrl = urlParsed[0];
			riakPort = Integer.parseInt(urlParsed[1]);
			
			client = RiakClient.newClient(riakPort, riakUrl);
		} 
		catch (Exception e) {
			throw new SQLException( "Unable to connect to Riak TS: " + e );
		} 
		
		return null;
	}
	
	private boolean validateRiakUrl(String url) {
		// Supported URL Format: riakts://127.0.0.1:8087 or riakts://something.com:8087
		if (url.startsWith(RIAKTS_URL_PREFIX)) {
			String[] urlParsed = url.replace(RIAKTS_URL_PREFIX, "").split(":");
			
			String testUrl = urlParsed[0];
			int testPort = -1;
			try {
				testPort = Integer.parseInt(urlParsed[1]);
			} catch (Exception e) {
				return false;
			}
			
			// Check that the IP Address and port are valid
			if (!InetAddresses.isInetAddress(testUrl)) return false;
			if (testPort < 0 || testPort > 64000) return false;
			
			return true;
		}
		else {
			return false;
		}
	}

	public boolean acceptsURL(String url) throws SQLException {
		return validateRiakUrl(url);
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
