package com.company.connectionmanager.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.connectionmanager.core.web.WebConfig;
import com.company.connectionmanager.domain.model.Column;
import com.company.connectionmanager.domain.model.Schema;
import com.company.connectionmanager.domain.model.Table;
import com.company.connectionmanager.domain.service.DatabaseService;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Database")
@RestController
@RequestMapping(value = "/databases")
public class DatabaseController {

	@Autowired
	private DatabaseService databaseService;

	@ApiOperation("Lists all schemes based on the connector's ID")
	@GetMapping("/{connectorId}/schemas")
	public ResponseEntity<List<Schema>> allSchemas(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId) {

		List<Schema> allSchemas = databaseService.getSchemas(connectorId);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(allSchemas);

	}

	@ApiOperation("Lists all tables for a given Schema based on the ID of a connector and the name of the Schema")
	@GetMapping("/{connectorId}/{schemaName}")
	public ResponseEntity<List<Table>> getAllTablesInASchema(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId, 
			@ApiParam(value = "A valid Schema name ID")
			@PathVariable String schemaName) {

		List<Table> tables = databaseService.getTables(connectorId, schemaName);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(tables);

	}
	
	@ApiOperation("Lists all columns in a Schema table based on a connector ID, Schema name and table name")
	@GetMapping("/{connectorId}/{schemaName}/{tableName}/columns")
	public ResponseEntity<List<Column>> getAllColumnsInTableSchema(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId, 
			@ApiParam(value = "A valid schema name")
			@PathVariable String schemaName, 
			@ApiParam(value = "A valid table name")
			@PathVariable String tableName) {

		List<Column> columns = databaseService.getColumns(connectorId, schemaName, tableName);

		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(columns);
	}
	@ApiOperation("Lists all data from a Schema table based on a connector ID, Schema name and table name")
	@GetMapping("/{connectorId}/{schemaName}/{tableName}/data")
	public ResponseEntity<List<JsonNode>> allDataFromTable(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId, 
			@ApiParam(value = "A valid schema name")
			@PathVariable String schemaName, 
			@ApiParam(value = "A valid table name")
			@PathVariable String tableName) {

		List<JsonNode> allData = databaseService.getAllDataFromTable(connectorId, schemaName, tableName);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(allData);
	}
	
	@ApiOperation("Get statistics for the columns of a table based on the connector ID, Schema name and table name")
	@GetMapping("/{connectorId}/{schemaName}/{tableName}/columns/statistic")
	public ResponseEntity<List<JsonNode>> getColumnStatistics(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId, 
			@ApiParam(value = "A valid schema name")
			@PathVariable String schemaName, 
			@ApiParam(value = "A valid table name")
			@PathVariable String tableName) {

		List<JsonNode> allStatistics =  databaseService.getTableColumnsStatistics(connectorId, schemaName, tableName);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(allStatistics);
	}

	@ApiOperation("Lists all Schemas and their respective tables")
	@GetMapping("/{connectorId}")
	public ResponseEntity<List<Schema>> allSchemasWithTables(
			@ApiParam(value = "A valid connector ID", example = "1") 
			@PathVariable Long connectorId) {

		List<Schema> allSchemasWithTables = databaseService.getSchemasWithTables(connectorId);

		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(allSchemasWithTables);
	}
	
}
