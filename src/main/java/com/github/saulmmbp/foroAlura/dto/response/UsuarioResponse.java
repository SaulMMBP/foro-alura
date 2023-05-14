package com.github.saulmmbp.foroAlura.dto.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.core.Relation;

import com.github.saulmmbp.foroAlura.controller.UsuarioController;
import com.github.saulmmbp.foroAlura.entity.Usuario;
import com.github.saulmmbp.foroAlura.util.ForoConstants;

import lombok.*;

@Getter
@Setter
@Relation(collectionRelation = "usuarios", itemRelation = "usuario")
public final class UsuarioResponse extends RepresentationModel<UsuarioResponse> {

	private Long id;
	private String nombre;
	private String email;
	
	public UsuarioResponse(Usuario usuario, Link... links) {
		this.id = usuario.getId();
		this.nombre = usuario.getNombre();
		this.email = usuario.getEmail();
		
		this.add(linkTo(methodOn(UsuarioController.class).getUsuario(this.id)).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).getUsuarios(Integer.parseInt(
						ForoConstants.DEFAULT_PAGE_NUMBER), 
						Integer.parseInt(ForoConstants.DEFAULT_PAGE_SIZE), 
						ForoConstants.DEFAULT_PAGE_SORT_BY, 
						ForoConstants.DEFAULT_PAGE_ORDER_BY)).withRel("usuarios"));
		this.add(links);
	}
	
}
