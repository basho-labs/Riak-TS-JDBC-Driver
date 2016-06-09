# Riak TS JDBC Driver
This code is a rough attempt at implementing a JDBC driver for Basho's open source Riak TS (Time Series) database (docs.basho.com/riakts/latest/).

Basic driver usage is demonstrated in the code block below. More example code is available in the project's tests.
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

**Important Note**
This driver only implements a small portion of the JDBC specification. When reading from a ResultSet the following operations are supported:

- next()
- close()
- getTimestamp(int columnIndex), getTimestamp(String columnLabel)
- getDouble(int columnIndex), getDouble(String columnLabel)
- getString(int columnIndex), getString(String columnLabel)
- getBoolean(int columnIndex), getBoolean(String columnLabel)
- getLong(int columnIndex), getLong(String columnLabel)
- getObject(int columnIndex), getObject(String columnLabel)

# Riak TS to JDBC Data Types
When writing data from the Riak TS QueryResult object to the JDBC ResultSet object the driver converts Riak TS's data types in the following mapping:

- Timestamp -> Timestamp
- Varchar -> String
- Boolean -> Boolean
- Sint64 -> Long
- Double -> Double

# Building the JDBC Driver
A copy of the current version of the compiled JAR file is located in the Release folder. If you want to build your own version of the JAR file using Maven you can do so with the following steps:

1. Clone the project to your local machine: https://github.com/cvitter/Riak-TS-JDBC-Driver.git
2. Open the projects root directory from the command line: > /riakts.jdbc.driver
3. Build the JAR using the following Maven command: > mvn install

**Note***: The Maven command above will also run the project's tests. If you wish to skip the tests you can use the following command: > mvn install -DskipTests

# Release Notes

Version 0.1:
- Initial Release

