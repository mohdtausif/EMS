package com.asgnmt.ems.services;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.asgnmt.ems.dto.ApiResponse;
import com.asgnmt.ems.dto.DepartmentDto;
import com.asgnmt.ems.dto.EmployeeDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmployeeNotFoundException;
import com.asgnmt.ems.exceptions.EmsException;

@Service
public interface EmployeeService {

	ApiResponse<EmployeeDto> saveEmployee(EmployeeDto employeeDto)throws EmsException, DepartmentNotFoundException,EmployeeNotFoundException;

	ApiResponse<EmployeeDto> getEmployee(String id) throws EmsException, EmployeeNotFoundException;

	ApiResponse<Page<EmployeeDto>> getEmployees(String queryText,int offset,int index) throws EmsException;

	ApiResponse<EmployeeDto> deleteEmployee(String id) throws EmsException, EmployeeNotFoundException;
	

}
