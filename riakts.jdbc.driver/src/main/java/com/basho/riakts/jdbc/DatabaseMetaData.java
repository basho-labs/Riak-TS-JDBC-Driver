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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

public class DatabaseMetaData implements java.sql.DatabaseMetaData {
	
	// These variables need to be updated with each new release
	private final static String DRIVER_NAME = "Riak TS JDBC Driver";
	private final static String DRIVER_VERSION = "0.7";
	private final static int DRIVER_MAJOR_VERSION = 0;
	private final static int DRIVER_MINOR_VERSION = 7;
	
	// Riak TS Product Variables
	// Note: Update version a new version is released and 
	// tested with the driver
	private final static String DATABASE_PRODUCT_NAME = "Riak TS";
	private final static String DATABASE_PRODUCT_VERSION = "1.4";
	
	// JDBC Driver specific variables that won't update a lot
	private final static boolean IS_READ_ONLY = false; 
	private final static boolean NULLS_ARE_SORTED_HIGH = false;
	private final static boolean NULLS_ARE_SORTED_LOW = false;
	private final static boolean NULLS_ARE_SORTED_AT_START = false;
	private final static boolean NULLS_ARE_SORTED_AT_END = false;
	private final static boolean USES_LOCAL_FILES = false;
	private final static String SQL_KEYWORDS = "";
	private final static String STRING_FUNCTIONS = "";
	private final static String NUMERIC_FUNCTIONS = "";
	private final static String SYSTEM_FUNCTIONS = "";
	private final static String TIME_DATE_FUNCTIONS = "";
	private final static boolean ALTER_TABLE_ADD_COLUMN = false;
	private final static boolean ALTER_TABLE_DROP_COLUMN = false;
	private final static boolean COLUMN_ALIASING = false;
	private final static boolean EXPRESIONS_IN_ORDER_BY = false;
	private final static boolean ORDER_BY_UNRELATED = false;
	private final static boolean SUPPORTS_GROUP_BY = false;
	private final static boolean GROUP_BY_UNRELATED = false;
	private final static boolean GROUP_BY_BEYOND_SELECT = false;
	private final static boolean LIKE_ESCAPE_CLAUSE = false;
	private final static boolean NON_NULLABLE_COLUMNS = false;
	private final static boolean OUTER_JOINS = false;
	private final static boolean FULL_OUTER_JOINS = false;
	private final static boolean LIMITED_OUTER_JOINS = false;
	private final static String SCHEMA_TERM = "";
	private final static String PROCEDURE_TERM = "";
	private final static String CATALOG_TERM = "";
	private final static boolean UNION = false;
	private final static boolean UNION_ALL = false;
	private final static boolean ALL_PROCEDURES_CALLABLE = false;
	private final static boolean ALL_TABLES_SELECTABLE = true;
	private final static boolean USES_LOCAL_FILE_PER_TABLE = false;
	private final static boolean SUPPORTS_MIXED_CASE_IDENTIFIERS = true;
	private final static boolean STORES_UPPER_CASE_IDENTIFIERS = false;
	private final static boolean STORES_MIXED_CASE_IDENTIFIERS = true;
	private final static boolean SUPPORTS_MIXED_CASE_QUOTED_IDENTIFIERS = true;
	private final static boolean STORES_UPPER_CASE_QUOTED_IDENTIFIERS = false;
	private final static boolean STORES_LOWER_CASE_IDENTIFIERS = false;
	private final static boolean STORES_LOWER_CASE_QUOTED_IDENTIFIERS = false;
	private final static boolean STORES_MIXED_CASE_QUOTED_IDENTIFIERS = false;
	private final static String IDENTIFIER_QUOTE_STRING = " ";
	private final static String SEARCH_STRING_ESCAPE = null;
	private final static String EXTRA_NAME_CHARACTERS = null;
	
	
	private String _url = null;
	private String _userName = null;
	
	public DatabaseMetaData(String url) {
		_url = url;
	}
	
	public DatabaseMetaData(String url, String userName) {
		_url = url;
	}
	
	public int getDatabaseMajorVersion() throws SQLException {
		return DRIVER_MAJOR_VERSION;
	}

	public int getDatabaseMinorVersion() throws SQLException {
		return DRIVER_MINOR_VERSION;
	}

	public boolean allProceduresAreCallable() throws SQLException {
		return ALL_PROCEDURES_CALLABLE;
	}

	public boolean allTablesAreSelectable() throws SQLException {
		return ALL_TABLES_SELECTABLE;
	}

	public String getURL() throws SQLException {
		return _url;
	}

	public String getUserName() throws SQLException {
		return _userName;
	}

	public boolean isReadOnly() throws SQLException {
		return IS_READ_ONLY;
	}

	public boolean nullsAreSortedHigh() throws SQLException {
		return NULLS_ARE_SORTED_HIGH;
	}

	public boolean nullsAreSortedLow() throws SQLException {
		return NULLS_ARE_SORTED_LOW;
	}

	public boolean nullsAreSortedAtStart() throws SQLException {
		return NULLS_ARE_SORTED_AT_START;
	}

	public boolean nullsAreSortedAtEnd() throws SQLException {
		return NULLS_ARE_SORTED_AT_END;
	}

	public String getDatabaseProductName() throws SQLException {
		return DATABASE_PRODUCT_NAME;
	}

	public String getDatabaseProductVersion() throws SQLException {
		return DATABASE_PRODUCT_VERSION;
	}

	public String getDriverName() throws SQLException {
		return DRIVER_NAME;
	}

	public String getDriverVersion() throws SQLException {
		return DRIVER_VERSION;
	}

	public int getDriverMajorVersion() {
		return DRIVER_MAJOR_VERSION;
	}

	public int getDriverMinorVersion() {
		return DRIVER_MINOR_VERSION;
	}

	public boolean usesLocalFiles() throws SQLException {
		return USES_LOCAL_FILES;
	}
	
	public boolean usesLocalFilePerTable() throws SQLException {
		return USES_LOCAL_FILE_PER_TABLE;
	}

	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		return SUPPORTS_MIXED_CASE_IDENTIFIERS;
	}

	public boolean storesUpperCaseIdentifiers() throws SQLException {
		return STORES_UPPER_CASE_IDENTIFIERS;
	}

	public boolean storesLowerCaseIdentifiers() throws SQLException {
		return STORES_LOWER_CASE_IDENTIFIERS;
	}

	public boolean storesMixedCaseIdentifiers() throws SQLException {
		return STORES_MIXED_CASE_IDENTIFIERS;
	}

	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		return SUPPORTS_MIXED_CASE_QUOTED_IDENTIFIERS;
	}

	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		return STORES_UPPER_CASE_QUOTED_IDENTIFIERS;
	}

	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		return STORES_LOWER_CASE_QUOTED_IDENTIFIERS;
	}

	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		return STORES_MIXED_CASE_QUOTED_IDENTIFIERS;
	}

	public String getIdentifierQuoteString() throws SQLException {
		return IDENTIFIER_QUOTE_STRING;
	}

	public String getSQLKeywords() throws SQLException {
		return SQL_KEYWORDS;
	}

	public String getNumericFunctions() throws SQLException {
		return NUMERIC_FUNCTIONS;
	}

	public String getStringFunctions() throws SQLException {
		return STRING_FUNCTIONS;
	}

	public String getSystemFunctions() throws SQLException {
		return SYSTEM_FUNCTIONS;
	}

	public String getTimeDateFunctions() throws SQLException {
		return TIME_DATE_FUNCTIONS;
	}

	public String getSearchStringEscape() throws SQLException {
		return SEARCH_STRING_ESCAPE;
	}

	public String getExtraNameCharacters() throws SQLException {
		return EXTRA_NAME_CHARACTERS;
	}

	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		return ALTER_TABLE_ADD_COLUMN;
	}

	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		return ALTER_TABLE_DROP_COLUMN;
	}

	public boolean supportsColumnAliasing() throws SQLException {
		return COLUMN_ALIASING;
	}

	public boolean nullPlusNonNullIsNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsConvert() throws SQLException {
		return false;
	}

	public boolean supportsConvert(int fromType, int toType) throws SQLException {
		return false;
	}

	public boolean supportsTableCorrelationNames() throws SQLException {
		return false;
	}

	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		return false;
	}

	public boolean supportsExpressionsInOrderBy() throws SQLException {
		return EXPRESIONS_IN_ORDER_BY;
	}

	public boolean supportsOrderByUnrelated() throws SQLException {
		return ORDER_BY_UNRELATED;
	}

	public boolean supportsGroupBy() throws SQLException {
		return SUPPORTS_GROUP_BY;
	}

	public boolean supportsGroupByUnrelated() throws SQLException {
		return GROUP_BY_UNRELATED;
	}

	public boolean supportsGroupByBeyondSelect() throws SQLException {
		return GROUP_BY_BEYOND_SELECT;
	}

	public boolean supportsLikeEscapeClause() throws SQLException {
		return LIKE_ESCAPE_CLAUSE;
	}

	public boolean supportsMultipleResultSets() throws SQLException {
		return false;
	}

	public boolean supportsMultipleTransactions() throws SQLException {
		return false;
	}

	public boolean supportsNonNullableColumns() throws SQLException {
		return NON_NULLABLE_COLUMNS;
	}

	public boolean supportsMinimumSQLGrammar() throws SQLException {
		return false;
	}

	public boolean supportsCoreSQLGrammar() throws SQLException {
		return false;
	}

	public boolean supportsExtendedSQLGrammar() throws SQLException {
		return false;
	}

	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		return false;
	}

	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		return false;
	}

	public boolean supportsANSI92FullSQL() throws SQLException {
		return false;
	}

	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		return false;
	}

	public boolean supportsOuterJoins() throws SQLException {
		return OUTER_JOINS;
	}

	public boolean supportsFullOuterJoins() throws SQLException {
		return FULL_OUTER_JOINS;
	}

	public boolean supportsLimitedOuterJoins() throws SQLException {
		return LIMITED_OUTER_JOINS;
	}

	public String getSchemaTerm() throws SQLException {
		return SCHEMA_TERM;
	}

	public String getProcedureTerm() throws SQLException {
		return PROCEDURE_TERM;
	}

	public String getCatalogTerm() throws SQLException {
		return CATALOG_TERM;
	}

	public boolean isCatalogAtStart() throws SQLException {
		return false;
	}

	public String getCatalogSeparator() throws SQLException {
		return null;
	}

	public boolean supportsSchemasInDataManipulation() throws SQLException {
		return false;
	}

	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		return false;
	}

	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		return false;
	}

	public boolean supportsPositionedDelete() throws SQLException {
		return false;
	}

	public boolean supportsPositionedUpdate() throws SQLException {
		return false;
	}

	public boolean supportsSelectForUpdate() throws SQLException {
		return false;
	}

	public boolean supportsStoredProcedures() throws SQLException {
		return false;
	}

	public boolean supportsSubqueriesInComparisons() throws SQLException {
		return false;
	}

	public boolean supportsSubqueriesInExists() throws SQLException {
		return false;
	}

	public boolean supportsSubqueriesInIns() throws SQLException {
		return false;
	}

	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		return false;
	}

	public boolean supportsCorrelatedSubqueries() throws SQLException {
		return false;
	}
	
	public boolean supportsUnion() throws SQLException {
		return UNION;
	}

	public boolean supportsUnionAll() throws SQLException {
		return UNION_ALL;
	}

	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getMaxBinaryLiteralLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxCharLiteralLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInGroupBy() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInIndex() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInOrderBy() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInSelect() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxColumnsInTable() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxConnections() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxCursorNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxIndexLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxSchemaNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxProcedureNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxCatalogNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxRowSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getMaxStatementLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxStatements() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxTableNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxTablesInSelect() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxUserNameLength() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDefaultTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean supportsTransactions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsTransactionIsolationLevel(int level)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsDataDefinitionAndDataManipulationTransactions()
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsDataManipulationTransactionsOnly()
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public ResultSet getProcedures(String catalog, String schemaPattern,
			String procedureNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getProcedureColumns(String catalog, String schemaPattern,
			String procedureNamePattern, String columnNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getTables(String catalog, String schemaPattern,
			String tableNamePattern, String[] types) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getSchemas() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getCatalogs() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getTableTypes() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getColumnPrivileges(String catalog, String schema,
			String table, String columnNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getTablePrivileges(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getBestRowIdentifier(String catalog, String schema,
			String table, int scope, boolean nullable) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getVersionColumns(String catalog, String schema,
			String table) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getPrimaryKeys(String catalog, String schema, String table)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getImportedKeys(String catalog, String schema, String table)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getExportedKeys(String catalog, String schema, String table)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getCrossReference(String parentCatalog,
			String parentSchema, String parentTable, String foreignCatalog,
			String foreignSchema, String foreignTable) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getTypeInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getIndexInfo(String catalog, String schema, String table,
			boolean unique, boolean approximate) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supportsResultSetType(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsResultSetConcurrency(int type, int concurrency)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ownUpdatesAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ownDeletesAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ownInsertsAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean othersUpdatesAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean othersDeletesAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean othersInsertsAreVisible(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updatesAreDetected(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deletesAreDetected(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean insertsAreDetected(int type) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsBatchUpdates() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public ResultSet getUDTs(String catalog, String schemaPattern,
			String typeNamePattern, int[] types) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supportsSavepoints() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsNamedParameters() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsMultipleOpenResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsGetGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public ResultSet getSuperTypes(String catalog, String schemaPattern,
			String typeNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getSuperTables(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getAttributes(String catalog, String schemaPattern,
			String typeNamePattern, String attributeNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supportsResultSetHoldability(int holdability)
			throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getJDBCMajorVersion() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getJDBCMinorVersion() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getSQLStateType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean locatorsUpdateCopy() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsStatementPooling() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public RowIdLifetime getRowIdLifetime() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getSchemas(String catalog, String schemaPattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public ResultSet getClientInfoProperties() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getFunctions(String catalog, String schemaPattern,
			String functionNamePattern) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getFunctionColumns(String catalog, String schemaPattern,
			String functionNamePattern, String columnNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getPseudoColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean generatedKeyAlwaysReturned() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

}
