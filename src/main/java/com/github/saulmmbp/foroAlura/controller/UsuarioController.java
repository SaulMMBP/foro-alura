package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.UsuarioDto;
import com.github.saulmmbp.foroAlura.service.UsuarioService;
import com.github.saulmmbp.foroAlura.util.UsuarioModelAssembler;

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
	public CollectionModel<EntityModel<UsuarioDto>> getUsuarios() {
		List<EntityModel<UsuarioDto>> usuarios = usuarioService.findAll().stream()
				.map(usuarioAssembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(usuarios, linkTo(methodOn(UsuarioController.class).getUsuarios()).withSelfRel());
	}
	
	@GetMapping("/{id}")
	public EntityModel<UsuarioDto> getUsuario(@PathVariable Long id) {
		UsuarioDto Usuario = usuarioService.findById(id);
		return usuarioAssembler.toModel(Usuario);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<UsuarioDto>> newUsuario(@RequestBody UsuarioDto usuarioDto) {
		EntityModel<UsuarioDto> usuario = usuarioAssembler.toModel(usuarioService.save(usuarioDto));
		return ResponseEntity
				.created(usuario.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuario);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<UsuarioDto>> updateUsuario(@RequestBody UsuarioDto usuarioDto, @PathVariable Long id) {
		EntityModel<UsuarioDto> usuario = usuarioAssembler.toModel(usuarioService.update(usuarioDto, id));
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
