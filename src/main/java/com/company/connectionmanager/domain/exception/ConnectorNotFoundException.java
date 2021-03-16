package com.company.connectionmanager.domain.exception;

public class ConnectorNotFoundException extends EntityNotFoundException{


	private static final long serialVersionUID = 1L;
	
	private static final String CONNECTOR_NOT_FOUND_MSG = 
			"There is no connector with id '%s'";

	public ConnectorNotFoundException(String message) {
		super(message);
	}
	
	public ConnectorNotFoundException(Long connectorId) {
		this(String.format(CONNECTOR_NOT_FOUND_MSG, connectorId));
	}

	
}
