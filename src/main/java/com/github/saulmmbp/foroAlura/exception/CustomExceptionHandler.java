package com.github.saulmmbp.foroAlura.exception;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.github.saulmmbp.foroAlura.dto.response.ErrorResponse;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> notFoundHandler() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> argumentNotValidHandler(MethodArgumentNotValidException e) {
		List<?> errores = e.getFieldErrors().stream().map(ErrorResponse::new).collect(Collectors.toList());
		return ResponseEntity.badRequest().body(errores);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> argumentTypeMismatchHandler(MethodArgumentTypeMismatchException e) {
		ErrorResponse error = new ErrorResponse(e.getPropertyName(), 
				"El valor debe ser del tipo " + e.getRequiredType().getSimpleName() + " o equivalente");
		return ResponseEntity.badRequest().body(error);
	}
}
