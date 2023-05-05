package com.github.saulmmbp.foroAlura.entity;

import java.util.Set;

import com.github.saulmmbp.foroAlura.dto.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
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
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	private Set<Topico> topicos;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	private Set<Respuesta> respuestas;
	
	public void update(UsuarioPutRequest usuarioReq) {
		if (usuarioReq.nombre() != null) {
			this.nombre = usuarioReq.nombre();
		}
		if (usuarioReq.email() != null) {
			this.email = usuarioReq.email();
		}
		if (usuarioReq.contrasena() != null) {
			this.contrasena = usuarioReq.contrasena();
		}
	}
	
	public UsuarioResponse toResponse() {
		return new UsuarioResponse(id, nombre, email);
	}
}
