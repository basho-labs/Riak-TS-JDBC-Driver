/** 
 * Copyright (C) 2016 Basho Technologies Inc
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

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.buckets.ListBuckets;
import com.basho.riak.client.api.commands.timeseries.Query;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.timeseries.Cell;
import com.basho.riak.client.core.query.timeseries.ColumnDescription;
import com.basho.riak.client.core.query.timeseries.QueryResult;
import com.basho.riak.client.core.query.timeseries.Row;
import com.google.common.net.InetAddresses;

public class Utility {
	
	static ResultSet _rs;
	
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
		_rs = new ResultSet();
		
		// Get column names from the QueryResult object, add to the ResultSetMetaData ColumnInfo List
		Iterator<ColumnDescription> columns = queryResult.getColumnDescriptionsCopy().iterator();
		int columnCount = 0;
		while (columns.hasNext()) {
			ColumnDescription desc = columns.next();
			_rs.getMetaData().addColumn(desc.getName());
			columnCount++;
		}
		
		_rs._rsMetaData.setColumnCount(columnCount);
		_rs._rsMetaData.setRowCount( queryResult.getRowsCount() );
		
		// Iterate over each row in our QueryResult object
		Iterator<Row> rows = queryResult.iterator();
		while (rows.hasNext()) {
			// Retrieve Row from QueryResult set
			Row row = (Row) rows.next();
			
			// Creates new row to add to the ResultSet
			_rs.moveToInsertRow();
			
			// Iterate over each cell in current QueryResult row add matching column to
			// the current ResultSet row
			Iterator<Cell> cells = row.iterator();
			int colIndex = 0;
			while (cells.hasNext()) {
				Cell cell = (Cell) cells.next();
				
				// Update the ResultSetMetaData object ColumnType for this cell
				if (!allColumnTypesSet && cell != null) setColumnType(colIndex, cell);
				
				// Check cell type for the 6 data types and add a new column to the
				// row of the correct type (boolean, double, long, date, varchar)
				// Start by handling null cell values returned
				if (cell == null) {
					_rs.updateNull(colIndex);
				}
				else if (cell.hasBlob()) {
					Blob blob = new javax.sql.rowset.serial.SerialBlob(cell.getBlob());
					_rs.updateBlob(colIndex, blob);
				}
				else if (cell.hasBoolean()) {
					_rs.updateBoolean(colIndex, cell.getBoolean());
				}
				else if (cell.hasDouble()) {
					_rs.updateDouble(colIndex, cell.getDouble());
				}
				else if (cell.hasLong()) {
					_rs.updateLong(colIndex, cell.getLong());
				}
				else if (cell.hasTimestamp()) {
					try {
						// Convert from Epoch as Long to java.sql.Timestamp
						_rs.updateTimestamp(colIndex, new Timestamp(cell.getTimestamp()));
					} 
					catch (Exception e) {
						_rs.updateDate(colIndex, null);
					}
				}
				else if (cell.hasVarcharValue()) {
					// Get varchar as plain string for compatibility
					_rs.updateString(colIndex, cell.getVarcharValue().toString());
				}
				colIndex++;
			}	
			// Adds new row to the ResultSet
			_rs.insertRow();
		}
		return _rs;
	}
	
	
	private static boolean allColumnTypesSet = false;
	private static String[] columnTypes = null;
	
	/***
	 * Sets the data type associated with the specified column in the
	 * ResultSetMetaData object
	 * @param index
	 * @param cell
	 * @throws SQLException
	 */
	private static void setColumnType(int index, Cell cell) throws SQLException {
		// Create a new array to hold the data types of each column to help us determine
		// that they have all been set in the ResultSetMetaData object
		if (columnTypes == null) columnTypes = new String[ _rs._rsMetaData.getColumnCount() ];
		
		// Check the data type and update the correct ColumnType in the ResultSetMetaData object
		if (cell.hasBoolean()) {
			_rs.getMetaData().updateColumnType(index, java.sql.Types.BOOLEAN, "java.sql.Types.BOOLEAN");
			columnTypes[index] = "BOOLEAN";
		}
		else if (cell.hasBlob()) {
			_rs.getMetaData().updateColumnType(index, java.sql.Types.BLOB, "java.sql.Types.BLOB");
			columnTypes[index] = "BLOB";
		}
		else if (cell.hasDouble()) {
			_rs.getMetaData().updateColumnType(index, java.sql.Types.DOUBLE, "java.sql.Types.DOUBLE");
			columnTypes[index] = "DOUBLE";
		}
		else if (cell.hasLong()) {
			_rs.getMetaData().updateColumnType(index, java.sql.Types.BIGINT, "java.sql.Types.BIGINT");
			columnTypes[index] = "BIGINT";
		}
		else if (cell.hasTimestamp()) {
			_rs.getMetaData().updateColumnType(index, java.sql.Types.TIMESTAMP, "java.sql.Types.TIMESTAMP");
			columnTypes[index] = "TIMESTAMP";
		}
		else if (cell.hasVarcharValue()) {
			_rs.getMetaData().updateColumnType(index, java.sql.Types.VARCHAR, "java.sql.Types.VARCHAR");
			columnTypes[index] = "VARCHAR";
		}
		
		// Check to see if all column data types are set
		checkIfAllColumnTypesSet();
	} // TESTED
	
	/***
	 * Flips allColumnTypesSet to true if we have set the data type for
	 * each column in the ResultSetMetaData object
	 */
	private static void checkIfAllColumnTypesSet() {
		for (String type : columnTypes) {
			if (type == null) return;
		}
		allColumnTypesSet = true;
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
	} // Tested
	
	
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
	} // Tested
	

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
	} // Tested
	
	
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
	} // Tested
	
	
	/***
	 * Returns a list of buckets contained in a given bucket type
	 * @param client
	 * @param bucket
	 * @return List<String> containing all of the buckets for a given bucket type
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static List<String> getBuckets(RiakClient client, String bucket) throws ExecutionException, InterruptedException {
		List<String> buckets = new ArrayList<String>();
		ListBuckets lb = new ListBuckets.Builder(bucket).build();
		ListBuckets.Response resp = client.execute(lb);
		for (Namespace ns : resp)
		{
			buckets.add(ns.getBucketNameAsString());
		}
		return buckets;
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
