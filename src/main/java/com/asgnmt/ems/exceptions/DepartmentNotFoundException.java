package com.asgnmt.ems.exceptions;

public class DepartmentNotFoundException extends Exception {
	public static final int ERROR_CODE=5002;
	public DepartmentNotFoundException(String message) {super(message);}
	public DepartmentNotFoundException(String message, Throwable throwable) {super(message,throwable);}
}
