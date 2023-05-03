package com.github.saulmmbp.foroAlura.entity;

import com.github.saulmmbp.foroAlura.dto.CursoDto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cursos")
@Data
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_curso")
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "categoria")
	private String categoria;
	
	public CursoDto toDto() {
		CursoDto cursoDto = new CursoDto(
				id,
				nombre,
				categoria
				);
		return cursoDto;
	}
}
