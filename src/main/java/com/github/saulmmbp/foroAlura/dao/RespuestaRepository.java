package com.github.saulmmbp.foroAlura.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.saulmmbp.foroAlura.entity.*;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

	Optional<Respuesta> findByIdAndAutor(Long id, Usuario autor);
}
