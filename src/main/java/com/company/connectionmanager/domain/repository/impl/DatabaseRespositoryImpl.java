package com.company.connectionmanager.domain.repository.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.connectionmanager.domain.exception.ConnectorNotFoundException;
import com.company.connectionmanager.domain.model.Connector;
import com.company.connectionmanager.domain.repository.ConnectorRepository;


@Repository
public class DatabaseRespositoryImpl{
	
	@Autowired
	private ConnectorRepository connectorRepository;
	
	public DatabaseMetaData getDatabaseMetaData(Long connectorId, String schemaName) {
		
		Connection connection = getConnectionByIdAndSchemaName(connectorId, schemaName);
		
		DatabaseMetaData metaData = null;

		try {
			metaData = connection.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return metaData;
	}

	public Connection getConnectionById(Long connectorId) {
		Connector connector = connectorRepository
				.findById(connectorId)
				.orElseThrow(() -> new ConnectorNotFoundException(connectorId));
		
	 	String jdbcDriver = connector.getDatabasetype().getJdbcDriver();
	 	
	 	String jdbcPrefix = connector.getDatabasetype().getJdbcPrefix();
	 	String hostName = connector.getHostName();
	 	String port = String.valueOf(connector.getPort());
	 	String databaseName = connector.getDatabaseName();
	 	
	 	String username = connector.getUsername();
	 	String password = connector.getPassword();
	 	
		String URI = String.format("%s%s:%s/%s", jdbcPrefix, hostName, port, databaseName);
		
		Connection conn = null;
		
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(URI, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	public Connection getConnectionByIdAndSchemaName(Long connectorId, String schemaName) {
		Connector connector = connectorRepository
				.findById(connectorId)
				.orElseThrow(() -> new ConnectorNotFoundException(connectorId));
		
	 	String jdbcDriver = connector.getDatabasetype().getJdbcDriver();
	 	
	 	String jdbcPrefix = connector.getDatabasetype().getJdbcPrefix();
	 	String hostName = connector.getHostName();
	 	String port = String.valueOf(connector.getPort());
	 	String username = connector.getUsername();
	 	String password = connector.getPassword();
	 	
		String URI = String.format("%s%s:%s/%s", jdbcPrefix, hostName, port, schemaName);
		
		Connection conn = null;
		
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(URI, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
}
