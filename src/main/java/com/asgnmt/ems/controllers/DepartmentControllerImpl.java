package com.asgnmt.ems.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asgnmt.ems.dto.ApiResponse;
import com.asgnmt.ems.dto.DepartmentDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmsException;
import com.asgnmt.ems.services.DepartmentService;

@RestController
public class DepartmentControllerImpl implements DepartmentController {
	private DepartmentService departmentService;

	@Autowired
	public DepartmentControllerImpl(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public ApiResponse<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto)
			throws EmsException, DepartmentNotFoundException {
		return departmentService.saveDepartment(departmentDto);
	}

	@Override
	public ApiResponse<DepartmentDto> getDepartment(@PathVariable("id") String id)
			throws EmsException, DepartmentNotFoundException {
		return departmentService.getDepartment(id);
	}

	@Override
	public ApiResponse<Page<DepartmentDto>> getDepartments(@RequestParam("queryText") String queryText,
			@PathVariable("offset") int offset, @PathVariable("index") int index) throws EmsException {
		return departmentService.getDepartments(queryText,offset, index);
	}

	@Override
	public ApiResponse<DepartmentDto> deleteDepartment(@PathVariable("id") String id)
			throws EmsException, DepartmentNotFoundException {
		return departmentService.deleteDepartment(id);
	}

}
