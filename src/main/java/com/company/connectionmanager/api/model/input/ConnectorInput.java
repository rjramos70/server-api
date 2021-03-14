package com.company.connectionmanager.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.company.connectionmanager.Groups;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConnectorInput {

	@ApiModelProperty(value = "Custom name of the database instance", example = "databaseDB", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "Hostname of the database", example = "localhost", required = true)
	@NotBlank
	private String hostName;
	
	@ApiModelProperty(value = "Port where the database runs", example = "3306")
	@PositiveOrZero
	private Long port;

	@ApiModelProperty(value = "Name of the database", example = "mydatabase", required = true)
	@NotBlank
	private String databaseName;

	@ApiModelProperty(value = "Username for connecting to the database", example = "root", required = true)
	@NotBlank
	private String username;

	@ApiModelProperty(value = "Password for connecting to the database", example = "root", required = true)
	@NotBlank
	private String password;
	
	@Valid
	@NotNull(groups = Groups.InsertConnector.class)
	private DatabaseTypeIdInput databasetype;
	
}
