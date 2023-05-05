package com.github.saulmmbp.foroAlura.dto.response;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios", itemRelation = "usuario")
public record UsuarioResponse(
		
		Long id,
		
		String nombre,
		
		String email
		
		) {
	
}
