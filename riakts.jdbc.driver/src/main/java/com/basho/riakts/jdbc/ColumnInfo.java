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
