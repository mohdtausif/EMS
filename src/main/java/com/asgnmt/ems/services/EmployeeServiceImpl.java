package com.asgnmt.ems.services;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.asgnmt.ems.dto.ApiResponse;
import com.asgnmt.ems.dto.EmployeeDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmployeeNotFoundException;
import com.asgnmt.ems.exceptions.EmsException;
import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.entity.EmployeeEntity;
import com.asgnmt.ems.jpa.entity.StatusType;
import com.asgnmt.ems.jpa.repository.DepartmentRepository;
import com.asgnmt.ems.jpa.repository.EmployeeRepository;
import com.asgnmt.ems.utils.DtoConverter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
	private DepartmentRepository departmentRepository;
	private EmployeeRepository employeeRepository;
	private DtoConverter dtoConverter;
	
	@Autowired
	public EmployeeServiceImpl(DepartmentRepository departmentRepository,EmployeeRepository employeeRepository,DtoConverter dtoConverter)
	{
		this.departmentRepository=departmentRepository;
		this.employeeRepository=employeeRepository;
		this.dtoConverter=dtoConverter;
	}
	
	@Override
	public ApiResponse<EmployeeDto> saveEmployee(EmployeeDto employeeDto)
			throws EmsException, DepartmentNotFoundException,EmployeeNotFoundException {
		Optional<DepartmentEntity> optionalDepartmentEntity=null;
		if(employeeDto.getDepartmentId()!=null && employeeDto.getDepartmentId().trim().length()>0)
		{
			optionalDepartmentEntity=departmentRepository.findById(employeeDto.getDepartmentId());
			if(!optionalDepartmentEntity.isPresent())
			{
				throw new DepartmentNotFoundException("Department not found with Id="+employeeDto.getDepartmentId());
			}
		}
		else
		{
			throw new DepartmentNotFoundException("Invalid Department Id="+employeeDto.getDepartmentId());
		}
		
		EmployeeEntity employeeEntity=null;
		if(employeeDto.getId()!=null && employeeDto.getId().trim().length()>0)
		{
			log.debug("Updating employee with id="+employeeDto.getId());
			Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(employeeDto.getId());
			if(!optionalEmployeeEntity.isPresent())
			{
				throw new EmployeeNotFoundException("Employee not found with id="+employeeDto.getId());
			}
			employeeEntity=optionalEmployeeEntity.get();
		}
		else
		{
			log.debug("Adding new employee with name="+employeeDto.getName());
			employeeEntity=new EmployeeEntity();
		}
		employeeEntity.setName(employeeDto.getName());
		employeeEntity.setEmailId(employeeDto.getEmailId());
		employeeEntity.setEmpId(employeeDto.getEmpId());
		employeeEntity.setIsPermanent(employeeDto.getIsPermanent());
		employeeEntity.setDepartment(optionalDepartmentEntity.get());
		employeeEntity=employeeRepository.save(employeeEntity);
		if(employeeEntity!=null)
		{
			ApiResponse<EmployeeDto> apiResponse=new ApiResponse<>();
			apiResponse.setData(dtoConverter.getEmployeeDto(employeeEntity));
			return apiResponse;
		}
		throw new EmsException("Error in saving employee with name="+employeeDto.getName());
	}

	@Override
	public ApiResponse<EmployeeDto> getEmployee(String id) throws EmsException, EmployeeNotFoundException {
		Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(id);
		if(!optionalEmployeeEntity.isPresent())
		{
			throw new EmployeeNotFoundException("Employee not found with id="+id);
		}
		ApiResponse<EmployeeDto> apiResponse=new ApiResponse<>();
		apiResponse.setData(dtoConverter.getEmployeeDto(optionalEmployeeEntity.get()));
		return apiResponse;
	}

	@Override
	public ApiResponse<Page<EmployeeDto>> getEmployees(String queryText, int offset, int index) throws EmsException {
		Pageable pageable = PageRequest.of(index, offset);
		Page<EmployeeEntity> pages = null;
		if (queryText != null && queryText.trim().length() > 0) {
			pages = employeeRepository.findByStatusAndNameContainingIgnoreCaseOrEmpIdContainingIgnoreCase(StatusType.ACTIVE, queryText,queryText, pageable);
		} else {
			pages = employeeRepository.findByStatus(StatusType.ACTIVE, pageable);
		}
		
		if (pages != null) {
			Page<EmployeeDto> dtoPage = pages.map(new Function<EmployeeEntity, EmployeeDto>() {
			    @Override
			    public EmployeeDto apply(EmployeeEntity entity) {
			    	return dtoConverter.getEmployeeDto(entity);
			    }
			});
			ApiResponse<Page<EmployeeDto>> apiResponse = new ApiResponse<>();
			apiResponse.setData(dtoPage);
			return apiResponse;
		}
		throw new EmsException("Error in fetching employees with queryText="+queryText+", offset="+offset+", index="+index);
	}

	@Override
	public ApiResponse<EmployeeDto> deleteEmployee(String id) throws EmsException, EmployeeNotFoundException {
		Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(id);
		if(!optionalEmployeeEntity.isPresent())
		{
			throw new EmployeeNotFoundException("Employee not found with id="+id);
		}
		EmployeeEntity employeeEntity=optionalEmployeeEntity.get();
		employeeEntity.setStatus(StatusType.DELETED);
		employeeEntity=employeeRepository.save(employeeEntity);
		if(employeeEntity!=null)
		{
			ApiResponse<EmployeeDto> apiResponse=new ApiResponse<>();
			apiResponse.setData(dtoConverter.getEmployeeDto(optionalEmployeeEntity.get()));
			return apiResponse;	
		}
		throw new EmsException("Error in deleting employee with id="+id);
	}

}
