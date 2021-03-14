package com.company.connectionmanager.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.connectionmanager.domain.exception.DatabaseTypeNotFoundException;
import com.company.connectionmanager.domain.model.DatabaseType;
import com.company.connectionmanager.domain.repository.DatabaseTypeRepository;

@Service
public class DatabaseTypeService {

	@Autowired
	private DatabaseTypeRepository databaseTypeRepository;
	
	public List<DatabaseType> findAll(){
		return databaseTypeRepository.findAll();
	}
	
	public DatabaseType save(DatabaseType databaseType) {
		return databaseTypeRepository.save(databaseType);
	}
	
	public void delete(Long databasetypeId) {
		databaseTypeRepository.deleteById(findOrFail(databasetypeId).getId());
	}
	
	public DatabaseType findOrFail(Long databasetypeId) {
		return databaseTypeRepository
				.findById(databasetypeId)
				.orElseThrow(() -> new DatabaseTypeNotFoundException(databasetypeId));
	}
}
