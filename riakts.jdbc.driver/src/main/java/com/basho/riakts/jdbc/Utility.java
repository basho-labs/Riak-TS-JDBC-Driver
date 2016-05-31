package com.basho.riakts.jdbc;

import java.util.Properties;

import com.google.common.net.InetAddresses;

public class Utility {
	
	private static String RIAKTS_URL_PREFIX = "riakts://";
	
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
			
			String riakUrl = urlParsed[0];
			int testPort = -1;
			try {
				testPort = Integer.parseInt(urlParsed[1]);
			} catch (Exception e) {
				return false;
			}
			
			// Check that the IP Address and port are valid
			if (isInetAddress( riakUrl ) && isValidPort( testPort )) {
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
			String riakUrl = info.getProperty("RiakUrl");
			int testPort = -1;
			try {
				testPort = Integer.parseInt( info.getProperty("RiakPort") );
			} catch (Exception e) {
				return false;
			}
			
			// Check that the IP Address and port are valid
			if (isInetAddress( riakUrl ) && isValidPort( testPort )) {
				return true;
			}
		}
		return false;
	}
	
	
	private static boolean isInetAddress( String url ) {
		if ( InetAddresses.isInetAddress( url )) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static boolean isValidPort( int port ) {
		if (port > 0 || port < 64000) {
			return true;
		}
		else {
			return false;
		}
	}

}
