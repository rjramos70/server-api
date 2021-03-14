package com.company.connectionmanager.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.company.connectionmanager.api.assembler.ConnectorInputDisassembler;
import com.company.connectionmanager.api.assembler.ConnectorModelAssembler;
import com.company.connectionmanager.api.model.ConnectorModel;
import com.company.connectionmanager.api.model.input.ConnectorInput;
import com.company.connectionmanager.core.web.WebConfig;
import com.company.connectionmanager.domain.exception.BusinessException;
import com.company.connectionmanager.domain.exception.ConnectorNotFoundException;
import com.company.connectionmanager.domain.model.Connector;
import com.company.connectionmanager.domain.service.ConnectorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Connector")
@RestController
@RequestMapping(value = "/connectors")
public class ConnectorController {
	
	@Autowired
	private ConnectorService connectorService;
	
	@Autowired
	private ConnectorModelAssembler connectorModelAssembler;
	
	@Autowired
	private ConnectorInputDisassembler connectorInputDisassembler;
	
	@ApiOperation("List all connectors registered in our database")
	@GetMapping
	public ResponseEntity<List<ConnectorModel>> findAll(ServletWebRequest request){
		
		// disable ShallowEtagHeaderFilter in this method in order to implement Deep ETag
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		// Implementing the Deep ETags conditional request
		String eTag = "0";
		
		LocalDateTime lastUpdate = connectorService.getLastUpdate();
		
		if(lastUpdate != null) {
			eTag = String.valueOf(lastUpdate.toEpochSecond(ZoneOffset.UTC));
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		List<ConnectorModel> collectionModels = connectorModelAssembler.toCollectionModel(connectorService.findAll());
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.eTag(eTag)
				.body(collectionModels);
		
	}
	
	@ApiOperation("Get a connector based on an ID")
	@GetMapping("/{connectorId}")
	public ResponseEntity<ConnectorModel> findById(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId) {
		
		ConnectorModel connectorModel = connectorModelAssembler.toModel(connectorService.findOrFail(connectorId));
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(connectorModel);
				
	}
	
	@ApiOperation("Register a new connector")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ConnectorModel insert(
			@ApiParam(name = "body", value = "A representation of a new Connector")
			@RequestBody @Valid ConnectorInput connectorInput) {	
		try {
			
			Connector connector = connectorInputDisassembler.toDomainObjet(connectorInput);
			
			
			return connectorModelAssembler.toModel(connectorService.save(connector));
		} catch (ConnectorNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@ApiOperation("Update a connector based on an ID")
	@PutMapping("/{connectorId}")
	public ConnectorModel update(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId, 
			@ApiParam(name = "body", value = "A representation of a new Connector with the new data")
			@RequestBody ConnectorInput connectorInput) {
		
		Connector currentConnector = connectorService.findOrFail(connectorId);

		connectorInputDisassembler.copyToDomainObject(connectorInput, currentConnector);
		
		try {
			return connectorModelAssembler.toModel(connectorService.save(currentConnector));
		} catch (ConnectorNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@ApiOperation("Remove a connector based on an valid ID")
	@DeleteMapping("/{connectorId}")
	public void delete(
			@ApiParam(value = "A valid connector ID", example = "1")
			@PathVariable Long connectorId) {
		connectorService.delete(connectorId);
	}

}
