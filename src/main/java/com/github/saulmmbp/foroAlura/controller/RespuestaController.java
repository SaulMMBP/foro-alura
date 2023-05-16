package com.github.saulmmbp.foroAlura.controller;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.RespuestaResponse;
import com.github.saulmmbp.foroAlura.service.RespuestaService;
import com.github.saulmmbp.foroAlura.util.RespuestasPagedModelAssembler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

	private RespuestaService respuestaService;
	private RespuestasPagedModelAssembler pagedAssembler;
	
	public RespuestaController(RespuestaService respuestaService, RespuestasPagedModelAssembler pagedAssembler) {
		this.respuestaService = respuestaService;
		this.pagedAssembler = pagedAssembler;
	}
	
	@GetMapping
	public ResponseEntity<PagedModel<RespuestaResponse>> getRespuestas(@PageableDefault Pageable pageable) {
		Page<RespuestaResponse> respuestas = respuestaService.findAllPageable(pageable);
		return ResponseEntity.ok(pagedAssembler.toModel(respuestas));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RespuestaResponse> getRespuesta(@PathVariable Long id) {
		RespuestaResponse respuesta = respuestaService.findById(id);
		return ResponseEntity.ok(respuesta);
	}
	
	@PostMapping
	public ResponseEntity<RespuestaResponse> newRespuesta(@RequestBody @Valid RespuestaPostRequest respuestaReq) {
		RespuestaResponse respuesta = respuestaService.save(respuestaReq);
		return ResponseEntity
				.created(respuesta.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(respuesta);
	}
	
	@PutMapping
	@PreAuthorize("#respuestaReq.autorId == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<RespuestaResponse> updateRespuesta(@RequestBody @Valid RespuestaPutRequest respuestaReq) {
		RespuestaResponse respuesta = respuestaService.update(respuestaReq);
		return ResponseEntity
				.created(respuesta.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(respuesta);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<?> deleteRespuesta(@PathVariable Long id) {
		respuestaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
