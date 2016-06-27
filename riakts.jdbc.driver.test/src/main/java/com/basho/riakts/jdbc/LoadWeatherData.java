package com.basho.riakts.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoadWeatherData {
	static final String JDBC_DRIVER = "com.basho.riakts.jdbc.Driver";  
	static final String DB_URL = "riakts://127.0.0.1:8087";
	static final String DATA_FILE = "/Users/cvitter/Downloads/201508_weather_data.csv";
	
	public static void main(String[] args) throws SQLException {
		java.sql.Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL);
			
			String line = "";
			String cvsSplitBy = ",";
			int lineCount = 0;
			
			BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
			System.out.println("File Read");
			
			while ((line = br.readLine()) != null) {
				if (lineCount > 0) {
					String[] lineArray = line.split(cvsSplitBy);
				
					String sql = "INSERT INTO BayAreaWeatherData VALUES (" +
						getEpic(lineArray[0]) + ", " + testInt(lineArray[1]) + ", " + testInt(lineArray[2]) + ", " + testInt(lineArray[3]) + ", " +
						testInt(lineArray[4]) + ", " + testInt(lineArray[5]) + ", " + testInt(lineArray[6]) + ", " +
						testInt(lineArray[7]) + ", " + testInt(lineArray[8]) + ", " + testInt(lineArray[9]) + ", " +
						testDbl(lineArray[10]) + ", " + testDbl(lineArray[11]) + ", " + testDbl(lineArray[12]) + ", " +
						testInt(lineArray[13]) + ", " + testInt(lineArray[14]) + ", " + testInt(lineArray[15]) + ", " +
						testInt(lineArray[16]) + ", " + testInt(lineArray[17]) + ", " + testInt(lineArray[18]) + ", " +
						testInt(lineArray[19]) + ", " + testInt(lineArray[20]) + ", '" + lineArray[21] + "', " +
						testInt(lineArray[22]) + ", " + lineArray[23] + 
						")";
				
					System.out.println(sql);
					Statement statement = conn.createStatement();
					System.out.println(statement.executeUpdate(sql));
				}
				lineCount++;
			}
			
			br.close();
			System.out.println("File Closed");
		}
		catch (Exception e) {
			System.out.print(e);
		}
		
		conn.close();
	}
	
	private static int testInt(String inValue) {
		try {
			return Integer.parseInt(inValue);
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	private static double testDbl(String inValue) {
		try {
			return Double.parseDouble(inValue);
		}
		catch (Exception e) {
			double out = 0.0;
			return out;
		} 
	}
	
	private static long getEpic(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse(dateString);
		return date.getTime();
	}
	
}
