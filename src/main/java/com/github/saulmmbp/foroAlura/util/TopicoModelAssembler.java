package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.*;
import com.github.saulmmbp.foroAlura.dto.response.TopicoResponse;

@Component
public class TopicoModelAssembler implements RepresentationModelAssembler<TopicoResponse, EntityModel<TopicoResponse>> {

	@Override
	public EntityModel<TopicoResponse> toModel(TopicoResponse entity) {
		return EntityModel.of(entity, 
				linkTo(methodOn(TopicoController.class).getTopico(entity.id())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).getUsuario(entity.autor().id())).withRel("autor"),
				linkTo(methodOn(CursoController.class).getCurso(entity.curso().id())).withRel("curso"),
				linkTo(methodOn(TopicoController.class)
						.getTopicos(Integer.parseInt(ForoConstants.DEFAULT_PAGE_NUMBER), 
							Integer.parseInt(ForoConstants.DEFAULT_PAGE_SIZE), 
							ForoConstants.DEFAULT_PAGE_SORT_BY, ForoConstants.DEFAULT_PAGE_ORDER_BY))
				.withRel("topicos"));
	}

}
