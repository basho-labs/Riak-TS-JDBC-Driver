package com.basho.riakts.jdbc;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import com.google.common.net.InetAddresses;

public class RiakTSDriver implements java.sql.Driver {

	private static String RIAKTS_URL_PREFIX = "riakts://";
	
	public Connection connect(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	//
	
	public boolean acceptsURL(String url) throws SQLException {
		return validateRiakUrl(url);
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
	
	

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
