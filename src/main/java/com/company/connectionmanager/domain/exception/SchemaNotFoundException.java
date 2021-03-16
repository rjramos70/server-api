package com.company.connectionmanager.domain.exception;

public class SchemaNotFoundException extends BusinessException{

	private static final long serialVersionUID = 1L;
	
	private static final String SCHEMA_NOT_FOUND_MSG = 
			"There is no Schema with name '%s'";

	public SchemaNotFoundException(String message) {
		super(String.format(SCHEMA_NOT_FOUND_MSG, message));
	}

	
}
