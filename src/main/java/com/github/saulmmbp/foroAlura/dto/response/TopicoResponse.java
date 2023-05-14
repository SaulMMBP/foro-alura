package com.github.saulmmbp.foroAlura.dto.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.saulmmbp.foroAlura.controller.*;
import com.github.saulmmbp.foroAlura.entity.*;
import com.github.saulmmbp.foroAlura.util.ForoConstants;

import lombok.*;

@Getter
@Setter
@Relation(collectionRelation = "topicos", itemRelation = "topico")
public final class TopicoResponse extends RepresentationModel<TopicoResponse> {
	
	private Long id;
	private String titulo;
	private String mensaje;
	private LocalDateTime fechaCreacion;
	private ESTADO estado;
	private UsuarioResponse autor;
	private CursoResponse curso;
	
	@JsonIgnoreProperties("topico")
	private Set<RespuestaResponse> respuestas;
	
	public TopicoResponse(Topico topico, Link... links) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensaje = topico.getMensaje();
		this.fechaCreacion = topico.getFechaCreacion();
		this.estado = topico.getEstado();
		this.autor = topico.getAutor().toResponse();
		this.curso = topico.getCurso().toResponse();
		this.respuestas = topico.getRespuestas().stream().map(Respuesta::toResponse).collect(Collectors.toSet());
		
		this.add(linkTo(methodOn(TopicoController.class).getTopico(id)).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).getUsuario(autor.getId())).withRel("autor"),
				linkTo(methodOn(CursoController.class).getCurso(curso.getId())).withRel("curso"),
				linkTo(methodOn(TopicoController.class).getTopicos(
						Integer.parseInt(ForoConstants.DEFAULT_PAGE_NUMBER), 
						Integer.parseInt(ForoConstants.DEFAULT_PAGE_SIZE), 
						ForoConstants.DEFAULT_PAGE_SORT_BY, 
						ForoConstants.DEFAULT_PAGE_ORDER_BY))
				.withRel("topicos"));
		
		this.add(links);
	}

}
