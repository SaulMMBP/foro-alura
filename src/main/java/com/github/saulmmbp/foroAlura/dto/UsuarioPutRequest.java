package com.github.saulmmbp.foroAlura.dto;

import jakarta.validation.constraints.NotNull;

public record UsuarioPutRequest(
		
		@NotNull
		Long id,

		String nombre,
		
		String email,
		
		String contrasena
		
		) {

}
