package com.github.saulmmbp.foroAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.saulmmbp.foroAlura.entity.Curso;

import jakarta.validation.constraints.NotNull;

public record CursoDto(
		@JsonIgnore Long id,
		@NotNull String nombre,
		@NotNull String categoria
		) {

	public Curso toEntity() {
		Curso curso = new Curso();
		curso.setId(id);
		curso.setNombre(nombre);
		curso.setCategoria(categoria);
		return curso;
	}
}
