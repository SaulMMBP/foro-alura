package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.*;
import com.github.saulmmbp.foroAlura.service.TopicoService;
import com.github.saulmmbp.foroAlura.util.TopicoModelAssembler;

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
	public CollectionModel<EntityModel<TopicoResponse>> getTopicos() {
		List<EntityModel<TopicoResponse>> topicos = this.topicoService.findAll().stream()
				.map(topicoAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(topicos, linkTo(methodOn(TopicoController.class).getTopicos()).withSelfRel());
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
	public ResponseEntity<EntityModel<TopicoResponse>> updateTopico(@RequestBody @Valid TopicoPutRequest topico) {
		EntityModel<TopicoResponse> updatedTopico = this.topicoAssembler.toModel(topicoService.update(topico));
		return ResponseEntity
				.created(updatedTopico.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(updatedTopico);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTopico(@PathVariable Long id) {
		this.topicoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
