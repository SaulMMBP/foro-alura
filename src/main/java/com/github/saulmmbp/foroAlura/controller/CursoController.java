package com.github.saulmmbp.foroAlura.controller;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.CursoResponse;
import com.github.saulmmbp.foroAlura.service.CursoService;
import com.github.saulmmbp.foroAlura.util.CursosPagedModelAssembler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
public class CursoController {

	private CursoService cursoService;
	private CursosPagedModelAssembler pagedAssembler;
	
	public CursoController(CursoService cursoService, CursosPagedModelAssembler pagedAssembler) {
		this.cursoService = cursoService;
		this.pagedAssembler = pagedAssembler;
	}
	
	@GetMapping
	public ResponseEntity<PagedModel<CursoResponse>> getCursos(@PageableDefault Pageable pageable) {
		Page<CursoResponse> cursos = cursoService.findAllPageable(pageable);
		return ResponseEntity.ok(pagedAssembler.toModel(cursos));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CursoResponse> getCurso(@PathVariable Long id) {
		CursoResponse curso = cursoService.findById(id);
		return ResponseEntity.ok(curso);
	}
	
	@PostMapping
	@PreAuthorize("#cursoReq.instructorId == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<CursoResponse> newCurso(@RequestBody @Valid CursoPostRequest cursoReq) {
		CursoResponse curso = cursoService.save(cursoReq);
		return ResponseEntity
				.created(curso.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(curso);
	}
	
	@PutMapping
	@PreAuthorize("#cursoReq.instructorId == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<CursoResponse> updateCurso(@RequestBody @Valid CursoPutRequest cursoReq) {
		CursoResponse curso = cursoService.update(cursoReq);
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
