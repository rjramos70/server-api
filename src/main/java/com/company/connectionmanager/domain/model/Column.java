package com.company.connectionmanager.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "Representation of a column type")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
public class Column {

	@ApiModelProperty(value = "Column name", example = "1")
	private String name;
	
	@ApiModelProperty(value = "Column type", example = "VARCHAR")
	private String type;

	
}
