package com.github.saulmmbp.foroAlura.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.saulmmbp.foroAlura.entity.*;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	Optional<Topico> findByIdAndAutor(Long id, Usuario autor);
}
