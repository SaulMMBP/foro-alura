package com.github.saulmmbp.foroAlura.entity;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.saulmmbp.foroAlura.dto.request.UsuarioPutRequest;
import com.github.saulmmbp.foroAlura.dto.response.UsuarioResponse;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 8297195248518752405L;

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return this.contrasena;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
