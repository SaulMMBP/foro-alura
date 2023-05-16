package com.github.saulmmbp.foroAlura.controller;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.UsuarioResponse;
import com.github.saulmmbp.foroAlura.service.UsuarioService;
import com.github.saulmmbp.foroAlura.util.UsuariosPagedModelAssembler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioService usuarioService;
	private UsuariosPagedModelAssembler pagedAssembler;

	public UsuarioController(UsuarioService usuarioService, UsuariosPagedModelAssembler pagedAssembler) {
		this.usuarioService = usuarioService;
		this.pagedAssembler = pagedAssembler;
	}
	
	@GetMapping
	public ResponseEntity<PagedModel<UsuarioResponse>> getUsuarios(@PageableDefault Pageable pageable) {
		Page<UsuarioResponse> usuarios = usuarioService.findAllPageable(pageable);
		return ResponseEntity.ok(pagedAssembler.toModel(usuarios));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable Long id) {
		UsuarioResponse Usuario = usuarioService.findById(id);
		return ResponseEntity.ok(Usuario);
	}
	
	@PostMapping
	public ResponseEntity<UsuarioResponse> newUsuario(@RequestBody @Valid UsuarioPostRequest usuarioReq) {
		UsuarioResponse usuario = usuarioService.save(usuarioReq);
		return ResponseEntity
				.created(usuario.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuario);
	}
	
	@PutMapping
	@PreAuthorize("#usuarioReq.id == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<UsuarioResponse> updateUsuario(@RequestBody @Valid UsuarioPutRequest usuarioReq) {
		UsuarioResponse usuario = usuarioService.update(usuarioReq);
		return ResponseEntity
				.created(usuario.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuario);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
