package com.asgnmt.ems.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
	private String message = "error";
	private Boolean success = false;
	private Integer errorCode = 0;
	private T data;
	
	public ApiResponse() {}
	public ApiResponse(Integer errorCode, String message) {
		this.message=message;
		this.errorCode=errorCode;
	}
	
	public void setData(T data) {
		this.data=data;
		this.message="success";
		this.success=true;
		this.errorCode=0;
	}
}
