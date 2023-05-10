package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.TopicoResponse;
import com.github.saulmmbp.foroAlura.service.TopicoService;
import com.github.saulmmbp.foroAlura.util.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	private TopicoService topicoService;
	private TopicoModelAssembler topicoAssembler;

	public TopicoController(TopicoService topicoService, TopicoModelAssembler topicoAssembler) {
		this.topicoService = topicoService;
		this.topicoAssembler = topicoAssembler;
	}

	@GetMapping
	public CollectionModel<EntityModel<TopicoResponse>> getTopicos(
			@RequestParam(value = "page", defaultValue = ForoConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = ForoConstants.DEFAULT_PAGE_SIZE, required = false) int size, 
			@RequestParam(value = "sortBy", defaultValue = ForoConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "orderBy", defaultValue = ForoConstants.DEFAULT_PAGE_ORDER_BY, required = false) String orderBy) {
		
		Page<EntityModel<TopicoResponse>> topicos = topicoService.findAllPageable(page, size, sortBy, orderBy)
				.map(topicoAssembler::toModel);
		
		PagedModel<EntityModel<TopicoResponse>> pagedModel = PagedModel.of(topicos.getContent(), 
				new PageMetadata(topicos.getSize(), topicos.getNumber(), topicos.getTotalElements(), topicos.getTotalPages()),
				linkTo(methodOn(TopicoController.class).getTopicos(page, size, sortBy, orderBy)).withSelfRel());
		
		if (topicos.hasNext()) {
			pagedModel.add(linkTo(methodOn(TopicoController.class).getTopicos(page + 1, size, sortBy, orderBy)).withRel("next"));
		}
		if (topicos.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(TopicoController.class).getTopicos(page - 1, size, sortBy, orderBy)).withRel("previous"));
		}
		
		return pagedModel;
	}

	@GetMapping("/{id}")
	public EntityModel<TopicoResponse> getTopico(@PathVariable Long id) {
		TopicoResponse topico = topicoService.findById(id);
		return topicoAssembler.toModel(topico);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<TopicoResponse>> newTopico(@RequestBody @Valid TopicoPostRequest topico) {
		EntityModel<TopicoResponse> newTopico = this.topicoAssembler.toModel(topicoService.save(topico));
		return ResponseEntity
				.created(newTopico.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(newTopico);
	}
	
	@PutMapping
	@PreAuthorize("#topico.autorId == authentication.principal.id")
	public ResponseEntity<EntityModel<TopicoResponse>> updateTopico(@RequestBody @Valid TopicoPutRequest topico) {
		EntityModel<TopicoResponse> updatedTopico = this.topicoAssembler.toModel(topicoService.update(topico));
		return ResponseEntity
				.created(updatedTopico.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(updatedTopico);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id")
	public ResponseEntity<?> deleteTopico(@PathVariable Long id) {
		this.topicoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
