package com.asgnmt.ems.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.asgnmt.ems.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackages = "com.asgnmt.ems")
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ EmployeeNotFoundException.class })
	public ResponseEntity<ApiResponse<String>> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
		log.error("EmployeeNotFoundException", e);
		return new ResponseEntity<>(new ApiResponse(e.ERROR_CODE, e.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ DepartmentNotFoundException.class })
	public ResponseEntity<ApiResponse<String>> handleDepartmentNotFoundException(DepartmentNotFoundException e) {
		log.error("DepartmentNotFoundException", e);
		return new ResponseEntity<>(new ApiResponse(e.ERROR_CODE, e.getMessage()),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({ EmsException.class })
	public ResponseEntity<ApiResponse<String>> handleEmsException(EmsException e) {
		log.error("EmsException", e);
		return new ResponseEntity<>(new ApiResponse(e.ERROR_CODE, e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

//////////////////////////////////////////////////
//////////////   Generic Exception   /////////////
//////////////////////////////////////////////////
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ApiResponse> handleException(Exception e) {
		log.error("Exception", e);
		return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
