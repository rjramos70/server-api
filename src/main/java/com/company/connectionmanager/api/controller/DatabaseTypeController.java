package com.company.connectionmanager.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.company.connectionmanager.api.openapi.controller.DatabaseTypeControllerOpenApi;
import com.company.connectionmanager.core.web.WebConfig;
import com.company.connectionmanager.domain.exception.BusinessException;
import com.company.connectionmanager.domain.exception.DatabaseTypeNotFoundException;
import com.company.connectionmanager.domain.model.DatabaseType;
import com.company.connectionmanager.domain.service.DatabaseTypeService;


@RestController
@RequestMapping(path = "/databasetypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DatabaseTypeController implements DatabaseTypeControllerOpenApi{
	
	@Autowired
	private DatabaseTypeService databaseTypeService;
	
	@Autowired
	private DatabaseTypeModelAssembler databaseTypeModelAssembler;
	
	@Autowired
	private DatabaseTypeInputDisassembler databaseTypeInputDisassembler;
	
	@GetMapping
	public ResponseEntity<List<DatabaseTypeModel>> findAll(){
		
		List<DatabaseTypeModel> databaseTypeCollectionModel = databaseTypeModelAssembler.toCollectionModel(databaseTypeService.findAll());
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(databaseTypeCollectionModel);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DatabaseTypeModel insert(@RequestBody @Valid DatabaseTypeInput databaseTypeInput) {
		try {
			DatabaseType databaseType = databaseTypeInputDisassembler.toDomainObjet(databaseTypeInput);
			
			return databaseTypeModelAssembler.toModel(databaseTypeService.save(databaseType));
		} catch (DatabaseTypeNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@PutMapping("/{databaseTypeId}")
	public DatabaseTypeModel update(@PathVariable Long databaseTypeId, 
			@RequestBody DatabaseTypeInput databaseTypeInput) {
		
		DatabaseType currentDatabaseType = databaseTypeService.findOrFail(databaseTypeId);
		
		databaseTypeInputDisassembler.copyToDomainObject(databaseTypeInput, currentDatabaseType);
		
		try {
			return databaseTypeModelAssembler.toModel(databaseTypeService.save(currentDatabaseType));
		} catch (DatabaseTypeNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
		
	}
	
	@GetMapping("/{databaseTypeId}")
	public ResponseEntity<DatabaseTypeModel> findById(@PathVariable Long databaseTypeId) {
		
		DatabaseTypeModel databaseTypeModel = databaseTypeModelAssembler.toModel(databaseTypeService.findOrFail(databaseTypeId));
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(databaseTypeModel);
		
	}
	
	@DeleteMapping("/{databaseTypeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long databaseTypeId) {
		
		databaseTypeService.delete(databaseTypeId);
	}
	
}
