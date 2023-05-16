package com.github.saulmmbp.foroAlura.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.saulmmbp.foroAlura.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<UserDetails> findByEmail(String email);

}
