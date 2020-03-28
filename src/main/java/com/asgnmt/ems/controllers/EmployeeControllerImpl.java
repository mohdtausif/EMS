package com.asgnmt.ems.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asgnmt.ems.dto.ApiResponse;
import com.asgnmt.ems.dto.EmployeeDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmployeeNotFoundException;
import com.asgnmt.ems.exceptions.EmsException;
import com.asgnmt.ems.services.EmployeeService;
@RestController
public class EmployeeControllerImpl implements EmployeeController {
	private EmployeeService employeeService;

	@Autowired
	public EmployeeControllerImpl(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public ApiResponse<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto)
			throws EmsException, DepartmentNotFoundException,EmployeeNotFoundException {
		return employeeService.saveEmployee(employeeDto);
	}

	@Override
	public ApiResponse<EmployeeDto> getEmployee(@PathVariable("id") String id) throws EmsException, EmployeeNotFoundException {
		return employeeService.getEmployee(id);
	}

	@Override
	public ApiResponse<Page<EmployeeDto>> getEmployees(@RequestParam("queryText") String queryText,
			@PathVariable("offset") int offset, @PathVariable("index") int index) throws EmsException {
		return employeeService.getEmployees(queryText,offset,index);
	}

	@Override
	public ApiResponse<EmployeeDto> deleteEmployee(@PathVariable("id") String id) throws EmsException, EmployeeNotFoundException {
		return employeeService.deleteEmployee(id);
	}

}
