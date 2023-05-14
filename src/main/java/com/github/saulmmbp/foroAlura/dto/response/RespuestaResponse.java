package com.github.saulmmbp.foroAlura.dto.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDateTime;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.saulmmbp.foroAlura.controller.*;
import com.github.saulmmbp.foroAlura.entity.Respuesta;
import com.github.saulmmbp.foroAlura.util.ForoConstants;

import lombok.*;

@Getter
@Setter
@Relation(collectionRelation = "respuestas", itemRelation = "respuesta")
public final class RespuestaResponse extends RepresentationModel<RespuestaResponse> {
	
	private Long id;
	private String mensaje;
	private LocalDateTime fechaCreacion;
	private boolean solucion;
	private UsuarioResponse autor;
	
	@JsonIgnore
	private Long topicoId;
	
	public RespuestaResponse(Respuesta respuesta, Link... links) {
		this.id = respuesta.getId();
		this.mensaje = respuesta.getMensaje();
		this.fechaCreacion = respuesta.getFechaCreacion();
		this.solucion = respuesta.getSolucion();
		this.autor = respuesta.getAutor().toResponse();
		this.topicoId = respuesta.getTopico().getId();
		
		this.add(linkTo(methodOn(RespuestaController.class).getRespuesta(id)).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).getUsuario(autor.getId())).withRel("autor"),
				linkTo(methodOn(TopicoController.class).getTopico(topicoId)).withRel("topico"),
				linkTo(methodOn(RespuestaController.class).getRespuestas(
						Integer.parseInt(ForoConstants.DEFAULT_PAGE_NUMBER), 
						Integer.parseInt(ForoConstants.DEFAULT_PAGE_SIZE), 
						ForoConstants.DEFAULT_PAGE_SORT_BY, 
						ForoConstants.DEFAULT_PAGE_ORDER_BY))
					.withRel("respuestas"));
		
		this.add(links);
	}
}
