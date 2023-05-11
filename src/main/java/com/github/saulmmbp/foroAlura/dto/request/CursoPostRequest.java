package com.github.saulmmbp.foroAlura.dto.request;

import com.github.saulmmbp.foroAlura.entity.Curso;

import jakarta.validation.constraints.NotNull;

public record CursoPostRequest(
		
		@NotNull 
		String nombre,
		
		@NotNull
		String categoria,
		
		@NotNull
		Long instructorId
		
		) {

	public Curso toEntity() {
		Curso curso = new Curso();
		curso.setNombre(nombre);
		curso.setCategoria(categoria);
		return curso;
	}
}
