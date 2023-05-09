package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.CursoController;
import com.github.saulmmbp.foroAlura.dto.response.CursoResponse;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<CursoResponse, EntityModel<CursoResponse>> {

	@Override
	public EntityModel<CursoResponse> toModel(CursoResponse entity) {
		return EntityModel.of(entity, 
				linkTo(methodOn(CursoController.class).getCurso(entity.id())).withSelfRel(),
				linkTo(methodOn(CursoController.class)
						.getCursos(Integer.parseInt(ForoConstants.DEFAULT_PAGE_NUMBER), 
								Integer.parseInt(ForoConstants.DEFAULT_PAGE_SIZE), 
								ForoConstants.DEFAULT_PAGE_SORT_BY, ForoConstants.DEFAULT_PAGE_ORDER_BY))
				.withRel("cursos"));
	}

}
