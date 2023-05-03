package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.CursoController;
import com.github.saulmmbp.foroAlura.dto.CursoDto;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<CursoDto, EntityModel<CursoDto>> {

	@Override
	public EntityModel<CursoDto> toModel(CursoDto entity) {
		return EntityModel.of(entity, 
				linkTo(methodOn(CursoController.class).getCurso(entity.id())).withSelfRel(),
				linkTo(methodOn(CursoController.class).getCursos()).withRel("cursos"));
	}

}
