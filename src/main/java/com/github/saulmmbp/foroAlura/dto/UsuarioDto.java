package com.github.saulmmbp.foroAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.saulmmbp.foroAlura.entity.Usuario;

import jakarta.validation.constraints.NotNull;

public record UsuarioDto(
		@JsonIgnore Long id,
		@NotNull String nombre,
		@NotNull String email,
		@JsonIgnore String contrasena
		) {

	public Usuario toEntity() {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNombre(nombre);
		usuario.setContrasena(contrasena);
		return usuario;
	}
}
