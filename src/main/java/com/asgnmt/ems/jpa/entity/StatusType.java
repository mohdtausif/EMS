package com.asgnmt.ems.jpa.entity;

import lombok.Getter;

@Getter
public enum StatusType {
	ACTIVE("ACTIVE"), DELETED("DELETED");

	private String value;

	private StatusType(String value) {
		this.value = value;
	}
}
