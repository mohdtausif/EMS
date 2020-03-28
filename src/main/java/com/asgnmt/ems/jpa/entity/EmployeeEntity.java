package com.asgnmt.ems.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "employee")
public class EmployeeEntity extends BaseEntity {
	@Column(name="name", length = 50)
	private String name;
	
	@Column(name="emp_id", length = 10, unique = true)
	private String empId;
	
	@Type(type="yes_no")
    @ColumnDefault("'N'")
	private Boolean isPermanent;
	
	@Column(name="email_id", length = 50, unique = true)
	private String emailId;
	
	@ManyToOne
	@JoinColumn(name = "dept_id")
	private DepartmentEntity department; 
}
