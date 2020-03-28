package com.asgnmt.ems.utils;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.asgnmt.ems.dto.DepartmentDto;
import com.asgnmt.ems.dto.EmployeeDto;
import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.entity.EmployeeEntity;

@Component
public class DtoConverter {

	public DepartmentDto getDepartmentDto(DepartmentEntity departmentEntity) {
		DepartmentDto departmentDto=new DepartmentDto();
		departmentDto.setId(departmentEntity.getId());
		departmentDto.setTitle(departmentEntity.getTitle());
		return departmentDto;
	}

	public Set<DepartmentDto> getDepartmentDtos(Set<DepartmentEntity> departmentEntities) {
		Set<DepartmentDto> departmentDtos=new HashSet<>(); 
		for(DepartmentEntity departmentEntity:departmentEntities)
		{
			departmentDtos.add(getDepartmentDto(departmentEntity));
		}
		return departmentDtos;
	}

	public EmployeeDto getEmployeeDto(EmployeeEntity employeeEntity) {
		EmployeeDto employeeDto=new EmployeeDto();
		employeeDto.setId(employeeEntity.getId());
		employeeDto.setName(employeeEntity.getName());
		employeeDto.setEmpId(employeeEntity.getEmpId());
		employeeDto.setIsPermanent(employeeEntity.getIsPermanent());
		employeeDto.setEmailId(employeeEntity.getEmailId());
		employeeDto.setDepartmentId(employeeEntity.getDepartment().getId());
		return employeeDto;
	}

}
