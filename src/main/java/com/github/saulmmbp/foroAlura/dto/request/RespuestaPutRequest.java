package com.github.saulmmbp.foroAlura.dto.request;

import jakarta.validation.constraints.NotNull;

public record RespuestaPutRequest(
		
		@NotNull
		Long id,
		
		String mensaje,
		
		Boolean solucion,
		
		@NotNull
		Long autorId
		
		) {

}
