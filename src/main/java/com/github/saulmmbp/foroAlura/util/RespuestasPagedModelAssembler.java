package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.RespuestaController;
import com.github.saulmmbp.foroAlura.dto.response.RespuestaResponse;

@Component
public class RespuestasPagedModelAssembler
		implements RepresentationModelAssembler<Page<RespuestaResponse>, PagedModel<RespuestaResponse>> {

	@Override
	public PagedModel<RespuestaResponse> toModel(Page<RespuestaResponse> entity) {
		PagedModel<RespuestaResponse> pagedModel = PagedModel.of(entity.getContent(), 
				new PageMetadata(entity.getSize(), entity.getNumber(), entity.getTotalElements(), entity.getTotalPages()),
				linkTo(methodOn(RespuestaController.class).getRespuestas(entity.getPageable())).withSelfRel());
		
		if (entity.hasNext()) {
			pagedModel.add(linkTo(methodOn(RespuestaController.class).getRespuestas(entity.getPageable().next())).withRel("next"));
		}
		if (entity.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(RespuestaController.class).getRespuestas(entity.getPageable().previousOrFirst())).withRel("previous"));
		}
		
		return pagedModel;
	}

}
