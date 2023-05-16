package com.github.saulmmbp.foroAlura.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.github.saulmmbp.foroAlura.controller.TopicoController;
import com.github.saulmmbp.foroAlura.dto.response.TopicoResponse;

@Component
public class TopicosPagedModelAssembler
		implements RepresentationModelAssembler<Page<TopicoResponse>, PagedModel<TopicoResponse>> {

	@Override
	public PagedModel<TopicoResponse> toModel(Page<TopicoResponse> entity) {
		PagedModel<TopicoResponse> pagedModel = PagedModel.of(entity.getContent(), 
				new PageMetadata(entity.getSize(), entity.getNumber(), entity.getTotalElements(), entity.getTotalPages()),
				linkTo(methodOn(TopicoController.class).getTopicos(entity.getPageable())).withSelfRel());
		
		if (entity.hasNext()) {
			pagedModel.add(linkTo(methodOn(TopicoController.class).getTopicos(entity.getPageable().next())).withRel("next"));
		}
		if (entity.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(TopicoController.class).getTopicos(entity.getPageable().previousOrFirst())).withRel("previous"));
		}
		
		return pagedModel;
	}

}
