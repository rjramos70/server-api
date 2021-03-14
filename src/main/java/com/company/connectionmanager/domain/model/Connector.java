package com.company.connectionmanager.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.company.connectionmanager.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel(description = "Representation of a database connector")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Connector {

//	@ApiModelProperty(value = "Unique key", example = "1")
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@ApiModelProperty(value = "Custom name of the database instance", example = "databaseDB")
	@NotBlank
	@Column(nullable = false)
	private String name;

//	@ApiModelProperty(value = "Hostname of the database", example = "localhost")
	@NotBlank
	@Column(name = "hostname",nullable = false)	
	private String hostName;
	
//	@ApiModelProperty(value = "Port where the database runs", example = "3306")
	@PositiveOrZero
	@Column(name = "port", nullable = false)
	private Long port;

//	@ApiModelProperty(value = "Name of the database", example = "mydatabase")
	@NotBlank
	@Column(name = "databasename", nullable = false)	
	private String databaseName;

//	@ApiModelProperty(value = "Username for connecting to the database", example = "root")
	@NotBlank
	@Column(nullable = false)	
	private String username;

//	@ApiModelProperty(value = "Password for connecting to the database", example = "root")
	@NotBlank
	@Column(nullable = false)
	private String password;
	
	@Valid
	@NotNull(groups = Groups.InsertConnector.class)
	@ManyToOne
	@JoinColumn(name = "databasetype_id", nullable = false)
	private DatabaseType databasetype;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime registered;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime updated;
	
	
}
