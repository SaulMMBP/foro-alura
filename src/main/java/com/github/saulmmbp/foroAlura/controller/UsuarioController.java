package com.github.saulmmbp.foroAlura.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.*;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.UsuarioResponse;
import com.github.saulmmbp.foroAlura.service.UsuarioService;
import com.github.saulmmbp.foroAlura.util.ForoConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Manipula información de usuarios")
public class UsuarioController {

	private UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping
	@Operation(summary = "Obtiene una lista paginada de todos los usuarios")
	public ResponseEntity<PagedModel<UsuarioResponse>> getUsuarios(
			@RequestParam(value = "page", defaultValue = ForoConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
			@RequestParam(value = "pageSize", defaultValue = ForoConstants.DEFAULT_PAGE_SIZE, required = false) int size, 
			@RequestParam(value = "sortBy", defaultValue = ForoConstants.DEFAULT_PAGE_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "orderBy", defaultValue = ForoConstants.DEFAULT_PAGE_ORDER_BY, required = false) String orderBy) {
		
		Page<UsuarioResponse> usuarios = usuarioService.findAllPageable(page, size, sortBy, orderBy);
		
		PagedModel<UsuarioResponse> pagedModel =  PagedModel.of(usuarios.getContent(), 
				new PageMetadata(usuarios.getSize(), usuarios.getNumber(), usuarios.getTotalElements(), usuarios.getTotalPages()),
				linkTo(methodOn(UsuarioController.class).getUsuarios(page, size, sortBy, orderBy)).withSelfRel());
		
		if (usuarios.hasNext()) {
			pagedModel.add(linkTo(methodOn(UsuarioController.class).getUsuarios(page + 1, size, sortBy, orderBy)).withRel("next"));
		}
		if (usuarios.hasPrevious()) {
			pagedModel.add(linkTo(methodOn(UsuarioController.class).getUsuarios(page - 1, size, sortBy, orderBy)).withRel("previous"));
		}
		
		return ResponseEntity.ok(pagedModel);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Obtiene un usuario por su id")
	public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable Long id) {
		UsuarioResponse Usuario = usuarioService.findById(id);
		return ResponseEntity.ok(Usuario);
	}
	
	@PostMapping
	@Operation(summary = "Crea un nuevo usuario", description = "Se debe usar una contraseña encriptada con bcrypt para registrar "
			+ "un nuevo usuario")
	public ResponseEntity<UsuarioResponse> newUsuario(@RequestBody @Valid UsuarioPostRequest usuarioReq) {
		UsuarioResponse usuario = usuarioService.save(usuarioReq);
		return ResponseEntity
				.created(usuario.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuario);
	}
	
	@PutMapping
	@PreAuthorize("#usuarioReq.id == authentication.principal.id or hasRole('ADMIN')")
	@SecurityRequirement(name = "Foro Alura Auth")
	@Operation(summary = "Modifica la información de un usuario", 
		description = "Solo los usuarios con rol `ADMIN` pueden modificar la información de cualquier usuario, "
				+ "los usuarios sin rol `ADMIN` solo pueden modificar su propia información.")
	public ResponseEntity<UsuarioResponse> updateUsuario(@RequestBody @Valid UsuarioPutRequest usuarioReq) {
		UsuarioResponse usuario = usuarioService.update(usuarioReq);
		return ResponseEntity
				.created(usuario.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuario);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
	@SecurityRequirement(name = "Foro Alura Auth")
	@Operation(summary = "Elimina un usuario por su id",
		description = "Solo los usuarios con rol `ADMIN` puden eliminar cualquier usuario, "
				+ "los usuarios sin rol `ADMIN` solo pueden eliminar su propia cuenta.")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
