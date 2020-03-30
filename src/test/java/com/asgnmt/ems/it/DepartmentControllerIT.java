package com.asgnmt.ems.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.asgnmt.ems.TestIntegrationConfig;
import com.asgnmt.ems.configs.WebConfig;
import com.asgnmt.ems.constants.Constants;
import com.asgnmt.ems.dto.DepartmentDto;
import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.repository.DepartmentRepository;
import com.asgnmt.ems.utils.JsonUtils;

@SpringBootTest(classes = { EmployeeManagementSystemApplication.class, TestIntegrationConfig.class,
		WebConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class DepartmentControllerIT {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	MessageSource messages;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void test_saveDepartment() throws Exception {
		DepartmentDto requestDto = new DepartmentDto(null, "R&D");
		this.mockMvc
				.perform(post(Constants.API_VERSION+Constants.URL_SAVE_DEPARTMENT)
						.content(JsonUtils.getJsonStrFromObject(requestDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data.title").value(requestDto.getTitle()))
				.andExpect(jsonPath("$.data.id").isString());
	}
	
	@Test
	public void test_updateDepartment() throws Exception {
		String actualTitle="R&D";
		String updatedTitle="Software";
		DepartmentEntity departmentEntity=new DepartmentEntity();
		departmentEntity.setTitle(actualTitle);
		departmentEntity=departmentRepository.save(departmentEntity);
		assertTrue(departmentEntity.getId()!=null && departmentEntity.getId().trim().length()>0);
		assertEquals(actualTitle, departmentEntity.getTitle());
		departmentEntity.setTitle(updatedTitle);
		this.mockMvc
				.perform(post(Constants.API_VERSION+Constants.URL_SAVE_DEPARTMENT)
						.content(JsonUtils.getJsonStrFromObject(departmentEntity))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data.title").value(updatedTitle))
				.andExpect(jsonPath("$.data.id").isString());
		
		Optional<DepartmentEntity> optionalDepartmentEntity=departmentRepository.findById(departmentEntity.getId());
		assertTrue(optionalDepartmentEntity.isPresent());
		assertEquals(updatedTitle, optionalDepartmentEntity.get().getTitle());
	}
	
	@Test
	public void test_getDepartment() throws Exception {
		DepartmentEntity departmentEntity=new DepartmentEntity();
		departmentEntity.setTitle("R&D");
		departmentEntity=departmentRepository.save(departmentEntity);
		
		this.mockMvc
				.perform(get(Constants.API_VERSION+Constants.URL_GET_DEPARTMENT, departmentEntity.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data.title").value(departmentEntity.getTitle()))
				.andExpect(jsonPath("$.data.id").isString());
	}
	
	@Test
	public void test_getDepartments() throws Exception {
		DepartmentEntity departmentEntity1=new DepartmentEntity();
		departmentEntity1.setTitle("R&D");
		entityManager.persist(departmentEntity1);
		
		DepartmentEntity departmentEntity2=new DepartmentEntity();
		departmentEntity2.setTitle("SOFTWARE");
		entityManager.persist(departmentEntity2);
		
		int offset=10;
		int index=0;
		String queryText=null;
		
		this.mockMvc
				.perform(get(Constants.API_VERSION+Constants.URL_GET_DEPARTMENTS+"?queryText="+queryText, offset, index)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data.content[0].id").value(departmentEntity1.getId()))
				.andExpect(jsonPath("$.data.content[0].title").value(departmentEntity1.getTitle()))
				.andExpect(jsonPath("$.data.content[1].id").value(departmentEntity2.getId()))
				.andExpect(jsonPath("$.data.content[1].title").value(departmentEntity2.getTitle()));
	}
}
