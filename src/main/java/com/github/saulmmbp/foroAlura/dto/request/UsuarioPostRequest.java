package com.github.saulmmbp.foroAlura.dto.request;

import com.github.saulmmbp.foroAlura.entity.Usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioPostRequest(
		
		@NotBlank
		String nombre,
		
		@NotBlank
		String email,
		
		@NotBlank
		String contrasena
		
		) {

	public Usuario toEntity() {
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setContrasena(contrasena);
		return usuario;
	}
}
