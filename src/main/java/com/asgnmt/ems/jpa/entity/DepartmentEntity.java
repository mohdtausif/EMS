package com.asgnmt.ems.jpa.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity
@Table(name = "department")
public class DepartmentEntity extends BaseEntity {
	@Column(name="title", length = 50)
	private String title;
	
	@OneToMany(mappedBy = "department")
	private Set<EmployeeEntity> employees=new HashSet<>();
}
