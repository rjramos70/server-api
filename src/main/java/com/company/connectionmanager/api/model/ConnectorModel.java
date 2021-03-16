package com.company.connectionmanager.api.model;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.company.connectionmanager.Groups;
import com.company.connectionmanager.api.model.input.DatabaseTypeIdInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConnectorModel {

	@ApiModelProperty(value = "Unique key", example = "1", position = 1)
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	
	@ApiModelProperty(value = "Custom name of the database instance", example = "databaseDB", required = true, position = 10)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "Hostname of the database", example = "localhost", required = true, position = 20)
	@NotBlank
	private String hostName;
	
	@ApiModelProperty(value = "Port where the database runs", example = "3306", position = 30)
	@PositiveOrZero
	private Long port;

	@ApiModelProperty(value = "Name of the database", example = "mydatabase", required = true, position = 40)
	@NotBlank
	private String databaseName;

	@ApiModelProperty(value = "Username for connecting to the database", example = "root", required = true, position = 50)
	@NotBlank
	private String username;

	@ApiModelProperty(value = "Password for connecting to the database", example = "root", required = true, position = 60)
	@NotBlank
	private String password;
	
	@ApiModelProperty(value = "Type of database connector", required = true, position = 70)
	@Valid
	@NotNull(groups = Groups.InsertConnector.class)
	private DatabaseTypeIdInput databasetype;
	
	@ApiModelProperty(value = "Registered timestamp", required = true, position = 80)
	private LocalDateTime registered;
	
	@ApiModelProperty(value = "Last updated timestamp", required = true, position = 90)
	private LocalDateTime updated;
	
}
