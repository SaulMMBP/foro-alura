package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.RespuestaDto;
import com.github.saulmmbp.foroAlura.service.RespuestaService;
import com.github.saulmmbp.foroAlura.util.RespuestaModelAssembler;

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
	public CollectionModel<EntityModel<RespuestaDto>> getRespuestas() {
		List<EntityModel<RespuestaDto>> respuestas = respuestaService.findAll().stream()
				.map(respuestaAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(respuestas, linkTo(methodOn(RespuestaController.class).getRespuestas()).withSelfRel());
	}
	
	@GetMapping("/{id}")
	public EntityModel<RespuestaDto> getRespuesta(@PathVariable Long id) {
		RespuestaDto respuesta = respuestaService.findById(id);
		return respuestaAssembler.toModel(respuesta);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<RespuestaDto>> newRespuesta(@RequestBody RespuestaDto respuestaDto) {
		EntityModel<RespuestaDto> respuesta = respuestaAssembler.toModel(respuestaService.save(respuestaDto));
		return ResponseEntity
				.created(respuesta.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(respuesta);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<RespuestaDto>> updateRespuesta(@RequestBody RespuestaDto respuestaDto, @PathVariable Long id) {
		EntityModel<RespuestaDto> respuesta = respuestaAssembler.toModel(respuestaService.update(respuestaDto, id));
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
