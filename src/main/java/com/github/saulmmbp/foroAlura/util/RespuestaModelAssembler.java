package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.*;
import com.github.saulmmbp.foroAlura.dto.response.RespuestaResponse;

@Component
public class RespuestaModelAssembler implements RepresentationModelAssembler<RespuestaResponse, EntityModel<RespuestaResponse>> {

	@Override
	public EntityModel<RespuestaResponse> toModel(RespuestaResponse entity) {
		return EntityModel.of(entity, 
				linkTo(methodOn(RespuestaController.class).getRespuesta(entity.id())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).getUsuario(entity.autor().id())).withRel("autor"),
				linkTo(methodOn(TopicoController.class).getTopico(entity.topico_id())).withRel("topico"),
				linkTo(methodOn(RespuestaController.class)
						.getRespuestas(Integer.parseInt(ForoConstants.DEFAULT_PAGE_NUMBER), 
								Integer.parseInt(ForoConstants.DEFAULT_PAGE_SIZE), 
								ForoConstants.DEFAULT_PAGE_SORT_BY, ForoConstants.DEFAULT_PAGE_ORDER_BY))
				.withRel("respuestas"));
	}

}
