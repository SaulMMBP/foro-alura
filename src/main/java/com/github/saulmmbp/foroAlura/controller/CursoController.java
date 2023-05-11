package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.CursoResponse;
import com.github.saulmmbp.foroAlura.service.CursoService;
import com.github.saulmmbp.foroAlura.util.*;

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
	public CollectionModel<EntityModel<CursoResponse>> getCursos(
			@RequestParam(value = "page", defaultValue = ForoConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = ForoConstants.DEFAULT_PAGE_SIZE, required = false) int size, 
			@RequestParam(value = "sortBy", defaultValue = ForoConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "orderBy", defaultValue = ForoConstants.DEFAULT_PAGE_ORDER_BY, required = false) String orderBy) {
		
		Page<EntityModel<CursoResponse>> cursos = cursoService.findAllPageable(page, size, sortBy, orderBy)
				.map(cursoAssembler::toModel);
		
		PagedModel<EntityModel<CursoResponse>> pagedModel = PagedModel.of(cursos.getContent(), 
				new PageMetadata(cursos.getSize(), cursos.getNumber(), cursos.getTotalElements(), cursos.getTotalPages()),
				linkTo(methodOn(CursoController.class).getCursos(page, size, sortBy, orderBy)).withSelfRel());
		
		if (cursos.hasNext()) {
			pagedModel.add(linkTo(methodOn(CursoController.class).getCursos(page + 1, size, sortBy, orderBy)).withRel("next"));
		}
		if(cursos.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(CursoController.class).getCursos(page - 1, size, sortBy, orderBy)).withRel("previous"));
		}
		
		return pagedModel;
	}
	
	@GetMapping("/{id}")
	public EntityModel<CursoResponse> getCurso(@PathVariable Long id) {
		CursoResponse curso = cursoService.findById(id);
		return cursoAssembler.toModel(curso);
	}
	
	@PostMapping
	@PreAuthorize("#cursoReq.instructorId == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<EntityModel<CursoResponse>> newCurso(@RequestBody @Valid CursoPostRequest cursoReq) {
		EntityModel<CursoResponse> curso = cursoAssembler.toModel(cursoService.save(cursoReq));
		return ResponseEntity
				.created(curso.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(curso);
	}
	
	@PutMapping
	@PreAuthorize("#cursoReq.instructorId == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<EntityModel<CursoResponse>> updateCurso(@RequestBody @Valid CursoPutRequest cursoReq) {
		EntityModel<CursoResponse> curso = cursoAssembler.toModel(cursoService.update(cursoReq));
		return ResponseEntity
				.created(curso.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(curso);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<?> deleteCurso(@PathVariable Long id) {
		cursoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
