package com.company.connectionmanager.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.connectionmanager.api.model.DatabaseTypeModel;
import com.company.connectionmanager.domain.model.DatabaseType;

@Component
public class DatabaseTypeModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public DatabaseTypeModel toModel(DatabaseType databaseType) {
		return modelMapper.map(databaseType, DatabaseTypeModel.class);
	}
	
	public List<DatabaseTypeModel> toCollectionModel(List<DatabaseType> databaseTypes){
		return databaseTypes.stream()
					.map(databaseType -> toModel(databaseType))
					.collect(Collectors.toList());
	}
	
}
