package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.CursoResponse;
import com.github.saulmmbp.foroAlura.service.CursoService;
import com.github.saulmmbp.foroAlura.util.ForoConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "Foro_Alura_Auth")
@Tag(name = "Cursos", description = "Manipula información de los cursos")
public class CursoController {

	private CursoService cursoService;
	
	public CursoController(CursoService cursoService) {
		this.cursoService = cursoService;
	}
	
	@GetMapping
	@Operation(summary = "Obtiene una lista paginada de todos los cursos")
	public ResponseEntity<PagedModel<CursoResponse>> getCursos(
			@RequestParam(value = "page", defaultValue = ForoConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = ForoConstants.DEFAULT_PAGE_SIZE, required = false) int size, 
			@RequestParam(value = "sortBy", defaultValue = ForoConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "orderBy", defaultValue = ForoConstants.DEFAULT_PAGE_ORDER_BY, required = false) String orderBy) {
		
		Page<CursoResponse> cursos = cursoService.findAllPageable(page, size, sortBy, orderBy);
		
		PagedModel<CursoResponse> pagedModel = PagedModel.of(cursos.getContent(), 
				new PageMetadata(cursos.getSize(), cursos.getNumber(), cursos.getTotalElements(), cursos.getTotalPages()),
				linkTo(methodOn(CursoController.class).getCursos(page, size, sortBy, orderBy)).withSelfRel());
		
		if (cursos.hasNext()) {
			pagedModel.add(linkTo(methodOn(CursoController.class).getCursos(page + 1, size, sortBy, orderBy)).withRel("next"));
		}
		if(cursos.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(CursoController.class).getCursos(page - 1, size, sortBy, orderBy)).withRel("previous"));
		}
		
		return ResponseEntity.ok(pagedModel);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtiene un curso por su id")
	public ResponseEntity<CursoResponse> getCurso(@PathVariable Long id) {
		CursoResponse curso = cursoService.findById(id);
		return ResponseEntity.ok(curso);
	}
	
	@PostMapping
	@PreAuthorize("#cursoReq.instructorId == authentication.principal.id or hasRole('ADMIN')")
	@Operation(summary = "Crea un curso", description = "Solo los usuarios con rol `ADMIN` y/o "
			+ "`INSTRUCTOR` pueden crear cursos.")
	public ResponseEntity<CursoResponse> newCurso(@RequestBody @Valid CursoPostRequest cursoReq) {
		CursoResponse curso = cursoService.save(cursoReq);
		return ResponseEntity
				.created(curso.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(curso);
	}
	
	@PutMapping
	@PreAuthorize("#cursoReq.instructorId == authentication.principal.id or hasRole('ADMIN')")
	@Operation(summary = "Modifica la información de un curso", description = "Solo el instructor "
			+ "del curso o un usuario con rol `ADMIN` pueden modificar la información de un curso.")
	public ResponseEntity<CursoResponse> updateCurso(@RequestBody @Valid CursoPutRequest cursoReq) {
		CursoResponse curso = cursoService.update(cursoReq);
		return ResponseEntity
				.created(curso.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(curso);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	@Operation(summary = "Eliminar un curso", description = "Solo el instructor del curso o un "
			+ "usuario con rol `ADMIN` pueden eliminar un curso.")
	public ResponseEntity<?> deleteCurso(@PathVariable Long id) {
		cursoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
