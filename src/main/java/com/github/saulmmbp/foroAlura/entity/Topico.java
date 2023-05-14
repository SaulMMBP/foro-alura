package com.github.saulmmbp.foroAlura.entity;

import java.time.LocalDateTime;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;

import com.github.saulmmbp.foroAlura.dto.request.TopicoPutRequest;
import com.github.saulmmbp.foroAlura.dto.response.TopicoResponse;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "autor", "curso"})
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
	private ESTADO estado = ESTADO.NO_RESPONDIDO;

	/* Relations */
	@ManyToOne
	@JoinColumn(name = "autor", referencedColumnName = "id_usuario")
	private Usuario autor;

	@ManyToOne
	@JoinColumn(name = "curso", referencedColumnName = "id_curso")
	private Curso curso;
	
	@OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
	private Set<Respuesta> respuestas = new HashSet<>();

	public void update(TopicoPutRequest topicoReq) {
		if (topicoReq.titulo() != null) {
			this.titulo = topicoReq.titulo();
		}
		if (topicoReq.mensaje() != null) {
			this.mensaje = topicoReq.mensaje();
		}
		if (topicoReq.estado() != null) {
			this.estado = topicoReq.estado();
		}
	}
	
	public TopicoResponse toResponse() {
		return new TopicoResponse(this);
	}

}
