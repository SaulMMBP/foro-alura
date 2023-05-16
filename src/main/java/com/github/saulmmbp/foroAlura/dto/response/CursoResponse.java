package com.github.saulmmbp.foroAlura.dto.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.core.Relation;

import com.github.saulmmbp.foroAlura.controller.CursoController;
import com.github.saulmmbp.foroAlura.entity.Curso;

import lombok.*;

@Getter
@Setter
@Relation(collectionRelation = "cursos", itemRelation = "curso")
public final class CursoResponse extends RepresentationModel<CursoResponse> {
	
	private Long id;
	private String nombre;
	private String categoria;
	private UsuarioResponse instructor;
	
	public CursoResponse(Curso curso, Link... links) {
		this.id = curso.getId();
		this.nombre = curso.getNombre();
		this.categoria = curso.getCategoria();
		this.instructor = curso.getInstructor().toResponse();
		
		this.add(linkTo(methodOn(CursoController.class).getCurso(id)).withSelfRel());
		
		this.add(links);
	}
}
