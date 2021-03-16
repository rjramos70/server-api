package com.company.connectionmanager.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "Representation of a database schema")
@JsonInclude(Include.NON_NULL)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
public class Schema {

	@ApiModelProperty(value = "Schema name", example = "ataccama")
	private String schemaName;

	@ApiModelProperty(value = "Number of tables in the schema", example = "10")
	private Integer numberOfTables;
	
	@ApiModelProperty(value = "Schema table list")
	private List<Table> tables;
	
	public Schema(String schemaName) {
		this.schemaName = schemaName;
	}
	
	
}
