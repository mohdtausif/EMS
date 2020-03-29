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
	@PostMapping(Constants.URL_SAVE_DEPARTMENT)
	public ApiResponse<DepartmentDto> saveDepartment(@Valid @RequestBody DepartmentDto departmentDto)
			throws EmsException, DepartmentNotFoundException;

	@GetMapping(Constants.URL_GET_DEPARTMENT)
	public ApiResponse<DepartmentDto> getDepartment(@PathVariable("id") String id)
			throws EmsException, DepartmentNotFoundException;

	@GetMapping(Constants.URL_GET_DEPARTMENTS)
	public ApiResponse<Page<DepartmentDto>> getDepartments(@RequestParam("queryText") String queryText,
			@PathVariable("offset") int offset, @PathVariable("index") int index) throws EmsException;

	@DeleteMapping(Constants.URL_DELETE_DEPARTMENT)
	public ApiResponse<DepartmentDto> deleteDepartment(@PathVariable("id") String id)
			throws EmsException, DepartmentNotFoundException;
}
