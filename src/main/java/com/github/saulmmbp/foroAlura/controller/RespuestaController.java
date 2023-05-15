package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.RespuestaResponse;
import com.github.saulmmbp.foroAlura.service.RespuestaService;
import com.github.saulmmbp.foroAlura.util.ForoConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuestas")
@Tag(name = "Respuestas", description = "Manipula información de respuestas")
public class RespuestaController {

	private RespuestaService respuestaService;
	
	public RespuestaController(RespuestaService respuestaService) {
		this.respuestaService = respuestaService;
	}
	
	@GetMapping
	@Operation(summary = "Obtiene un lista paginada de todas las respuestas a tópicos")
	public ResponseEntity<PagedModel<RespuestaResponse>> getRespuestas(
			@RequestParam(value = "page", defaultValue = ForoConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = ForoConstants.DEFAULT_PAGE_SIZE, required = false) int size, 
			@RequestParam(value = "sortBy", defaultValue = ForoConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "orderBy", defaultValue = ForoConstants.DEFAULT_PAGE_ORDER_BY, required = false) String orderBy) {
		
		Page<RespuestaResponse> respuestas = respuestaService.findAllPageable(page, size, sortBy, orderBy);
		
		PagedModel<RespuestaResponse> pagedModel = PagedModel.of(respuestas.getContent(), 
				new PageMetadata(respuestas.getSize(), respuestas.getNumber(), respuestas.getTotalElements(), respuestas.getTotalPages()),
				linkTo(methodOn(RespuestaController.class).getRespuestas(page, size, sortBy, orderBy)).withSelfRel());
		
		if (respuestas.hasNext()) {
			pagedModel.add(linkTo(methodOn(RespuestaController.class).getRespuestas(page + 1, size, sortBy, orderBy)).withRel("next"));
		}
		if (respuestas.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(RespuestaController.class).getRespuestas(page - 1, size, sortBy, orderBy)).withRel("previous"));
		}
		
		return ResponseEntity.ok(pagedModel);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtiene una respuesta por su id")
	public ResponseEntity<RespuestaResponse> getRespuesta(@PathVariable Long id) {
		RespuestaResponse respuesta = respuestaService.findById(id);
		return ResponseEntity.ok(respuesta);
	}
	
	@PostMapping
	@SecurityRequirement(name = "Foro Alura Auth")
	@Operation(summary = "Crea una respuesta a un tópico específico", description = "El autor de "
			+ "la respuesta será el usuario loggeado.")
	public ResponseEntity<RespuestaResponse> newRespuesta(@RequestBody @Valid RespuestaPostRequest respuestaReq) {
		RespuestaResponse respuesta = respuestaService.save(respuestaReq);
		return ResponseEntity
				.created(respuesta.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(respuesta);
	}
	
	@PutMapping
	@PreAuthorize("#respuestaReq.autorId == authentication.principal.id or hasRole('ADMIN')")
	@SecurityRequirement(name = "Foro Alura Auth")
	@Operation(summary = "Modifica la información de una respuesta", description = "Solo el autor "
			+ "o un usuario con rol `ADMIN` pueden modificar la información de una respuesta.")
	public ResponseEntity<RespuestaResponse> updateRespuesta(@RequestBody @Valid RespuestaPutRequest respuestaReq) {
		RespuestaResponse respuesta = respuestaService.update(respuestaReq);
		return ResponseEntity
				.created(respuesta.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(respuesta);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	@SecurityRequirement(name = "Foro Alura Auth")
	@Operation(summary = "Elimina una respuesta", description = "Solo el autor o un usuario "
			+ "con rol `ADMIN` pueden eliminar una respuesta.")
	public ResponseEntity<?> deleteRespuesta(@PathVariable Long id) {
		respuestaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
