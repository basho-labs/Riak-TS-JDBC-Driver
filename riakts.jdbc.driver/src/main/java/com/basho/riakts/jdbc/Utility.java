package com.basho.riakts.jdbc;

import com.google.common.net.InetAddresses;

public class Utility {
	
	private static String RIAKTS_URL_PREFIX = "riakts://";
	
	public static boolean validateRiakUrl(String url) {
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

}
