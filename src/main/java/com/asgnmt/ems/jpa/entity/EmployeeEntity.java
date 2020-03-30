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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		result = prime * result + ((isPermanent == null) ? 0 : isPermanent.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeEntity other = (EmployeeEntity) obj;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (empId == null) {
			if (other.empId != null)
				return false;
		} else if (!empId.equals(other.empId))
			return false;
		if (isPermanent == null) {
			if (other.isPermanent != null)
				return false;
		} else if (!isPermanent.equals(other.isPermanent))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeEntity [name=" + name + ", empId=" + empId + ", isPermanent=" + isPermanent + ", emailId="
				+ emailId + ", department=" + department.getId() + "]";
	}
	
	
}
