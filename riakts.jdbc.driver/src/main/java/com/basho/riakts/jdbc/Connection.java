package com.basho.riakts.jdbc;

import java.net.UnknownHostException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.core.RiakNode;

public class Connection implements java.sql.Connection {
	
	RiakClient client = null;
	private DatabaseMetaData metaData = null;
	
	/***
	 * Connection - instantiates a RiakClient using the connection information passed in
	 * via the url or info parameters
	 * @param url
	 * @param info
	 * @throws UnknownHostException
	 * @throws SQLException
	 */
	public Connection(String url, Properties info) throws UnknownHostException, SQLException {
		if (Utility.validateRiakUrl(url)) { // Use the URL passed in to connect
			info = Utility.getRiakPropertiesFromUrl(url);
		}
		
		client = RiakClient.newClient(Integer.parseInt( info.getProperty("RiakPort") ), info.getProperty("RiakUrl"));
		this.metaData = new com.basho.riakts.jdbc.DatabaseMetaData(url);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
	

	public Statement createStatement() throws SQLException {
		return createStatement(0, 0, 0);
	}
	
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return createStatement(resultSetType, resultSetConcurrency, 0);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return new com.basho.riakts.jdbc.Statement(client, resultSetType, resultSetConcurrency, resultSetHoldability);
	}
	
	
	// TODO: Clean up prepareStatement methods
	
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return prepareStatement(sql, 0, 0, 0);
	}
	
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return prepareStatement(sql, 0, 0, 0);
	}
	
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) 
			throws SQLException {
		return new com.basho.riakts.jdbc.PreparedStatement(client, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}
	
	// TODO: Fix alternate prepareStatement invocations of PreparedStatement
	
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return prepareStatement(sql, 0, 0, 0);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return prepareStatement(sql, 0, 0, 0);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return prepareStatement(sql, 0, 0, 0);
	}
	
	
	
	
	
	public CallableStatement prepareCall(String sql) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public String nativeSQL(String sql) throws SQLException {
		return null;
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException { }

	public boolean getAutoCommit() throws SQLException {
		return false;
	}

	public void commit() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void rollback() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void close() throws SQLException {
		client.shutdown();
	}

	public boolean isClosed() throws SQLException {
		// Retrieves a list of nodes, if at least one of the nodes
		// is in RUNNING state returns false
		List<RiakNode> nodes = client.getRiakCluster().getNodes();
		for (RiakNode node : nodes)
		{
		    if ( node.getNodeState().toString().equalsIgnoreCase("RUNNING") ) return false;
		}
		return true;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return metaData;
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isReadOnly() throws SQLException {
		return false;
	}

	public void setCatalog(String catalog) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public String getCatalog() throws SQLException {
		return null;
	}

	public void setTransactionIsolation(int level) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getTransactionIsolation() throws SQLException {
		return 0;
	}

	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	public void clearWarnings() throws SQLException {
		
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
	
	}

	public void setHoldability(int holdability) throws SQLException {

	}

	public int getHoldability() throws SQLException {
		return 0;
	}

	public Savepoint setSavepoint() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
	
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {

	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Clob createClob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Blob createBlob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public NClob createNClob() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public SQLXML createSQLXML() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setSchema(String schema) throws SQLException {
		
	}

	public String getSchema() throws SQLException {
		return null;
	}

	public void abort(Executor executor) throws SQLException {
		
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getNetworkTimeout() throws SQLException {
		return 0;
	}

}
