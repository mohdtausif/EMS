package com.asgnmt.ems.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class DepartmentDto {
	
	private String id;
	
	@NotNull @NotEmpty
	@Size(max = 50)
	private String title;
}
