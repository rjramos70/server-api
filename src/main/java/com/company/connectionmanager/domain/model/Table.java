package com.company.connectionmanager.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(Include.NON_NULL)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Table {

	private String name;
	
	private String tableType;

	private String primaryKey;

	private Integer numberOfRows;
	
	private List<Column> columns;
	
//	public Table(String name) {
//		this.name = name;
//	}
	

}
