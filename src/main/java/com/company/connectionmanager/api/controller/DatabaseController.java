package com.company.connectionmanager.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.connectionmanager.api.openapi.controller.DatabaseControllerOpenApi;
import com.company.connectionmanager.core.web.WebConfig;
import com.company.connectionmanager.domain.model.Column;
import com.company.connectionmanager.domain.model.Schema;
import com.company.connectionmanager.domain.model.Table;
import com.company.connectionmanager.domain.service.DatabaseService;
import com.fasterxml.jackson.databind.JsonNode;


@RestController
@RequestMapping(path = "/databases", produces = MediaType.APPLICATION_JSON_VALUE)
public class DatabaseController implements DatabaseControllerOpenApi{

	@Autowired
	private DatabaseService databaseService;

	@GetMapping("/{connectorId}/schemas")
	public ResponseEntity<List<Schema>> allSchemas(@PathVariable Long connectorId) {

		List<Schema> allSchemas = databaseService.getSchemas(connectorId);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(allSchemas);

	}

	@GetMapping("/{connectorId}/{schemaName}/detail")
	public ResponseEntity<List<Table>> getAllTablesInASchema(@PathVariable Long connectorId, 
			@PathVariable String schemaName) {

		List<Table> tables = databaseService.getTables(connectorId, schemaName);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(tables);

	}
	
	@GetMapping("/{connectorId}/{schemaName}")
	public ResponseEntity<Schema> getSchema(@PathVariable Long connectorId, 
			@PathVariable String schemaName) {

		Schema schema = databaseService.findOrFailSchema(connectorId, schemaName);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(schema);

	}
	
	@GetMapping("/{connectorId}/{schemaName}/{tableName}/columns")
	public ResponseEntity<List<Column>> getAllColumnsInTableSchema(@PathVariable Long connectorId, 
			@PathVariable String schemaName, @PathVariable String tableName) {

		List<Column> columns = databaseService.getColumns(connectorId, schemaName, tableName);

		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(columns);
	}
	
	@GetMapping("/{connectorId}/{schemaName}/{tableName}/data")
	public ResponseEntity<List<JsonNode>> allDataFromTable(@PathVariable Long connectorId, 
			@PathVariable String schemaName, @PathVariable String tableName) {

		List<JsonNode> allData = databaseService.getAllDataFromTable(connectorId, schemaName, tableName);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(allData);
	}
	
	@GetMapping("/{connectorId}/{schemaName}/{tableName}/statistic/columns")
	public ResponseEntity<List<JsonNode>> getColumnStatistics(@PathVariable Long connectorId, 
			@PathVariable String schemaName, @PathVariable String tableName) {
		
		List<JsonNode> allStatistics =  databaseService.getTableColumnsStatistics(connectorId, schemaName, tableName);
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(allStatistics);
	}

	@GetMapping("/{connectorId}")
	public ResponseEntity<List<Schema>> allSchemasWithTables(@PathVariable Long connectorId) {

		List<Schema> allSchemasWithTables = databaseService.getSchemasWithTables(connectorId);

		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(allSchemasWithTables);
	}
	
	
}
