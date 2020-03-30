package com.asgnmt.ems.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.asgnmt.ems.EmployeeManagementSystemApplication;
import com.asgnmt.ems.TestHelper;
import com.asgnmt.ems.TestIntegrationConfig;
import com.asgnmt.ems.configs.WebConfig;
import com.asgnmt.ems.constants.Constants;
import com.asgnmt.ems.dto.EmployeeDto;
import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.entity.EmployeeEntity;
import com.asgnmt.ems.jpa.entity.StatusType;
import com.asgnmt.ems.jpa.repository.DepartmentRepository;
import com.asgnmt.ems.jpa.repository.EmployeeRepository;
import com.asgnmt.ems.utils.DtoConverter;
import com.asgnmt.ems.utils.JsonUtils;
import com.mysql.cj.util.TestUtils;

@SpringBootTest(classes = { EmployeeManagementSystemApplication.class, TestIntegrationConfig.class,
		WebConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class EmployeeControllerIT {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	MessageSource messages;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	DtoConverter dtoConverter;
	
	@Autowired
	TestHelper testHelper;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void test_saveEmployee() throws Exception {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setTitle("R&D");
		departmentEntity = departmentRepository.save(departmentEntity);

		EmployeeDto employeeDto = testHelper.getEmployeeDto(departmentEntity.getId(),"1");
		
		this.mockMvc
				.perform(post(Constants.API_VERSION + Constants.URL_SAVE_EMPLOYEE)
						.content(JsonUtils.getJsonStrFromObject(employeeDto)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data.name").value(employeeDto.getName()))
				.andExpect(jsonPath("$.data.empId").value(employeeDto.getEmpId()))
				.andExpect(jsonPath("$.data.isPermanent").value(employeeDto.getIsPermanent()))
				.andExpect(jsonPath("$.data.emailId").value(employeeDto.getEmailId()))
				.andExpect(jsonPath("$.data.departmentId").value(employeeDto.getDepartmentId()))
				.andExpect(jsonPath("$.data.id").isString());
	}

	@Test
	public void test_updateEmployee() throws Exception {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setTitle("R&D");
		departmentEntity = departmentRepository.save(departmentEntity);
		
		EmployeeEntity employeeEntity=testHelper.getEmployeeEntity(departmentEntity,"1");
		employeeEntity=employeeRepository.save(employeeEntity);
		
		EmployeeDto employeeDto=dtoConverter.getEmployeeDto(employeeEntity);
		String updatedName="UpdatedName";
		employeeDto.setName(updatedName);
		
		this.mockMvc
				.perform(post(Constants.API_VERSION + Constants.URL_SAVE_EMPLOYEE)
						.content(JsonUtils.getJsonStrFromObject(employeeDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data.name").value(updatedName))
				.andExpect(jsonPath("$.data.empId").value(employeeEntity.getEmpId()))
				.andExpect(jsonPath("$.data.isPermanent").value(employeeEntity.getIsPermanent()))
				.andExpect(jsonPath("$.data.emailId").value(employeeEntity.getEmailId()))
				.andExpect(jsonPath("$.data.departmentId").value(employeeEntity.getDepartment().getId()))
				.andExpect(jsonPath("$.data.id").value(employeeEntity.getId()));

	}

	@Test
	public void test_getEmployee() throws Exception {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setTitle("R&D");
		departmentEntity = departmentRepository.save(departmentEntity);
		
		EmployeeEntity employeeEntity=testHelper.getEmployeeEntity(departmentEntity,"1");
		employeeEntity=employeeRepository.save(employeeEntity);

		this.mockMvc
				.perform(get(Constants.API_VERSION + Constants.URL_GET_EMPLOYEE, employeeEntity.getId())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data.name").value(employeeEntity.getName()))
				.andExpect(jsonPath("$.data.empId").value(employeeEntity.getEmpId()))
				.andExpect(jsonPath("$.data.isPermanent").value(employeeEntity.getIsPermanent()))
				.andExpect(jsonPath("$.data.emailId").value(employeeEntity.getEmailId()))
				.andExpect(jsonPath("$.data.departmentId").value(employeeEntity.getDepartment().getId()))
				.andExpect(jsonPath("$.data.id").value(employeeEntity.getId()));
	}

	@Test
	public void test_getEmployees() throws Exception {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setTitle("R&D");
		entityManager.persist(departmentEntity);
		
		EmployeeEntity employeeEntity1=testHelper.getEmployeeEntity(departmentEntity,"1");
		entityManager.persist(employeeEntity1);
		
		EmployeeEntity employeeEntity2=testHelper.getEmployeeEntity(departmentEntity,"2");
		entityManager.persist(employeeEntity2);

		int offset = 10;
		int index = 0;
		String queryText = null;

		this.mockMvc
				.perform(get(Constants.API_VERSION + Constants.URL_GET_EMPLOYEES + "?queryText=" + queryText, offset,
						index).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.errorCode").value(0))
				
				.andExpect(jsonPath("$.data.content[0].name").value(employeeEntity1.getName()))
				.andExpect(jsonPath("$.data.content[0].empId").value(employeeEntity1.getEmpId()))
				.andExpect(jsonPath("$.data.content[0].isPermanent").value(employeeEntity1.getIsPermanent()))
				.andExpect(jsonPath("$.data.content[0].emailId").value(employeeEntity1.getEmailId()))
				.andExpect(jsonPath("$.data.content[0].departmentId").value(employeeEntity1.getDepartment().getId()))
				
				.andExpect(jsonPath("$.data.content[1].name").value(employeeEntity2.getName()))
				.andExpect(jsonPath("$.data.content[1].empId").value(employeeEntity2.getEmpId()))
				.andExpect(jsonPath("$.data.content[1].isPermanent").value(employeeEntity2.getIsPermanent()))
				.andExpect(jsonPath("$.data.content[1].emailId").value(employeeEntity2.getEmailId()))
				.andExpect(jsonPath("$.data.content[1].departmentId").value(employeeEntity2.getDepartment().getId()))
				;
	}
	
	@Test
	public void test_deleteEmployee() throws Exception {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setTitle("R&D");
		departmentEntity = departmentRepository.save(departmentEntity);
		
		EmployeeEntity employeeEntity=testHelper.getEmployeeEntity(departmentEntity,"1");
		employeeEntity=employeeRepository.save(employeeEntity);

		this.mockMvc
				.perform(delete(Constants.API_VERSION + Constants.URL_DELETE_EMPLOYEE, employeeEntity.getId())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data.name").value(employeeEntity.getName()))
				.andExpect(jsonPath("$.data.empId").value(employeeEntity.getEmpId()))
				.andExpect(jsonPath("$.data.isPermanent").value(employeeEntity.getIsPermanent()))
				.andExpect(jsonPath("$.data.emailId").value(employeeEntity.getEmailId()))
				.andExpect(jsonPath("$.data.departmentId").value(employeeEntity.getDepartment().getId()))
				.andExpect(jsonPath("$.data.id").value(employeeEntity.getId()));
		
		Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(employeeEntity.getId());
		assertTrue(optionalEmployeeEntity.isPresent());
		assertEquals(StatusType.DELETED, optionalEmployeeEntity.get().getStatus());
	}
}
