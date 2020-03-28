package com.asgnmt.ems.exceptions;

public class EmployeeNotFoundException extends Exception {
	public static final int ERROR_CODE=5001;
	public EmployeeNotFoundException(String message) {super(message);}
	public EmployeeNotFoundException(String message, Throwable throwable) {super(message,throwable);}
}
