package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.TopicoResponse;
import com.github.saulmmbp.foroAlura.service.TopicoService;
import com.github.saulmmbp.foroAlura.util.ForoConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "Foro_Alura_Auth")
@Tag(name = "Topicos", description = "Manipula información de topicos")
public class TopicoController {

	private TopicoService topicoService;

	public TopicoController(TopicoService topicoService) {
		this.topicoService = topicoService;
	}

	@GetMapping
	@Operation(summary = "Obtiene una lista paginada de todos los topicos")
	public ResponseEntity<PagedModel<TopicoResponse>> getTopicos(
			@RequestParam(value = "page", defaultValue = ForoConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = ForoConstants.DEFAULT_PAGE_SIZE, required = false) int size, 
			@RequestParam(value = "sortBy", defaultValue = ForoConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "orderBy", defaultValue = ForoConstants.DEFAULT_PAGE_ORDER_BY, required = false) String orderBy) {
		
		Page<TopicoResponse> topicos = topicoService.findAllPageable(page, size, sortBy, orderBy);
		
		PagedModel<TopicoResponse> pagedModel = PagedModel.of(topicos.getContent(), 
				new PageMetadata(topicos.getSize(), topicos.getNumber(), topicos.getTotalElements(), topicos.getTotalPages()),
				linkTo(methodOn(TopicoController.class).getTopicos(page, size, sortBy, orderBy)).withSelfRel());
		
		if (topicos.hasNext()) {
			pagedModel.add(linkTo(methodOn(TopicoController.class).getTopicos(page + 1, size, sortBy, orderBy)).withRel("next"));
		}
		if (topicos.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(TopicoController.class).getTopicos(page - 1, size, sortBy, orderBy)).withRel("previous"));
		}
		
		return ResponseEntity.ok(pagedModel);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtiene un tópico por su id")
	public ResponseEntity<TopicoResponse> getTopico(@PathVariable Long id) {
		TopicoResponse topico = topicoService.findById(id);
		return ResponseEntity.ok(topico);
	}
	
	@PostMapping
	@Operation(summary = "Crea un tópico", description = "El autor del tópico será usuario loggeado" )
	public ResponseEntity<TopicoResponse> newTopico(@RequestBody @Valid TopicoPostRequest topico) {
		TopicoResponse newTopico = topicoService.save(topico);
		return ResponseEntity
				.created(newTopico.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(newTopico);
	}
	
	@PutMapping
	@PreAuthorize("#topico.autorId == authentication.principal.id or hasRole('ADMIN')")
	@Operation(summary = "Modifica la información de un tópico", description = "Solo el autor "
			+ "del tópico o un usuario con rol `ADMIN` pueden modificar la información del mismo")
	public ResponseEntity<TopicoResponse> updateTopico(@RequestBody @Valid TopicoPutRequest topico) {
		TopicoResponse updatedTopico = topicoService.update(topico);
		return ResponseEntity
				.created(updatedTopico.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(updatedTopico);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	@Operation(summary = "Elmina un tópico", description = "Solo el autor del tópico o un usuario "
			+ "con rol `ADMIN` puede eliminar el mismo.")
	public ResponseEntity<?> deleteTopico(@PathVariable Long id) {
		this.topicoService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
