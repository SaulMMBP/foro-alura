package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.UsuarioController;
import com.github.saulmmbp.foroAlura.dto.response.UsuarioResponse;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioResponse, EntityModel<UsuarioResponse>> {

	@Override
	public EntityModel<UsuarioResponse> toModel(UsuarioResponse entity) {
		return EntityModel.of(entity, 
				linkTo(methodOn(UsuarioController.class).getUsuario(entity.id())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).getUsuarios(Integer.parseInt(ForoConstants.DEFAULT_PAGE_NUMBER), 
						Integer.parseInt(ForoConstants.DEFAULT_PAGE_SIZE), 
						ForoConstants.DEFAULT_PAGE_SORT_BY, ForoConstants.DEFAULT_PAGE_ORDER_BY))
				.withRel("usuarios"));
	}

}
