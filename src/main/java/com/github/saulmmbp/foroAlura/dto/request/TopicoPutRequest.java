package com.github.saulmmbp.foroAlura.dto.request;

import com.github.saulmmbp.foroAlura.entity.ESTADO;

import jakarta.validation.constraints.NotNull;

public record TopicoPutRequest(
		
		@NotNull
		Long id,
		
		String titulo,
		
		String mensaje,
		
		ESTADO estado
		
		) {
	
}
