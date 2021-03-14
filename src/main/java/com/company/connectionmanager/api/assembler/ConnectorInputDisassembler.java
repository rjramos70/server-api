package com.company.connectionmanager.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.connectionmanager.api.model.input.ConnectorInput;
import com.company.connectionmanager.domain.model.Connector;
import com.company.connectionmanager.domain.model.DatabaseType;

@Component
public class ConnectorInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Connector toDomainObjet(ConnectorInput connectorInput) {
		return modelMapper.map(connectorInput, Connector.class);
	}
	
	public void copyToDomainObject(ConnectorInput connectorInput, Connector currentConnector) {
		
		currentConnector.setDatabasetype(new DatabaseType());
		
		modelMapper.map(connectorInput, currentConnector);
	}
}
