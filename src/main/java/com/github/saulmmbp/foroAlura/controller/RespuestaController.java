package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.*;
import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.RespuestaResponse;
import com.github.saulmmbp.foroAlura.service.RespuestaService;
import com.github.saulmmbp.foroAlura.util.RespuestaModelAssembler;

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
	public CollectionModel<EntityModel<RespuestaResponse>> getRespuestas() {
		List<EntityModel<RespuestaResponse>> respuestas = respuestaService.findAll().stream()
				.map(respuestaAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(respuestas, linkTo(methodOn(RespuestaController.class).getRespuestas()).withSelfRel());
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
