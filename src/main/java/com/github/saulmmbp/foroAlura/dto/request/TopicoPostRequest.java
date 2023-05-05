package com.github.saulmmbp.foroAlura.dto.request;

import com.github.saulmmbp.foroAlura.entity.Topico;

import jakarta.validation.constraints.*;

public record TopicoPostRequest(
		
		@NotBlank 
		String titulo,
		
		@NotBlank 
		String mensaje,
		
		@NotNull
		Long autor_id,
		
		@NotNull
		Long curso_id
		
		) {

	public Topico toEntity() {
		Topico topico = new Topico();
		topico.setTitulo(titulo);
		topico.setMensaje(mensaje);
		return topico;
	}
	
}
