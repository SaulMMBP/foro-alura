package com.github.saulmmbp.foroAlura.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.github.saulmmbp.foroAlura.dto.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "respuestas")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "autor", "topico"})
@ToString(exclude = {"topico"})
public class Respuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_respuesta")
	private Long id;
	
	@Column(name = "mensaje")
	private String mensaje;

	@Column(name = "fecha_creacion")
	@CreationTimestamp
	private LocalDateTime fechaCreacion;
	
	@Column(name = "solucion")
	private Boolean solucion = false;
	
	/* Relations */
	@ManyToOne
	@JoinColumn(name = "autor", referencedColumnName = "id_usuario")
	private Usuario autor;
	
	@ManyToOne
	@JoinColumn(name = "topico", referencedColumnName = "id_topico")
	private Topico topico;
	
	public void update(RespuestaPutRequest RespuestaReq) {
		if (RespuestaReq.mensaje() != null) {
			this.mensaje = RespuestaReq.mensaje();
		}
		if (RespuestaReq.solucion() != null) {
			this.solucion = RespuestaReq.solucion();
			if(this.solucion) {
				this.topico.setEstado(ESTADO.SOLUCIONADO);
			}
		}
	}
	
	public RespuestaResponse toResponse() {
		return new RespuestaResponse(id, mensaje, fechaCreacion, solucion, autor.toResponse(), topico.getId());
	}
}
