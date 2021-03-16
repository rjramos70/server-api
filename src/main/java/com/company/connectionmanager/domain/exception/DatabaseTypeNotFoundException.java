package com.company.connectionmanager.domain.exception;

public class DatabaseTypeNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;
	
	private static final String DATABASETYPE_NOT_FOUND_MSG = 
			"There is no databaseType with id '%s'";

	public DatabaseTypeNotFoundException(String message) {
		super(message);
	}
	
	public DatabaseTypeNotFoundException(Long databasetypeId) {
		this(String.format(DATABASETYPE_NOT_FOUND_MSG, databasetypeId));
	}

	
}
