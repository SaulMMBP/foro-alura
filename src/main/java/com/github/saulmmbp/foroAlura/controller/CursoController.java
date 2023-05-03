package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.*;
import com.github.saulmmbp.foroAlura.service.CursoService;
import com.github.saulmmbp.foroAlura.util.CursoModelAssembler;

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
	public CollectionModel<EntityModel<CursoDto>> getCursos() {
		List<EntityModel<CursoDto>> cursos = cursoService.findAll().stream()
				.map(cursoAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(cursos, linkTo(methodOn(CursoController.class).getCursos()).withSelfRel());
	}
	
	@GetMapping("/{id}")
	public EntityModel<CursoDto> getCurso(@PathVariable Long id) {
		CursoDto curso = cursoService.findById(id);
		return cursoAssembler.toModel(curso);
	}
}
