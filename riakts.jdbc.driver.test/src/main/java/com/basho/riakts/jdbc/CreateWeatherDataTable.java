package com.basho.riakts.jdbc;

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
	
	public static void main(String[] args) {
		
	}
	
}
