package com.asgnmt.ems.controllers;

import java.util.Set;

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
import com.asgnmt.ems.dto.DepartmentDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmsException;

@RestController
@RequestMapping(Constants.API_VERSION)
public interface DepartmentController {
	/**
	 * This API is used to Add or Update a department, if department already exist with the same id then that department data will be updated.
	 * Notes:
	 * Department title max size is 50 chars.
	 * @param departmentDto request dto to add or  update a document
	 * @return ApiResponse<DepartmentDto> response dto
	 * @throws EmsException Generic app exceptions
	 * @throws DepartmentNotFoundException Department not exist
	 */
	@PostMapping(Constants.URL_SAVE_DEPARTMENT)
	public ApiResponse<DepartmentDto> saveDepartment(@Valid @RequestBody DepartmentDto departmentDto)
			throws EmsException, DepartmentNotFoundException;

	/**
	 * This API is used to fetch a specific department by department id.
	 * @param id department id to fetch
	 * @return ApiResponse<DepartmentDto> response dto
	 * @throws EmsException Generic app exceptions
	 * @throws DepartmentNotFoundException Department not exist
	 */
	@GetMapping(Constants.URL_GET_DEPARTMENT)
	public ApiResponse<DepartmentDto> getDepartment(@PathVariable("id") String id)
			throws EmsException, DepartmentNotFoundException;

	/**
	 * This api is used to fetch all departments with pagination.
	 * @param queryText search departments by title containing search query text.
	 * @param offset number of items in one page
	 * @param index index of the page in all available pages list
	 * @return ApiResponse<Page<DepartmentDto>>  response dto 
	 * @throws EmsException  Generic app exceptions
	 */
	@GetMapping(Constants.URL_GET_DEPARTMENTS)
	public ApiResponse<Page<DepartmentDto>> getDepartments(@RequestParam("queryText") String queryText,
			@PathVariable("offset") int offset, @PathVariable("index") int index) throws EmsException;

	/**
	 * This api used to delete a department
	 * @param id department id to be delete
	 * @return ApiResponse<DepartmentDto> response dto
	 * @throws EmsException Generic app exceptions
	 * @throws DepartmentNotFoundException Department not exist
	 */
	@DeleteMapping(Constants.URL_DELETE_DEPARTMENT)
	public ApiResponse<DepartmentDto> deleteDepartment(@PathVariable("id") String id)
			throws EmsException, DepartmentNotFoundException;
}
