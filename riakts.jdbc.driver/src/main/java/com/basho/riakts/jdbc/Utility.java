package com.basho.riakts.jdbc;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.timeseries.Query;
import com.basho.riak.client.core.query.timeseries.Cell;
import com.basho.riak.client.core.query.timeseries.ColumnDescription;
import com.basho.riak.client.core.query.timeseries.QueryResult;
import com.basho.riak.client.core.query.timeseries.Row;
import com.google.common.net.InetAddresses;

public class Utility {
	
	/***
	 * Executes SQL query against Riak TS and converts the 
	 * QueryResult object to a ResultSet
	 * @param sql
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws SQLException 
	 */
	public static ResultSet query(RiakClient client, String sql) throws ExecutionException, InterruptedException, SQLException {
		Query query = new Query.Builder(sql).build();
		QueryResult queryResult = client.execute(query);
		return getResultSetFromQueryResult(queryResult);
	}
	
	
	/***
	 * Converts a Riak TS QueryResult object to a JDBC ResultSet 
	 * @param queryResult com.basho.riak.client.core.query.timeseries.QueryResult
	 * @return java.sql.ResultSet
	 * @throws SQLException 
	 */
	private static ResultSet getResultSetFromQueryResult(QueryResult queryResult) throws SQLException {
		// Create new empty ResultSet
		ResultSet rs = new ResultSet();
		
		// TODO: Refactor to set appropriate values in ResultSetMetaData
		

		// Get column names from the QueryResult object
		Iterator<ColumnDescription> columns = queryResult.getColumnDescriptionsCopy().iterator();
		while (columns.hasNext()) {
			ColumnDescription desc = columns.next();
			rs.columnList.add(desc.getName());
			rs.getMetaData().addColumn(desc.getName());
		}
		
		rs.columnCount = rs.columnList.size();
		
		rs.rsMetaData.setColumnCount(rs.columnList.size());
		rs.rowsInResult = queryResult.getRowsCount();
		
		// Iterate over each row in our QueryResult object
		Iterator<Row> rows = queryResult.iterator();
		while (rows.hasNext()) {
			// Retrieve Row from QueryResult set
			Row row = (Row) rows.next();
			
			// Creates new row to add to the ResultSet
			rs.moveToInsertRow();
			
			// Iterate over each cell in current QueryResult row add matching column to
			// the current ResultSet row
			Iterator<Cell> cells = row.iterator();
			int colIndex = 0;
			while (cells.hasNext()) {
				Cell cell = (Cell) cells.next();
				// Check cell type for the 5 data types and add a new column to the
				// row of the correct type (boolean, double, long, date, varchar)
				// Start by handling null cell values returned
				// Also set columnType information in ResultSetMetaData
				// TODO: Find efficient method to not repeatedly set column type as we write the data ******
				if (cell == null) {
					rs.updateNull(colIndex);
				}
				else if (cell.hasBoolean()) {
					rs.updateBoolean(colIndex, cell.getBoolean());
					rs.getMetaData().updateColumnType(colIndex, java.sql.Types.BOOLEAN, "java.sql.Types.BOOLEAN");
				}
				else if (cell.hasDouble()) {
					rs.updateDouble(colIndex, cell.getDouble());
					rs.getMetaData().updateColumnType(colIndex, java.sql.Types.DOUBLE, "java.sql.Types.DOUBLE");
				}
				else if (cell.hasLong()) {
					rs.updateLong(colIndex, cell.getLong());
					rs.getMetaData().updateColumnType(colIndex, java.sql.Types.BIGINT, "java.sql.Types.BIGINT");
				}
				else if (cell.hasTimestamp()) {
					try {
						// Convert from Epoch as Long to java.sql.Timestamp
						rs.updateTimestamp(colIndex, new Timestamp(cell.getTimestamp()));
						rs.getMetaData().updateColumnType(colIndex, java.sql.Types.TIMESTAMP, "java.sql.Types.TIMESTAMP");
					} 
					catch (Exception e) {
						rs.updateDate(colIndex, null);
					}
				}
				else if (cell.hasVarcharValue()) {
					// Get varchar as plain string for compatibility
					rs.updateString(colIndex, cell.getVarcharValue().toString());
					rs.getMetaData().updateColumnType(colIndex, java.sql.Types.VARCHAR, "java.sql.Types.VARCHAR");
				}
				colIndex++;
			}	
			// Adds new row to the ResultSet
			rs.insertRow();
		}
		return rs;
	}
	
	
	private static String RIAKTS_URL_PREFIX = "riakts://";
	
	/***
	 * Creates Properties object with RiakPort and RiakUrl key from
	 * URL passed in
	 * @param url Riak connection URL
	 * @return Properties object with RiakPort and RiakUrl key/value pairs
	 * @throws SQLException
	 */
	public static Properties getRiakPropertiesFromUrl(String url) throws SQLException {
		if (validateRiakUrl(url)) {
			String[] urlParsed = url.replace(RIAKTS_URL_PREFIX, "").split(":");
			Properties riakProperties = new Properties();
			riakProperties.setProperty("RiakUrl", urlParsed[0]);
			riakProperties.setProperty("RiakPort", urlParsed[1]);
			return riakProperties;
		}
		throw new SQLException();
	}
	
	/***
	 * Attempts to validate that the URL passed in can be parsed into a valid
	 * URL and Port that can be used to connect to Riak. Does not test the
	 * ability to connect to the specified Riak cluster.
	 * @param url The URL and Port to connect to
	 * @return True or False
	 */
	public static boolean validateRiakUrl(String url) {
		// Supported URL Format: riakts://127.0.0.1:8087 or riakts://something.com:8087
		if (url.startsWith(RIAKTS_URL_PREFIX)) {
			String[] urlParsed = url.replace(RIAKTS_URL_PREFIX, "").split(":");
			// Check that the IP Address and port are valid
			if (isInetAddress( urlParsed[0] ) && isValidPort( urlParsed[1] )) {
				return true;
			}
		}
		return false;
	}

	/***
	 * Checks to see if RiakUrl and RiakPort exist and if the values appear
	 * to be of a valid type required to connect to Riak
	 * @param info Properties object with RiakUrl and RiakPort keys
	 * @return True or False
	 */
	public static boolean validateRiakProperties(Properties info) {
		// Make sure info isn't empty or null
		if (info.isEmpty() != true && info != null) {
			// Check that the IP Address and port are valid
			if (isInetAddress( info.getProperty("RiakUrl") ) && isValidPort( info.getProperty("RiakPort") )) {
				return true;
			}
		}
		return false;
	}
	
	
	/***
	 * Simple helper method that converts date strings in the 
	 * MM/dd/yyyy HH:mm:ss.SS format to long
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static long dateStringMMddyyyyHHmmssSSToEpoch(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SS");
		Date date = sdf.parse(dateString);
		return date.getTime();
	}
	
	
	private static boolean isInetAddress( String url ) {
		if ( InetAddresses.isInetAddress( url )) {
			return true;
		}
		return false;
	}
	
	private static boolean isValidPort( String port ) {
		int testPort = -1;
		try {
			testPort = Integer.parseInt( port );
		} catch (Exception e) {
			return false;
		}
		
		if (testPort > 0 || testPort < 64000) {
			return true;
		}
		return false;
	}

}
