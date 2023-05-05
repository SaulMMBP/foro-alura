package com.github.saulmmbp.foroAlura.dto.response;

import org.springframework.validation.FieldError;

public record ErrorResponse(String campo, String error) {
	public ErrorResponse(FieldError fieldError) {
		this(fieldError.getField(), fieldError.getDefaultMessage());
	}
}
