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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {
	public List<ColumnInfo> _columns = new ArrayList<ColumnInfo>();
    private int _columnCount = 0;
    private int _rowCount = 0;
	
	protected void setColumnCount(int columnCount) {
		_columnCount = columnCount;
	}
	
	protected void setRowCount(int rowCount) {
		_rowCount = rowCount;
	}
	
	/***
	 * Returns the number of rows in this ResultSet object.
	 * @return the number of rows
	 * @throws SQLException
	 */
	public int getRowCount() throws SQLException {
		return _rowCount;
	}
	
	
	ResultSetMetaData() {}

	public void addColumn(String columnName) {
		ColumnInfo newColumn = new ColumnInfo();
		newColumn.setColumnName(columnName);
		newColumn.setColumnLabel(columnName); // TODO: Add Support for Column Labels in the future
		_columns.add(newColumn);
	}
	
	public void updateColumnType(int index, int type, String typeName) {
		 _columns.get(index).setColumnType(type);
		 _columns.get(index).setColumnTypeName(typeName);
	}
	
	public void updateColumnLable(int index, String label) {
		_columns.get(index).setColumnLabel(label);
	}

	public int getColumnCount() throws SQLException {
		return _columnCount;
	}
	
	// NOTE: In get methods below we subtract one from column to adjust for the fact
	// that JDBC indexes columns starting with 1 insted of the 0 based index of our
	// List<ColumnInfo> object

	public String getColumnLabel(int column) throws SQLException {
		return _columns.get( column - 1 ).getColumnLabel();
	}

	public String getColumnName(int column) throws SQLException {
		return _columns.get( column - 1 ).getColumnName();
	}

	public int getColumnType(int column) throws SQLException {
		return _columns.get( column - 1 ).getColumnType();
	}

	public String getColumnTypeName(int column) throws SQLException {
		return _columns.get( column - 1 ).getColumnTypeName();
	}
	
	
	
	/***
	 * Returns the 0 based index of the column matching the name passed in
	 * @param columnName
	 * @return index of the column
	 * @throws SQLException
	 */
	public int getColumnIndexByName(String columnName) throws SQLException {
		int column = 0;
		for (ColumnInfo c : _columns) {
			if (c.getColumnName().equalsIgnoreCase(columnName)) return column;
			column++;
		}
		return -1;
	}
	
	/***
	 * Returns the 0 based index of the column matching the label passed in
	 * @param columnLabel
	 * @return index of the column
	 * @throws SQLException
	 */
	public int getColumnIndexByLabel(String columnLabel) throws SQLException {
		int column = 0;
		for (ColumnInfo c : _columns) {
			if (c.getColumnLabel().equalsIgnoreCase(columnLabel)) {
				return column;
			}
			column++;
		}
		return -1;
	}
	



	// Stuff I haven't implemented yet below (or isn't implemented in TS)
	

	public boolean isReadOnly(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isDefinitelyWritable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public String getColumnClassName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAutoIncrement(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCaseSensitive(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSearchable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCurrency(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int isNullable(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isSigned(int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getColumnDisplaySize(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public String getSchemaName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPrecision(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getScale(int column) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTableName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCatalogName(int column) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
