package com.company.connectionmanager.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	INVALID_DATA("/invalid-data", "Invalid data"), 
	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"), 
	ARGUMENT_TYPE_MISMATCH("/argument-type-mismatch", "Argument type not valid"), 
	BUSINESS_RULE_ERROR("/business-rule-error", "Business rule error");
	
	
	
	private String title;
	private String uri;
	
	
	private ProblemType(String path, String title) {
		this.uri = "https://ataccama.com".concat(path);
		this.title = title;
	}

}
