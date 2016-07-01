# Riak TS JDBC Driver Test
External test code to test the functionality of the Raik TS JDBC Driver found at: https://github.com/cvitter/Riak-TS-JDBC-Driver/tree/master/riakts.jdbc.driver

This project also includes code to create a new table and load records into the table to provide sufficient data for test against external reporting tools.

# ExternalDriverTest.java
Simple example code that verifies that the Riak TS JDBC Driver can perform a select against Riak TS and return a ResultSet. **Note**: This code requires the jdbcDriverTest table created in the main project's unit tests.

# CreateWeatherDataTable.java
Example code that creates a table in Riak TS (using the JDBC Driver) that will be used to store data from the Bay Area Bike Share program (http://www.bayareabikeshare.com/open-data). 

# LoadWeatherData.java
Example code that writes data to the table created in CreateWeatherDataTable.java. The data to load is found in the 201508_weather_data.csv file. **Note**: You will need to update the path to the file to match the location on your machine in order to execute this code successfully.
