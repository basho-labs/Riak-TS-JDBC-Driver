package com.basho.riakts.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {
	public List<ColumnInfo> _columns = new ArrayList<ColumnInfo>();
    private int _columnCount = 0;
	
	protected void setColumnCount(int columnCount) {
		_columnCount = columnCount;
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
