package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.UsuarioController;
import com.github.saulmmbp.foroAlura.dto.response.UsuarioResponse;

@Component
public class UsuariosPagedModelAssembler
		implements RepresentationModelAssembler<Page<UsuarioResponse>, PagedModel<UsuarioResponse>> {

	@Override
	public PagedModel<UsuarioResponse> toModel(Page<UsuarioResponse> entity) {
		PagedModel<UsuarioResponse> pagedModel = PagedModel.of(entity.getContent(), 
				new PageMetadata(entity.getSize(), entity.getNumber(), entity.getTotalElements(), entity.getTotalPages()),
				linkTo(methodOn(UsuarioController.class).getUsuarios(entity.getPageable())).withSelfRel());
		
		if (entity.hasNext()) {
			pagedModel.add(linkTo(methodOn(UsuarioController.class).getUsuarios(entity.getPageable().next())).withRel("next"));
		}
		if (entity.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(UsuarioController.class).getUsuarios(entity.getPageable().previousOrFirst())).withRel("previous"));
		}
		
		return pagedModel;
	}

}
