package com.company.connectionmanager.api.model;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.company.connectionmanager.Groups;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DatabaseTypeModel {
	
	@ApiModelProperty(value = "Unique key", example = "1")
	@NotNull(groups = Groups.InsertConnector.class)
	@Id
	private Long id;
	
	@ApiModelProperty(value = "A brief description of the connection type name", example = "MySQL database instance")
	@NotBlank(groups = Groups.InsertConnector.class)
	private String description;	
	
	@ApiModelProperty(value = "What type of database connection", example = "MySQL")
	@NotBlank(groups = Groups.InsertConnector.class)
	private String type;
	
	@ApiModelProperty(value = "The JDBC string connection", example = "jdbc:mysql://")
	@NotBlank(groups = Groups.InsertConnector.class)
	private String jdbcPrefix;
	
	@ApiModelProperty(value = "The JDBC driver string connection", example = "com.mysql.cj.jdbc.Driver")
	@NotBlank(groups = Groups.InsertConnector.class)
	private String jdbcDriver;

}
