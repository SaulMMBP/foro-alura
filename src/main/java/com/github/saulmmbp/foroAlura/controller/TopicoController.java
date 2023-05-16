package com.github.saulmmbp.foroAlura.controller;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.TopicoResponse;
import com.github.saulmmbp.foroAlura.service.TopicoService;
import com.github.saulmmbp.foroAlura.util.TopicosPagedModelAssembler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	private TopicoService topicoService;
	private TopicosPagedModelAssembler pagedAssembler;

	public TopicoController(TopicoService topicoService, TopicosPagedModelAssembler pagedAssembler) {
		this.topicoService = topicoService;
		this.pagedAssembler = pagedAssembler;
	}

	@GetMapping
	public ResponseEntity<PagedModel<TopicoResponse>> getTopicos(@PageableDefault Pageable pageable) {		
		Page<TopicoResponse> topicos = topicoService.findAllPageable(pageable);
		return ResponseEntity.ok(pagedAssembler.toModel(topicos));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicoResponse> getTopico(@PathVariable Long id) {
		TopicoResponse topico = topicoService.findById(id);
		return ResponseEntity.ok(topico);
	}
	
	@PostMapping
	public ResponseEntity<TopicoResponse> newTopico(@RequestBody @Valid TopicoPostRequest topico) {
		TopicoResponse newTopico = topicoService.save(topico);
		return ResponseEntity
				.created(newTopico.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(newTopico);
	}
	
	@PutMapping
	@PreAuthorize("#topico.autorId == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<TopicoResponse> updateTopico(@RequestBody @Valid TopicoPutRequest topico) {
		TopicoResponse updatedTopico = topicoService.update(topico);
		return ResponseEntity
				.created(updatedTopico.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(updatedTopico);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<?> deleteTopico(@PathVariable Long id) {
		this.topicoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
