package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.UsuarioResponse;
import com.github.saulmmbp.foroAlura.service.UsuarioService;
import com.github.saulmmbp.foroAlura.util.*;

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
	public CollectionModel<EntityModel<UsuarioResponse>> getUsuarios(
			@RequestParam(value = "page", defaultValue = ForoConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = ForoConstants.DEFAULT_PAGE_SIZE, required = false) int size, 
			@RequestParam(value = "sortBy", defaultValue = ForoConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "orderBy", defaultValue = ForoConstants.DEFAULT_PAGE_ORDER_BY, required = false) String orderBy) {
		
		Page<EntityModel<UsuarioResponse>> usuarios = usuarioService.findAllPageable(page, size, sortBy, orderBy)
				.map(usuarioAssembler::toModel);
		
		PagedModel<EntityModel<UsuarioResponse>> pagedModel =  PagedModel.of(usuarios.getContent(), 
				new PageMetadata(usuarios.getSize(), usuarios.getNumber(), usuarios.getTotalElements(), usuarios.getTotalPages()),
				linkTo(methodOn(UsuarioController.class).getUsuarios(page, size, sortBy, orderBy)).withSelfRel());
		
		if (usuarios.hasNext()) {
			pagedModel.add(linkTo(methodOn(UsuarioController.class).getUsuarios(page + 1, size, sortBy, orderBy)).withRel("next"));
		}
		if (usuarios.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(UsuarioController.class).getUsuarios(page - 1, size, sortBy, orderBy)).withRel("previous"));
		}
		
		return pagedModel;
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
