package com.asgnmt.ems.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.asgnmt.ems.jpa.entity.DepartmentEntity;
import com.asgnmt.ems.jpa.entity.EmployeeEntity;
import com.asgnmt.ems.jpa.entity.StatusType;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<DepartmentEntity, String> {

	Page<DepartmentEntity> findByStatusAndTitleContainingIgnoreCase(StatusType status, String queryText, Pageable pageable);

	Page<DepartmentEntity> findByStatus(StatusType active, Pageable pageable);
	
	

}
