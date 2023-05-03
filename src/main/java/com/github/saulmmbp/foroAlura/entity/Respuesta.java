package com.github.saulmmbp.foroAlura.entity;

import java.time.LocalDateTime;

import com.github.saulmmbp.foroAlura.dto.RespuestaDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "respuestas")
@Getter
@Setter
@ToString(exclude = {"topico"})
public class Respuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_respuesta")
	private Long id;
	
	@Column(name = "mensaje")
	private String mensaje;

	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	@Column(name = "solucion")
	private Boolean solucion;
	
	/* Relations */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "autor")
	private Usuario autor;
	
	@ManyToOne
	@JoinColumn(name = "topico")
	private Topico topico;
	
	public RespuestaDto toDto() {
		RespuestaDto respuestaDto = new RespuestaDto(
				id,
				mensaje,
				fechaCreacion,
				solucion,
				autor.toDto(),
				topico.getId()
				);
		return respuestaDto;
	}
}
