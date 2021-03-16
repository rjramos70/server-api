package com.company.connectionmanager.api.openapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.company.connectionmanager.api.exceptionhandler.Problem;
import com.company.connectionmanager.api.model.DatabaseTypeModel;
import com.company.connectionmanager.api.model.input.DatabaseTypeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Database Type")
public interface DatabaseTypeControllerOpenApi {

	@ApiOperation("Lists all DatabaseTypes")
	@GetMapping
	public ResponseEntity<List<DatabaseTypeModel>> findAll();
	
	@ApiOperation("Insert a new DatabaseType")
	@ApiResponses({
		@ApiResponse(code = 201, message = "DatabaseType createad")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DatabaseTypeModel insert(
			@ApiParam("A representation of a new DatabaseType with the new data")
			DatabaseTypeInput databaseTypeInput) ;
	
	@ApiOperation("Update a DatabaseType with the new data")
	@ApiResponses({
		@ApiResponse(code = 200, message = "DatabaseType updated"),
		@ApiResponse(code = 404, message = "DatabaseType not found", response = Problem.class)
	})
	@PutMapping("/{databaseTypeId}")
	public DatabaseTypeModel update(
			@ApiParam(value = "A valid DatabaseType ID", example = "1")
			Long databaseTypeId, 
			
			@ApiParam(name = "body", value = "A representation of a new DatabaseType with the new data")
			DatabaseTypeInput databaseTypeInput);
	
	@ApiOperation("Get a DatabaseType based on an ID")
	@GetMapping("/{databaseTypeId}")
	public ResponseEntity<DatabaseTypeModel> findById(
			@ApiParam(value = "A valid DatabaseType ID", example = "1")
			Long databaseTypeId);
	
	@ApiOperation("Delete a DatabaseType based on an valid ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "DatabaseType deleted"),
		@ApiResponse(code = 404, message = "DatabaseType not found", response = Problem.class)
	})
	@DeleteMapping("/{databaseTypeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(
			@ApiParam(value = "A valid databaseType ID", example = "1")
			Long databaseTypeId);
	
}
