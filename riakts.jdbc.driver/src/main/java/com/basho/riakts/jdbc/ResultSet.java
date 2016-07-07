/** 
 * Copyright (C) 2016 Craig Vitter - https://github.com/cvitter
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
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class ResultSet implements java.sql.ResultSet {

	protected static final int POS_BEFORE_FIRST = -1;
    protected static final int POS_AFTER_LAST = -1;
    
    protected ResultSetMetaData _rsMetaData;
    
    protected int _direction = FETCH_FORWARD;
	protected int _fetchDirection = FETCH_FORWARD;
	protected int _rowPosition = POS_BEFORE_FIRST;
	
	protected ArrayList<Object[]> _rowData;
	
	protected boolean _closed;
	private Object[] _currentRow;
	private Object[] _insertRow;
	private boolean _inserting = false;
	
	
	ResultSet() { 
		_closed = false;
		_rowData = new ArrayList<Object[]>();
		_rsMetaData = new ResultSetMetaData();
	}
	
	public void close() throws SQLException {
		_rowData = null;
		_currentRow = null;
		_rowPosition = -1;
		_closed = true;
	}
	
	public boolean isClosed() throws SQLException {
		return _closed;
	}
	
	
	public void moveToInsertRow() throws SQLException {
		// Throw exception if there are no columns or rows in the QueryResult
		if (_rsMetaData.getColumnCount() == 0 || _rsMetaData.getRowCount() == 0) throw new SQLException();
		
		// Create a new Object[] to store column values
		_insertRow = new Object[_rsMetaData.getColumnCount()];
		
		// Set inserting to true versus updating an existing row
		_inserting = true;
	}
	
	public void insertRow() throws SQLException {
		// Add the new row to the rowData ArrayList<Object[]>
		_rowData.add(_insertRow);
		
		// Clear insertRow
		_insertRow = null;
		
		// Set inserting back to false
		_inserting = false;
		
		// Update currentRow to equal our newly added row
		_currentRow = _rowData.get(_rowData.size() - 1);
	}
	
	
	/***
	 * Updates a column based on its index, works for both inserts of new rows
	 * and updates of existing rows
	 * @param columnIndex index of the row to update
	 * @param value column value
	 * @throws SQLException
	 */
	protected void setColumnValue(int columnIndex, Object value) throws SQLException {
		if (columnIndex < 0 || columnIndex > _rsMetaData.getColumnCount() - 1) throw new SQLException();
		if (_inserting) {
			_insertRow[columnIndex] = value;
		}
		else {
			_currentRow[columnIndex] = value;
		}
	}

	
	// Start - Get Methods that have been implemented for Riak TS
	// NOTE: In get methods below we subtract one from column to adjust for the fact
	// that JDBC indexes columns starting with 1 insted of the 0 based index of our
	// currentRow Object[] ONLY when attempting to get a value by index
	
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return (Timestamp) _currentRow[ columnIndex - 1 ];
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return (Timestamp) _currentRow[ _rsMetaData.getColumnIndexByLabel(columnLabel) ];
	}

	public double getDouble(int columnIndex) throws SQLException {
		return Double.parseDouble( (String) _currentRow[ columnIndex - 1 ] );
	}
	
	public double getDouble(String columnLabel) throws SQLException {
		return (Double) _currentRow[ _rsMetaData.getColumnIndexByLabel(columnLabel) ];
	}

	public String getString(int columnIndex) throws SQLException {
		return (String) _currentRow[ columnIndex - 1 ];
	}
	
	public String getString(String columnLabel) throws SQLException {
		return (String) _currentRow[ _rsMetaData.getColumnIndexByLabel(columnLabel) ];
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		return (Boolean) _currentRow[ columnIndex - 1 ];
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		return (Boolean) _currentRow[ _rsMetaData.getColumnIndexByLabel(columnLabel) ];
	}
	
	public long getLong(int columnIndex) throws SQLException {
		return (Long) _currentRow[ columnIndex - 1 ] ;
	}

	public long getLong(String columnLabel) throws SQLException {
		return (Long) _currentRow[ _rsMetaData.getColumnIndexByLabel(columnLabel) ];
	}
	
	public Object getObject(int columnIndex) throws SQLException {
		return _currentRow[ columnIndex - 1 ];
	}

	public Object getObject(String columnLabel) throws SQLException {
		return _currentRow[ _rsMetaData.getColumnIndexByLabel(columnLabel) ];
	}
	// End - Get Methods that have been implemented for Riak TS

	
	
	public ResultSetMetaData getMetaData() throws SQLException {
		return _rsMetaData;
	}
	
	
	public int findColumn(String columnLabel) throws SQLException {
		// Add 1 to the value returned by getColumnIndexByLabel
		return _rsMetaData.getColumnIndexByLabel(columnLabel) + 1;
	}

	
	
	// Start - Row/Cursor position related methods
	/***
	 * Retrieve our Object[] for the specified row
	 * @param i
	 * @throws SQLException
	 */
	private void setCurrentRow(int i) throws SQLException {
		_currentRow = _rowData.get(i);
	} // Tested
	
	public boolean isBeforeFirst() throws SQLException {
		if (_rowPosition == -1) {
			return true;
		}
		return false;
	} // Tested

	public boolean isAfterLast() throws SQLException {
		if (_rowPosition > _rowData.size() - 1) {
			return true;
		}
		return false;
	} // Tested

	public boolean isFirst() throws SQLException {
		if (_rowPosition == 0) {
			return true;
		}
		return false;
	} // Tested

	public boolean isLast() throws SQLException {
		if (_rowPosition == _rowData.size() - 1) {
			return true;
		}
		return false;
	} // Tested

	public void beforeFirst() throws SQLException {
		_currentRow = null;
		_rowPosition = -1;
	} // Tested

	public void afterLast() throws SQLException {
		_currentRow = null;
		_rowPosition = _rowData.size() + 1;
	} // Tested

	public boolean first() throws SQLException {
		if (_rowData.size() > 0) {
			_rowPosition = 0;
			setCurrentRow(0);
			return true;
		}
		return false;
	} // Tested

	public boolean last() throws SQLException {
		if (_rowData.size() > 0) {
			_rowPosition = _rowData.size() - 1;
			setCurrentRow(_rowPosition);
			return true;
		}
		return false;
	} // Tested

	public int getRow() throws SQLException {
		return _rowPosition;
	} // Tested

	public boolean absolute(int row) throws SQLException {
		if (_rowData.size() > 0 && row <= _rowData.size() && row >= 0) {
			_rowPosition = row - 1;
			setCurrentRow(_rowPosition);
			return true;
		}
		return false;
	} // Tested

	public boolean relative(int rows) throws SQLException {
		if (_rowData.size() > 0) {
			int newRowPosition = _rowPosition + rows;
			if (newRowPosition > -1 && newRowPosition < _rowData.size()) {
				_rowPosition = newRowPosition;
				setCurrentRow(_rowPosition);
				return true;
			}
		}
		return false;
	} // Tested

	public boolean previous() throws SQLException {
		if (_rowData.size() > 0 && _rowPosition > 0) {
			_rowPosition--;
			setCurrentRow(_rowPosition);
			return true;
		}
		return false;
	} // Tested
		
	public boolean next() throws SQLException {
		if (_rowData.size() > 0 && _rowPosition < _rowData.size() - 1) {
			_rowPosition++;
			setCurrentRow(_rowPosition);
			return true;
		}
		return false;
	} // Tested
	// End - Row/Cursor position related methods
	


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
	
	public void updateNull(String columnLabel) throws SQLException {
		setColumnValue( _rsMetaData.getColumnIndexByLabel(columnLabel), null);
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		setColumnValue(columnIndex, x);
	}
	
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		setColumnValue( _rsMetaData.getColumnIndexByLabel(columnLabel), x);
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		setColumnValue(columnIndex, x);
	}
	
	public void updateLong(String columnLabel, long x) throws SQLException {
		setColumnValue( _rsMetaData.getColumnIndexByLabel(columnLabel), x);
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		setColumnValue(columnIndex, x);
	}
	
	public void updateDouble(String columnLabel, double x) throws SQLException {
		setColumnValue( _rsMetaData.getColumnIndexByLabel(columnLabel), x);
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		setColumnValue(columnIndex, x);
	}

	public void updateString(String columnLabel, String x) throws SQLException {
		setColumnValue( _rsMetaData.getColumnIndexByLabel(columnLabel), x);
	}
	
	public void updateDate(int columnIndex, Date x) throws SQLException {
		setColumnValue(columnIndex, x);
	}
	
	public void updateDate(String columnLabel, Date x) throws SQLException {
		setColumnValue( _rsMetaData.getColumnIndexByLabel(columnLabel), x);
	}
	
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException { 
		setColumnValue( _rsMetaData.getColumnIndexByLabel(columnLabel), x);
	}
	
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException { 
		setColumnValue(columnIndex, x);
	}


	
	// Update methods not implement for Riak TS
	
	/***
	 * Method not implemented - no value returned
	 */
	public void updateFloat(int columnIndex, float x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBytes(int columnIndex, byte[] x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateByte(int columnIndex, byte x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateShort(int columnIndex, short x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateInt(int columnIndex, int x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateTime(int columnIndex, Time x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {  }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateObject(int columnIndex, Object x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateFloat(String columnLabel, float x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateTime(String columnLabel, Time x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateObject(String columnLabel, Object x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateByte(String columnLabel, byte x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateShort(String columnLabel, short x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateInt(String columnLabel, int x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBytes(String columnLabel, byte[] x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateRef(int columnIndex, Ref x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateRef(String columnLabel, Ref x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBlob(int columnIndex, Blob x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBlob(String columnLabel, Blob x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateClob(int columnIndex, Clob x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateClob(String columnLabel, Clob x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateArray(int columnIndex, Array x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateArray(String columnLabel, Array x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNString(int columnIndex, String nString) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNString(String columnLabel, String nString) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateClob(int columnIndex, Reader reader) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateClob(String columnLabel, Reader reader) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNClob(int columnIndex, Reader reader) throws SQLException { }
	/***
	 * Method not implemented - no value returned
	 */
	public void updateNClob(String columnLabel, Reader reader) throws SQLException { }

	
	/***
	 * Method not implemented - no value returned
	 */
	public void updateRow() throws SQLException {
		
	}

	public void deleteRow() throws SQLException {
		_rowData.remove(_rowPosition);
	}

	/***
	 * Method not implemented - no value returned
	 */
	public void refreshRow() throws SQLException {
		
	}

	/***
	 * Method not implemented - no value returned
	 */
	public void cancelRowUpdates() throws SQLException {
		
	}

	/***
	 * Method not implemented - no value returned
	 */
	public void moveToCurrentRow() throws SQLException {
		
	}

	/***
	 * Method not implemented - null value returned
	 */
	public Statement getStatement() throws SQLException {
		return null;
	}
	
	
	

	// Get Methods not implemented for Riak TS
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Date getDate(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Date getDate(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Ref getRef(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Ref getRef(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public byte getByte(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public short getShort(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public int getInt(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public float getFloat(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Time getTime(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Clob getClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Array getArray(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Clob getClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Array getArray(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public URL getURL(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public URL getURL(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public String getNString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public String getNString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public byte getByte(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public short getShort(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public int getInt(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public float getFloat(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Time getTime(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	
	
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - no value returned
	 */
	public void updateRowId(int columnIndex, RowId x) throws SQLException { }
	/***
	 * Not implemented - no value returned
	 */
	public void updateRowId(String columnLabel, RowId x) throws SQLException { }
	

	public int getHoldability() throws SQLException {
		return 0;
	}

	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	/***
	 * Not implemented - throws UnsupportedOperationException
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}


	public boolean wasNull() throws SQLException {
		return false;
	}	
	
	
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	public void clearWarnings() throws SQLException { }

	public String getCursorName() throws SQLException {
		return null;
	}

}
