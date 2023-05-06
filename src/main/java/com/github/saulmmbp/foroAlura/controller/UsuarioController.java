package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.UsuarioResponse;
import com.github.saulmmbp.foroAlura.service.UsuarioService;
import com.github.saulmmbp.foroAlura.util.UsuarioModelAssembler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioService usuarioService;
	private UsuarioModelAssembler usuarioAssembler;

	public UsuarioController(UsuarioService usuarioService, UsuarioModelAssembler usuarioModelAssembler) {
		this.usuarioService = usuarioService;
		this.usuarioAssembler = usuarioModelAssembler;
	}
	
	@GetMapping
	public CollectionModel<EntityModel<UsuarioResponse>> getUsuarios() {
		List<EntityModel<UsuarioResponse>> usuarios = usuarioService.findAll().stream()
				.map(usuarioAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(usuarios, linkTo(methodOn(UsuarioController.class).getUsuarios()).withSelfRel());
	}
	
	@GetMapping("/{id}")
	public EntityModel<UsuarioResponse> getUsuario(@PathVariable Long id) {
		UsuarioResponse Usuario = usuarioService.findById(id);
		return usuarioAssembler.toModel(Usuario);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<UsuarioResponse>> newUsuario(@RequestBody @Valid UsuarioPostRequest usuarioReq) {
		EntityModel<UsuarioResponse> usuario = usuarioAssembler.toModel(usuarioService.save(usuarioReq));
		return ResponseEntity
				.created(usuario.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuario);
	}
	
	@PutMapping
	public ResponseEntity<EntityModel<UsuarioResponse>> updateUsuario(@RequestBody @Valid UsuarioPutRequest usuarioReq) {
		EntityModel<UsuarioResponse> usuario = usuarioAssembler.toModel(usuarioService.update(usuarioReq));
		return ResponseEntity
				.created(usuario.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuario);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
