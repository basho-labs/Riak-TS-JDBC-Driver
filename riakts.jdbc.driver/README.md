# Riak TS JDBC Driver
This code is a rough attempt at implementing a JDBC driver for Basho's open source Riak TS (Time Series) database (docs.basho.com/riakts/latest/).

```Java
Driver d = new Driver();
Connection conn = (Connection) d.connect("riakts://127.0.0.1:8087", null);
		
String sqlStatement = "SELECT * FROM WaterMeterData WHERE time_stamp >= 1464739200000 AND time_stamp < 1464770000000;";
Statement statement = conn.createStatement();
ResultSet rs = statement.executeQuery(sqlStatement);
		
if (rs != null) {
	while (rs.next()) {
		System.out.println( rs.getString("customer_id") +  " | " + rs.getTimestamp("time_stamp") );
	}
}
		
rs.close();
conn.close();
```

# Riak TS to JDBC Data Types
When writing data from the Riak TS QueryResult object to the JDBC ResultSet object the driver converts Riak TS's data types in the following mapping:

- Timestamp -> Timestamp
- Varchar -> String
- Boolean -> Boolean
- Sint64 -> Long
- Double -> Double

# Release Notes

Version 0.1:
