package com.asgnmt.ems.services;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.asgnmt.ems.dto.ApiResponse;
import com.asgnmt.ems.dto.DepartmentDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmsException;

@Service
public interface DepartmentService {

	ApiResponse<DepartmentDto> saveDepartment(DepartmentDto departmentDto)throws EmsException, DepartmentNotFoundException;

	ApiResponse<DepartmentDto> getDepartment(String id)throws EmsException, DepartmentNotFoundException;

	ApiResponse<Page<DepartmentDto>> getDepartments(String queryText,int offset, int index) throws EmsException;

	ApiResponse<DepartmentDto> deleteDepartment(String id)throws EmsException, DepartmentNotFoundException;
}
