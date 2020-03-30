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
import com.asgnmt.ems.exceptions.DepartmentNotFoundException;
import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.entity.StatusType;
import com.asgnmt.ems.jpa.repository.DepartmentRepository;
import com.asgnmt.ems.services.DepartmentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class DepartmentControllerUT {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	MessageSource messages;

	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	TestHelper testHelper;

	@Test
	public void test_addDepartment_success() throws Exception{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> response = departmentService.saveDepartment(departmentDto);
		assertTrue(response.getSuccess());
		assertEquals(departmentDto.getTitle(), response.getData().getTitle());
	}
	
	@Test
	public void test_updateDepartment_success() throws Exception{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> addedDepartmentResponse = departmentService.saveDepartment(departmentDto);
		assertTrue(addedDepartmentResponse.getSuccess());
		assertEquals(departmentDto.getTitle(), addedDepartmentResponse.getData().getTitle());
		
		DepartmentDto updatedDepartmentDto=addedDepartmentResponse.getData();
		updatedDepartmentDto.setTitle("Updated Title");
		ApiResponse<DepartmentDto> updatedDepartmentResponse = departmentService.saveDepartment(updatedDepartmentDto);
		assertTrue(updatedDepartmentResponse.getSuccess());
		assertEquals(updatedDepartmentDto.getTitle(), updatedDepartmentResponse.getData().getTitle());
	}
	
	@Test
	public void test_saveDepartment_DepartmentNotFoundException() {
		DepartmentDto departmentDto = testHelper.getDepartmentDto("non_existing_department_id", "1");

		Assertions.assertThrows(DepartmentNotFoundException.class, () -> {
			ApiResponse<DepartmentDto> response = departmentService.saveDepartment(departmentDto);
		});
	}
	
	@Test
	public void test_getDepartment_success() throws Exception{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> response = departmentService.saveDepartment(departmentDto);
		assertTrue(response.getSuccess());
		assertEquals(departmentDto.getTitle(), response.getData().getTitle());
		
		ApiResponse<DepartmentDto> fetchedDepartmentResponse = departmentService.getDepartment(response.getData().getId());
		assertTrue(fetchedDepartmentResponse.getSuccess());
		assertEquals(departmentDto.getTitle(), fetchedDepartmentResponse.getData().getTitle());
	}
	
	@Test
	public void test_getDepartment_DepartmentNotFoundException() throws Exception{
		Assertions.assertThrows(DepartmentNotFoundException.class, () -> {
			ApiResponse<DepartmentDto> response = departmentService.getDepartment("non_existing_dept_id");
		});
	}
	
	@Test
	public void test_getDepartments_success() throws Exception{
		DepartmentDto departmentDto1 = testHelper.getDepartmentDto(null, "1");
		departmentService.saveDepartment(departmentDto1);
		DepartmentDto departmentDto2 = testHelper.getDepartmentDto(null, "2");
		departmentService.saveDepartment(departmentDto2);
		
		ApiResponse<Page<DepartmentDto>> response = departmentService.getDepartments(null,10,0);
		assertTrue(response.getSuccess());
		assertEquals(departmentDto1.getTitle(), response.getData().getContent().get(0).getTitle());
		assertEquals(departmentDto2.getTitle(), response.getData().getContent().get(1).getTitle());
	}
	
	@Test
	public void test_deleteDepartment_success() throws Exception{
		DepartmentDto departmentDto = testHelper.getDepartmentDto(null, "1");
		ApiResponse<DepartmentDto> response = departmentService.saveDepartment(departmentDto);
		assertTrue(response.getSuccess());
		assertEquals(departmentDto.getTitle(), response.getData().getTitle());
		
		ApiResponse<DepartmentDto> deletedDepartmentResponse = departmentService.deleteDepartment(response.getData().getId());
		assertTrue(deletedDepartmentResponse.getSuccess());
		assertEquals(departmentDto.getTitle(), deletedDepartmentResponse.getData().getTitle());
		
		Optional<DepartmentEntity> optionalDepartmentEntity=departmentRepository.findById(deletedDepartmentResponse.getData().getId());
		assertTrue(optionalDepartmentEntity.isPresent());
		assertEquals(StatusType.DELETED, optionalDepartmentEntity.get().getStatus());
	}
}
