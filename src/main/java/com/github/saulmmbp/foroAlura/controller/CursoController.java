package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.*;
import com.github.saulmmbp.foroAlura.service.CursoService;
import com.github.saulmmbp.foroAlura.util.CursoModelAssembler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
public class CursoController {

	private CursoService cursoService;
	private CursoModelAssembler cursoAssembler;
	
	public CursoController(CursoService cursoService, CursoModelAssembler cursoAssembler) {
		this.cursoService = cursoService;
		this.cursoAssembler = cursoAssembler;
	}
	
	@GetMapping
	public CollectionModel<EntityModel<CursoResponse>> getCursos() {
		List<EntityModel<CursoResponse>> cursos = cursoService.findAll().stream()
				.map(cursoAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(cursos, linkTo(methodOn(CursoController.class).getCursos()).withSelfRel());
	}
	
	@GetMapping("/{id}")
	public EntityModel<CursoResponse> getCurso(@PathVariable Long id) {
		CursoResponse curso = cursoService.findById(id);
		return cursoAssembler.toModel(curso);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<CursoResponse>> newCurso(@RequestBody @Valid CursoPostRequest cursoDto) {
		EntityModel<CursoResponse> curso = cursoAssembler.toModel(cursoService.save(cursoDto));
		return ResponseEntity
				.created(curso.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(curso);
	}
	
	@PutMapping
	public ResponseEntity<EntityModel<CursoResponse>> updateCurso(@RequestBody @Valid CursoPutRequest cursoDto) {
		EntityModel<CursoResponse> curso = cursoAssembler.toModel(cursoService.update(cursoDto));
		return ResponseEntity
				.created(curso.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(curso);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCurso(@PathVariable Long id) {
		cursoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
