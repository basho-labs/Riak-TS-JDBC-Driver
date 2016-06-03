package com.basho.riakts.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.basho.riak.client.core.query.timeseries.QueryResult;
import com.google.common.net.InetAddresses;

public class Utility {
	
	
	/***
	 * Converts a Riak TS QueryResult object to a JDBC ResultSet 
	 * @param queryResult
	 * @return
	 */
	public static ResultSet getResultSetFromQueryResult(QueryResult queryResult) {
		com.basho.riakts.jdbc.ResultSet out = new com.basho.riakts.jdbc.ResultSet();
		
		return out;
	}
	
	
	private static String RIAKTS_URL_PREFIX = "riakts://";
	
	/***
	 * Creates Properties object with RiakPort and RiakUrl key from
	 * URL passed in
	 * @param url Riak connection URL
	 * @return Properties object with RiakPort and RiakUrl key/value pairs
	 * @throws SQLException
	 */
	public static Properties getRiakPropertiesFromUrl(String url) throws SQLException {
		if (validateRiakUrl(url)) {
			String[] urlParsed = url.replace(RIAKTS_URL_PREFIX, "").split(":");
			Properties riakProperties = new Properties();
			riakProperties.setProperty("RiakUrl", urlParsed[0]);
			riakProperties.setProperty("RiakPort", urlParsed[1]);
			return riakProperties;
		}
		throw new SQLException();
	}
	
	/***
	 * Attempts to validate that the URL passed in can be parsed into a valid
	 * URL and Port that can be used to connect to Riak. Does not test the
	 * ability to connect to the specified Riak cluster.
	 * @param url The URL and Port to connect to
	 * @return True or False
	 */
	public static boolean validateRiakUrl(String url) {
		// Supported URL Format: riakts://127.0.0.1:8087 or riakts://something.com:8087
		if (url.startsWith(RIAKTS_URL_PREFIX)) {
			String[] urlParsed = url.replace(RIAKTS_URL_PREFIX, "").split(":");
			// Check that the IP Address and port are valid
			if (isInetAddress( urlParsed[0] ) && isValidPort( urlParsed[1] )) {
				return true;
			}
		}
		return false;
	}

	/***
	 * Checks to see if RiakUrl and RiakPort exist and if the values appear
	 * to be of a valid type required to connect to Riak
	 * @param info Properties object with RiakUrl and RiakPort keys
	 * @return True or False
	 */
	public static boolean validateRiakProperties(Properties info) {
		// Make sure info isn't empty or null
		if (info.isEmpty() != true && info != null) {
			// Check that the IP Address and port are valid
			if (isInetAddress( info.getProperty("RiakUrl") ) && isValidPort( info.getProperty("RiakPort") )) {
				return true;
			}
		}
		return false;
	}
	
	
	private static boolean isInetAddress( String url ) {
		if ( InetAddresses.isInetAddress( url )) {
			return true;
		}
		return false;
	}
	
	private static boolean isValidPort( String port ) {
		int testPort = -1;
		try {
			testPort = Integer.parseInt( port );
		} catch (Exception e) {
			return false;
		}
		
		if (testPort > 0 || testPort < 64000) {
			return true;
		}
		return false;
	}

}
