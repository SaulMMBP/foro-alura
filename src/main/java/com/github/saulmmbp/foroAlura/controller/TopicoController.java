package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.TopicoDto;
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
	public CollectionModel<EntityModel<TopicoDto>> getTopicos() {
		List<EntityModel<TopicoDto>> topicos = this.topicoService.findAll().stream()
				.map(topicoAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(topicos, linkTo(methodOn(TopicoController.class).getTopicos()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<TopicoDto> getTopico(@PathVariable Long id) {
		TopicoDto topico = topicoService.findById(id);
		return topicoAssembler.toModel(topico);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<TopicoDto>> newTopico(@RequestBody @Valid TopicoDto topico) {
		EntityModel<TopicoDto> newTopico = this.topicoAssembler.toModel(this.topicoService.save(topico));
		return ResponseEntity
				.created(newTopico.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(newTopico);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<TopicoDto>> updateTopico(@RequestBody @Valid TopicoDto topico, @PathVariable Long id) {
		EntityModel<TopicoDto> updatedTopico = this.topicoAssembler.toModel(this.topicoService.update(topico, id));
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
