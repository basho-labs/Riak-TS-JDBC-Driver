package com.basho.riakts.jdbc;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {
	
	// Variables need to be updated on a per release basis
	private static int MAJOR_VERSION = 0;
	private static int MINOR_VERSION = 1;
	private static boolean JDBC_COMPLIANT = false;

	public Connection connect(String url, Properties info) throws SQLException {
		// Validate that either the URL is valid or required 
		// information has been been passed via Properties (URL, Port)
		if (acceptsURL(url) || acceptsProperties(info)) {
			Connection conn = new  com.basho.riakts.jdbc.Connection();
		
			return conn;
		}
		else {
			return null;
		}
	}

	public boolean acceptsURL(String url) throws SQLException {
		return Utility.validateRiakUrl( url );
	}
	
	public boolean acceptsProperties(Properties info) throws SQLException {
		return Utility.validateRiakProperties( info );
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getMajorVersion() {
		return MAJOR_VERSION;
	}

	public int getMinorVersion() {
		return MINOR_VERSION;
	}

	public boolean jdbcCompliant() {
		return JDBC_COMPLIANT;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}
}
