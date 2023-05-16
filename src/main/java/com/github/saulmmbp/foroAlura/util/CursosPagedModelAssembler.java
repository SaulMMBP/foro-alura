package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.CursoController;
import com.github.saulmmbp.foroAlura.dto.response.CursoResponse;

@Component
public class CursosPagedModelAssembler
		implements RepresentationModelAssembler<Page<CursoResponse>, PagedModel<CursoResponse>> {

	@Override
	public PagedModel<CursoResponse> toModel(Page<CursoResponse> entity) {
		PagedModel<CursoResponse> pagedModel = PagedModel.of(entity.getContent(), 
				new PageMetadata(entity.getSize(), entity.getNumber(), entity.getTotalElements(), entity.getTotalPages()),
				linkTo(methodOn(CursoController.class).getCursos(entity.getPageable())).withSelfRel());
		
		if (entity.hasNext()) {
			pagedModel.add(linkTo(methodOn(CursoController.class).getCursos(entity.getPageable().next())).withRel("next"));
		}
		if (entity.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(CursoController.class).getCursos(entity.getPageable().previousOrFirst())).withRel("previous"));
		}
		
		return pagedModel;
	}

}
