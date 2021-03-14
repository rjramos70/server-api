package com.company.connectionmanager.api.model.input;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import com.company.connectionmanager.Groups;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DatabaseTypeInput {

	@ApiModelProperty(value = "A brief description of the connection type name", example = "MySQL database instance", required = true)
	@NotBlank(groups = Groups.InsertConnector.class)
	@Column(nullable = false)
	private String description;	// MySQL database instance
	
	@ApiModelProperty(value = "What type of database connection", example = "MySQL", required = true)
	@NotBlank(groups = Groups.InsertConnector.class)
	@Column(name = "type", nullable = false)
	private String type;	// Oracle, MySQL, SQLServer
	
	@ApiModelProperty(value = "The JDBC string connection", example = "jdbc:mysql://", required = true)
	@NotBlank(groups = Groups.InsertConnector.class)
	@Column(name = "jdbcprefix", nullable = false)
	private String jdbcPrefix;		// jdbc:mysql://
	
	@ApiModelProperty(value = "The JDBC driver string connection", example = "com.mysql.cj.jdbc.Driver", required = true)
	@NotBlank(groups = Groups.InsertConnector.class)
	@Column(name = "jdbcdriver", nullable = false)
	private String jdbcDriver;		// com.mysql.cj.jdbc.Driver
}
