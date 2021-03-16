package com.company.connectionmanager.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.company.connectionmanager.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "Representation of a database connection type")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "databasetype")
public class DatabaseType {
	
	@ApiModelProperty(value = "Unique key", example = "1")
	@NotNull(groups = Groups.InsertConnector.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ApiModelProperty(value = "A brief description of the connection type name", example = "MySQL database instance")
	@NotBlank(groups = Groups.InsertConnector.class)
	@Column(nullable = false)
	private String description;	// MySQL database instance
	
	@ApiModelProperty(value = "What type of database connection", example = "MySQL")
	@NotBlank(groups = Groups.InsertConnector.class)
	@Column(name = "type", nullable = false)
	private String type;	// Oracle, MySQL, SQLServer
	
	@ApiModelProperty(value = "The JDBC string connection", example = "jdbc:mysql://")
	@NotBlank(groups = Groups.InsertConnector.class)
	@Column(name = "jdbcprefix", nullable = false)
	private String jdbcPrefix;		// jdbc:mysql://
	
	@ApiModelProperty(value = "The JDBC driver string connection", example = "com.mysql.cj.jdbc.Driver")
	@NotBlank(groups = Groups.InsertConnector.class)
	@Column(name = "jdbcdriver", nullable = false)
	private String jdbcDriver;		// com.mysql.cj.jdbc.Driver
	
	@JsonIgnore
	@OneToMany(mappedBy = "databasetype")
	private List<Connector> connectors = new ArrayList<>();
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime registered;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime updated;
	

	
}
