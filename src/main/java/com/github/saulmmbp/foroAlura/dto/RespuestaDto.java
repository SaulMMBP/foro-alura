package com.github.saulmmbp.foroAlura.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.saulmmbp.foroAlura.entity.*;

import jakarta.validation.constraints.*;

public record RespuestaDto(
		@JsonIgnore Long id,
		@NotBlank String mensaje,
		LocalDateTime fechaCreacion,
		Boolean solucion,
		@NotNull UsuarioDto autor,
		@JsonIgnore @NotNull Long topico_id
		) {

	public Respuesta toEntity() {
		Respuesta respuesta = new Respuesta();
		respuesta.setId(id);
		respuesta.setMensaje(mensaje);
		respuesta.setFechaCreacion(fechaCreacion);
		if (solucion == null) {
			respuesta.setSolucion(false);
		} else {
			respuesta.setSolucion(solucion);
		}
		respuesta.setAutor(autor.toEntity());
		return respuesta;
	}
}
