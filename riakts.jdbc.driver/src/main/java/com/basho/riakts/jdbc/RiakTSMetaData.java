package com.basho.riakts.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

public class RiakTSMetaData implements java.sql.DatabaseMetaData {
	
	private static String DATABASE_PRODUCT_NAME = "Riak TS (Time Series)";
	private static String DATABASE_PRODUCT_VERSION = "1.3";
	private static int DB_MAJOR_VERSION = 1;
	private static int DB_MINOR_VERSION = 3;
	private static String DRIVER_PRODUCT_NAME = "Riak TS JDBC";
	private static String DRIVER_PRODUCT_VERSION = "0.1";
	private static int DRIVER_MAJOR_VERSION = 0;
	private static int DRIVER_MINOR_VERSION = 1;
	private static String SQL_KEYWORDS = null;
	private static String NUMERIC_FUNCTIONS = null;
	private static String STRING_FUNCTIONS = null;
	private static String SYSTEM_FUNCTIONS = null;
	private static String TIME_DATE_FUNCTIONS = null;
	private static String SEARCH_STRING_ESCAPE = null;
	private static String EXTRA_NAME_CHARACTERS = null;
	private static String SCHEMA_TERM = "Schema";
	private static String PROCEDURE_TERM = null;
	private static String CATALOG_TERM = null;
	

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getURL() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getClientInfoProperties() throws SQLException {
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
	
	

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public boolean allProceduresAreCallable() throws SQLException {
		return false;
	}

	public boolean allTablesAreSelectable() throws SQLException {
		return false;
	}

	public String getUserName() throws SQLException {
		return null;
	}

	public boolean isReadOnly() throws SQLException {
		return false;
	}

	public boolean nullsAreSortedHigh() throws SQLException {
		return false;
	}

	public boolean nullsAreSortedLow() throws SQLException {
		return false;
	}

	public boolean nullsAreSortedAtStart() throws SQLException {
		return false;
	}

	public boolean nullsAreSortedAtEnd() throws SQLException {
		return false;
	}

	public String getDatabaseProductName() throws SQLException {
		return DATABASE_PRODUCT_NAME;
	}

	public String getDatabaseProductVersion() throws SQLException {
		return DATABASE_PRODUCT_VERSION;
	}

	public String getDriverName() throws SQLException {
		return DRIVER_PRODUCT_NAME;
	}

	public String getDriverVersion() throws SQLException {
		return DRIVER_PRODUCT_VERSION;
	}

	public int getDriverMajorVersion() {
		return DRIVER_MAJOR_VERSION;
	}

	public int getDriverMinorVersion() {
		return DRIVER_MINOR_VERSION;
	}

	public boolean usesLocalFiles() throws SQLException {
		return false;
	}

	public boolean usesLocalFilePerTable() throws SQLException {
		return false;
	}

	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		return true;
	}

	public boolean storesUpperCaseIdentifiers() throws SQLException {
		return false;
	}

	public boolean storesLowerCaseIdentifiers() throws SQLException {
		return false;
	}

	public boolean storesMixedCaseIdentifiers() throws SQLException {
		return true;
	}

	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		return true;
	}

	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		return false;
	}

	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		return true;
	}

	public String getIdentifierQuoteString() throws SQLException {
		return null;
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
		return false;
	}

	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		return false;
	}

	public boolean supportsColumnAliasing() throws SQLException {
		return false;
	}

	public boolean nullPlusNonNullIsNull() throws SQLException {
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
		return false;
	}

	public boolean supportsOrderByUnrelated() throws SQLException {
		return false;
	}

	public boolean supportsGroupBy() throws SQLException {
		return false;
	}

	public boolean supportsGroupByUnrelated() throws SQLException {
		return false;
	}

	public boolean supportsGroupByBeyondSelect() throws SQLException {
		return false;
	}

	public boolean supportsLikeEscapeClause() throws SQLException {
		return false;
	}

	public boolean supportsMultipleResultSets() throws SQLException {
		return false;
	}

	public boolean supportsMultipleTransactions() throws SQLException {
		return false;
	}

	public boolean supportsNonNullableColumns() throws SQLException {
		return true;
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
		return false;
	}

	public boolean supportsFullOuterJoins() throws SQLException {
		return false;
	}

	public boolean supportsLimitedOuterJoins() throws SQLException {
		return false;
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
		return false;
	}

	public boolean supportsUnionAll() throws SQLException {
		return false;
	}

	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		return false;
	}

	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		return false;
	}

	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		return false;
	}

	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		return false;
	}

	public int getMaxBinaryLiteralLength() throws SQLException {
		return 0;
	}

	public int getMaxCharLiteralLength() throws SQLException {
		return 0;
	}

	public int getMaxColumnNameLength() throws SQLException {
		return 0;
	}

	public int getMaxColumnsInGroupBy() throws SQLException {
		return 0;
	}

	public int getMaxColumnsInIndex() throws SQLException {
		return 0;
	}

	public int getMaxColumnsInOrderBy() throws SQLException {
		return 0;
	}

	public int getMaxColumnsInSelect() throws SQLException {
		return 0;
	}

	public int getMaxColumnsInTable() throws SQLException {
		return 0;
	}

	public int getMaxConnections() throws SQLException {
		return 0;
	}

	public int getMaxCursorNameLength() throws SQLException {
		return 0;
	}

	public int getMaxIndexLength() throws SQLException {
		return 0;
	}

	public int getMaxSchemaNameLength() throws SQLException {
		return 0;
	}

	public int getMaxProcedureNameLength() throws SQLException {
		return 0;
	}

	public int getMaxCatalogNameLength() throws SQLException {
		return 0;
	}

	public int getMaxRowSize() throws SQLException {
		return 0;
	}

	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		return false;
	}

	public int getMaxStatementLength() throws SQLException {
		return 0;
	}

	public int getMaxStatements() throws SQLException {
		return 0;
	}

	public int getMaxTableNameLength() throws SQLException {
		return 0;
	}

	public int getMaxTablesInSelect() throws SQLException {
		return 1;
	}

	public int getMaxUserNameLength() throws SQLException {
		return 0;
	}

	public int getDefaultTransactionIsolation() throws SQLException {
		return 0;
	}

	public boolean supportsTransactions() throws SQLException {
		return false;
	}

	public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
		return false;
	}

	public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
		return false;
	}

	public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
		return false;
	}

	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		return false;
	}

	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		return false;
	}

	public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getSchemas() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getCatalogs() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getTableTypes() throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getColumnPrivileges(String catalog, String schema,
			String table, String columnNamePattern) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getTablePrivileges(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getBestRowIdentifier(String catalog, String schema,
			String table, int scope, boolean nullable) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getVersionColumns(String catalog, String schema,
			String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getCrossReference(String parentCatalog,
			String parentSchema, String parentTable, String foreignCatalog,
			String foreignSchema, String foreignTable) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean supportsResultSetType(int type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean supportsResultSetConcurrency(int type, int concurrency)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean ownUpdatesAreVisible(int type) throws SQLException {
		return false;
	}

	public boolean ownDeletesAreVisible(int type) throws SQLException {
		return false;
	}

	public boolean ownInsertsAreVisible(int type) throws SQLException {
		return false;
	}

	public boolean othersUpdatesAreVisible(int type) throws SQLException {
		return false;
	}

	public boolean othersDeletesAreVisible(int type) throws SQLException {
		return false;
	}

	public boolean othersInsertsAreVisible(int type) throws SQLException {
		return false;
	}

	public boolean updatesAreDetected(int type) throws SQLException {
		return false;
	}

	public boolean deletesAreDetected(int type) throws SQLException {
		return false;
	}

	public boolean insertsAreDetected(int type) throws SQLException {
		return false;
	}

	public boolean supportsBatchUpdates() throws SQLException {
		return true;
	}

	public ResultSet getUDTs(String catalog, String schemaPattern,
			String typeNamePattern, int[] types) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean supportsSavepoints() throws SQLException {
		return false;
	}

	public boolean supportsNamedParameters() throws SQLException {
		return false;
	}

	public boolean supportsMultipleOpenResults() throws SQLException {
		return false;
	}

	public boolean supportsGetGeneratedKeys() throws SQLException {
		return false;
	}

	public ResultSet getSuperTypes(String catalog, String schemaPattern,
			String typeNamePattern) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getSuperTables(String catalog, String schemaPattern,
			String tableNamePattern) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getAttributes(String catalog, String schemaPattern,
			String typeNamePattern, String attributeNamePattern)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean supportsResultSetHoldability(int holdability)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getResultSetHoldability() throws SQLException {
		return 0;
	}

	public int getDatabaseMajorVersion() throws SQLException {
		return DB_MAJOR_VERSION;
	}

	public int getDatabaseMinorVersion() throws SQLException {
		return DB_MINOR_VERSION;
	}

	public int getJDBCMajorVersion() throws SQLException {
		return 0;
	}

	public int getJDBCMinorVersion() throws SQLException {
		return 0;
	}

	public int getSQLStateType() throws SQLException {
		return 0;
	}

	public boolean locatorsUpdateCopy() throws SQLException {
		return false;
	}

	public boolean supportsStatementPooling() throws SQLException {
		return false;
	}

	public RowIdLifetime getRowIdLifetime() throws SQLException {
		return null;
	}

	public ResultSet getSchemas(String catalog, String schemaPattern)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		return false;
	}

	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		return false;
	}


	public ResultSet getFunctions(String catalog, String schemaPattern,
			String functionNamePattern) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getFunctionColumns(String catalog, String schemaPattern,
			String functionNamePattern, String columnNamePattern)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public ResultSet getPseudoColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean generatedKeyAlwaysReturned() throws SQLException {
		return false;
	}

}
