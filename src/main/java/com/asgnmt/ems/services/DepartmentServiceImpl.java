package com.asgnmt.ems.services;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.asgnmt.ems.dto.ApiResponse;
import com.asgnmt.ems.dto.DepartmentDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmsException;
import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.entity.StatusType;
import com.asgnmt.ems.jpa.repository.DepartmentRepository;
import com.asgnmt.ems.jpa.repository.EmployeeRepository;
import com.asgnmt.ems.utils.DtoConverter;
import com.asgnmt.ems.utils.LogUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
	private DepartmentRepository departmentRepository;
	private DtoConverter dtoConverter;

	@Autowired
	public DepartmentServiceImpl(DepartmentRepository departmentRepository,
			DtoConverter dtoConverter) {
		this.departmentRepository = departmentRepository;
		this.dtoConverter = dtoConverter;
	}

	@Override
	public ApiResponse<DepartmentDto> saveDepartment(DepartmentDto departmentDto)
			throws EmsException, DepartmentNotFoundException {
		DepartmentEntity departmentEntity = null;
		if (departmentDto.getId() != null) {
			LogUtils.debug(log, "Updating existing department with id=" + departmentDto.getId());
			Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(departmentDto.getId());
			if (!optionalDepartmentEntity.isPresent()) {
				throw new DepartmentNotFoundException("Department not found with Id=" + departmentDto.getId());
			}
			departmentEntity = optionalDepartmentEntity.get();
		} else {
			LogUtils.debug(log, "Inserting a new department with title=" + departmentDto.getTitle());
			departmentEntity = new DepartmentEntity();
		}
		departmentEntity.setTitle(departmentDto.getTitle());
		departmentEntity = departmentRepository.save(departmentEntity);

		if (departmentEntity != null) {
			ApiResponse<DepartmentDto> apiResponse = new ApiResponse<>();
			departmentDto = dtoConverter.getDepartmentDto(departmentEntity);
			apiResponse.setData(departmentDto);
			return apiResponse;
		}
		throw new EmsException("Error in saving/updating department with id=" + departmentDto.getId());
	}

	@Override
	public ApiResponse<DepartmentDto> getDepartment(String id) throws EmsException, DepartmentNotFoundException {
		Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(id);
		if (!optionalDepartmentEntity.isPresent()) {
			throw new DepartmentNotFoundException("Department not found with Id=" + id);
		}

		ApiResponse<DepartmentDto> apiResponse = new ApiResponse<>();
		DepartmentDto departmentDto = dtoConverter.getDepartmentDto(optionalDepartmentEntity.get());
		apiResponse.setData(departmentDto);
		return apiResponse;
	}

	@Override
	public ApiResponse<Page<DepartmentDto>> getDepartments(String queryText,int offset, int index) throws EmsException {
		Pageable pageable = PageRequest.of(index, offset);
		Page<DepartmentEntity> pages = null;
		if (queryText != null && !queryText.trim().equals("null") && queryText.trim().length() > 0) {
			LogUtils.debug(log, "Fetching departments with queryText="+queryText);
			pages = departmentRepository.findByStatusAndTitleContainingIgnoreCase(StatusType.ACTIVE, queryText, pageable);
		} else {
			LogUtils.debug(log, "Fetching departments without queryText");
			pages = departmentRepository.findByStatus(StatusType.ACTIVE, pageable);
		}
		
		if (pages != null) {
			LogUtils.debug(log, "Converted entity list size="+pages.getSize());
			Page<DepartmentDto> dtoPage = pages.map(new Function<DepartmentEntity, DepartmentDto>() {
			    @Override
			    public DepartmentDto apply(DepartmentEntity entity) {
			    	return dtoConverter.getDepartmentDto(entity);
			    }
			});
			LogUtils.debug(log, "Converted dto list size="+dtoPage.getSize());
			ApiResponse<Page<DepartmentDto>> apiResponse = new ApiResponse<>();
			apiResponse.setData(dtoPage);
			return apiResponse;
		}
		throw new EmsException("Error in fetching departments with queryText="+queryText+", offset="+offset+", index="+index);
	}

	@Override
	public ApiResponse<DepartmentDto> deleteDepartment(String id) throws EmsException, DepartmentNotFoundException {
		Optional<DepartmentEntity> optionalDepartmentEntity = departmentRepository.findById(id);
		if (!optionalDepartmentEntity.isPresent()) {
			throw new DepartmentNotFoundException("Department not found with Id=" + id);
		}
		DepartmentEntity departmentEntity = optionalDepartmentEntity.get();
		departmentEntity.setStatus(StatusType.DELETED);
		departmentEntity = departmentRepository.save(departmentEntity);

		if (departmentEntity != null) {
			ApiResponse<DepartmentDto> apiResponse = new ApiResponse<>();
			DepartmentDto departmentDto = dtoConverter.getDepartmentDto(departmentEntity);
			apiResponse.setData(departmentDto);
			return apiResponse;
		}
		throw new EmsException("Error in deleting department with id=" + id);
	}

}
