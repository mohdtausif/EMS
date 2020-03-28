package com.asgnmt.ems.exceptions;

public class EmsException extends Exception {
	public static final int ERROR_CODE=5000;
	public EmsException(String message) {super(message);}
	public EmsException(String message, Throwable throwable) {super(message,throwable);}
}
