package com.asgnmt.ems.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class DepartmentDto {
	
	private String id;
	
	@NotNull @NotEmpty
	@Size(max = 50)
	private String title;
}
