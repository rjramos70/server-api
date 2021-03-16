package com.company.connectionmanager.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import com.company.connectionmanager.api.exceptionhandler.Problem;
import com.company.connectionmanager.api.model.ConnectorModel;
import com.company.connectionmanager.api.model.input.ConnectorInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Connector")
public interface ConnectorControllerOpenApi {

	@ApiOperation("List all connectors registered in our database")
	public ResponseEntity<List<ConnectorModel>> findAll(ServletWebRequest request);
	
	@ApiOperation("Get a connector based on an ID")
	public ResponseEntity<ConnectorModel> findById(
			@ApiParam(value = "A valid connector ID", example = "1")
			Long connectorId);
	
	@ApiOperation("Register a new connector")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Connector createad")
	})
	public ConnectorModel insert(
			@ApiParam(name = "body", value = "A representation of a new Connector")
			ConnectorInput connectorInput);
	
	@ApiOperation("Update a connector based on an ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Connector updated"),
		@ApiResponse(code = 404, message = "Connector not found", response = Problem.class)
	})
	public ConnectorModel update(
			@ApiParam(value = "A valid connector ID", example = "1")
			Long connectorId, 
			
			@ApiParam(name = "body", value = "A representation of a new Connector with the new data")
			ConnectorInput connectorInput);
	
	@ApiOperation("Remove a connector based on an valid ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Connector deleted"),
		@ApiResponse(code = 404, message = "Connector not found", response = Problem.class)
	})
	public void delete(
			@ApiParam(value = "A valid connector ID", example = "1")
			Long connectorId);
	
	
}
