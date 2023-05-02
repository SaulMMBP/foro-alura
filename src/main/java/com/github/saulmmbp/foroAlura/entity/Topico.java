package com.github.saulmmbp.foroAlura.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "topicos")
@Data
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
	private LocalDate fechaCreación;
	
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private ESTADO estado;
	
	@Column(name = "autor")
	private String autor;
	
	@Column(name = "curso")
	private String curso;
}
