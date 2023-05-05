package com.github.saulmmbp.foroAlura.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
		
		@NotBlank
		String email,
		
		@NotBlank
		String contrasena
		
		) {

}
