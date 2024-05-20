/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:1:41 PM
 */
package com.chatserver.exceptionhandler;

import com.chatserver.exception.MessageNotFoundException;
import com.chatserver.exception.UserNotFoundException;
import com.chatserver.presentation.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;

@RestControllerAdvice
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlobalExceptionHandler {
	static String space = " ";

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUsernameNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handlePasswordMismatchException(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password Mismatch");
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("please try again");
	}

	@ExceptionHandler(MessageNotFoundException.class)
	public ResponseEntity<String> handleMessageNotFoundException(MessageNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	ResponseEntity<ErrorResponse> handleConstrainViolation(final ConstraintViolationException exception) {
		log.error("An error occurred: {}", exception.getMessage(), exception);
		return new ResponseEntity<>(new ErrorResponse(buildErrors(exception).toString()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
		log.error("An error occurred: {}", exception.getMessage(), exception);
		return new ResponseEntity<>(new ErrorResponse(buildErrors(exception).toString()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("please try again");
	}

	private StringJoiner buildErrors(final ConstraintViolationException exception) {
		var errors = new StringJoiner(",");
		for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
			var propertyPath = constraintViolation.getPropertyPath().toString();
			var errorViolation = propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
			errors.add(errorViolation + space + constraintViolation.getMessage());
		}
		return errors;
	}

	private StringJoiner buildErrors(final MethodArgumentNotValidException exception) {
		var errors = new StringJoiner(",");
		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
			errors.add(fieldError.getDefaultMessage());
		}
		return errors;
	}
}
