package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.RespuestaResponse;
import com.github.saulmmbp.foroAlura.service.RespuestaService;
import com.github.saulmmbp.foroAlura.util.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

	private RespuestaService respuestaService;
	private RespuestaModelAssembler respuestaAssembler;
	
	public RespuestaController(RespuestaService respuestaService, RespuestaModelAssembler respuestaAssembler) {
		this.respuestaService = respuestaService;
		this.respuestaAssembler = respuestaAssembler;
	}
	
	@GetMapping
	public CollectionModel<EntityModel<RespuestaResponse>> getRespuestas(
			@RequestParam(value = "page", defaultValue = ForoConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = ForoConstants.DEFAULT_PAGE_SIZE, required = false) int size, 
			@RequestParam(value = "sortBy", defaultValue = ForoConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "orderBy", defaultValue = ForoConstants.DEFAULT_PAGE_ORDER_BY, required = false) String orderBy) {
		
		Page<EntityModel<RespuestaResponse>> respuestas = respuestaService.findAllPageable(page, size, sortBy, orderBy)
				.map(respuestaAssembler::toModel);
		
		PagedModel<EntityModel<RespuestaResponse>> pagedModel = PagedModel.of(respuestas.getContent(), 
				new PageMetadata(respuestas.getSize(), respuestas.getNumber(), respuestas.getTotalElements(), respuestas.getTotalPages()),
				linkTo(methodOn(RespuestaController.class).getRespuestas(page, size, sortBy, orderBy)).withSelfRel());
		
		if (respuestas.hasNext()) {
			pagedModel.add(linkTo(methodOn(RespuestaController.class).getRespuestas(page + 1, size, sortBy, orderBy)).withRel("next"));
		}
		if (respuestas.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(RespuestaController.class).getRespuestas(page - 1, size, sortBy, orderBy)).withRel("previous"));
		}
		
		return pagedModel;
	}
	
	@GetMapping("/{id}")
	public EntityModel<RespuestaResponse> getRespuesta(@PathVariable Long id) {
		RespuestaResponse respuesta = respuestaService.findById(id);
		return respuestaAssembler.toModel(respuesta);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<RespuestaResponse>> newRespuesta(@RequestBody @Valid RespuestaPostRequest respuestaDto) {
		EntityModel<RespuestaResponse> respuesta = respuestaAssembler.toModel(respuestaService.save(respuestaDto));
		return ResponseEntity
				.created(respuesta.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(respuesta);
	}
	
	@PutMapping
	public ResponseEntity<EntityModel<RespuestaResponse>> updateRespuesta(@RequestBody @Valid RespuestaPutRequest respuestaDto) {
		EntityModel<RespuestaResponse> respuesta = respuestaAssembler.toModel(respuestaService.update(respuestaDto));
		return ResponseEntity
				.created(respuesta.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(respuesta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRespuesta(@PathVariable Long id) {
		respuestaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
