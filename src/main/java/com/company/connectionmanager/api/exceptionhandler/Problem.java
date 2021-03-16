package com.company.connectionmanager.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(description = "Representation of a Problem type")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem{
	
	@ApiModelProperty(value = "Problem status", example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(value = "Problem type", example = "https://ataccama.com/argument-type-mismatch", position = 10)
	private String type;
	
	@ApiModelProperty(value = "Problem title", example = "Argument type not valid", position = 20)
	private String title;
	
	@ApiModelProperty(value = "Problem detail", position = 30, example = "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"1\"")
	private String detail;
	
	@ApiModelProperty(value = "User message", example = "The parameter passed in the request has an invalid type", position = 40)
	private String userMassage;
	
	@ApiModelProperty(value = "Problem timestamp", example = "2021-03-14T17:50:31.403245", position = 50)
	private LocalDateTime timestamp;
	
	@ApiModelProperty(value = "List of objects or fields that generated an error", position = 60)
	private List<Field> fields;
	
	
	@ApiModel(value = "ProblemField", description = "Representation of a Field object problem (optional)")
	@Getter
	@Builder
	public static class Field {
		
		@ApiModelProperty(value = "Field name", example = "Name", position = 1)
		private String name;
		
		@ApiModelProperty(value = "User message", example = "One or more fields are invalid. Fill in these correctly and try again.", position = 10)
		private String userMessage;
	}

	
}
