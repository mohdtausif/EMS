package com.asgnmt.ems;

import org.springframework.stereotype.Component;

import com.asgnmt.ems.dto.EmployeeDto;
import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.entity.EmployeeEntity;

@Component
public class TestHelper {
	
	public EmployeeDto getEmployeeDto(String departmentId,String random) {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("name" + random);
		employeeDto.setEmpId("empId" + random);
		employeeDto.setIsPermanent(false);
		employeeDto.setEmailId("emailId" + random + "@company.com");
		employeeDto.setDepartmentId(departmentId);
		return employeeDto;
	}

	public EmployeeEntity getEmployeeEntity(DepartmentEntity departmentEntity,String random) {
		EmployeeEntity employeeEntity=new EmployeeEntity();
		employeeEntity.setName("name"+random);
		employeeEntity.setEmpId("empId"+random);
		employeeEntity.setIsPermanent(false);
		employeeEntity.setEmailId("emailId"+random);
		employeeEntity.setDepartment(departmentEntity);
		return employeeEntity;
	}
}
