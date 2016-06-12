# Riak TS JDBC Driver
A basic JDBC driver for Basho's open source Riak TS (Time Series) database (docs.basho.com/riakts/latest/). The driver implements support for the following JDBC features also supported by Riak TS:

- executeQuery(String sql) for SELECT and DESCRIBE TABLE statements
- executeUpdate(String sql) for CREATE TABLE and INSERT statements

The following example code demonstrates how to use the driver to execute a SELECT statement:
```Java
// Start and end date to search on
String startDateStr = "06/06/2016 0:00:00.00";
String endDateStr = "06/06/2016 23:59:59.59";
		
String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= " + 
	Utility.dateStringToEpoch(startDateStr) +
	" AND joined <= " + Utility.dateStringToEpoch(endDateStr) + ";";
		
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

See the following documentation for more information on querying Riak TS with SQL: http://docs.basho.com/riak/ts/latest/using/querying/

The driver allow you to create new tables in Riak TS using CREATE TABLE and executeUpdate() as demonstrated below:
```Java
String sqlStatement = "CREATE TABLE jdbcDriverTest " + 
	"( " +
		"name 			varchar   	not null, " +
	    "age			sint64   	not null, " +
	    "joined        	timestamp 	not null, " +
	    "weight		 	double		not null, " +
	    "PRIMARY KEY ( " +
	    "(quantum(joined, 365, 'd')), " +
	    "	joined, name, age " +
	") " +
")";
			
Statement statement = conn.createStatement();
int result = statement.executeUpdate(sqlStatement);
```
See the following documentation for more information about creating Riak TS tables: http://docs.basho.com/riak/ts/latest/using/creating-activating/

The example below demonstrates how to add data to Riak TS using INSERT and executeUpdate():
```Java
// Create timestamp string for our record
String timeStamp = "06/06/2016 12:30:00.00";
		
String sqlStatement = "INSERT INTO jdbcDriverTest " +
	"(name, age, joined, weight) " +
	"VALUES " +
	"('Craig', 92, " + Utility.dateStringToEpoch(timeStamp) + ", 202.5);";
		
Statement statement = conn.createStatement();
int result = statement.executeUpdate(sqlStatement);
// Insert returns 0 on success
Assert.assertTrue(result == 0);
```
**Important Note** In Riak TS 1.3 there is a bug that prevents insertion of boolean values via the SQL Insert command. This bug should be corrected in 1.4. See the following documentation for more information about adding data to Riak TS with SQL: http://docs.basho.com/riak/ts/latest/using/writingdata/#adding-data-via-sql

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