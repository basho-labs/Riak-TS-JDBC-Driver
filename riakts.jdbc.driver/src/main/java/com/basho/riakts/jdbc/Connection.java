package com.basho.riakts.jdbc;

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
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import com.basho.riak.client.api.RiakClient;


public class Connection {

	RiakClient client = null;
	DatabaseMetaData databaseMetaData = null;
	
	
	public Connection( RiakClient connection ) {
		client = connection;
	}
	
	
	public Statement createStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	public String nativeSQL(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	public void close() throws SQLException {
		client.shutdown();
	}


	public DatabaseMetaData getMetaData() throws SQLException {
		return databaseMetaData;
	}



	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTypeMap(Map map) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void setHoldability(int holdability) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
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



	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	/**
	 * Unsupported Operations Below
	 */
	
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean getAutoCommit() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void commit() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void rollback() throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public void setReadOnly(boolean readOnly) throws SQLException {
		// Does Nothing and silently laughs about it
	}

	public boolean isReadOnly() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setCatalog(String catalog) throws SQLException {
		// Does Nothing and silently laughs about it
	}

	public String getCatalog() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setTransactionIsolation(int level) throws SQLException {
		// Does Nothing and silently laughs about it
	}

	public int getTransactionIsolation() throws SQLException {
		// Riak doesn't support transactions
		return 0;
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void clearWarnings() throws SQLException {
		// Does Nothing and silently laughs about it
	}
	
	public Object unwrap(Class iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isWrapperFor(Class iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public CallableStatement prepareCall(String sql) throws SQLException {
		throw new UnsupportedOperationException();
	}
	

	public Savepoint setSavepoint() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		// Does Nothing and silently laughs about it
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		// Does Nothing and silently laughs about it
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

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void setSchema(String schema) throws SQLException {
		// Does Nothing and silently laughs about it
	}

	public String getSchema() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public void abort(Executor executor) throws SQLException {
		// Does Nothing and silently laughs about it
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		// Does Nothing and silently laughs about it		
	}
	
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}
	
}
