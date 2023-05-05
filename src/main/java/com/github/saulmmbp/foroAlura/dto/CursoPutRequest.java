package com.github.saulmmbp.foroAlura.dto;

import jakarta.validation.constraints.NotNull;

public record CursoPutRequest(
		
		@NotNull
		Long id,
		
		String nombre,
		
		String categoria
		
		) {

}
