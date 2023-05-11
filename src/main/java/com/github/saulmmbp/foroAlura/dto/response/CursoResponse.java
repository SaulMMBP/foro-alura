package com.github.saulmmbp.foroAlura.dto.response;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cursos", itemRelation = "curso")
public record CursoResponse(
		
		Long id,
		
		String nombre,
		
		String categoria,
		
		UsuarioResponse instructor
		
		) {

}
