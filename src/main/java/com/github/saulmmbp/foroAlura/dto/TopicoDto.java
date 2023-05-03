package com.github.saulmmbp.foroAlura.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.saulmmbp.foroAlura.entity.*;

import jakarta.validation.constraints.*;

public record TopicoDto(
		@JsonIgnore Long id,
		@NotBlank String titulo,
		@NotBlank String mensaje,
		LocalDateTime fechaCreacion,
		ESTADO estado,
		@NotEmpty UsuarioDto autor, 
		@NotEmpty CursoDto curso,
		Set<RespuestaDto> respuestas) {

	public Topico toEntity() {
		Topico topico = new Topico();
		topico.setId(id);
		topico.setTitulo(titulo);
		topico.setMensaje(mensaje);
		topico.setFechaCreacion(fechaCreacion);
		if (estado == null) {
			topico.setEstado(ESTADO.NO_RESPONDIDO);
		} else {
			topico.setEstado(estado);
		}
		topico.setAutor(autor.toEntity());
		topico.setCurso(curso.toEntity());
		return topico;
	}
	
}
