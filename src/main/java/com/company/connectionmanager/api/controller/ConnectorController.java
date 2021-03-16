package com.company.connectionmanager.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.company.connectionmanager.api.assembler.ConnectorInputDisassembler;
import com.company.connectionmanager.api.assembler.ConnectorModelAssembler;
import com.company.connectionmanager.api.model.ConnectorModel;
import com.company.connectionmanager.api.model.input.ConnectorInput;
import com.company.connectionmanager.api.openapi.controller.ConnectorControllerOpenApi;
import com.company.connectionmanager.core.web.WebConfig;
import com.company.connectionmanager.domain.exception.BusinessException;
import com.company.connectionmanager.domain.exception.ConnectorNotFoundException;
import com.company.connectionmanager.domain.model.Connector;
import com.company.connectionmanager.domain.service.ConnectorService;


@RestController
@RequestMapping(path = "/connectors", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConnectorController implements ConnectorControllerOpenApi{
	
	@Autowired
	private ConnectorService connectorService;
	
	@Autowired
	private ConnectorModelAssembler connectorModelAssembler;
	
	@Autowired
	private ConnectorInputDisassembler connectorInputDisassembler;
	
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
	
	@GetMapping("/{connectorId}")
	public ResponseEntity<ConnectorModel> findById(@PathVariable Long connectorId) {
		
		ConnectorModel connectorModel = connectorModelAssembler.toModel(connectorService.findOrFail(connectorId));
		
		return ResponseEntity.ok()
				.cacheControl(WebConfig.getCacheControlMaxAge())
				.body(connectorModel);
				
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ConnectorModel insert(@RequestBody @Valid ConnectorInput connectorInput) {	
		try {
			
			Connector connector = connectorInputDisassembler.toDomainObjet(connectorInput);
			
			return connectorModelAssembler.toModel(connectorService.save(connector));
		} catch (ConnectorNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@PutMapping("/{connectorId}")
	public ConnectorModel update(@PathVariable Long connectorId, 
			@RequestBody ConnectorInput connectorInput) {
		
		Connector currentConnector = connectorService.findOrFail(connectorId);

		connectorInputDisassembler.copyToDomainObject(connectorInput, currentConnector);
		
		try {
			return connectorModelAssembler.toModel(connectorService.save(currentConnector));
		} catch (ConnectorNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{connectorId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long connectorId) {
		connectorService.delete(connectorId);
	}

}
