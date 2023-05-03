package com.github.saulmmbp.foroAlura.entity;

import com.github.saulmmbp.foroAlura.dto.UsuarioDto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "contrasena")
	private String contrasena;
	
	public UsuarioDto toDto() {
		UsuarioDto usuarioDto = new UsuarioDto(
				id,
				nombre,
				email,
				contrasena);
		return usuarioDto;
	}
}
