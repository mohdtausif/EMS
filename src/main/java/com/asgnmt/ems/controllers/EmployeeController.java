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
	/**
	 * This api add or update a employee, if employee already exist then employee data will be updated else new employee will be added.
	 * @param employeeDto employee dto to add or update a employee
	 * @return ApiResponse<EmployeeDto> reponse dto
	 * @throws EmsException Generic app exceptions
	 * @throws DepartmentNotFoundException Department not exist
	 * @throws EmployeeNotFoundException Employee not exist
	 */
	@PostMapping(Constants.URL_SAVE_EMPLOYEE)
	public ApiResponse<EmployeeDto> saveEmployee(@Valid @RequestBody EmployeeDto employeeDto)
			throws EmsException, DepartmentNotFoundException, EmployeeNotFoundException;

	/**
	 * This api fetch a employee by employee 
	 * @param id employee id to fetch
	 * @return ApiResponse<EmployeeDto> reponse dto
	 * @throws EmsException Generic app exceptions
	 * @throws EmployeeNotFoundException Employee not exist
	 */
	@GetMapping(Constants.URL_GET_EMPLOYEE)
	public ApiResponse<EmployeeDto> getEmployee(@PathVariable("id") String id)
			throws EmsException, EmployeeNotFoundException;

	/**
	 * This api fetches all employees with a search query and pagination parameters.
	 * @param queryText search query text to search employee by name
	 * @param offset number of employees in a page
	 * @param index index of the page
	 * @return ApiResponse<Page<EmployeeDto>>  reponse dto
	 * @throws EmsException Generic app exceptions
	 */
	@GetMapping(Constants.URL_GET_EMPLOYEES)
	public ApiResponse<Page<EmployeeDto>> getEmployees(@RequestParam("queryText") String queryText,
			@PathVariable("offset") int offset, @PathVariable("index") int index) throws EmsException;

	/**
	 * This api is used to delete a employee
	 * @param id employee id to delete
	 * @return ApiResponse<EmployeeDto> reponse dto
	 * @throws EmsException Generic app exceptions
	 * @throws EmployeeNotFoundException Employee not exist
	 */
	@DeleteMapping(Constants.URL_DELETE_EMPLOYEE)
	public ApiResponse<EmployeeDto> deleteEmployee(@PathVariable("id") String id)
			throws EmsException, EmployeeNotFoundException;
}
