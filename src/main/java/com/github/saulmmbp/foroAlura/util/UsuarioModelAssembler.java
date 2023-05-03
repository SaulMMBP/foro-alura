package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.UsuarioController;
import com.github.saulmmbp.foroAlura.dto.UsuarioDto;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioDto, EntityModel<UsuarioDto>> {

	@Override
	public EntityModel<UsuarioDto> toModel(UsuarioDto entity) {
		return EntityModel.of(entity, 
				linkTo(methodOn(UsuarioController.class).getUsuario(entity.id())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).getUsuarios()).withRel("usuarios"));
	}

}
