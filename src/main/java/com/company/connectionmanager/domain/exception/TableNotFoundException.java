package com.company.connectionmanager.domain.exception;

public class TableNotFoundException extends BusinessException{

	private static final long serialVersionUID = 1L;
	
	private static final String TABLE_NOT_FOUND_MSG = 
			"There is no table with name '%s'";

	public TableNotFoundException(String message) {
		super(String.format(TABLE_NOT_FOUND_MSG, message));
	}

	
}
