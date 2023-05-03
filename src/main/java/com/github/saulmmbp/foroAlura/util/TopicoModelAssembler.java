package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.TopicoController;
import com.github.saulmmbp.foroAlura.dto.TopicoDto;

@Component
public class TopicoModelAssembler implements RepresentationModelAssembler<TopicoDto, EntityModel<TopicoDto>> {

	@Override
	public EntityModel<TopicoDto> toModel(TopicoDto entity) {
		return EntityModel.of(entity, 
				linkTo(methodOn(TopicoController.class).getTopico(entity.id())).withSelfRel(),
				linkTo(methodOn(TopicoController.class).getTopicos()).withRel("topicos"));
	}

}
