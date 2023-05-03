package com.github.saulmmbp.foroAlura.entity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;

import com.github.saulmmbp.foroAlura.dto.TopicoDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@ToString(exclude = {"respuestas"})
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_topico")
	private Long id;

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "mensaje")
	private String mensaje;

	@Column(name = "fecha_creacion")
	@CreationTimestamp
	private LocalDateTime fechaCreacion;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private ESTADO estado;

	/* Relations */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "autor")
	private Usuario autor;

	@ManyToOne
	@JoinColumn(name = "curso")
	private Curso curso;
	
	@OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Respuesta> respuestas = new HashSet<>();

	public TopicoDto toDto() {
		return new TopicoDto(id, titulo, mensaje, fechaCreacion, estado, autor.toDto(), curso.toDto(),
				respuestas.stream().map(Respuesta::toDto).collect(Collectors.toSet()));
	}

}
