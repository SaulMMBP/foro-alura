package com.github.saulmmbp.foroAlura.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.saulmmbp.foroAlura.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
