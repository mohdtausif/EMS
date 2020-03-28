package com.asgnmt.ems.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
@Getter@Setter
public class EmployeeDto {
	
	private String id;
	
	@NotNull @NotEmpty
	@Size(max = 50)
	private String name;
	
	@NotNull @NotEmpty
	@Size(max = 10)
	private String empId;
	
	@NotNull
	private Boolean isPermanent;
	
	@NotNull @NotEmpty
	@Size(max = 50)
	private String emailId;
	
	@NotNull @NotEmpty
	private String departmentId;
}
