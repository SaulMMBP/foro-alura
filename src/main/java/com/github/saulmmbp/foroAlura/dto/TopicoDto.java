package com.github.saulmmbp.foroAlura.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.saulmmbp.foroAlura.entity.*;

import jakarta.validation.constraints.*;

public record TopicoDto(
		@JsonIgnore Long id,
		@NotBlank String titulo,
		@NotBlank String mensaje,
		LocalDate fechaCreacion,
		ESTADO estado,
		@NotEmpty String autor, 
		@NotEmpty String curso) {

	public Topico toEntity() {
		Topico topico = new Topico();
		topico.setId(id);
		topico.setTitulo(titulo);
		topico.setMensaje(mensaje);
		topico.setFechaCreacion(fechaCreacion);
		if (estado == null) {
			topico.setEstado(ESTADO.NOSOLUCIONADO);
		}
		topico.setAutor(autor);
		topico.setCurso(curso);
		return topico;
	}
	
}
