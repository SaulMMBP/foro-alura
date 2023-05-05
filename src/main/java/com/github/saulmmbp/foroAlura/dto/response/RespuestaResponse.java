package com.github.saulmmbp.foroAlura.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Relation(collectionRelation = "respuestas", itemRelation = "respuesta")
public record RespuestaResponse(
		
		Long id,

		String mensaje,
		
		LocalDateTime fechaCreacion,
		
		Boolean solucion,
		
		UsuarioResponse autor,
		
		@JsonIgnore
		Long topico_id
		
		) {

}
