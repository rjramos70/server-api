package com.company.connectionmanager.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "Representation of a database table")
@JsonInclude(Include.NON_NULL)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Table {

	@ApiModelProperty(value = "Table name", example = "People", position = 1)
	private String name;
	
	@ApiModelProperty(value = "Table key", example = "VIEW", position = 10)
	private String tableType;

	@ApiModelProperty(value = "Name of the unique key", example = "id", position = 20)
	private String primaryKey;

	@ApiModelProperty(value = "Number of columns in the tables", example = "5", position = 30)
	private Integer numberOfColumns;
	
	@ApiModelProperty(value = "List of table columns", position = 401)
	private List<Column> columns;
	

	
}
