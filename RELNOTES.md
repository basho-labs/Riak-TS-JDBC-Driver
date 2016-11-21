# Release Notes

**Version 0.7**
- Updated to use Version 2.1.0 of the Riak Java Client (https://github.com/basho/riak-java-client/releases/tag/riak-client-2.1.0)

**Version 0.6**
- Updated tests to reflect new functionality in Riak TS 1.4 that allows the use of ISO 8061 dates in addition to the epoch
- Updated tests SQL INSERT tests to test capability of inserting boolean values based on bug fix in Riak TS 1.4
- Updated tests for JUnit 4, modified POM to include the dependency
- Updated file headers for move to github.com/basho-labs and expanded license text

**Version 0.5**
- Tested to support report creation in Jaspersoft Studio 6.3.0 (http://community.jaspersoft.com/project/jaspersoft-studio/releases)
- Added PreporedStatement, ResultSetMetaData, and ColumnInfo classes as part of refactoring to support reporting tools
- Lots of minor refactoring and bug fixes throughout 

**Version 0.4**
- Corrected bug in dateStringToEpoch, changed method to dateStringMMddyyyyHHmmssSSToEpoch for clarity

**Version 0.3**
- Fix riakts.jdbc.driver.Driver to register with DriverManager so that third party apps can use the driver
- Create an external test project (riakts.jdbc.driver.test) to test that the driver works with third party apps

**Version 0.2**
- Added executeUpdate() for CREATE TABLE and INSERT
- Fixed Type conversion bugs in ResultSet
- Added and corrected tests in DriverTest to verify support for CREATE TABLE, INSERT, SELECT, DESCRIBE TABLE
- Added tests for next(), previous(), first(), last(), absolute(), relative(), beforeFirst(), afterLast(), isFirst(), isLast(), isBeforeFirst(), isAfterLast()
- Updated and expanded documentation

**Version 0.1**
- Initial Release