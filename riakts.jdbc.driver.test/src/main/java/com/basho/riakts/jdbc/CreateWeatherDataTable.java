/** 
 * Copyright (C) 2016 Craig Vitter - https://github.com/cvitter
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

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author cvitter
 * Example code that creates a table in Riak TS (using the JDBC Driver) 
 * that will be used to store data from the Bay Area Bike Share program 
 * (http://www.bayareabikeshare.com/open-data).
 */
public class CreateWeatherDataTable {
	static final String JDBC_DRIVER = "com.basho.riakts.jdbc.Driver";  
	static final String DB_URL = "riakts://127.0.0.1:8087";
	static final String sql = "" +
			"CREATE TABLE BayAreaWeatherData (" +
			"	    date               timestamp     not null," +
			"	    max_temp           sint64," +
			"	    mean_temp          sint64," +
			"	    min_temp           sint64," +
			"	    max_dew_point      sint64," +
			"	    mean_dew_point     sint64," +
			"	    min_dew_point      sint64," +
			"	    max_humidity       sint64," +
			"	    mean_humidity      sint64," +
			"	    min_humidity       sint64," +
			"	    max_pressure       double," +
			"	    mean_pressure      double," +
			"	    min_pressure       double," +
			"	    max_visibility     sint64," +
			"	    mean_visibility    sint64," +
			"	    min_visibility     sint64," +
			"	    max_wind_speed     sint64," +
			"	    mean_wind_speed    sint64," +
			"	    max_wind_gust      sint64," +
			"	    precipitation_in   sint64," +
			"	    cloud_cover        sint64," +
			"	    events             varchar," +
			"	    wind_direction     sint64," +
			"	    zip_code           sint64        not null," +   
			"	    PRIMARY KEY(" +
			"	        (quantum(date, 365, 'd'))," +
			"	         date, zip_code" +
			"	    )" +
			"	)" +
			"	WITH (" +
			"	    n_val=1" +
			"	)";
	
	public static void main(String[] args) throws SQLException {
		java.sql.Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL);
			Statement statement = conn.createStatement();
			int result = statement.executeUpdate(sql);
			System.out.print("Result Output: " + result);
		}
		catch (Exception e) {
			System.out.print(e);
		}
	}
	
}
