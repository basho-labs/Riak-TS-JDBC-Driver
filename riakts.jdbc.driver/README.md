# Riak TS JDBC Driver

A basic JDBC driver for Basho's open source Riak TS (Time Series) database (https://docs.basho.com/riakts/latest/). 

**Note**: So far the driver has been tested with the following reporting tools:
- Jaspersoft Studio 6.3.0 (http://community.jaspersoft.com/project/jaspersoft-studio/releases)

# Using the Driver in Code

The driver implements support for the following JDBC features also supported by Riak TS:

**java.sql.Statement**
- executeQuery(String sql), execute(String sql), getResultSet() for **SELECT** and **DESCRIBE TABLE** statements
- executeUpdate(String sql) for **CREATE TABLE** and **INSERT** statements

**java.sql.PreparedStatement**
- executeQuery(String sql), executeQuery(), execute(), getResultSet() for **SELECT** and **DESCRIBE TABLE** statements
- executeUpdate(String sql) for **CREATE TABLE** and **INSERT** statements

**Note**: Currently there is no advantage (performance or otherwise) to using PreparedStatement over Statement.

The following example code demonstrates how to use the driver to execute a SELECT statement:
```Java
String sqlStatement = "SELECT * FROM jdbcDriverTest WHERE joined >= '2016-06-05 10:00:00'" + 
	" AND joined <= '2016-06-08 10:30:00';";
		
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

**Note**: The Riak TS timestamp data type uses the Unix epoch but you can write and query dates in SQL use the ISO 8061 date format as illustrated in the example above and below.

See the following documentation for more information on querying Riak TS with SQL: http://docs.basho.com/riak/ts/latest/using/querying/

Currently the Riak TS JDBC driver only implements a small subset of the functionality typically found in a fully featured driver. When reading from a ResultSet the following operations are supported:

- next(), previous(), first(), last(), absolute(), relative(), beforeFirst(), afterLast()
- getRow()
- isFirst(), isLast(), isBeforeFirst(), isAfterLast()
- close()
- getTimestamp(int columnIndex), getTimestamp(String columnLabel)
- getDouble(int columnIndex), getDouble(String columnLabel)
- getString(int columnIndex), getString(String columnLabel)
- getBoolean(int columnIndex), getBoolean(String columnLabel)
- getLong(int columnIndex), getLong(String columnLabel)
- getObject(int columnIndex), getObject(String columnLabel)
- getBlob(int columnIndex), getBlob(String columnLabel)

**Create a Table in Riak TS**

The driver allows you to create new tables in Riak TS using the CREATE TABLE command and executeUpdate() as demonstrated below:
```Java
String sqlStatement = "CREATE TABLE jdbcDriverTest " + 
	"( " +
		"name 			varchar   	not null, " +
	    "age			sint64   	not null, " +
	    "joined        	timestamp 	not null, " +
	    "weight		 	double		not null, " +
	    "active		 	boolean		not null, " +
	    "blobText	 	blob, " +
	    "PRIMARY KEY ( " +
	    	"(quantum(joined, 5, 'd')), " +
	    	"	joined, name, age " +
	    	") " +
	") WITH (n_val = 1)";
			
Statement statement = conn.createStatement();
int result = statement.executeUpdate(sqlStatement);
```
See the following documentation for more information about creating Riak TS tables: http://docs.basho.com/riak/ts/latest/using/creating-activating/

**Write Data to Riak TS**

The example below demonstrates how to add data to Riak TS using INSERT and executeUpdate():
```Java
String sqlStatement = "INSERT INTO jdbcDriverTest " +
	"(name, age, joined, weight, active, blobText) " +
	"VALUES " +
	"('Craig', 92, '2016-06-06 12:30:00', 202.5, true, 'xxxxxxxxxxxxxxx');";
		
Statement statement = conn.createStatement();
int result = statement.executeUpdate(sqlStatement);
// Insert returns 0 on success
Assert.assertTrue(result == 0);
```

**Important Note**: Inserting data into Riak TS is not as fast as using native methods supported by the Riak Java Client (https://github.com/basho/riak-java-client) directly to insert data.

# Riak TS to JDBC Data Types
When writing data from the Riak TS QueryResult object to the JDBC ResultSet object the driver converts Riak TS's data types using the following mapping:

- Timestamp -> Timestamp
- Varchar -> String
- Boolean -> Boolean
- Sint64 -> Long/Bigint
- Double -> Double
- Blob -> Blob 

# Building the JDBC Driver
A copy of the current version of the compiled JAR file is located in https://github.com/cvitter/Riak-TS-JDBC-Driver/releases. If you want to build your own version of the JAR file using Maven you can do so with the following steps:

1. Clone the project to your local machine: https://github.com/cvitter/Riak-TS-JDBC-Driver.git
2. Open the projects root directory from the command line: > /riakts.jdbc.driver
3. Build the JAR using the following Maven command: > mvn install -DskipTests

**Important Note** remove **-DskipTests** if you want the JUnit tests to execute during the build. 


# License
**The Riak TS JDBC Client** is Open Source software released under the Apache 2.0 License. Please see the [LICENSE](LICENSE) file for full license details.

# Authors
* Author: [Craig Vitter](https://github.com/cvitter)

# Contributors 
Please submit Issues and/or Pull Requests.
