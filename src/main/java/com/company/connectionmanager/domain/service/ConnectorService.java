package com.company.connectionmanager.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.connectionmanager.domain.exception.ConnectorNotFoundException;
import com.company.connectionmanager.domain.model.Connector;
import com.company.connectionmanager.domain.model.DatabaseType;
import com.company.connectionmanager.domain.repository.ConnectorRepository;

@Service
public class ConnectorService {
	
	@Autowired
	private ConnectorRepository connectionRepository;
	
	@Autowired
	private DatabaseTypeService databaseTypeService;
	
	
	public LocalDateTime getLastUpdate() {
		return connectionRepository.getLastUpdate();
	}
	
	public List<Connector> findAll(){
		return connectionRepository.findAll();
	}
	
	public Connector save(Connector connector) {
		
		DatabaseType databaseType = databaseTypeService.findOrFail(connector.getDatabasetype().getId());
		
		connector.setDatabasetype(databaseType);
		
		return connectionRepository.save(connector);
	}
	
	public void delete(Long connectorId) {
		connectionRepository.deleteById(findOrFail(connectorId).getId());
	}
	
	public Connector findOrFail(Long connectorId) {
		return connectionRepository
				.findById(connectorId)
				.orElseThrow(() -> new ConnectorNotFoundException(connectorId));
	}
	
}
