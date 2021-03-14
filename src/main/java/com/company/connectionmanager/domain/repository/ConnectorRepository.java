package com.company.connectionmanager.domain.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.company.connectionmanager.domain.model.Connector;


public interface ConnectorRepository extends JpaRepository<Connector, Long>{

	@Query(nativeQuery = true, value = "select max(updated) from connector")
	LocalDateTime getLastUpdate();
	
}
