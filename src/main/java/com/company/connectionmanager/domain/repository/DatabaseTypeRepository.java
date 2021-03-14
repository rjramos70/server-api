package com.company.connectionmanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.connectionmanager.domain.model.DatabaseType;

public interface DatabaseTypeRepository extends JpaRepository<DatabaseType, Long>{

}
