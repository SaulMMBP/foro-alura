package com.github.saulmmbp.foroAlura.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.saulmmbp.foroAlura.entity.*;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Optional<Curso> findByIdAndInstructor(Long id, Usuario instructor);
}
