# Riak TS JDBC Driver
A basic JDBC driver for Basho's open source Riak TS (Time Series) database (docs.basho.com/riakts/latest/). The driver implements support for the following JDBC features also supported by Riak TS:

- executeQuery(String sql) for SELECT and DESCRIBE TABLE statements
- executeUpdate(String sql) for CREATE TABLE and INSERT statements

The following example code demonstrates how to use the driver to execute a SELECT statement:
```Java
// Start and end date to search on
String startDateStr = "06/01/2016 12:30:00.00";
String endDateStr = "06/10/2016 12:30:00.00";
		
// Convert string formats to epoch for TS
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
Date date = sdf.parse(startDateStr);
long startDate = date.getTime();
date = sdf.parse(endDateStr);
long endDate = date.getTime();
		
String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + startDate +
	"AND joined <= " + endDate + ";";
		
Statement statement = conn.createStatement();
ResultSet rs = statement.executeQuery(sqlStatement);
Assert.assertTrue(rs != null);
		
if (rs != null) {
	while (rs.next()) {
		System.out.println( rs.getString("name") + " | " + rs.getLong("age") + 
			" | " + rs.getTimestamp("joined")  + " | " + rs.getDouble("weight"));
	}
}
rs.close();;
```
**Note** Riak TS stores dates as Unix Epochs and the code above demonstrates how to convert Java dates to Epoch for queries.

Basic driver usage is demonstrated in the code block below. More example code is available in the project's tests.


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
A copy of the current version of the compiled JAR file is located in https://github.com/cvitter/Riak-TS-JDBC-Driver/releases. If you want to build your own version of the JAR file using Maven you can do so with the following steps:

1. Clone the project to your local machine: https://github.com/cvitter/Riak-TS-JDBC-Driver.git
2. Open the projects root directory from the command line: > /riakts.jdbc.driver
3. Build the JAR using the following Maven command: > mvn install -DskipTests

**Important Note** remove **-DskipTests** if you want the JUnit tests to execute during the build.  

# Release Notes
Version 0.2:
- Added executeUpdate() for CREATE TABLE and INSERT
- Fixed Type conversion bugs in ResultSet
- Added and corrected tests in DriverTest to verify support for CREATE TABLE, INSERT, SELECT, DESCRIBE TABLE
- Updated and expanded documentation

Version 0.1:
- Initial Release

# Feedback
Please submit Issues and/or Pull Requests.