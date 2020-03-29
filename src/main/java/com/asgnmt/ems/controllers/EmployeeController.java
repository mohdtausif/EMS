package com.asgnmt.ems.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asgnmt.ems.constants.Constants;
import com.asgnmt.ems.dto.ApiResponse;
import com.asgnmt.ems.dto.EmployeeDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmployeeNotFoundException;
import com.asgnmt.ems.exceptions.EmsException;

@RestController
@RequestMapping(Constants.API_VERSION)
public interface EmployeeController {
	@PostMapping(Constants.URL_SAVE_EMPLOYEE)
	public ApiResponse<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto)
			throws EmsException, DepartmentNotFoundException, EmployeeNotFoundException;

	@GetMapping(Constants.URL_GET_EMPLOYEE)
	public ApiResponse<EmployeeDto> getEmployee(@PathVariable("id") String id)
			throws EmsException, EmployeeNotFoundException;

	@GetMapping(Constants.URL_GET_EMPLOYEES)
	public ApiResponse<Page<EmployeeDto>> getEmployees(@RequestParam("queryText") String queryText,
			@PathVariable("offset") int offset, @PathVariable("index") int index) throws EmsException;

	@DeleteMapping(Constants.URL_DELETE_EMPLOYEE)
	public ApiResponse<EmployeeDto> deleteEmployee(@PathVariable("id") String id)
			throws EmsException, EmployeeNotFoundException;
}
