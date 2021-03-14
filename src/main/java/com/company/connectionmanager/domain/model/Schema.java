package com.company.connectionmanager.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(Include.NON_NULL)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
public class Schema {

	private String schemaName;

	private Integer numberOfTables;
	
	private List<Table> tables;
	
	public Schema(String schemaName) {
		this.schemaName = schemaName;
	}
}
