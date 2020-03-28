package com.asgnmt.ems.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.asgnmt.ems.jpa.entity.EmployeeEntity;
import com.asgnmt.ems.jpa.entity.StatusType;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, String> {

	Page<EmployeeEntity> findByStatusAndNameContainingIgnoreCaseOrEmpIdContainingIgnoreCase(StatusType active,
			String queryText, String queryText2, Pageable pageable);

	Page<EmployeeEntity> findByStatus(StatusType active, Pageable pageable);
	
	

}
