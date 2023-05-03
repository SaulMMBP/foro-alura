package com.github.saulmmbp.foroAlura.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.github.saulmmbp.foroAlura.dto.TopicoDto;

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
	private LocalDate fechaCreacion;
	
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private ESTADO estado;
	
	@Column(name = "autor")
	private String autor;
	
	@Column(name = "curso")
	private String curso;
	
	public TopicoDto toDto() {
		TopicoDto topicoDto = new TopicoDto(this.id, this.titulo, this.mensaje, this.fechaCreacion,
				this.estado, this.autor, this.curso);
		return topicoDto;
	}
	
}
