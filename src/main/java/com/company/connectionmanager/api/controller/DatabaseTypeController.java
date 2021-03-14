package com.company.connectionmanager.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.connectionmanager.api.assembler.DatabaseTypeInputDisassembler;
import com.company.connectionmanager.api.assembler.DatabaseTypeModelAssembler;
import com.company.connectionmanager.api.model.DatabaseTypeModel;
import com.company.connectionmanager.api.model.input.DatabaseTypeInput;
import com.company.connectionmanager.core.web.WebConfig;
import com.company.connectionmanager.domain.exception.BusinessException;
import com.company.connectionmanager.domain.exception.DatabaseTypeNotFoundException;
import com.company.connectionmanager.domain.model.DatabaseType;
import com.company.connectionmanager.domain.service.DatabaseTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Database Type")
@RestController
@RequestMapping(value = "/databasetypes")
public class DatabaseTypeController {
	
	@Autowired
	private DatabaseTypeService databaseTypeService;
	
	@Autowired
	private DatabaseTypeModelAssembler databaseTypeModelAssembler;
	
	@Autowired
	private DatabaseTypeInputDisassembler databaseTypeInputDisassembler;
	
	
	@ApiOperation("Lists all DatabseTypes")
	@GetMapping
	public ResponseEntity<List<DatabaseTypeModel>> findAll(){
		
		List<DatabaseTypeModel> databaseTypeCollectionModel = databaseTypeModelAssembler.toCollectionModel(databaseTypeService.findAll());
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(databaseTypeCollectionModel);
	}
	
	@ApiOperation("Insert a new DatabaseType")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DatabaseTypeModel insert(
			@ApiParam("A representation of a new DatabaseType with the new data")
			@RequestBody @Valid DatabaseTypeInput databaseTypeInput) {
		try {
			DatabaseType databaseType = databaseTypeInputDisassembler.toDomainObjet(databaseTypeInput);
			
			return databaseTypeModelAssembler.toModel(databaseTypeService.save(databaseType));
		} catch (DatabaseTypeNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@ApiOperation("Update a DatabaseType with the new data")
	@PutMapping("/{databaseTypeId}")
	public DatabaseTypeModel update(
			@ApiParam(value = "A valid DatabaseType ID", example = "1")
			@PathVariable Long databaseTypeId, 
			
			@ApiParam(name = "body", value = "A representation of a new DatabaseType with the new data")
			@RequestBody DatabaseTypeInput databaseTypeInput) {
		
		DatabaseType currentDatabaseType = databaseTypeService.findOrFail(databaseTypeId);
		
		databaseTypeInputDisassembler.copyToDomainObject(databaseTypeInput, currentDatabaseType);
		
		try {
			return databaseTypeModelAssembler.toModel(databaseTypeService.save(currentDatabaseType));
		} catch (DatabaseTypeNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	@ApiOperation("Get a DatabaseType based on an ID")
	@GetMapping("/{databaseTypeId}")
	public ResponseEntity<DatabaseTypeModel> findById(
			@ApiParam(value = "A valid DatabaseType ID", example = "1")
			@PathVariable Long databaseTypeId) {
		
		DatabaseTypeModel databaseTypeModel = databaseTypeModelAssembler.toModel(databaseTypeService.findOrFail(databaseTypeId));
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(databaseTypeModel);
		
	}
	
	@ApiOperation("Delete a DatabaseType based on an valid ID")
	@DeleteMapping("/{databaseTypeId}")
	public void delete(
			@ApiParam(value = "A valid databaseType ID", example = "1")
			@PathVariable Long databaseTypeId) {
		
		databaseTypeService.delete(databaseTypeId);
	}
	
}
