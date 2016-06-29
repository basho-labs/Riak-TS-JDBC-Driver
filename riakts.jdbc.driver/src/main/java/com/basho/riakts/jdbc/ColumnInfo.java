package com.basho.riakts.jdbc;

public class ColumnInfo {
	
	private String columnName;
	private String columnLabel;
	private int columnType;
	private String columnTypeName;
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnLabel() {
		return columnLabel;
	}
	
	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}
	
	public int getColumnType() {
		return columnType;
	}
	
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	
	public String getColumnTypeName() {
		return columnTypeName;
	}
	
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

}
