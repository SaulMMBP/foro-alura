package com.github.saulmmbp.foroAlura.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.saulmmbp.foroAlura.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	UserDetails findByEmail(String email);

}
