package com.company.connectionmanager.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.connectionmanager.api.model.input.DatabaseTypeInput;
import com.company.connectionmanager.domain.model.DatabaseType;

@Component
public class DatabaseTypeInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public DatabaseType toDomainObjet(DatabaseTypeInput databaseTypeInput) {
		return modelMapper.map(databaseTypeInput, DatabaseType.class);
	}
	
	public void copyToDomainObject(DatabaseTypeInput databaseTypeInput, DatabaseType currentDatabaseType) {
		
		// currentConnector.setDatabasetype(new DatabaseType());
		
		modelMapper.map(databaseTypeInput, currentDatabaseType);
	}
}
