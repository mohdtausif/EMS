package com.asgnmt.ems.ut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.asgnmt.ems.TestHelper;
import com.asgnmt.ems.dto.ApiResponse;
import com.asgnmt.ems.dto.DepartmentDto;
import com.asgnmt.ems.dto.EmployeeDto;
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.exceptions.EmployeeNotFoundException;
import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.entity.EmployeeEntity;
import com.asgnmt.ems.jpa.entity.StatusType;
import com.asgnmt.ems.jpa.repository.DepartmentRepository;
import com.asgnmt.ems.jpa.repository.EmployeeRepository;
import com.asgnmt.ems.services.DepartmentService;
import com.asgnmt.ems.services.EmployeeService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EmployeeControllerUT {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	MessageSource messages;

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	TestHelper testHelper;

	@Test
	public void test_addEmployee_success() throws Exception
	{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> addDepartmentResponse = departmentService.saveDepartment(departmentDto);
		EmployeeDto employeeDto=testHelper.getEmployeeDto(addDepartmentResponse.getData().getId(), "1");
		
		ApiResponse<EmployeeDto> addEmployeeResponse = employeeService.saveEmployee(employeeDto);
		assertTrue(addEmployeeResponse.getSuccess());
		assertEquals(employeeDto.getEmpId(), addEmployeeResponse.getData().getEmpId());
	}
	
	@Test
	public void test_updateEmployee_success() throws Exception
	{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> addDepartmentResponse = departmentService.saveDepartment(departmentDto);
		EmployeeDto employeeDto=testHelper.getEmployeeDto(addDepartmentResponse.getData().getId(), "1");
		
		String updatedName="Updated Name";
		employeeDto.setName(updatedName);
		
		ApiResponse<EmployeeDto> updatedEmployeeResponse = employeeService.saveEmployee(employeeDto);
		assertTrue(updatedEmployeeResponse.getSuccess());
		assertEquals(employeeDto.getEmpId(), updatedEmployeeResponse.getData().getEmpId());
		assertEquals(updatedName, updatedEmployeeResponse.getData().getName());
	}
	
	@Test
	public void test_addEmployee_DepartmentNotFoundException() throws Exception
	{
		EmployeeDto employeeDto=testHelper.getEmployeeDto("non_existing_department_id", "1");
		Assertions.assertThrows(DepartmentNotFoundException.class, () -> {
			employeeService.saveEmployee(employeeDto);
		});
	}
	
	@Test
	public void test_addEmployee_EmployeeNotFoundException() throws Exception
	{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> addDepartmentResponse = departmentService.saveDepartment(departmentDto);
		EmployeeDto employeeDto=testHelper.getEmployeeDto(addDepartmentResponse.getData().getId(), "1");
		employeeDto.setId("non_existing_employee_id");
		Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
			employeeService.saveEmployee(employeeDto);
		});
	}
	
	@Test
	public void test_getEmployee_success() throws Exception
	{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> addDepartmentResponse = departmentService.saveDepartment(departmentDto);
		EmployeeDto employeeDto=testHelper.getEmployeeDto(addDepartmentResponse.getData().getId(), "1");
		ApiResponse<EmployeeDto> addEmployeeResponse = employeeService.saveEmployee(employeeDto);
		
		ApiResponse<EmployeeDto> fetchedEmployeeResponse = employeeService.getEmployee(addEmployeeResponse.getData().getId());
		
		assertTrue(fetchedEmployeeResponse.getSuccess());
		assertEquals(employeeDto.getEmpId(), fetchedEmployeeResponse.getData().getEmpId());
	}
	
	@Test
	public void test_getEmployee_EmployeeNotFoundException() throws Exception
	{
		Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
			employeeService.getEmployee("non_existing_employee_id");
		});
	}
	
	@Test
	public void test_getEmployees_success() throws Exception
	{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> addDepartmentResponse = departmentService.saveDepartment(departmentDto);
		
		EmployeeDto employeeDto1=testHelper.getEmployeeDto(addDepartmentResponse.getData().getId(), "1");
		ApiResponse<EmployeeDto> addEmployeeResponse1 = employeeService.saveEmployee(employeeDto1);
		
		EmployeeDto employeeDto2=testHelper.getEmployeeDto(addDepartmentResponse.getData().getId(), "2");
		ApiResponse<EmployeeDto> addEmployeeResponse2 = employeeService.saveEmployee(employeeDto2);
		
		ApiResponse<Page<EmployeeDto>> fetchedEmployeesResponse = employeeService.getEmployees(null, 10,0);
		
		assertTrue(fetchedEmployeesResponse.getSuccess());
		assertEquals(employeeDto1.getEmpId(), fetchedEmployeesResponse.getData().getContent().get(0).getEmpId());
		assertEquals(employeeDto2.getEmpId(), fetchedEmployeesResponse.getData().getContent().get(1).getEmpId());
	}
	
	@Test
	public void test_deleteEmployee_success() throws Exception
	{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> addDepartmentResponse = departmentService.saveDepartment(departmentDto);
		EmployeeDto employeeDto=testHelper.getEmployeeDto(addDepartmentResponse.getData().getId(), "1");
		ApiResponse<EmployeeDto> addedEmployeeResponse = employeeService.saveEmployee(employeeDto);
		
		Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(addedEmployeeResponse.getData().getId());
		assertTrue(optionalEmployeeEntity.isPresent());
		assertEquals(StatusType.ACTIVE, optionalEmployeeEntity.get().getStatus());
		
		ApiResponse<EmployeeDto> deletedEmployeeResponse = employeeService.deleteEmployee(addedEmployeeResponse.getData().getId());
		
		Optional<EmployeeEntity> optionalDeletedEmployeeEntity=employeeRepository.findById(addedEmployeeResponse.getData().getId());
		assertTrue(optionalDeletedEmployeeEntity.isPresent());
		assertEquals(StatusType.DELETED, optionalDeletedEmployeeEntity.get().getStatus());
	}
}
