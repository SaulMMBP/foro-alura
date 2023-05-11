package com.github.saulmmbp.foroAlura.dto.request;

import com.github.saulmmbp.foroAlura.entity.Respuesta;

import jakarta.validation.constraints.*;

public record RespuestaPostRequest(
		
		@NotBlank 
		String mensaje,
		
		@NotNull
		Long autorId,
		
		@NotNull
		Long topicoId 
		
		) {

	public Respuesta toEntity() {
		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(mensaje);
		return respuesta;
	}
}
