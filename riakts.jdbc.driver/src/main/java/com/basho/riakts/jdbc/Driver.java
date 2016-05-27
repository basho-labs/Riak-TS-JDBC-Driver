package com.basho.riakts.jdbc;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {
	
	private static int MAJOR_VERSION = 0;
	private static int MINOR_VERSION = 1;
	private static boolean JDBC_COMPLIANT = false;

	public Connection connect(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean acceptsURL(String url) throws SQLException {
		return Utility.validateRiakUrl( url );
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
