package com.company.connectionmanager.domain.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.connectionmanager.domain.exception.SchemaNotFoundException;
import com.company.connectionmanager.domain.exception.TableNotFoundException;
import com.company.connectionmanager.domain.model.Column;
import com.company.connectionmanager.domain.model.Connector;
import com.company.connectionmanager.domain.model.Schema;
import com.company.connectionmanager.domain.model.Table;
import com.company.connectionmanager.domain.repository.impl.DatabaseRespositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DatabaseService {

	@Autowired
	private DatabaseRespositoryImpl databaseRepositoryImpl;

	@Autowired
	private ConnectorService connectorService;

	/**
	 * Method that returns the list of all tables with their respective columns and
	 * types for a given Schema
	 * 
	 * @param connectorId
	 * @param schemaName
	 * @return List
	 *         <Table>
	 */
	public List<Table> getTables(Long connectorId, String schemaName) {

		Schema schema = findOrFailSchema(connectorId, schemaName);

		DatabaseMetaData metaData = databaseRepositoryImpl.getDatabaseMetaData(connectorId);

		List<Table> tables = new ArrayList<>();

		try {
			ResultSet rsTables = metaData.getTables(schema.getSchemaName(), null, "%", null);
			
			
			while (rsTables.next()) {
				String catalog = rsTables.getString("TABLE_CAT");
		        String schem = rsTables.getString("TABLE_SCHEM");
		        String tableName = rsTables.getString("TABLE_NAME");
		        String tableType = rsTables.getString("TABLE_TYPE");
		        
		        String primaryKey = null;
		        try (ResultSet primaryKeys = metaData.getPrimaryKeys(catalog, schem, tableName)) {
		            while (primaryKeys.next()) {
		            	primaryKey = primaryKeys.getString("COLUMN_NAME");
		            }
		        }
				
				ResultSet rsColumns = metaData.getColumns(null, null, tableName, null);
				
				rsColumns.last();
				int numberOfRows = rsColumns.getRow();
				rsColumns.beforeFirst();
				
				
				List<Column> columns = new ArrayList<>();
				while (rsColumns.next()) {
					columns.add(Column.builder().name(rsColumns.getString(4)).type(rsColumns.getString(6)).build());
				}
				rsColumns.close();
				
				
				Table tableBuilder = Table
					.builder()
					.name(tableName)
					.primaryKey(primaryKey)
					.tableType(tableType)
					.numberOfRows(numberOfRows)
					.columns(columns)
					.build();
								
				tables.add(tableBuilder);
			}
			rsTables.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}
	

	/**
	 * Method that returns a list of all existing Schemas in a given database
	 * 
	 * @param connectorId
	 * @return List<Schema>
	 */
	public List<Schema> getSchemas(Long connectorId) {

		Connector connector = findOrFailConnector(connectorId);

		List<Schema> schemas = new ArrayList<>();

		try {
			DatabaseMetaData metaData = databaseRepositoryImpl.getDatabaseMetaData(connector.getId());

			ResultSet catalogs = metaData.getCatalogs();

			while (catalogs.next()) {
				schemas.add(Schema.builder().schemaName(catalogs.getString(1)).build());
			}
			catalogs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schemas;
	}

	/**
	 * Method that returns a list of all existing Schemas in a given database and
	 * their respective tables
	 * 
	 * @param connectorId
	 * @return List<Schema>
	 */
	public List<Schema> getSchemasWithTables(Long connectorId) {

		List<Schema> schemaList = getSchemas(connectorId);
		
		schemaList.forEach((schema) -> {
			List<Table> tables = getTables(connectorId, schema.getSchemaName());
			schema.setTables(tables);
			schema.setNumberOfTables(tables.size());
		});
		

		return schemaList;
	}

	/**
	 * Method that returns a list of data from a given table in the database
	 * 
	 * @param connectorId
	 * @param schemaName
	 * @param tableName
	 * @return List<JsonNode>
	 */
	public List<JsonNode> getAllDataFromTable(Long connectorId, String schemaName, String tableName) {

		Connection connection = databaseRepositoryImpl.getConnection(connectorId);

		Table table = findOrFailTable(connectorId, schemaName, tableName);

		return createJsonNodeList(connection, table.getName());

	}

	/**
	 * Method lists the statistics for each column based on its data type
	 * 
	 * @param connectorId
	 * @param schemaName
	 * @param tableName
	 * @return List<JsonNode>
	 */
	public List<JsonNode> getTableColumnsStatistics(Long connectorId, String schemaName, String tableName) {

		Connection connection = databaseRepositoryImpl.getConnection(connectorId);

		List<Column> columns = getColumns(connectorId, schemaName, tableName);

		List<JsonNode> resultJsonNodes = new ArrayList<>();

		try {
			Statement stmt = connection.createStatement();

			for (int i = 0; i < columns.size(); i++) {

				Column column = columns.get(i);

				if (isColumnNumeric(column.getType())) {

					String queryFormat = "SELECT \n" + "   '%s'as columnName, \n" + "   '%s'as columnType, \n"
							+ "	   MIN(%s) as min, \n" + "    AVG(%s) as avg,\n"
							+ "    IF(count(*) %s 2 = 1, CAST(SUBSTRING_INDEX(SUBSTRING_INDEX( GROUP_CONCAT(%s ORDER BY %s SEPARATOR ','), ',', 50/100 * COUNT(*)), ',', -1) AS DECIMAL), ROUND((CAST(SUBSTRING_INDEX(SUBSTRING_INDEX( GROUP_CONCAT(%s ORDER BY %s SEPARATOR ','), ',', 50/100 * COUNT(*) + 1), ',', -1) AS DECIMAL) + CAST(SUBSTRING_INDEX(SUBSTRING_INDEX( GROUP_CONCAT(%s ORDER BY %s SEPARATOR ','), ',', 50/100 * COUNT(*)), ',', -1) AS DECIMAL)) / 2)) as median, \n"
							+ "    MAX(%s) as max \n" + "FROM %s";

					ResultSet result = stmt.executeQuery(
							String.format(queryFormat, column.getName(), column.getType(), column.getName(),
									column.getName(), "%", column.getName(), column.getName(), column.getName(),
									column.getName(), column.getName(), column.getName(), column.getName(), tableName));

					List<JsonNode> numericJsonNodeList = resultSetToListOfJsonNode(result);

					resultJsonNodes.addAll(numericJsonNodeList);

				} else if (column.getType().equalsIgnoreCase("DATETIME")) {

					String queryFormat = "SELECT \n" + "   '%s'as columnName, \n" + "   '%s'as columnType, \n"
							+ "	MIN(%s) as min, \n" + "   MAX(%s) as max \n" + "FROM %s";

					ResultSet result = stmt.executeQuery(String.format(queryFormat, column.getName(), column.getType(),
							column.getName(), column.getName(), tableName));

					List<JsonNode> numericJsonNodeList = resultSetToListOfJsonNode(result);

					resultJsonNodes.addAll(numericJsonNodeList);

				} else {

					String queryFormat = "SELECT \n" + "   '%s'as columnName, \n" + "   '%s'as columnType, \n"
							+ "	MIN(LENGTH(%s)) as minLength, \n" + "    AVG(LENGTH(%s)) as avgLength,\n"
							+ "    IF(count(*) %s 2 = 1, CAST(SUBSTRING_INDEX(SUBSTRING_INDEX( GROUP_CONCAT(LENGTH(%s) ORDER BY LENGTH(%s) SEPARATOR ','), ',', 50/100 * COUNT(*)), ',', -1) AS DECIMAL), ROUND((CAST(SUBSTRING_INDEX(SUBSTRING_INDEX( GROUP_CONCAT(LENGTH(%s) ORDER BY LENGTH(%s) SEPARATOR ','), ',', 50/100 * COUNT(*) + 1), ',', -1) AS DECIMAL) + CAST(SUBSTRING_INDEX(SUBSTRING_INDEX( GROUP_CONCAT(LENGTH(%s) ORDER BY LENGTH(%s) SEPARATOR ','), ',', 50/100 * COUNT(*)), ',', -1) AS DECIMAL)) / 2)) as medianLength, \n"
							+ "    MAX(LENGTH(%s)) as maxLength \n" + "FROM %s";

					ResultSet result = stmt.executeQuery(
							String.format(queryFormat, column.getName(), column.getType(), column.getName(),
									column.getName(), "%", column.getName(), column.getName(), column.getName(),
									column.getName(), column.getName(), column.getName(), column.getName(), tableName));

					List<JsonNode> noNumericJsonNodeList = resultSetToListOfJsonNode(result);

					resultJsonNodes.addAll(noNumericJsonNodeList);
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultJsonNodes;

	}

	/**
	 * Method that retrieves the data from the respective table in the database and
	 * transforms it into a JsonNode list
	 * 
	 * @param connection
	 * @param tableName
	 * @return List<JsonNode>
	 */
	private List<JsonNode> createJsonNodeList(Connection connection, String tableName) {

		List<JsonNode> resultJsonNodes = new ArrayList<>();

		try {
			Statement stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(String.format("SELECT * FROM %s", tableName));

			resultJsonNodes = resultSetToListOfJsonNode(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultJsonNodes;
	}

	/**
	 * Method that transforms a ResultSet into a JsonNode list
	 * 
	 * @param result
	 * @return List<JsonNode>
	 */
	private List<JsonNode> resultSetToListOfJsonNode(ResultSet result) {
		List<JsonNode> resultJsonNodes = new ArrayList<>();
		if (result != null) {
			try {
				ResultSetMetaData metadata = result.getMetaData();
				int numberOfColumns = metadata.getColumnCount();
				while (result.next()) {
					String str = "{";
					for (int i = 1; i <= numberOfColumns; i++) {
						String columnName = metadata.getColumnName(i);
						String columnValue = result.getString(i);
						String columnType = metadata.getColumnTypeName(i);

						if (i < numberOfColumns) {
							str += formatValueJson(columnName, columnValue, columnType) + ", ";
						} else {
							str += formatValueJson(columnName, columnValue, columnType);
						}
					}
					str += "}";

					JsonNode json = stringJsonToJSONObject(str);

					resultJsonNodes.add(json);

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultJsonNodes;
	}

	/**
	 * Method that returns the list of columns for a given database table
	 * 
	 * @param connectorId
	 * @param schemaName
	 * @param tableName
	 * @return List<Column>
	 */
	public List<Column> getColumns(Long connectorId, String schemaName, String tableName) {

		Table table = findOrFailTable(connectorId, schemaName, tableName);

		DatabaseMetaData metaData = databaseRepositoryImpl.getDatabaseMetaData(connectorId);

		List<Column> columns = new ArrayList<>();

		ResultSet rs;
		try {
			rs = metaData.getColumns(null, null, table.getName(), null);
			while (rs.next()) {
				columns.add(Column.builder().name(rs.getString(4)).type(rs.getString(6)).build());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return columns;
	}

	/**
	 * Method that validates whether a given Schema exists or not
	 * 
	 * @param connectorId
	 * @param schemaName
	 * @return Schema
	 */
	public Schema findOrFailSchema(Long connectorId, String schemaName) {
		List<Schema> schemas = getSchemas(connectorId);
		return schemas.stream().filter(s -> s.getSchemaName().equals(schemaName)).findAny()
				.orElseThrow(() -> new SchemaNotFoundException(schemaName));
	}

	/**
	 * Method that validates whether a given Table exists or not
	 * 
	 * @param connectorId
	 * @param schemaName
	 * @param tableName
	 * @return Table
	 */
	public Table findOrFailTable(Long connectorId, String schemaName, String tableName) {
		Schema schema = findOrFailSchema(connectorId, schemaName);
		List<Table> tables = getTables(connectorId, schema.getSchemaName());
		return tables.stream().filter(t -> t.getName().equals(tableName)).findAny()
				.orElseThrow(() -> new TableNotFoundException(tableName));
	}

	/**
	 * Method that validates a Connector is valid
	 * 
	 * @param connectorId
	 * @return Connector
	 */
	public Connector findOrFailConnector(Long connectorId) {
		return connectorService.findOrFail(connectorId);
	}

	/**
	 * Method that returns whether a given column is numeric or not
	 * 
	 * @param columnType
	 * @return boolean
	 */
	private boolean isColumnNumeric(String columnType) {
		return (columnType.equalsIgnoreCase("BIGINT") || columnType.equalsIgnoreCase("INT")
				|| columnType.equalsIgnoreCase("DOUBLE") || columnType.equalsIgnoreCase("FLOAT")
				|| columnType.equalsIgnoreCase("YEAR") || columnType.equalsIgnoreCase("MEDIUMINT")
				|| columnType.equalsIgnoreCase("REAL") || columnType.equalsIgnoreCase("SMALLINT")
				|| columnType.equalsIgnoreCase("TINYINT") || columnType.equalsIgnoreCase("DECIMAL"));
	}

	/**
	 * Method that assembles the body of the names and values of the JSon attributes
	 * that will be returned as String
	 * 
	 * @param columnName
	 * @param columnValue
	 * @param columnType
	 * @return String
	 */
	private String formatValueJson(String columnName, String columnValue, String columnType) {
		String result = "\"" + columnName + "\": ";

		if (columnValue == null || columnValue.isBlank() || columnValue.isEmpty()) {
			result += columnValue;
		} else {
			if (isColumnNumeric(columnType)) {
				result += columnValue;
			} else {
				result += "\"" + columnValue + "\"";
			}
		}

		return result;
	}

	/**
	 * Method that transforms from StringJson to JsonNode
	 * 
	 * @param stringJson
	 * @return JsonNode
	 */
	private JsonNode stringJsonToJSONObject(String stringJson) {
		ObjectMapper jacksonObj = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = jacksonObj.readTree(stringJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonNode;
	}

}
