package com.company.connectionmanager.api.model;

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

	@ApiModelProperty(value = "Unique key", example = "1")
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	
	@ApiModelProperty(value = "Custom name of the database instance", example = "databaseDB")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "Hostname of the database", example = "localhost")
	@NotBlank
	private String hostName;
	
	@ApiModelProperty(value = "Port where the database runs", example = "3306")
	@PositiveOrZero
	private Long port;

	@ApiModelProperty(value = "Name of the database", example = "mydatabase")
	@NotBlank
	private String databaseName;

	@ApiModelProperty(value = "Username for connecting to the database", example = "root")
	@NotBlank
	private String username;

	@ApiModelProperty(value = "Password for connecting to the database", example = "root")
	@NotBlank
	private String password;
	
	@Valid
	@NotNull(groups = Groups.InsertConnector.class)
	private DatabaseTypeIdInput databasetype;
	
}
