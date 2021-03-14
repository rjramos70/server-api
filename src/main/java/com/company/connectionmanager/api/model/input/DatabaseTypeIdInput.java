package com.company.connectionmanager.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DatabaseTypeIdInput {

	@ApiModelProperty(value = "Unique key", example = "1", required = true)
	@NotNull
	private Long id;
}
