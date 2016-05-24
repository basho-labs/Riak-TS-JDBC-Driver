package com.basho.riakts.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

public class RiakTSMetaData implements java.sql.DatabaseMetaData {
	
	private static String databaseProductName = "Riak TS (Time Series)";
	private static String databaseProductVersion = "1.3";
	private static String driverProductName = "Riak TS JDBC";
	private static String driverProductVersion = "0.1";
	private static int driverMajorVersion = 0;
	private static int driverMinorVersion = 1;
	private static String sqlKeywords = null;
	private static String numericFunctions = null;
	private static String stringFunctions = null;
	private static String systemFunctions = null;
	private static String timeDateFunctions = null;
	private static String searchStringEscape = null;
	private static String extraNameCharacters = null;
	private static String schemaTerm = "Schema";
	private static String procedureTerm = null;
	private static String catalogTerm = null;

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

	public String getURL() throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
		return databaseProductName;
	}

	public String getDatabaseProductVersion() throws SQLException {
		return databaseProductVersion;
	}

	public String getDriverName() throws SQLException {
		return driverProductName;
	}

	public String getDriverVersion() throws SQLException {
		return driverProductVersion;
	}

	public int getDriverMajorVersion() {
		return driverMajorVersion;
	}

	public int getDriverMinorVersion() {
		return driverMinorVersion;
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
		return sqlKeywords;
	}

	public String getNumericFunctions() throws SQLException {
		return numericFunctions;
	}

	public String getStringFunctions() throws SQLException {
		return stringFunctions;
	}

	public String getSystemFunctions() throws SQLException {
		return systemFunctions;
	}

	public String getTimeDateFunctions() throws SQLException {
		return timeDateFunctions;
	}

	public String getSearchStringEscape() throws SQLException {
		return searchStringEscape;
	}

	public String getExtraNameCharacters() throws SQLException {
		return extraNameCharacters;
	}

	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		return false;
	}

	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		return false;
	}

	public boolean supportsColumnAliasing() throws SQLException {
		// TODO Auto-generated method stub
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
		return schemaTerm;
	}

	public String getProcedureTerm() throws SQLException {
		return procedureTerm;
	}

	public String getCatalogTerm() throws SQLException {
		// TODO Auto-generated method stub
		return catalogTerm;
	}

	public boolean isCatalogAtStart() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public String getCatalogSeparator() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supportsSchemasInDataManipulation() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsPositionedDelete() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsPositionedUpdate() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSelectForUpdate() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsStoredProcedures() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSubqueriesInComparisons() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSubqueriesInExists() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSubqueriesInIns() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsCorrelatedSubqueries() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsUnion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean supportsUnionAll() throws SQLException {
		// TODO Auto-generated method stub
		return false;
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

	public int getDatabaseMajorVersion() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDatabaseMinorVersion() throws SQLException {
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

}
