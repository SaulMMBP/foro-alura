package com.github.saulmmbp.foroAlura.entity;

import java.util.Set;

import com.github.saulmmbp.foroAlura.dto.request.CursoPutRequest;
import com.github.saulmmbp.foroAlura.dto.response.CursoResponse;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_curso")
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "categoria")
	private String categoria;
	
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
	private Set<Topico> topicos;
	
	@ManyToOne
	@JoinColumn(name = "instructor", referencedColumnName = "id_usuario")
	private Usuario instructor;

	public void update(CursoPutRequest cursoReq) {
		if (cursoReq.nombre() != null) {
			this.nombre = cursoReq.nombre();
		}
		if (cursoReq.categoria() != null) {
			this.categoria = cursoReq.categoria();
		}
	}

	public CursoResponse toResponse() {
		return new CursoResponse(id, nombre, categoria, instructor.toResponse());
	}
}
