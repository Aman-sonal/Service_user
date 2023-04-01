package com.restaurant.demo.Advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.restaurant.demo.exception.BusinessException;
import com.restaurant.demo.exception.NoDataFoundException;
import com.restaurant.demo.exception.UserNotFoundException;

import jakarta.validation.ConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<?> handleNoDataException(BusinessException be) {
		return new ResponseEntity<String>("No Data Found", HttpStatus.ACCEPTED);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(BusinessException be) {
		return new ResponseEntity<String>("Sorry! User not found", HttpStatus.BAD_GATEWAY);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException be) {
		return new ResponseEntity<String>(be.getErrMsg(), HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleValidationException(ConstraintViolationException exp){
		return new ResponseEntity<String>(exp.toString(),HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
		 super.handleMethodArgumentNotValid(ex, headers, status, request);
		 return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
}