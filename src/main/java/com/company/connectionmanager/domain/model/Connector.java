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
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "Representation of a database connector")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Connector {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotBlank
	@Column(name = "hostname",nullable = false)	
	private String hostName;
	
	@PositiveOrZero
	@Column(name = "port", nullable = false)
	private Long port;

	@NotBlank
	@Column(name = "databasename", nullable = false)	
	private String databaseName;

	@NotBlank
	@Column(nullable = false)	
	private String username;

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
