package com.github.saulmmbp.foroAlura.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.saulmmbp.foroAlura.entity.ESTADO;

@Relation(collectionRelation = "topicos", itemRelation = "topico")
public record TopicoResponse(
		
		Long id,
		
		String titulo,
		
		String mensaje,
		
		LocalDateTime fechaCreacion,
		
		ESTADO estado,
		
		UsuarioResponse autor, 
		
		CursoResponse curso,
		
		@JsonIgnoreProperties("topico")
		Set<RespuestaResponse> respuestas
		
		) {

}
