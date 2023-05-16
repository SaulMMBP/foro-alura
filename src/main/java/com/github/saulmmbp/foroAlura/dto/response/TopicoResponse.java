package com.github.saulmmbp.foroAlura.dto.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.saulmmbp.foroAlura.controller.TopicoController;
import com.github.saulmmbp.foroAlura.entity.*;

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
		
		this.add(linkTo(methodOn(TopicoController.class).getTopico(id)).withSelfRel());
		
		this.add(links);
	}

}
