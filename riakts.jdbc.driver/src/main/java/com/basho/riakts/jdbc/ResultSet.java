package com.basho.riakts.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ResultSet implements java.sql.ResultSet {
	
	static final int HOLD_CURSORS_OVER_COMMIT = 1;
    static final int CLOSE_CURSORS_AT_COMMIT  = 2;
    protected static final int POS_BEFORE_FIRST = 0;
    protected static final int POS_AFTER_LAST = -1;
	
    protected int direction = FETCH_FORWARD;
	protected int fetchDirection = FETCH_FORWARD;
	
	/** The current row number that is being written to or read from. */
    protected int rowPosition = POS_BEFORE_FIRST;
    /** Total number of rows in the ResultSet */
	protected int rowsInResult = 0;
	/** Number of columns in the ResultSet */
	protected int columnCount = 0;
	
	protected ArrayList<Object[]> rowData;
	protected Object[] currentRow;
	ArrayList<String> columnList;
	
	protected boolean closed;
	protected boolean cancell;
	
	
	ResultSet() { 
		rowData = new ArrayList<Object[]>();
		columnList = new ArrayList<String>();
	}
	
	
	/***
	 * Adds new row to result set (currentRow) upon which update statements will act.
	 * Updates pos value to equal current row position in ResultSet.
	 * @throws SQLException 
	 */
	public void addRow() throws SQLException {
		// Throw exception if there are now columns or rows
		if (columnCount == 0 || rowsInResult == 0) throw new SQLException();
		
		// Create new row, set position in ResultSet
		currentRow = new Object[columnCount];
		if (rowData.size() == 0) {
			rowPosition = 0;
		}
		else {
			rowPosition++;
		}
	}
	
	
	/***
	 * Takes the column index and value as an object
	 * @param columnIndex
	 * @param dataType
	 * @param value
	 * @param length
	 * @throws SQLException 
	 */
	protected void setColumnValue(int columnIndex, Object value) throws SQLException {
		if (columnIndex < 0 || columnIndex > columnCount - 1) throw new SQLException();
		currentRow[columnIndex] = value;
	}


	public boolean next() throws SQLException {
		return false;
	}

	public void close() throws SQLException {
		
	}

	public boolean wasNull() throws SQLException {
		return false;
	}
	
	
	

	public String getString(int columnIndex) throws SQLException {
		return (String) currentRow[columnIndex];
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		return Boolean.valueOf( (String) currentRow[columnIndex] );
	}

	public byte getByte(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public short getShort(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getInt(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public long getLong(int columnIndex) throws SQLException {
		return Long.parseLong( (String) currentRow[columnIndex] );
	}

	public float getFloat(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public double getDouble(int columnIndex) throws SQLException {
		return Double.parseDouble( (String) currentRow[columnIndex] );
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Date getDate(int columnIndex) throws SQLException {
		return (Date) currentRow[columnIndex];
	}

	public Time getTime(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}



	public String getString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public byte getByte(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public short getShort(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getInt(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public long getLong(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFloat(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public double getDouble(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	public Date getDate(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Time getTime(String columnLabel) throws SQLException {
		return null;
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	public void clearWarnings() throws SQLException { }

	public String getCursorName() throws SQLException {
		return null;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return null;
	}

	public Object getObject(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getObject(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int findColumn(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean absolute(int row) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean relative(int rows) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setFetchDirection(int direction) throws SQLException { }

	public int getFetchDirection() throws SQLException {
		return 0;
	}

	public void setFetchSize(int rows) throws SQLException { }

	public int getFetchSize() throws SQLException {
		return 0;
	}

	public int getType() throws SQLException {
		return TYPE_FORWARD_ONLY;
	}

	public int getConcurrency() throws SQLException {
		return CONCUR_READ_ONLY;
	}

	public boolean rowUpdated() throws SQLException {
		return false;
	}

	public boolean rowInserted() throws SQLException {
		return false;
	}

	public boolean rowDeleted() throws SQLException {
		return false;
	}
	
	
	// Update Column Methods
	public void updateNull(int columnIndex) throws SQLException {
		setColumnValue(columnIndex, null);
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		setColumnValue(columnIndex, x);
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		setColumnValue(columnIndex, x);
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		setColumnValue(columnIndex, x);
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		setColumnValue(columnIndex, x);
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		setColumnValue(columnIndex, x);
	}
	
	public void updateNull(String columnLabel) throws SQLException {
		setColumnValue(columnList.indexOf(columnLabel), null);
	}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		setColumnValue(columnList.indexOf(columnLabel), x);
	}

	public void updateLong(String columnLabel, long x) throws SQLException {
		setColumnValue(columnList.indexOf(columnLabel), x);
	}

	public void updateDouble(String columnLabel, double x) throws SQLException {
		setColumnValue(columnList.indexOf(columnLabel), x);
	}


	public void updateString(String columnLabel, String x) throws SQLException {
		setColumnValue(columnList.indexOf(columnLabel), x);
	}

	public void updateDate(String columnLabel, Date x) throws SQLException {
		setColumnValue(columnList.indexOf(columnLabel), x);
	}

	
	
	
	// Un-Implemented Update Methods from ResultSet
	public void updateFloat(int columnIndex, float x) throws SQLException { }

	public void updateBytes(int columnIndex, byte[] x) throws SQLException { }

	public void updateByte(int columnIndex, byte x) throws SQLException { }

	public void updateShort(int columnIndex, short x) throws SQLException { }

	public void updateInt(int columnIndex, int x) throws SQLException { }

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException { }

	public void updateTime(int columnIndex, Time x) throws SQLException { }

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException { }

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException { }

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {  }

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException { }

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException { }

	public void updateObject(int columnIndex, Object x) throws SQLException { }

	public void updateFloat(String columnLabel, float x) throws SQLException { }
	
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException { }
	
	public void updateTime(String columnLabel, Time x) throws SQLException { }

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException { }

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException { }

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException { }

	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException { }

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException { }

	public void updateObject(String columnLabel, Object x) throws SQLException { }

	public void updateByte(String columnLabel, byte x) throws SQLException { }

	public void updateShort(String columnLabel, short x) throws SQLException { }

	public void updateInt(String columnLabel, int x) throws SQLException { }

	public void updateBytes(String columnLabel, byte[] x) throws SQLException { }

	public void updateRef(int columnIndex, Ref x) throws SQLException { }

	public void updateRef(String columnLabel, Ref x) throws SQLException { }

	public void updateBlob(int columnIndex, Blob x) throws SQLException { }

	public void updateBlob(String columnLabel, Blob x) throws SQLException { }

	public void updateClob(int columnIndex, Clob x) throws SQLException { }

	public void updateClob(String columnLabel, Clob x) throws SQLException { }

	public void updateArray(int columnIndex, Array x) throws SQLException { }

	public void updateArray(String columnLabel, Array x) throws SQLException { }
	
	public void updateNString(int columnIndex, String nString) throws SQLException { }

	public void updateNString(String columnLabel, String nString) throws SQLException { }

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException { }

	public void updateNClob(String columnLabel, NClob nClob) throws SQLException { }
	
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException { }

	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException { }
	
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException { }

	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { }

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException { }

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException { }

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException { }

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException { }

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException { }

	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { }

	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException { }

	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException { }

	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException { }

	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException { }

	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException { }

	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException { }

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException { }

	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException { }

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException { }

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException { }

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException { }

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException { }

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException { }

	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException { }

	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException { }

	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException { }

	public void updateClob(int columnIndex, Reader reader) throws SQLException { }

	public void updateClob(String columnLabel, Reader reader) throws SQLException { }

	public void updateNClob(int columnIndex, Reader reader) throws SQLException { }

	public void updateNClob(String columnLabel, Reader reader) throws SQLException { }
	
	
	
	
	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	// Column Get Methods
	public Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object getObject(String columnLabel, Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Ref getRef(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Ref getRef(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	// Un-Implemented Get Methods from ResultSet
	public Blob getBlob(int columnIndex) throws SQLException {
		return null;
	}

	public Clob getClob(int columnIndex) throws SQLException {
		return null;
	}

	public Array getArray(int columnIndex) throws SQLException {
		return null;
	}

	public Blob getBlob(String columnLabel) throws SQLException {
		return null;
	}

	public Clob getClob(String columnLabel) throws SQLException {
		return null;
	}

	public Array getArray(String columnLabel) throws SQLException {
		return null;
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return null;
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return null;
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		return null;
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		return null;
	}

	public URL getURL(int columnIndex) throws SQLException {
		return null;
	}

	public URL getURL(String columnLabel) throws SQLException {
		return null;
	}
	
	public NClob getNClob(int columnIndex) throws SQLException {
		return null;
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		return null;
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return null;
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return null;
	}
	
	public String getNString(int columnIndex) throws SQLException {
		return null;
	}

	public String getNString(String columnLabel) throws SQLException {
		return null;
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return null;
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return null;
	}
	
	
	

	public RowId getRowId(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	

	public int getHoldability() throws SQLException {
		return 0;
	}

	public boolean isClosed() throws SQLException {
		return false;
	}











	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
}
