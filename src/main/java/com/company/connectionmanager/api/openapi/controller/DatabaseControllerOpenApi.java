package com.company.connectionmanager.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.company.connectionmanager.domain.model.Column;
import com.company.connectionmanager.domain.model.Schema;
import com.company.connectionmanager.domain.model.Table;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Database")
public interface DatabaseControllerOpenApi {

	@ApiOperation("Lists all schemes based on the connector's ID")
	public ResponseEntity<List<Schema>> allSchemas(
			@ApiParam(value = "A valid connector ID", example = "1")
			Long connectorId);

	@ApiOperation("Lists all details for a given Schema based on the ID of a connector and the name of the Schema")
	public ResponseEntity<List<Table>> getAllTablesInASchema(
			@ApiParam(value = "A valid connector ID", example = "1")
			Long connectorId, 
			
			@ApiParam(value = "A valid Schema name ID")
			String schemaName);
	
	@ApiOperation("Returns a single representation of Schema")
	public ResponseEntity<Schema> getSchema(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId,
			
			@ApiParam(value = "A valid Schema name ID")
			@PathVariable String schemaName);
	
	
	@ApiOperation("Lists all columns in a Schema table based on a connector ID, Schema name and table name")
	public ResponseEntity<List<Column>> getAllColumnsInTableSchema(
			@ApiParam(value = "A valid connector ID", example = "1")
			Long connectorId, 
			
			@ApiParam(value = "A valid schema name")
			String schemaName, 
			
			@ApiParam(value = "A valid table name")
			String tableName);
	
	@ApiOperation("Lists all data from a Schema table based on a connector ID, Schema name and table name")
	public ResponseEntity<List<JsonNode>> allDataFromTable(
			@ApiParam(value = "A valid connector ID", example = "1")
			Long connectorId, 
			
			@ApiParam(value = "A valid schema name")
			String schemaName, 
			
			@ApiParam(value = "A valid table name")
			String tableName);
	
	@ApiOperation("Get statistics for the columns of a table based on the connector ID, Schema name and table name")
	public ResponseEntity<List<JsonNode>> getColumnStatistics(
			@ApiParam(value = "A valid connector ID", example = "1")
			Long connectorId, 
			
			@ApiParam(value = "A valid schema name")
			String schemaName, 
			
			@ApiParam(value = "A valid table name")
			String tableName);

	@ApiOperation("Lists all Schemas and their respective tables")
	public ResponseEntity<List<Schema>> allSchemasWithTables(
			@ApiParam(value = "A valid connector ID", example = "1") 
			Long connectorId);
	
}
