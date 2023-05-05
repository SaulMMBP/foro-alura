package com.github.saulmmbp.foroAlura.dto;

import com.github.saulmmbp.foroAlura.entity.Curso;

import jakarta.validation.constraints.NotNull;

public record CursoPostRequest(
		
		@NotNull 
		String nombre,
		
		@NotNull
		String categoria
		
		) {

	public Curso toEntity() {
		Curso curso = new Curso();
		curso.setNombre(nombre);
		curso.setCategoria(categoria);
		return curso;
	}
}